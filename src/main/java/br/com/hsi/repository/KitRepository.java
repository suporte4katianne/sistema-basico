package br.com.hsi.repository;

import br.com.hsi.model.Kit;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author Eriel Miquilino
 */
public class KitRepository implements Serializable {

    @Inject
    private EntityManager entityManager;

    public void salvarKit(Kit kit) {
        entityManager.merge(kit);
    }

    public Kit kitPorId(Long id) {
        return entityManager.find(Kit.class, id);
    }
}
