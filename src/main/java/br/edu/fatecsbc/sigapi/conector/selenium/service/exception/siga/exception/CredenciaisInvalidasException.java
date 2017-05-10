package br.edu.fatecsbc.sigapi.conector.selenium.service.exception.siga.exception;

public class CredenciaisInvalidasException
    extends SigaClientException {

    private static final long serialVersionUID = 1L;

    public CredenciaisInvalidasException() {
        super("Credenciais inválidas");
    }

}