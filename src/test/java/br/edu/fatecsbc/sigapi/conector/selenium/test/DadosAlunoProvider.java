package br.edu.fatecsbc.sigapi.conector.selenium.test;

import java.util.ArrayList;
import java.util.Collection;

public class DadosAlunoProvider {

    public static Object[] provideAlunos() {

        final Collection<DadosAluno> dados = new ArrayList<>();
        dados.add(aluno01());
        dados.add(aluno02());
        dados.add(aluno03());
        dados.add(aluno04());
        dados.add(aluno05());
        dados.add(aluno06());
        dados.add(aluno07());
        dados.add(aluno08());
        dados.add(aluno09());
        dados.add(aluno10());

        return dados.stream().map(x -> x).toArray(Object[]::new);

    }

    private static DadosAluno aluno01() {

        final DadosAluno dados = informaticaParaNegociosNoturnoFatecSbc("1760777533");
        dados.setFoto("https://sigacentropaulasouza.com.br/imagem//ZQ3YVAVR4NXTBKX8UJL6X9KMN35SR5.TMB.JPG");
        desempenho(dados, 8.4F, 76.7F);

        // Faltas
        dados.addFalta("AGO007", 30, 14);
        dados.addFalta("IMH002", 28, 12);
        dados.addFalta("ISA001", 12, 6);
        dados.addFalta("ISJ002", 32, 8);
        dados.addFalta("ITI104", 19, 1);
        dados.addFalta("LIN600", 22, 0);
        dados.addFalta("TES001");
        dados.addFalta("TTG103");
        dados.addFalta("TTG202", 18, 0);

        // Médias Parciais
        dados.addMediaParcial("AGO007", 2.8f, 0f, 0);
        dados.addMediaParcial("IMH002", 0f, 0f, 0);
        dados.addMediaParcial("ISA001", 2.5f, 0, 0);
        dados.addMediaParcial("ISJ002", 2.5f, 90f, 8);
        dados.addMediaParcial("ITI104", 2.7f, 0f, 0);
        dados.addMediaParcial("LIN600", 0f, 0f, 0);
        dados.addMediaParcial("TES001", 1.5f, 0, 0);
        dados.addMediaParcial("TTG103", 0f, 0f, 0);
        dados.addMediaParcial("TTG202", 0f, 0f, 0);

        // Notas
        dados.addNotaP1("AGO007", 9.0F);
        dados.addNotaP2("AGO007", 0.0F);
        dados.addNotaP3("AGO007", 0.0F);
        dados.addNotaTrabalho("AGO007", 0.0F);

        dados.addNotaP1("IMH002", 0.0F);
        dados.addNotaP2("IMH002", 0.0F);
        dados.addNotaP3("IMH002", 0.0F);
        dados.addNotaTrabalho("IMH002", 0.0F);

        dados.addNotaP1("ISA001", 8.0F);
        dados.addNotaP2("ISA001", 0.0F);
        dados.addNotaP3("ISA001", 0.0F);
        dados.addNotaTrabalho("ISA001", 0.0F);

        dados.addNotaP1("ISJ002", 8.0F);
        dados.addNotaP2("ISJ002", 0.0F);
        dados.addNotaP3("ISJ002", 0.0F);
        dados.addNotaTrabalho("ISJ002", 0.0F);

        dados.addNotaP1("ITI104", 8.5F);
        dados.addNotaP2("ITI104", 0.0F);
        dados.addNotaP3("ITI104", 0.0F);
        dados.addNotaTrabalho("ITI104", 0.0F);

        dados.addNotaParcial("TES001", "Relatório de Estágio Supervisionado", 3.0F);
        dados.addNotaParcial("TES001", "Relatório de Estágio Supervisionado", 0.0F);
        dados.addNotaParcial("TES001", "Relatório de Estágio Supervisionado", 0.0F);
        dados.addNotaParcial("TES001", "Relatório de Estágio Supervisionado", 0.0F);

        return dados;
    }

    private static DadosAluno aluno02() {

        final DadosAluno dados = informaticaParaNegociosNoturnoFatecSbc("263045750");
        dados.setFoto("https://sigacentropaulasouza.com.br/imagem//WNZXISMNU9AE5RX22TUPJJIMDRHTFN.TMB.JPG");
        desempenho(dados, 7.8F, 76.7F);

        return dados;
    }

    private static DadosAluno aluno03() {

        final DadosAluno dados = informaticaParaNegociosNoturnoFatecSbc("347785182");
        dados.setFoto("https://sigacentropaulasouza.com.br/imagem//2PCCJABGYB79ZKQUQCY4QURDSHCWY5.TMB.JPG");
        desempenho(dados, 8.1F, 76.7F);

        return dados;
    }

    private static DadosAluno aluno04() {

        final DadosAluno dados = informaticaParaNegociosNoturnoFatecSbc("877265991");
        dados.setFoto("https://sigacentropaulasouza.com.br/imagem//HF8QRG5K4NYQUIZJQU8UY6T6CDCQPP.TMB.JPG");
        desempenho(dados, 7.7F, 79F);

        return dados;
    }

    private static DadosAluno aluno05() {

        final DadosAluno dados = aluno01();
        identificacao(dados, "1702532413");
        dados.setFoto("https://sigacentropaulasouza.com.br/imagem//ZQ3YVAVR4NXTBKX8UJL6X9KMN35SR5.TMB.JPG");

        return dados;
    }

    private static DadosAluno aluno06() {

        final DadosAluno dados = aluno01();
        identificacao(dados, "1702532413");
        dados.setFoto("https://sigacentropaulasouza.com.br/imagem//ZQ3YVAVR4NXTBKX8UJL6X9KMN35SR5.TMB.JPG");

        return dados;
    }

    private static DadosAluno aluno07() {

        final DadosAluno dados = aluno01();
        identificacao(dados, "1837913935");

        return dados;
    }

    private static DadosAluno aluno08() {

        final DadosAluno dados = aluno01();
        identificacao(dados, "1838231415");

        return dados;
    }

    private static DadosAluno aluno09() {

        final DadosAluno dados = aluno01();
        identificacao(dados, "1869359666");

        return dados;

    }

    private static DadosAluno aluno10() {

        final DadosAluno dados = aluno01();
        identificacao(dados, "1870068625", 3);
        dados.setEmailFatec(2);
        dados.setEmailPreferencial(1);
        dados.setEmailWebsai(1);

        return dados;

    }

    private static DadosAluno aluno(final String hash) {

        final DadosAluno dados = new DadosAluno();
        identificacao(dados, hash);
        dados.setFoto("http://" + hash);

        return dados;

    }

    private static DadosAluno informaticaParaNegociosNoturnoFatecSbc(final String hash) {

        final DadosAluno dados = aluno(hash);

        fatecSbc(dados);
        informaticaParaNegocios(dados);
        periodoNoturno(dados);
        dados.setMaiorPrCurso(10);

        return dados;

    }

    private static void identificacao(final DadosAluno dados, final String hash) {
        identificacao(dados, hash, 0);
    }

    private static void identificacao(final DadosAluno dados, final String hash, final int quantidadeEmails) {
        dados.setHash(hash);
        dados.setNome(hash);
        dados.setRa(hash);
        dados.setFoto("http://" + hash + ".jpg");

        for (int i = 0; i < quantidadeEmails; i++) {
            dados.addEmail(String.format("%1$s@%1$s.com", hash));
        }
    }

    private static void desempenho(final DadosAluno dados, final float pr, final float pp) {
        dados.setPp(pp);
        dados.setPr(pr);
    }

    private static void fatecSbc(final DadosAluno dados) {
        dados.setInstituicao("Faculdade de Tecnologia de São Bernardo do Campo \"Adib Moises Dib\"");
    }

    private static void informaticaParaNegocios(final DadosAluno dados) {
        dados.setCurso("Tecnologia em Informática para Negócios");
    }

    private static void periodoNoturno(final DadosAluno dados) {
        dados.setPeriodo("Noite");
    }

}
