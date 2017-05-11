package br.edu.fatecsbc.sigapi.conector.selenium.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import org.springframework.stereotype.Component;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.edu.fatecsbc.sigapi.conector.dto.DadosCadastrais;
import br.edu.fatecsbc.sigapi.conector.dto.Email;
import br.edu.fatecsbc.sigapi.conector.dto.TipoEmail;
import br.edu.fatecsbc.sigapi.conector.dto.Turno;
import br.edu.fatecsbc.sigapi.conector.selenium.helper.JsoupHelper;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.PaginaEnum;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.SigaClient;

@Component
public class DadosCadastraisService
    extends AbstractService<DadosCadastrais> {

    @Override
    protected DadosCadastrais getDados(final SigaClient client) {

        // Obtém o HTML da página de avisos (que contém os dados cadastrais)
        final String htmlAvisos = client.goAndGetSourcePage(PaginaEnum.AVISOS);

        // Obtém o HTML da página de emails
        final String htmlEmails = client.goAndGetSourcePage(PaginaEnum.EMAILS);

        // Caso tenha falhado em pegar as duas, houve algum problema, então o retorno precisa ser nulo
        if (StringUtils.isBlank(htmlAvisos) && StringUtils.isBlank(htmlEmails)) {
            return null;
        }

        return extrair(htmlAvisos, htmlEmails);

    }

    protected DadosCadastrais extrair(final String htmlAvisos, final String htmlEmails) {

        // Extrai e retorna os dados
        final DadosCadastrais dadosCadastrais = new DadosCadastrais();
        extrairDadosCadastrais(dadosCadastrais, htmlAvisos);
        extrairEmails(dadosCadastrais, htmlEmails);
        return dadosCadastrais;

    }

    private void extrairDadosCadastrais(final DadosCadastrais dadosCadastrais, final String html) {

        final JsoupHelper helper = JsoupHelper.create(html);

        if (helper == null) {
            return;
        }

        final String nome = helper.getTextFromElementById("span_MPW0039vPRO_PESSOALNOME", true);
        final String foto = helper.getImageSrcById("MPW0039FOTO");
        final String ra = helper.getTextFromElementById("span_MPW0039vACD_ALUNOCURSOREGISTROACADEMICOCURSO");
        final String instituicao = helper.getTextFromElementById("span_vUNI_UNIDADENOME_MPAGE", true);
        final String curso = helper.getTextFromElementById("span_vACD_CURSONOME_MPAGE", true);
        final String periodo = helper.getTextFromElementById("span_vACD_PERIODODESCRICAO_MPAGE");

        dadosCadastrais.setNome(nome);
        dadosCadastrais.setFoto(foto);
        dadosCadastrais.setRa(ra);
        dadosCadastrais.setInstituicao(instituicao);
        dadosCadastrais.setCurso(curso);
        dadosCadastrais.setTurno(Turno.getBySinonimo(periodo));

    }

    private void extrairEmails(final DadosCadastrais dadosCadastrais, final String html) {

        final JsoupHelper helper = JsoupHelper.create(html);

        final Elements elements = helper.getElementsByClass("GridClearUniform");
        if (elements != null) {

            final Iterator<Element> linhas = elements.iterator();

            while (linhas.hasNext()) {
                final Element linhaAtual = linhas.next();
                final Elements celulas = linhaAtual.getElementsByTag("td");

                final Element celulaEmail = celulas.get(0);
                final String email = celulaEmail.text();

                if (StringUtils.isNotBlank(email)) {

                    final Email e = new Email();
                    e.setEndereco(email);

                    final Collection<TipoEmail> tipos = new ArrayList<>();

                    tipos.add(checkTipoEmail(linhaAtual, "vPRO_PESSOALEMAILINSTITUCIONALFLAGFATEC", TipoEmail.FATEC));
                    tipos.add(checkTipoEmail(linhaAtual, "vPRO_PESSOALEMAILINSTITUCIONALFLAGETEC", TipoEmail.ETEC));
                    tipos.add(checkTipoEmail(linhaAtual, "vPRO_PESSOALEMAILPREFERENCIAL", TipoEmail.PREFERENCIAL));
                    tipos.add(checkTipoEmail(linhaAtual, "vPRO_PESSOALEMAILINSTITUCIONALFLAGWEBSAI", TipoEmail.WEBSAI));

                    tipos.stream().filter(Objects::nonNull).forEach(e::addTipoEmail);

                    dadosCadastrais.addEmail(e);

                }

            }

        }

    }

    private static final TipoEmail checkTipoEmail(final Element linha, final String name, final TipoEmail tipoEmail) {

        if (linha == null) {
            return null;
        }

        final Elements checkbox = linha.getElementsByAttributeValueStarting("name", name);
        if (checkbox == null) {
            return null;
        }

        final String valor = checkbox.val();
        if (StringUtils.equalsIgnoreCase("1", valor)) {
            return tipoEmail;
        }

        return null;

    }

}
