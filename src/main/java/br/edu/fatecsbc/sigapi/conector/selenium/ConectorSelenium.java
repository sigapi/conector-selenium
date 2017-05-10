package br.edu.fatecsbc.sigapi.conector.selenium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.fatecsbc.sigapi.conector.Conector;
import br.edu.fatecsbc.sigapi.conector.CredenciaisBuilder;
import br.edu.fatecsbc.sigapi.conector.dto.CalendarioProvas;
import br.edu.fatecsbc.sigapi.conector.dto.DadosCadastrais;
import br.edu.fatecsbc.sigapi.conector.dto.DadosDesempenho;
import br.edu.fatecsbc.sigapi.conector.dto.FaltasParciais;
import br.edu.fatecsbc.sigapi.conector.dto.Historico;
import br.edu.fatecsbc.sigapi.conector.dto.NotasParciais;
import br.edu.fatecsbc.sigapi.conector.selenium.dto.CredenciaisSelenium;
import br.edu.fatecsbc.sigapi.conector.selenium.service.AutenticacaoService;
import br.edu.fatecsbc.sigapi.conector.selenium.service.CalendarioProvasService;
import br.edu.fatecsbc.sigapi.conector.selenium.service.DadosCadastraisService;
import br.edu.fatecsbc.sigapi.conector.selenium.service.DadosDesempenhoService;
import br.edu.fatecsbc.sigapi.conector.selenium.service.FaltasParciaisService;
import br.edu.fatecsbc.sigapi.conector.selenium.service.HistoricoService;
import br.edu.fatecsbc.sigapi.conector.selenium.service.NotasParciaisService;

@Component
public class ConectorSelenium
    implements Conector<CredenciaisSelenium> {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private DadosCadastraisService dadosCadastraisService;

    @Autowired
    private DadosDesempenhoService dadosDesempenhoService;

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private NotasParciaisService notasParciaisService;

    @Autowired
    private FaltasParciaisService faltasParciaisService;

    @Autowired
    private CalendarioProvasService calendarioProvasService;

    @Override
    public CredenciaisBuilder<CredenciaisSelenium> getCredenciaisBuilder() {
        return new CredenciaisSeleniumBuilder();
    }

    @Override
    public boolean autenticar(final CredenciaisSelenium credenciais) {
        return autenticacaoService.autenticar(credenciais);
    }

    @Override
    public DadosCadastrais getDadosCadastrais(final CredenciaisSelenium credenciais) {
        return dadosCadastraisService.getDados(credenciais);
    }

    @Override
    public DadosDesempenho getDadosDesempenho(final CredenciaisSelenium credenciais) {
        return dadosDesempenhoService.getDados(credenciais);
    }

    @Override
    public Historico getHistorico(final CredenciaisSelenium credenciais) {
        return historicoService.getDados(credenciais);
    }

    @Override
    public NotasParciais getNotasParciais(final CredenciaisSelenium credenciais) {
        return notasParciaisService.getDados(credenciais);
    }

    @Override
    public FaltasParciais getFaltasParciais(final CredenciaisSelenium credenciais) {
        return faltasParciaisService.getDados(credenciais);
    }

    @Override
    public CalendarioProvas getCalendarioProvas(final CredenciaisSelenium credenciais) {
        return calendarioProvasService.getDados(credenciais);
    }

}
