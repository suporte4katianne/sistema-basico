package br.com.hsi.controller;

import br.com.hsi.model.AjusteEstoque;
import br.com.hsi.service.GestaoAjusteEstoque;
import br.com.hsi.util.jsf.FacesUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AjusteEstoqueBean implements Serializable {

    @Inject
    private GestaoAjusteEstoque gestaoAjusteEstoque;

    private List<AjusteEstoque> ajusteEstoques;
    private List<AjusteEstoque> ajusteEstoquesFiltro;

    private AjusteEstoque ajusteEstoque;


    @PostConstruct
    public void init() {
        if(ajusteEstoquesFiltro != null){
           ajusteEstoquesFiltro.clear();
        }
        ajusteEstoques = gestaoAjusteEstoque.listarAjusteEstoque();
    }

    public void excluir() {
        gestaoAjusteEstoque.excluir(ajusteEstoque);
        FacesUtil.addInfoMessage("Registro removido com sucesso!");
    }


    public List<AjusteEstoque> getAjusteEstoques() {
        return ajusteEstoques;
    }

    public void setAjusteEstoques(List<AjusteEstoque> ajusteEstoques) {
        this.ajusteEstoques = ajusteEstoques;
    }

    public List<AjusteEstoque> getAjusteEstoquesFiltro() {
        return ajusteEstoquesFiltro;
    }

    public void setAjusteEstoquesFiltro(List<AjusteEstoque> ajusteEstoquesFiltro) {
        this.ajusteEstoquesFiltro = ajusteEstoquesFiltro;
    }

    public AjusteEstoque getAjusteEstoque() {
        return ajusteEstoque;
    }

    public void setAjusteEstoque(AjusteEstoque ajusteEstoque) {
        this.ajusteEstoque = ajusteEstoque;
    }
}
