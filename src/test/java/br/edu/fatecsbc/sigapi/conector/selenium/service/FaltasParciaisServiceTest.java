package br.edu.fatecsbc.sigapi.conector.selenium.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import br.edu.fatecsbc.sigapi.conector.dto.FaltasParciais;
import br.edu.fatecsbc.sigapi.conector.dto.RegistroFaltaParcial;
import br.edu.fatecsbc.sigapi.conector.selenium.AbstractTest;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.PaginaEnum;
import br.edu.fatecsbc.sigapi.conector.selenium.test.DadosAluno;
import br.edu.fatecsbc.sigapi.conector.selenium.test.DadosAluno.FaltaParcial;
import br.edu.fatecsbc.sigapi.conector.selenium.test.DadosAlunoProvider;

import junitparams.Parameters;

public class FaltasParciaisServiceTest
    extends AbstractTest {

    @Autowired
    private FaltasParciaisService service;

    @Test
    public void testAutowiring() {
        assertThat(service).isNotNull();
    }

    @Test
    public void testGetDadosSemCredenciais() {
        assertThat(service.getDados(CREDENCIAIS_NULAS)).isNull();
    }

    @Test
    @Parameters(source = DadosAlunoProvider.class)
    public void extrair(final DadosAluno d) {

        final String hash = d.getHash();
        final String html = getHtml(hash, PaginaEnum.FALTAS_PARCIAIS);

        final FaltasParciais result = service.extrair(html);

        final List<FaltaParcial> faltasParciaisEsperadas = d.getFaltasParciais();
        if (faltasParciaisEsperadas != null) {

            final Collection<RegistroFaltaParcial> registros = result.getRegistros();

            // Quantidade de registros
            assertThat(registros).as("%s: Quantidade de registros", hash).hasSameSizeAs(faltasParciaisEsperadas);

            // Registros de faltas
            final Tuple[] tuplas = faltasParciaisEsperadas.stream()
                .map(f -> tuple(f.getSigla(), f.getPresencas(), f.getAusencias())).toArray(Tuple[]::new);

            assertThat(registros).extracting("disciplina.sigla", "quantidadePresencas", "quantidadeAusencias")
                .as("%s: Registros", hash).contains(tuplas);

        }

        result.getRegistros().forEach(r -> assertDisciplina(r.getDisciplina()));

    }

}
