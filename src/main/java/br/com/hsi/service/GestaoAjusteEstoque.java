package br.com.hsi.service;

import br.com.hsi.model.AjusteEstoque;
import br.com.hsi.repository.AjusteEstoqueReopsitory;
import br.com.hsi.util.Transacional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class GestaoAjusteEstoque implements Serializable {

    @Inject
    private AjusteEstoqueReopsitory ajusteEstoqueReopsitory;

    @Transacional
    public void salvar(AjusteEstoque ajusteEstoque) {
        ajusteEstoqueReopsitory.salvar(ajusteEstoque);
    }

    @Transacional
    public void excluir(AjusteEstoque ajusteEstoque) {
        ajusteEstoqueReopsitory.excluir(ajusteEstoque);
    }

    @Transacional
    public AjusteEstoque ajusteEstoquePorId(long id) {
        return ajusteEstoqueReopsitory.ajusteEstoquePorId(id);
    }

    @Transacional
    public List<AjusteEstoque> listarAjusteEstoque(){
        return ajusteEstoqueReopsitory.listarAjusteEstoque();
    }
}
