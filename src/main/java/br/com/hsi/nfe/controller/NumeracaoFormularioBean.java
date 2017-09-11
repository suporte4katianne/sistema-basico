package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.Numeracao;
import br.com.hsi.nfe.service.GestaoNumeracao;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class NumeracaoFormularioBean implements Serializable {

    @Inject
    private GestaoNumeracao gestaoNumeracao;

    private Numeracao numeracao;

    public void inicializar (){
        if(numeracao == null){
            numeracao = new Numeracao();
        }
    }


    public void salvar() throws IOException {
        gestaoNumeracao.salvar(numeracao);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Numeracao.xhtml");
    }

}
