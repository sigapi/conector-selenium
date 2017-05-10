package br.edu.fatecsbc.sigapi.conector.selenium.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.edu.fatecsbc.sigapi.conector.selenium.dto.CredenciaisSelenium;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.SigaClient;

public abstract class AbstractService<T> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AutenticacaoService autenticacaoService;

    protected abstract T getDados(SigaClient client);

    public T getDados(final CredenciaisSelenium credenciais) {

        SigaClient client = null;
        try {
            client = autenticacaoService.conectar(credenciais);
            if (client != null) {
                return getDados(client);
            }
        } finally {
            if (client != null) {
                client.quit();
            }
        }

        return null;

    }

}
