package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.dados.Cfop;
import br.com.hsi.nfe.service.GestaoNotaFiscal;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CfopBean implements Serializable {


    @Inject
    private GestaoNotaFiscal gestaoNotaFiscal;

    private List<Cfop> cfops;
    private List<Cfop> cfopsFiltro;
    private Cfop cfop;

    @PostConstruct
    public void init(){
        if(cfopsFiltro != null){
            cfopsFiltro.clear();
        }
        cfops = gestaoNotaFiscal.listarCfops();
    }

    public void excluir () {

    }


    public List<Cfop> getCfops() {
        return cfops;
    }

    public void setCfops(List<Cfop> cfops) {
        this.cfops = cfops;
    }

    public List<Cfop> getCfopsFiltro() {
        return cfopsFiltro;
    }

    public void setCfopsFiltro(List<Cfop> cfopsFiltro) {
        this.cfopsFiltro = cfopsFiltro;
    }

    public Cfop getCfop() {
        return cfop;
    }

    public void setCfop(Cfop cfop) {
        this.cfop = cfop;
    }
}
