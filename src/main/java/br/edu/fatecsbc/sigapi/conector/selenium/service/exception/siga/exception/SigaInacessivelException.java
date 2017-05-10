package br.edu.fatecsbc.sigapi.conector.selenium.service.exception.siga.exception;

public class SigaInacessivelException
    extends SigaClientException {

    private static final long serialVersionUID = 1L;

    public SigaInacessivelException(final String message) {
        super(message);
    }

    public SigaInacessivelException(final String message, final Throwable cause) {
        super(message, cause);
    }

}