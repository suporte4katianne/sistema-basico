package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.dados.Cfop;
import br.com.hsi.nfe.service.GestaoNotaFiscal;
import br.com.hsi.nfe.util.jsf.FacesUtil;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;


@Named
@ViewScoped
public class CfopFormularioBean implements Serializable {

    @Inject
    private GestaoNotaFiscal gestaoNotaFiscal;

    private Cfop cfop;

    public void inicializar (){
        if(cfop == null){
            cfop = new Cfop();
        }
    }

    public void salvar () throws IOException {
        gestaoNotaFiscal.salvarCfop(cfop);
        FacesUtil.addInfoMessage("Cfop salva com sucesso!");
        FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Cfop.xhtml");
    }

    public Cfop getCfop() {
        return cfop;
    }

    public void setCfop(Cfop cfop) {
        this.cfop = cfop;
    }
}
