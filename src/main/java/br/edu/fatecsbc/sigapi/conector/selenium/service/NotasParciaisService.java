package br.edu.fatecsbc.sigapi.conector.selenium.service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.edu.fatecsbc.sigapi.conector.dto.Disciplina;
import br.edu.fatecsbc.sigapi.conector.dto.NotasParciais;
import br.edu.fatecsbc.sigapi.conector.dto.RegistroNotaParcial;
import br.edu.fatecsbc.sigapi.conector.selenium.helper.JsoupHelper;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.PaginaEnum;

@Service
public class NotasParciaisService
    extends AbstractPaginaSimplesService<NotasParciais> {

    @Override
    protected PaginaEnum getPagina() {
        return PaginaEnum.NOTAS_PARCIAIS;
    }

    @Override
    protected NotasParciais extrair(final JsoupHelper helper) {

        NotasParciais notasParciais = null;

        final Element tabela = helper.getElementById("Grid3ContainerTbl");
        if (tabela != null) {

            notasParciais = new NotasParciais();

            final Elements linhasDisciplinas = tabela.getElementsByAttributeValueStarting("id", "Grid3ContainerRow");
            final Iterator<Element> iteratorLinhasDisciplinas = linhasDisciplinas.iterator();

            while (iteratorLinhasDisciplinas.hasNext()) {

                // Obtém a linha da disciplina
                Element linhaDisciplina = iteratorLinhasDisciplinas.next();
                final String idLinhaDisciplina = linhaDisciplina.id();

                // Atualiza a linha da disciplina pelo ID
                linhaDisciplina = helper.getElementById(idLinhaDisciplina);

                // Obtém as linhas de cada item
                final Element linhaMedia = linhaDisciplina.nextElementSibling();
                final Element linhaQuantidadeFaltas = linhaMedia.nextElementSibling();
                final Element linhaFrequencia = linhaQuantidadeFaltas.nextElementSibling();
                final Element linhaResultados = linhaFrequencia.nextElementSibling();

                // Dados da disciplinas
                final Element celulaDisciplina = linhaDisciplina.child(1);
                final String siglaDisciplina = celulaDisciplina.child(0).text();
                final String nomeDisciplina = celulaDisciplina.child(1).text();

                // Outros dados
                final String media = linhaMedia.child(1).text();
                final String quantidadeFaltas = linhaQuantidadeFaltas.child(1).child(0).text();
                final String frequencia = linhaFrequencia.child(1).text();

                final Disciplina d = new Disciplina();
                d.setSigla(siglaDisciplina);
                d.setNome(nomeDisciplina);

                final RegistroNotaParcial r = new RegistroNotaParcial();
                r.setDisciplina(d);
                r.setMediaFinal(toFloat(media, 0));
                r.setPercentualFrequencia(toFloat(frequencia, 0));
                r.setQuantidadeFaltas(NumberUtils.toInt(quantidadeFaltas));

                extrairAvaliacoes(linhaResultados, r);
                notasParciais.addRegistro(r);

            }

        }

        return notasParciais;
    }

    private void extrairAvaliacoes(final Element linhaResultados, final RegistroNotaParcial r) {

        final Elements linhasResultado = linhaResultados.getElementsByAttributeValueStarting("id", "Grid2Container_");

        final Iterator<Element> iterator = linhasResultado.iterator();

        while (iterator.hasNext()) {

            final Element next = iterator.next();

            if (!"tr".equalsIgnoreCase(next.tagName())) {
                continue;
            }

            final Elements eTitulo;
            eTitulo = next.getElementsByAttributeValueStarting("id", "span_CTLACD_PLANOENSINOAVALIACAOTITULO");

            final Elements eNota;
            eNota = next.getElementsByAttributeValueStarting("id", "span_CTLACD_PLANOENSINOAVALIACAOPARCIALNOTA");

            final String titulo = eTitulo.text();
            final String nota = eNota.text();

            r.addAvaliacao(ajustaTipo(titulo), toFloat(nota, -1));

        }
    }

    private static final float toFloat(final String s, final float defaultValue) {

        if (StringUtils.isNotBlank(s)) {

            final Locale locale = new Locale("pt", "BR");

            Number parse;
            try {
                parse = NumberFormat.getNumberInstance(locale).parse(s);
                if (parse != null) {
                    return parse.floatValue();
                }
            } catch (final ParseException e) {
                //
            }

        }

        return defaultValue;

    }

    private static final String ajustaTipo(final String s) {

        String tmp = StringUtils.trimToNull(s);
        if (tmp != null) {

            final char last = tmp.charAt(tmp.length() - 1);

            // Provas
            if (Arrays.asList('1', '2', '3').contains(last) && StringUtils.containsIgnoreCase(tmp, "P" + last)) {
                return "Avaliação Oficial - P" + last;
            }

            // Trabalhos
            if (Arrays.asList("trabalho", "trabalhos", "avaliação t").contains(tmp.toLowerCase())) {
                return "Trabalho";
            }

            // Conversão para primeiras letras maiúsculas, mas substituindo algumas palavras
            tmp = WordUtils.capitalizeFully(tmp);
            tmp = StringUtils.replace(tmp, " De ", " de ");

            return tmp;

        }

        return null;

    }

}
