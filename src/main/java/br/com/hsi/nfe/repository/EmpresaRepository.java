package br.com.hsi.nfe.repository;

import br.com.hsi.nfe.model.Empresa;
import br.com.hsi.nfe.model.Numeracao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;


public class EmpresaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Empresa salvar(Empresa emitente) {
		return manager.merge(emitente);
	}
	
	public void excluir(Empresa emitente){
		Object objetoEmitente = manager.merge(emitente);
		manager.remove(objetoEmitente);
	}
		
	public List<Empresa> empresas(){
		TypedQuery<Empresa> selectQuery = manager.createQuery("FROM Empresa ORDER BY id DESC", Empresa.class);
		return selectQuery.getResultList();
	}
	
	public Empresa emitentePorId(Long id){
		return manager.find(Empresa.class, id);
	}
	
	public boolean checaCnpj(String cnpj){
		TypedQuery<Empresa> selectQuery = manager.createQuery("FROM Empresa WHERE cpfCnpj = :cnpj", Empresa.class);
		selectQuery.setParameter("cnpj", cnpj);
        return selectQuery.getResultList().size() != 0;
	}


}
