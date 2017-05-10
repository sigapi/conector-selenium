package br.edu.fatecsbc.sigapi.conector.selenium.service;

import org.springframework.stereotype.Service;

import br.edu.fatecsbc.sigapi.conector.dto.CalendarioProvas;
import br.edu.fatecsbc.sigapi.conector.selenium.helper.JsoupHelper;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.PaginaEnum;

@Service
public class CalendarioProvasService
    extends AbstractPaginaSimplesService<CalendarioProvas> {

    @Override
    protected PaginaEnum getPagina() {
        return PaginaEnum.CALENDARIO_PROVAS;
    }

    @Override
    protected CalendarioProvas extrair(final JsoupHelper helper) {
        throw new UnsupportedOperationException("Ainda não implementado");
    }

}
