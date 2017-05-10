package br.edu.fatecsbc.sigapi.conector.selenium;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import br.edu.fatecsbc.sigapi.conector.dto.Disciplina;
import br.edu.fatecsbc.sigapi.conector.selenium.dto.CredenciaisSelenium;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.PaginaEnum;

import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(classes = { ConectorSeleniumConfig.class })
@TestExecutionListeners(
    listeners = { DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
public abstract class AbstractTest {

    protected static final CredenciaisSelenium CREDENCIAIS_NULAS = null;

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    private static final Map<String, String> DISCIPLINAS = new HashMap<>();

    @BeforeClass
    public static final void init() {

        DISCIPLINAS.put("AGO007", "Gestão e Operação por Processos");
        DISCIPLINAS.put("IMH002", "Multimídia e Hipermídia");
        DISCIPLINAS.put("ISA001", "Fundamentos de Auditoria");
        DISCIPLINAS.put("ISJ002", "Sistemas de Gestão de Produção e Logística");
        DISCIPLINAS.put("ITI104", "Governança em Tecnologia da Informação");
        DISCIPLINAS.put("LIN600", "Inglês VI");
        DISCIPLINAS.put("TES001", "Estágio Supervisionado");
        DISCIPLINAS.put("TTG103", "Trabalho de Graduação II");
        DISCIPLINAS.put("TTG202", "Projeto de Trabalho de Graduação");

    }

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    protected final void assertUnsupportedOperationException(final ThrowingCallable callable) {
        // @formatter:off
        assertThatThrownBy(callable)
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("Ainda não implementado")
            .hasNoCause();
        // @formatter:onf
    }

    protected final String getArquivoClasspath(final String caminho) {

        final URL url = Resources.getResource(caminho);

        try {
            return Resources.toString(url, Charsets.UTF_8);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    protected final String getHtml(final String hash, final PaginaEnum pagina) {

        final String caminho = String.format("coletor/%s/%s", hash, pagina.name());
        return getArquivoClasspath(caminho);

    }

    protected final void assertDisciplina(final Disciplina d) {

        assertThat(d).isNotNull();

        final String sigla = d.getSigla();
        final String nome = d.getNome();

        assertThat(sigla).isNotBlank();
        assertThat(nome).isNotBlank();

        final String nomeEsperado = DISCIPLINAS.get(sigla);
        assertThat(nomeEsperado) //
            .as("Disciplina desconhecida: %s", sigla).isNotNull() // Mapeamento
            .as("Disciplina com nome diferente: %s", sigla).isEqualToIgnoringCase(nome);

    }

}