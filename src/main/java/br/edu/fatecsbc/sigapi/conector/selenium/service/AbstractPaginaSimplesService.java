package br.edu.fatecsbc.sigapi.conector.selenium.service;

import br.edu.fatecsbc.sigapi.conector.selenium.helper.JsoupHelper;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.PaginaEnum;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.SigaClient;

public abstract class AbstractPaginaSimplesService<T>
    extends AbstractService<T> {

    protected abstract PaginaEnum getPagina();

    protected abstract T extrair(final JsoupHelper helper);

    @Override
    protected T getDados(final SigaClient client) {

        final PaginaEnum pagina = getPagina();
        final String html = client.goAndGetSourcePage(pagina);

        return extrair(html);

    }

    protected T extrair(final String html) {

        final JsoupHelper helper = JsoupHelper.create(html);
        if (helper == null) {
            return null;
        }

        return extrair(helper);

    }

}
