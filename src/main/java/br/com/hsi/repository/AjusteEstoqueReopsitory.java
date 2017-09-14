package br.com.hsi.repository;

import br.com.hsi.model.AjusteEstoque;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class AjusteEstoqueReopsitory implements Serializable{

    @Inject
    private EntityManager manager;

    public AjusteEstoque salvar(AjusteEstoque ajusteEstoque) {
        return manager.merge(ajusteEstoque);
    }

    public void excluir(AjusteEstoque ajusteEstoque) {
        Object objetoajusteEstoque = manager.merge(ajusteEstoque);
        manager.remove(objetoajusteEstoque);
    }

    public AjusteEstoque ajusteEstoquePorId(long id) {
        return manager.find(AjusteEstoque.class, id);
    }

    public List<AjusteEstoque> listarAjusteEstoque() {
        return manager.createQuery("SELECT a FROM AjusteEstoque a", AjusteEstoque.class).getResultList();
    }
}
