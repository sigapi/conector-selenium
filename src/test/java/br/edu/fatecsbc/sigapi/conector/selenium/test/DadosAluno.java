package br.edu.fatecsbc.sigapi.conector.selenium.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class DadosAluno {

    private String hash;

    private String nome;
    private String foto;
    private String ra;
    private String instituicao;
    private String curso;
    private String periodo;

    private float pp;
    private float pr;
    private float maiorPrCurso;

    private List<String> emails = null;
    private int emailFatec = -1;
    private int emailEtec = -1;
    private int emailPreferencial = -1;
    private int emailWebsai = -1;

    private List<FaltaParcial> faltasParciais = null;
    private List<MediaParcial> mediasParciais = null;
    private List<NotaParcial> notasParciais = null;

    public String getHash() {
        return hash;
    }

    public void setHash(final String hash) {
        this.hash = hash;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(final String foto) {
        this.foto = foto;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(final String ra) {
        this.ra = ra;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(final String instituicao) {
        this.instituicao = instituicao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(final String curso) {
        this.curso = curso;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(final String periodo) {
        this.periodo = periodo;
    }

    public float getPp() {
        return pp;
    }

    public void setPp(final float pp) {
        this.pp = pp;
    }

    public float getPr() {
        return pr;
    }

    public void setPr(final float pr) {
        this.pr = pr;
    }

    public float getMaiorPrCurso() {
        return maiorPrCurso;
    }

    public void setMaiorPrCurso(final float maiorPrCurso) {
        this.maiorPrCurso = maiorPrCurso;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void addEmail(final String s) {
        if (StringUtils.isNotBlank(s)) {
            if (emails == null) {
                emails = new ArrayList<>();
            }
            emails.add(s);
        }
    }

    public int getEmailFatec() {
        return emailFatec;
    }

    public void setEmailFatec(final int emailFatec) {
        this.emailFatec = emailFatec;
    }

    public int getEmailEtec() {
        return emailEtec;
    }

    public void setEmailEtec(final int emailEtec) {
        this.emailEtec = emailEtec;
    }

    public int getEmailPreferencial() {
        return emailPreferencial;
    }

    public void setEmailPreferencial(final int emailPreferencial) {
        this.emailPreferencial = emailPreferencial;
    }

    public int getEmailWebsai() {
        return emailWebsai;
    }

    public void setEmailWebsai(final int emailWebsai) {
        this.emailWebsai = emailWebsai;
    }

    public List<FaltaParcial> getFaltasParciais() {
        return faltasParciais;
    }

    public void addFalta(final String sigla, final int presencas, final int ausencias) {
        if (StringUtils.isNotBlank(sigla)) {
            if (faltasParciais == null) {
                faltasParciais = new ArrayList<>();
            }
            faltasParciais.add(new FaltaParcial(sigla, presencas, ausencias));
        }
    }

    public void addFalta(final String sigla) {
        addFalta(sigla, 0, 0);
    }

    public List<MediaParcial> getMediasParciais() {
        return mediasParciais;
    }

    public void addMediaParcial(final String sigla, final float media, final float frequencia,
        final int quantidadeFaltas) {
        if (StringUtils.isNotBlank(sigla)) {
            if (mediasParciais == null) {
                mediasParciais = new ArrayList<>();
            }
            mediasParciais.add(new MediaParcial(sigla, media, frequencia, quantidadeFaltas));
        }
    }

    public List<NotaParcial> getNotasParciais() {
        return notasParciais;
    }

    public void addNotaP1(final String sigla, final float nota) {
        addNotaParcial(sigla, "Avaliação Oficial - P1", nota);
    }

    public void addNotaP2(final String sigla, final float nota) {
        addNotaParcial(sigla, "Avaliação Oficial - P2", nota);
    }

    public void addNotaP3(final String sigla, final float nota) {
        addNotaParcial(sigla, "Avaliação Oficial - P3", nota);
    }

    public void addNotaTrabalho(final String sigla, final float nota) {
        addNotaParcial(sigla, "Trabalho", nota);
    }

    public void addNotaParcial(final String sigla, final String tipo, final float nota) {
        if (StringUtils.isNoneBlank(sigla, tipo) && nota >= 0) {
            if (notasParciais == null) {
                notasParciais = new ArrayList<>();
            }

            notasParciais.add(new NotaParcial(sigla, tipo, nota));
        }
    }

    public static final class FaltaParcial {

        private final String sigla;
        private final int presencas;
        private final int ausencias;

        public FaltaParcial(final String sigla, final int presencas, final int ausencias) {
            this.sigla = sigla;
            this.presencas = presencas;
            this.ausencias = ausencias;
        }

        public String getSigla() {
            return sigla;
        }

        public int getPresencas() {
            return presencas;
        }

        public int getAusencias() {
            return ausencias;
        }

    }

    public static final class MediaParcial {

        private final String sigla;
        private final float media;
        private final float frequencia;
        private final int quantidadeFaltas;

        public MediaParcial(final String sigla, final float media, final float frequencia, final int quantidadeFaltas) {
            this.sigla = sigla;
            this.media = media;
            this.frequencia = frequencia;
            this.quantidadeFaltas = quantidadeFaltas;
        }

        public String getSigla() {
            return sigla;
        }

        public float getMedia() {
            return media;
        }

        public float getFrequencia() {
            return frequencia;
        }

        public int getQuantidadeFaltas() {
            return quantidadeFaltas;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final MediaParcial other = (MediaParcial) obj;
            if (sigla == null) {
                if (other.sigla != null) {
                    return false;
                }
            } else if (!sigla.equals(other.sigla)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "MediaParcial [sigla=" + sigla + ", media=" + media + ", frequencia=" + frequencia
                + ", quantidadeFaltas=" + quantidadeFaltas + "]";
        }

    }

    public static final class NotaParcial {

        private final String sigla;
        private final String tipo;
        private final float nota;

        public NotaParcial(final String sigla, final String tipo, final float nota) {
            this.sigla = sigla;
            this.tipo = tipo;
            this.nota = nota;
        }

        public String getSigla() {
            return sigla;
        }

        public String getTipo() {
            return tipo;
        }

        public float getNota() {
            return nota;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Float.floatToIntBits(nota);
            result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
            result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final NotaParcial other = (NotaParcial) obj;
            if (Float.floatToIntBits(nota) != Float.floatToIntBits(other.nota)) {
                return false;
            }
            if (sigla == null) {
                if (other.sigla != null) {
                    return false;
                }
            } else if (!sigla.equals(other.sigla)) {
                return false;
            }
            if (tipo == null) {
                if (other.tipo != null) {
                    return false;
                }
            } else if (!tipo.equals(other.tipo)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "NotaParcial [sigla=" + sigla + ", tipo=" + tipo + ", nota=" + nota + "]";
        }

    }

}
