package br.edu.fatecsbc.sigapi.conector.selenium;

import java.util.Map;

import br.edu.fatecsbc.sigapi.conector.CredenciaisBuilder;
import br.edu.fatecsbc.sigapi.conector.selenium.dto.CredenciaisSelenium;

public class CredenciaisSeleniumBuilder
    extends CredenciaisBuilder<CredenciaisSelenium> {

    private static final String PARAMETRO_USUARIO = "USUARIO";
    private static final String PARAMETRO_SENHA = "SENHA";

    @Override
    public CredenciaisSelenium build() {

        final Map<String, String> parametros = getParametros();
        final String usuario = parametros.get(PARAMETRO_USUARIO);
        final String senha = parametros.get(PARAMETRO_SENHA);

        return new CredenciaisSelenium(usuario, senha);

    }

    public CredenciaisSelenium build(final String usuario, final String senha) {
        return credenciais(usuario, senha).build();
    }

    public CredenciaisSeleniumBuilder usuario(final String usuario) {
        adicionar(PARAMETRO_USUARIO, usuario);
        return this;
    }

    public CredenciaisSeleniumBuilder senha(final String senha) {
        adicionar(PARAMETRO_SENHA, senha);
        return this;
    }

    public CredenciaisSeleniumBuilder credenciais(final String usuario, final String senha) {
        return usuario(usuario).senha(senha);
    }

}
