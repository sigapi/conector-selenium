package br.edu.fatecsbc.sigapi.conector.selenium.service.siga;

import org.openqa.selenium.By;

public enum PaginaEnum {

    AVISOS("Avisos", "home.aspx"),

    EMAILS(By.name("Embpage1"), AVISOS),

    CALENDARIO_PROVAS("Calendário de Provas", "calendarioprovas.aspx"),

    FALTAS_PARCIAIS("Faltas Parciais", "faltasparciais.aspx"),

    HISTORICO_GRADE("Histórico (Grade)", "historicograde.aspx"),

    HISTORICO_COMPLETO("Histórico Completo", "historicocompleto.aspx"),

    HISTORICO("Histórico", "historico.aspx"),

    HORARIO("Horário", "horario.aspx"),

    NOTAS_PARCIAIS("Notas Parciais", "notasparciais.aspx");

    private final By frame;
    private final PaginaEnum parentPage;
    private final String label;
    private final String url;

    private PaginaEnum(final By frame, final PaginaEnum parentPage) {
        this(frame, parentPage, null, null);
    }

    private PaginaEnum(final String label, final String url) {
        this(null, null, label, url);
    }

    private PaginaEnum(final By frame, final PaginaEnum parentPage, final String label, final String url) {
        this.frame = frame;
        this.parentPage = parentPage;
        this.label = label;
        this.url = url;
    }

    public By getFrame() {
        return frame;
    }

    public PaginaEnum getParentPage() {
        return parentPage;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public boolean isFrame() {
        return frame != null;
    }

}