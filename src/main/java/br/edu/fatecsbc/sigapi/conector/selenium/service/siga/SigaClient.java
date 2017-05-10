package br.edu.fatecsbc.sigapi.conector.selenium.service.siga;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;

import br.edu.fatecsbc.sigapi.conector.selenium.service.exception.siga.exception.CredenciaisInvalidasException;
import br.edu.fatecsbc.sigapi.conector.selenium.service.exception.siga.exception.SigaClientException;
import br.edu.fatecsbc.sigapi.conector.selenium.service.exception.siga.exception.SigaInacessivelException;
import br.edu.fatecsbc.sigapi.conector.selenium.service.exception.siga.exception.TempoEsgotadoException;

public class SigaClient
    extends JBrowserDriver {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int DEFAULT_TIMEOUT = 120;
    private static final int DEFAULT_IMPLICITY_TIMEOUT = DEFAULT_TIMEOUT;

    private static final String URL_LOGIN = "https://www.sigacentropaulasouza.com.br/aluno/login.aspx";

    private static final String PAGE_HOME = "home.aspx";

    private static final String ELEMENT_ID_LOGIN_INPUT_SENHA = "vSIS_USUARIOSENHA";
    private static final String ELEMENT_ID_LOGIN_INPUT_USUARIO = "vSIS_USUARIOID";
    private static final String ELEMENT_NAME_LOGIN_BT_CONFIRMA = "BTCONFIRMA";

    private static final String ELEMENT_ID_LOGIN_TXT_ERROR = "gxErrorViewer";

    private static final String ERROR_MESSAGE_LOGIN = "Não confere Login e Senha";

    private static final ExpectedCondition<Boolean> ERROR_CONDITION_LOGIN = ExpectedConditions
        .textToBe(By.id(ELEMENT_ID_LOGIN_TXT_ERROR), ERROR_MESSAGE_LOGIN);

    private static final ExpectedCondition<Boolean> CONDITION_PAGE_IS_HOME = ExpectedConditions.urlContains(PAGE_HOME);

    private static final ExpectedCondition<Boolean> CONDITION_AFTER_LOGIN = ExpectedConditions
        .or(CONDITION_PAGE_IS_HOME, ERROR_CONDITION_LOGIN);

    private final int implicityTimeout;
    private PaginaEnum currentPage;
    private boolean logged = false;

    public SigaClient() {
        this(DEFAULT_IMPLICITY_TIMEOUT);
    }

    public SigaClient(final int implicityTimeout) {
        super(createSettings());
        this.implicityTimeout = implicityTimeout;
        turnOnImplicitWaits();
    }

    private static Settings createSettings() {
        // @formatter:off
        return Settings.builder()
            .cache(false)
            .logger(null)
            .build();
        // @formatter:on
    }

    public void login(final String usuario, final String senha) throws SigaClientException {

        currentPage = null;
        go(URL_LOGIN);

        if (!isElementVisibleById(ELEMENT_ID_LOGIN_INPUT_USUARIO)) {
            throw new SigaInacessivelException("Não foi possível acessar a página inicial");
        }

        sendKeysElementById(ELEMENT_ID_LOGIN_INPUT_USUARIO, usuario);
        sendKeysElementById(ELEMENT_ID_LOGIN_INPUT_SENHA, senha);
        clickElementByName(ELEMENT_NAME_LOGIN_BT_CONFIRMA);

        try {
            wait(CONDITION_AFTER_LOGIN);
        } catch (final TempoEsgotadoException e) {
            throw new SigaInacessivelException("Erro inesperado após o login", e);
        }

        if (ERROR_CONDITION_LOGIN.apply(this)) {
            throw new CredenciaisInvalidasException();
        }

        currentPage = PaginaEnum.AVISOS;
        logged = true;

    }

    public void go(final PaginaEnum newPage) throws SigaInacessivelException {

        if (!logged) {
            throw new IllegalStateException("Não está logado");
        }

        if (currentPage.isFrame()) {
            switchTo().defaultContent();
        }

        PaginaEnum destination = newPage;
        if (newPage.isFrame()) {
            destination = newPage.getParentPage();
        }

        if (currentPage != destination) {

            // Clica no link
            findElementByLinkText(destination.getLabel()).click();
            try {
                // Aguarda o carregamento da URL
                wait(ExpectedConditions.urlContains(newPage.getUrl()));
            } catch (final TempoEsgotadoException e) {
                throw new SigaInacessivelException("Erro inesperado indo para a página " + newPage.name(), e);
            }

        }

        if (newPage.isFrame()) {

            try {
                wait(ExpectedConditions.frameToBeAvailableAndSwitchToIt(newPage.getFrame()));
            } catch (final TempoEsgotadoException e) {
                throw new SigaInacessivelException(
                    "Erro inesperado tentando obter o conteúdo de um frame da página " + newPage.name(), e);
            }

        }

        currentPage = newPage;

    }

    public String goAndGetSourcePage(final PaginaEnum pagina) {

        try {
            go(pagina);
            return getPageSource();
        } catch (final SigaInacessivelException e) {
            logger.warn("Não foi possível obter o código fonte da página {}", pagina.name(), e);
        }

        return null;

    }

    public void wait(final ExpectedCondition<?> condition) throws TempoEsgotadoException {
        wait(condition, DEFAULT_TIMEOUT);
    }

    public void wait(final ExpectedCondition<?> condition, final int timeout) throws TempoEsgotadoException {

        final WebDriverWait wait = new WebDriverWait(this, timeout);
        turnOffImplicitWaits();
        try {
            wait.until(condition);
        } catch (final Exception e) {
            throw new TempoEsgotadoException(e);
        } finally {
            turnOnImplicitWaits();
        }

    }

    public void sendKeysElementById(final String id, final String keys) {
        findElement(By.id(id)).sendKeys(keys);
    }

    public void clickElementByName(final String name) {
        findElement(By.name(name)).click();
    }

    public void go(final String url) {
        navigate().to(url);
    }

    public boolean isElementVisibleById(final String id) {
        return isElementVisible(By.id(id));
    }

    public boolean isElementVisible(final By by) {

        turnOffImplicitWaits();
        try {
            return ExpectedConditions.visibilityOfElementLocated(by).apply(this) != null;
        } catch (final NoSuchElementException e) {
            return false;
        } finally {
            turnOnImplicitWaits();
        }

    }

    @Override
    public void quit() {
        logged = false;
        try {
            super.quit();
        } catch (final WebDriverException e) {}
    }

    private void turnOffImplicitWaits() {
        updateImplicityTimeout(0);
    }

    private void turnOnImplicitWaits() {
        updateImplicityTimeout(implicityTimeout);
    }

    private void updateImplicityTimeout(final int newTimeout) {
        manage().timeouts().implicitlyWait(newTimeout, TimeUnit.SECONDS);
    }

}
