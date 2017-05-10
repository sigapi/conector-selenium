package br.edu.fatecsbc.sigapi.conector.selenium.service.exception.siga.exception;

public class SigaClientException
    extends Exception {

    private static final long serialVersionUID = 1L;

    public SigaClientException() {
        super();
    }

    public SigaClientException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SigaClientException(final String message) {
        super(message);
    }

    public SigaClientException(final Throwable cause) {
        super(cause);
    }

}
