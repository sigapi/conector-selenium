package br.edu.fatecsbc.sigapi.conector.selenium.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Test;

import br.edu.fatecsbc.sigapi.conector.dto.DadosDesempenho;
import br.edu.fatecsbc.sigapi.conector.selenium.AbstractTest;
import br.edu.fatecsbc.sigapi.conector.selenium.test.DadosAluno;
import br.edu.fatecsbc.sigapi.conector.selenium.test.DadosAlunoProvider;

import junitparams.Parameters;

public class DadosDesempenhoServiceTest
    extends AbstractTest {

    @Autowired
    private DadosDesempenhoService service;

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
        final String html = getHtml(hash, service.getPagina());

        final DadosDesempenho result = service.extrair(html);
        assertThat(result).isNotNull();
        assertThat(result.getMaiorPrCurso()).as("%s: Maior PR do Curso", hash).isEqualTo(d.getMaiorPrCurso());
        assertThat(result.getPp()).as("%s: PP", hash).isEqualTo(d.getPp());
        assertThat(result.getPr()).as("%s: PR", hash).isEqualTo(d.getPr());

    }

}
