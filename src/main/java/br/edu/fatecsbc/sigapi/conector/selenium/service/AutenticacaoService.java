package br.edu.fatecsbc.sigapi.conector.selenium.service;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;

import br.edu.fatecsbc.sigapi.conector.selenium.dto.CredenciaisSelenium;
import br.edu.fatecsbc.sigapi.conector.selenium.service.exception.siga.exception.SigaClientException;
import br.edu.fatecsbc.sigapi.conector.selenium.service.siga.SigaClient;

@Service
public class AutenticacaoService {

    private static final Logger log = LoggerFactory.getLogger(AutenticacaoService.class);

    public boolean autenticar(final CredenciaisSelenium credenciais) {

        SigaClient client = null;
        try {
            client = conectar(credenciais);
        } finally {
            if (client != null) {
                client.quit();
            }
        }

        return client != null;

    }

    protected SigaClient conectar(final CredenciaisSelenium credenciais) {

        if (credenciais == null) {
            log.warn("Credenciais não informadas");
            return null;
        }

        final String usuario = credenciais.getUsuario();
        final String senha = credenciais.getSenha();

        return conectar(usuario, senha);

    }

    private SigaClient conectar(final String usuario, final String senha) {

        if (StringUtils.isAnyBlank(usuario, senha)) {

            log.warn("Usuário ou senha em brancos");
            return null;

        }

        log.info("Tentando conexão com o usuário '{}'", usuario);
        final SigaClient client = new SigaClient();

        try {
            client.login(usuario, senha);
            return client;
        } catch (final SigaClientException e) {
            log.error("Erro conectando com o usuario {}", usuario, e);
        }

        return null;

    }

}
