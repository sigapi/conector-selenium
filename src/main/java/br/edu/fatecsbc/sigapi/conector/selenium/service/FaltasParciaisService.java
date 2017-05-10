package br.edu.fatecsbc.sigapi.conector.selenium.service;

import java.util.Iterator;

import org.springframework.stereotype.Service;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.edu.fatecsbc.sigapi.conector.dto.Disciplina;
import br.edu.fatecsbc.sigapi.conector.dto.FaltasParciais;
import br.edu.fatecsbc.sigapi.conector.dto.RegistroFaltaParcial;
import br.edu.fatecsbc.sigapi.conector.selenium.helper.JsoupHelper;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.PaginaEnum;

@Service
public class FaltasParciaisService
    extends AbstractPaginaSimplesService<FaltasParciais> {

    @Override
    protected PaginaEnum getPagina() {
        return PaginaEnum.FALTAS_PARCIAIS;
    }

    @Override
    protected FaltasParciais extrair(final JsoupHelper helper) {

        FaltasParciais faltasParciais = null;

        final Elements elements = helper.getElementsByClass("GridClearOdd");
        if (elements != null) {

            faltasParciais = new FaltasParciais();

            final Iterator<Element> linhas = elements.iterator();

            while (linhas.hasNext()) {

                final Element linhaAtual = linhas.next();
                final Elements celulas = linhaAtual.getElementsByTag("td");

                final String sigla = celulas.get(0).text();
                final String nome = celulas.get(1).text();
                final String presencas = celulas.get(2).text();
                final String ausencias = celulas.get(3).text();

                final Disciplina disciplina = new Disciplina();
                disciplina.setSigla(sigla);
                disciplina.setNome(nome);

                final RegistroFaltaParcial registro = new RegistroFaltaParcial();
                registro.setDisciplina(disciplina);
                registro.setQuantidadeAusencias(NumberUtils.toInt(ausencias));
                registro.setQuantidadePresencas(NumberUtils.toInt(presencas));

                faltasParciais.add(registro);

            }

        }

        return faltasParciais;

    }

}
