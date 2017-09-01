package br.com.hsi.nfe.repository;

import br.com.hsi.nfe.model.Entidade;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;




public class TransportadoraRepository implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Entidade salvar(Entidade transportadora){
		return manager.merge(transportadora);
	}
	
	public void excluir(Entidade transportadora) {
		Object obejtoTransportadora = manager.merge(transportadora);
		manager.remove(obejtoTransportadora);
	}
	
	public boolean checaCpfCnpj(String cpfCnpj){
		TypedQuery<Entidade> selectQuery =  manager.createQuery("FROM Entidade WHERE cpfCnpj = :cpfCnpj", Entidade.class);
		selectQuery.setParameter("cpfCnpj", cpfCnpj);
        return selectQuery.getResultList().size() != 0;
	}

	public List<Entidade> listarTransportadora() {
		TypedQuery<Entidade> selectQuery =  manager.createQuery("FROM Entidade", Entidade.class);
		return selectQuery.getResultList();
	}

	public Entidade transportadoraPorId(Long idTransportadora) {
		return manager.find(Entidade.class, idTransportadora);
	}

	
	
}