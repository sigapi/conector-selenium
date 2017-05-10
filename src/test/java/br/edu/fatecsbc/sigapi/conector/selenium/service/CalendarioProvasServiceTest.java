package br.edu.fatecsbc.sigapi.conector.selenium.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Ignore;
import org.junit.Test;

import br.edu.fatecsbc.sigapi.conector.selenium.AbstractTest;

public class CalendarioProvasServiceTest
    extends AbstractTest {

    @Autowired
    private CalendarioProvasService service;

    @Test
    public void testAutowiring() {
        assertThat(service).isNotNull();
    }

    @Test
    public void testGetDadosSemCredenciais() {
        assertThat(service.getDados(CREDENCIAIS_NULAS)).isNull();
    }

    @Test
    @Ignore
    public void extrair() {
        assertUnsupportedOperationException(() -> service.extrair(""));
    }

}
