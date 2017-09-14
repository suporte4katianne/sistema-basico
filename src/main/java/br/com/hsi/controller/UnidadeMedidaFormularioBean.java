package br.com.hsi.controller;

import br.com.hsi.model.dados.UnidadeMedida;
import br.com.hsi.service.GestaoProduto;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class UnidadeMedidaFormularioBean implements Serializable {

    @Inject
    private GestaoProduto gestaoProduto;

    private UnidadeMedida unidadeMedida;

    public void inicializar (){
        if(unidadeMedida == null){
            unidadeMedida = new UnidadeMedida();
        }
    }

    public void salvar () throws IOException {
        gestaoProduto.salvarUnidadeMedida(unidadeMedida);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/UnidadeMedida.xhtml");
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}
