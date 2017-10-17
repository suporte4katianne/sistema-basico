package br.com.hsi.repository;

import br.com.hsi.model.Praca;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Classe de persistencia dos dados de Praca com o banco de dados
 *
 * @author Eriel Miquilino
 */
public class PracaRepository implements Serializable {

    @Inject
    private EntityManager entityManager;

    public void salvar(Praca praca){
        entityManager.merge(praca);
    }

    public void excluir(Praca praca){
        Object object = entityManager.merge(praca);
        entityManager.remove(object);
    }

    public List<Praca> listarPracas() {
        return entityManager.createQuery("SELECT p FROM Praca p", Praca.class).getResultList();
    }

    public Praca pracaPorId(Long id) {
        return entityManager.find(Praca.class, id);
    }

}
