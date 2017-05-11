package br.edu.fatecsbc.sigapi.conector.selenium.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Test;

import br.edu.fatecsbc.sigapi.conector.dto.DadosCadastrais;
import br.edu.fatecsbc.sigapi.conector.dto.Email;
import br.edu.fatecsbc.sigapi.conector.dto.TipoEmail;
import br.edu.fatecsbc.sigapi.conector.dto.Turno;
import br.edu.fatecsbc.sigapi.conector.selenium.AbstractTest;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.PaginaEnum;
import br.edu.fatecsbc.sigapi.conector.selenium.test.DadosAluno;
import br.edu.fatecsbc.sigapi.conector.selenium.test.DadosAlunoProvider;

import junitparams.Parameters;

public class DadosCadastraisServiceTest
    extends AbstractTest {

    @Autowired
    private DadosCadastraisService service;

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
        final String htmlAvisos = getHtml(hash, PaginaEnum.AVISOS);
        final String htmlEmails = getHtml(hash, PaginaEnum.EMAILS);

        final DadosCadastrais result = service.extrair(htmlAvisos, htmlEmails);
        assertThat(result).isNotNull();

        assertThat(result.getNome()).as("%s: Nome", hash).isEqualTo(d.getNome());

        assertThat(result.getFoto()).as("%s: Foto", hash).isEqualTo(d.getFoto());

        assertThat(result.getRa()).as("%s: RA", hash).isEqualTo(d.getRa());

        assertThat(result.getInstituicao()).as("%s: Instituição", hash).isEqualTo(d.getInstituicao());

        assertThat(result.getCurso()).as("%s: Curso", hash).isEqualTo(d.getCurso());

        final Turno turno = result.getTurno();
        assertThat(turno).isNotNull();
        assertThat(turno.name()).as("%s: Turno", hash).isEqualToIgnoringCase(d.getPeriodo());

        final List<String> emailsEsperados = d.getEmails();
        if (emailsEsperados != null) {

            final List<Email> emails = result.getEmails();

            assertThat(emails) //
                .as("%s: Quantidade de emails", hash).hasSameSizeAs(emailsEsperados) // Tamanho
                .extracting(e -> e.getEndereco()).as("%s: Emails", hash).containsExactlyElementsOf(emailsEsperados); // Conteúdo

            assertTipoEmail(hash, emails, TipoEmail.ETEC, d.getEmailEtec());
            assertTipoEmail(hash, emails, TipoEmail.FATEC, d.getEmailFatec());
            assertTipoEmail(hash, emails, TipoEmail.PREFERENCIAL, d.getEmailPreferencial());
            assertTipoEmail(hash, emails, TipoEmail.WEBSAI, d.getEmailWebsai());

        }

    }

    private static final void assertTipoEmail(final String hash, final List<Email> emails, final TipoEmail tipoEmail,
        final int indice) {

        if (indice >= 0) {

            // Verifica se possui emails
            assertThat(emails).as("%s: Tipo de email", hash).isNotEmpty();

            // Verifica se o índice é válido
            if (indice >= emails.size()) {
                fail("%s: Tipo de email esperado, mas a posição indicada não existe", hash);
            }

            // Verifica o email indicado no índice
            final Email email = emails.get(indice);
            assertThat(email).as("%s: Tipo de email (%s)", hash, tipoEmail).isNotNull();
            assertThat(email.getTiposEmail()).as("%s: Tipo de email (%s)", hash, tipoEmail).contains(tipoEmail);

        } else if (emails != null) {

            // Verifica se não está presente
            for (final Email email : emails) {
                assertThat(email).as("%s: Tipo de Email (%s)", hash, tipoEmail.name())
                    .extracting(e -> e.getTiposEmail()).doesNotContain(tipoEmail);
            }

        }

    }

}
