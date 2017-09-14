package br.com.hsi.controller;

import br.com.hsi.model.dados.UnidadeMedida;
import br.com.hsi.service.GestaoProduto;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UnidadeMedidaBean implements Serializable {

    @Inject
    private GestaoProduto gestaoProduto;

    private List<UnidadeMedida> unidadeMedidas;
    private List<UnidadeMedida> unidadeMedidasFiltro;
    private UnidadeMedida unidadeMedida;

    @PostConstruct
    public void init(){
        if(unidadeMedidasFiltro != null){
            unidadeMedidasFiltro.clear();
        }
        unidadeMedidas = gestaoProduto.listaUnidadeMedida();
    }

    public List<UnidadeMedida> getUnidadeMedidas() {
        return unidadeMedidas;
    }

    public void setUnidadeMedidas(List<UnidadeMedida> unidadeMedidas) {
        this.unidadeMedidas = unidadeMedidas;
    }

    public List<UnidadeMedida> getUnidadeMedidasFiltro() {
        return unidadeMedidasFiltro;
    }

    public void setUnidadeMedidasFiltro(List<UnidadeMedida> unidadeMedidasFiltro) {
        this.unidadeMedidasFiltro = unidadeMedidasFiltro;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}
