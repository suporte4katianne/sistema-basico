package br.com.hsi.repository;

import br.com.hsi.model.Remessa;
import br.com.hsi.model.RemessaItem;
import br.com.hsi.model.dados.text.StatusRemessa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Classe de persistencia dos dados de {@link br.com.hsi.model.Remessa} e {@link br.com.hsi.model.RemessaItem} com o banco de dados
 *
 * @author Eriel Miquilino
 */
public class RemessaRepository implements Serializable{

    @Inject
    private EntityManager entityManager;

    public void salvar(Remessa remessa){
        entityManager.merge(remessa);
    }

    public void excluir(Remessa remessa){
        Object object = entityManager.merge(remessa);
        entityManager.remove(object);
    }

    public List<Remessa> listarRemessas(){
        return entityManager.createQuery("SELECT r FROM Remessa r ORDER BY r.id DESC", Remessa.class)
                .getResultList();
    }

    public List<Remessa> listarRemessas(StatusRemessa statusRemessa){
        return entityManager
                .createQuery("SELECT r FROM Remessa r " +
                        "WHERE r.statusRemessa = : statusRemessa ORDER BY r.id DESC", Remessa.class)
                .setParameter("statusRemessa", statusRemessa)
                .getResultList();
    }

    public BigDecimal saldoRemessa(Remessa remessa) {
        return entityManager.createQuery("SELECT sum(r.quantidade) FROM RemessaItem r " +
                "WHERE r.remessa = :remessa", BigDecimal.class)
                .setParameter("remessa",remessa)
                .getSingleResult();
    }


    public Remessa remessaPorId(Long id) {
        return entityManager.find(Remessa.class, id);
    }

    public List<RemessaItem> remessaItemPorRemessa(Remessa remessa) {
        return entityManager
                .createQuery("SELECT r FROM RemessaItem r WHERE r.remessa.id" +
                        " = :id", RemessaItem.class)
                .setParameter("id", remessa.getId())
                .getResultList();
    }



}
