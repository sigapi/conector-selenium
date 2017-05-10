package br.edu.fatecsbc.sigapi.conector.selenium.service;

import org.springframework.stereotype.Service;

import br.edu.fatecsbc.sigapi.conector.dto.Historico;
import br.edu.fatecsbc.sigapi.conector.selenium.helper.JsoupHelper;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.PaginaEnum;

@Service
public class HistoricoService
    extends AbstractPaginaSimplesService<Historico> {

    @Override
    protected PaginaEnum getPagina() {
        return PaginaEnum.HISTORICO;
    }

    @Override
    protected Historico extrair(final JsoupHelper helper) {
        throw new UnsupportedOperationException("Ainda não implementado");
    }

}
