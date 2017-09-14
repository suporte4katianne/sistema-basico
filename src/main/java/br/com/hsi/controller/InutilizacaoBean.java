package br.com.hsi.controller;

import br.com.hsi.model.Inutilizacao;
import br.com.hsi.model.Numeracao;
import br.com.hsi.service.GestaoNotaFiscal;
import br.com.hsi.service.GestaoNumeracao;
import br.com.hsi.util.exception.TransmissaoException;
import br.com.hsi.util.jsf.FacesUtil;
import br.com.hsi.util.nfe.inutilizacao.GeraXmlInutilizacao;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class InutilizacaoBean implements Serializable {

    @Inject
    private GestaoNumeracao gestaoNumeracao;

    @Inject
    private GestaoNotaFiscal gestaoNotaFiscal;

    private List<Inutilizacao> inutilizacoes;
    private List<Inutilizacao> inutilizacoesFiltro;
    private Inutilizacao inutilizacao;

    @PostConstruct
    public void init(){
        if(inutilizacoesFiltro != null){
            inutilizacoesFiltro.clear();
        }
        inutilizacoes = gestaoNotaFiscal.listarInutilizacoes();
    }

    public void transmitir() {
        try {
            inutilizacao = new GeraXmlInutilizacao().inutilizaSequencia(inutilizacao);
            gestaoNotaFiscal.salvarInutilizacao(inutilizacao);

            for (Numeracao numeracao: gestaoNumeracao.numeracoes()) {
                if(numeracao.getSerie() == inutilizacao.getSerie() && numeracao.getModelo().equals("55")){
                    if(numeracao.getNumero() <= inutilizacao.getNumeroFim()){
                        numeracao.setNumero((inutilizacao.getNumeroFim()));
                        gestaoNumeracao.atualizaSequenciaNumeracao(numeracao);
                    }
                }
            }



            FacesUtil.addInfoMessage("Sequência de númeração cancelada com sucesso!");
        } catch (TransmissaoException e) {
            e.printStackTrace();
        }
    }


    public List<Inutilizacao> getInutilizacoes() {
        return inutilizacoes;
    }

    public void setInutilizacoes(List<Inutilizacao> inutilizacoes) {
        this.inutilizacoes = inutilizacoes;
    }

    public List<Inutilizacao> getInutilizacoesFiltro() {
        return inutilizacoesFiltro;
    }

    public void setInutilizacoesFiltro(List<Inutilizacao> inutilizacoesFiltro) {
        this.inutilizacoesFiltro = inutilizacoesFiltro;
    }

    public Inutilizacao getInutilizacao() {
        return inutilizacao;
    }

    public void setInutilizacao(Inutilizacao inutilizacao) {
        this.inutilizacao = inutilizacao;
    }
}
