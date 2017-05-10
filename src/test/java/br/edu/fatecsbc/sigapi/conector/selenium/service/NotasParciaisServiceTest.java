package br.edu.fatecsbc.sigapi.conector.selenium.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.assertj.core.api.iterable.Extractor;
import org.junit.Test;

import br.edu.fatecsbc.sigapi.conector.dto.AvaliacaoParcial;
import br.edu.fatecsbc.sigapi.conector.dto.Disciplina;
import br.edu.fatecsbc.sigapi.conector.dto.NotasParciais;
import br.edu.fatecsbc.sigapi.conector.dto.RegistroNotaParcial;
import br.edu.fatecsbc.sigapi.conector.selenium.AbstractTest;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.PaginaEnum;
import br.edu.fatecsbc.sigapi.conector.selenium.test.DadosAluno;
import br.edu.fatecsbc.sigapi.conector.selenium.test.DadosAluno.MediaParcial;
import br.edu.fatecsbc.sigapi.conector.selenium.test.DadosAluno.NotaParcial;
import br.edu.fatecsbc.sigapi.conector.selenium.test.DadosAlunoProvider;

import junitparams.Parameters;

public class NotasParciaisServiceTest
    extends AbstractTest {

    @Autowired
    private NotasParciaisService service;

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
        final String html = getHtml(hash, PaginaEnum.NOTAS_PARCIAIS);

        final NotasParciais result = service.extrair(html);
        assertThat(result).isNotNull();

        final List<NotaParcial> notasEsperadas = d.getNotasParciais();
        final List<MediaParcial> mediasEsperadas = d.getMediasParciais();

        final Collection<RegistroNotaParcial> registros = result.getRegistros();
        assertThat(registros).isNotNull();
        result.getRegistros().forEach(r -> assertDisciplina(r.getDisciplina()));

        if (notasEsperadas != null) {

            final NotaParcial[] array = notasEsperadas.stream().toArray(NotaParcial[]::new);

            assertThat(registros) //
                .flatExtracting(new RegistroNotaParcialToNotaParcialExtractor()) //
                .as("%s: Notas", hash).contains(array);

        }

        if (mediasEsperadas != null) {

            assertThat(registros) //
                .flatExtracting(new RegistroNotaParcialToMediaParcialExtractor()) //
                .as("%s: Médias", hash).containsAll(mediasEsperadas);

        }

    }

    private static class RegistroNotaParcialToNotaParcialExtractor
        implements Extractor<RegistroNotaParcial, List<NotaParcial>> {

        @Override
        public List<NotaParcial> extract(final RegistroNotaParcial input) {

            if (input == null) {
                return null;
            }

            final Collection<AvaliacaoParcial> avaliacoes = input.getAvaliacoes();
            final Disciplina disciplina = input.getDisciplina();

            if (avaliacoes == null) {
                return Collections.emptyList();
            }

            return avaliacoes.stream() //
                .map(a -> new NotaParcial(disciplina.getSigla(), a.getTipo(), a.getNota())) //
                .collect(Collectors.toList());

        }

    }

    private static class RegistroNotaParcialToMediaParcialExtractor
        implements Extractor<RegistroNotaParcial, MediaParcial> {

        @Override
        public MediaParcial extract(final RegistroNotaParcial input) {

            if (input == null) {
                return null;
            }

            final String sigla = input.getDisciplina().getSigla();
            final float mediaFinal = input.getMediaFinal();
            final int quantidadeFaltas = input.getQuantidadeFaltas();
            final float percentualFrequencia = input.getPercentualFrequencia();

            return new MediaParcial(sigla, mediaFinal, percentualFrequencia, quantidadeFaltas);

        }

    }

}
