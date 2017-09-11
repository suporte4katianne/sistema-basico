package br.com.hsi.nfe.repository;

import br.com.hsi.nfe.model.Numeracao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public class NumeracaoRepository implements Serializable {

    @Inject
    private EntityManager manager;


    public Numeracao salvar(Numeracao numeracao) {
        return manager.merge(numeracao);
    }

    public void excluir(Numeracao numeracao){
        Object objetoNumeracao = manager.merge(numeracao);
        manager.remove(objetoNumeracao);
    }

    public Numeracao numeracaoPorId(Long id){
        return manager.find(Numeracao.class, id);
    }

    public List<Numeracao> numeracoes() {
        return manager.createQuery("SELECT n FROM Numeracao n", Numeracao.class).getResultList();
    }

    public boolean verificaExistentes(Numeracao numeracao){

        TypedQuery<Numeracao> query = manager.createQuery("SELECT n FROM Numeracao n WHERE n.modelo = :modelo AND " +
                "n.serie = :serie AND n.numero = :numero", Numeracao.class)
                .setParameter("modelo", numeracao.getModelo())
                .setParameter("serie", numeracao.getSerie())
                .setParameter("numero", numeracao.getNumero());

        if(query.getResultList().isEmpty()){
            return false;
        }

        return true;

    }


}
