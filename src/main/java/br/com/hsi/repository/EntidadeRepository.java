package br.com.hsi.repository;

import br.com.hsi.model.Entidade;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public class EntidadeRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Entidade salvar(Entidade cliente){
		return manager.merge(cliente);
	}
	
	public void exclui(Entidade cliente){
		Object objetoCliente = manager.merge(cliente);
		manager.remove(objetoCliente);
	}
	
	public List<Entidade> listarEntidades(String tipoEntidade){
		TypedQuery<Entidade> selectQuery = manager.createQuery("FROM Entidade WHERE tipoEntidade = :tipoEntidade", Entidade.class);
		selectQuery.setParameter("tipoEntidade", tipoEntidade);
		return selectQuery.getResultList();
	}

	
	public Entidade clientePorId(Long id){
		return manager.find(Entidade.class, id);
	}

	public boolean checaCnpj(String cnpj){
		TypedQuery<Entidade> selectQuery = manager.createQuery("FROM Entidade WHERE cpfCnpj = :cnpj", Entidade.class);
		selectQuery.setParameter("cnpj", cnpj);
        return selectQuery.getResultList().size() != 0;
	}

}
