package br.edu.fatecsbc.sigapi.conector.selenium.service;

import org.springframework.stereotype.Service;

import br.edu.fatecsbc.sigapi.conector.dto.DadosDesempenho;
import br.edu.fatecsbc.sigapi.conector.selenium.helper.JsoupHelper;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.PaginaEnum;

@Service
public class DadosDesempenhoService
    extends AbstractPaginaSimplesService<DadosDesempenho> {

    @Override
    protected PaginaEnum getPagina() {
        return PaginaEnum.AVISOS;
    }

    @Override
    protected DadosDesempenho extrair(final JsoupHelper helper) {

        // PP
        final float pp = helper.getTextAsFloatFromElementById("span_MPW0039vACD_ALUNOCURSOINDICEPP");

        // PR
        final float pr = helper.getTextAsFloatFromElementById("span_MPW0039vACD_ALUNOCURSOINDICEPR");

        // Maior PR do Curso
        final float maiorPrCurso = helper.getTextAsFloatFromElementById("span_MPW0039vMAX_ACD_ALUNOCURSOINDICEPR");

        final DadosDesempenho dados = new DadosDesempenho();

        dados.setPp(pp);
        dados.setPr(pr);
        dados.setMaiorPrCurso(maiorPrCurso);

        return dados;

    }

}
