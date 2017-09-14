package br.com.hsi.repository;

import br.com.hsi.model.dados.Estado;
import br.com.hsi.model.dados.Cep;
import br.com.hsi.model.dados.Cidade;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public class EnderecoRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Cidade cidadePorId(Long id) {
		TypedQuery<Cidade> selectQuery = manager.createQuery("FROM Cidade WHERE idCidade = :id", Cidade.class);
		selectQuery.setParameter("id", id);
		Cidade cidade = selectQuery.getSingleResult();
		return cidade;
	}

	public Estado estadoPorId(Long id) {
		TypedQuery<Estado> selectQuery = manager.createQuery("FROM Estado WHERE idEstado = :id", Estado.class);
		selectQuery.setParameter("id", id);
		Estado estado = selectQuery.getSingleResult();
		return estado;
	}
	
	public List<Cidade> cidades(){
		TypedQuery<Cidade> selectQuery = manager.createQuery("FROM Cidade", Cidade.class);
		List<Cidade> cidades = selectQuery.getResultList();
		return cidades;
	}

	public List<Estado> estados() {
		TypedQuery<Estado> selectQuery = manager.createQuery("FROM Estado", Estado.class);
		List<Estado> estados = selectQuery.getResultList();
		return estados;
	}
	
	public List<Cidade> cidadePorEstado(Long id){
		TypedQuery<Cidade> selectQuery = manager.createQuery("FROM Cidade WHERE estado.id = :id", Cidade.class);
		selectQuery.setParameter("id", id);
		List<Cidade> cidades = selectQuery.getResultList();
		return cidades;
	}
	
	public Cep cep(String cep){
		TypedQuery<Cep> selectQuery = manager.createQuery("FROM Cep WHERE cep = :cep", Cep.class);
		selectQuery.setParameter("cep", cep);
		return selectQuery.getSingleResult();
	}

	public Cidade cidadePorCodigoIbge(String codigoIbge) {
		TypedQuery<Cidade> selectQuery = manager.createQuery("FROM Cidade WHERE codigo = :codigoIbge", Cidade.class);
		selectQuery.setParameter("codigoIbge", codigoIbge);
		Cidade cidade = selectQuery.getSingleResult();
		return cidade;
	}

	public Estado estadoPorCodigoIbge(String codigoIbge) {
		TypedQuery<Estado> selectQuery = manager.createQuery("FROM Estado WHERE codigoIbge = :codigoIbge", Estado.class);
		selectQuery.setParameter("codigoIbge", codigoIbge);
		Estado estado = selectQuery.getSingleResult();
		return estado;
	}


}
