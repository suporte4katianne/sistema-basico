package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.Numeracao;
import br.com.hsi.nfe.service.GestaoNumeracao;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class NumeracaoBean implements Serializable {

    @Inject
    private GestaoNumeracao gestaoNumeracao;

    private List<Numeracao> numeracoes;
    private List<Numeracao> numeracoesFiltro;
    private Numeracao numeracao;

    @PostConstruct
    public void init(){
        if(numeracoesFiltro != null){
            numeracoesFiltro.clear();
        }
        numeracoes = gestaoNumeracao.numeracoes();
    }

    public List<Numeracao> getNumeracoes() {
        return numeracoes;
    }

    public void setNumeracoes(List<Numeracao> numeracoes) {
        this.numeracoes = numeracoes;
    }

    public List<Numeracao> getNumeracoesFiltro() {
        return numeracoesFiltro;
    }

    public void setNumeracoesFiltro(List<Numeracao> numeracoesFiltro) {
        this.numeracoesFiltro = numeracoesFiltro;
    }

    public Numeracao getNumeracao() {
        return numeracao;
    }

    public void setNumeracao(Numeracao numeracao) {
        this.numeracao = numeracao;
    }
}
