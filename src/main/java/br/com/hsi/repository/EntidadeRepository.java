package br.com.hsi.repository;

import br.com.hsi.model.Entidade;
import br.com.hsi.model.NotaFiscal;

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
	
	public void excluir(Entidade cliente){
		Object objetoCliente = manager.merge(cliente);
		manager.remove(objetoCliente);
	}
	
	public List<Entidade> listarEntidades(String tipoEntidade){
		TypedQuery<Entidade> selectQuery = manager.createQuery("SELECT e FROM Entidade e " +
				"WHERE e.tipoEntidade = :tipoEntidade", Entidade.class);
		selectQuery.setParameter("tipoEntidade", tipoEntidade);
		return selectQuery.getResultList();
	}

	
	public Entidade entidadePorId(Long id){
		return manager.find(Entidade.class, id);
	}

	public boolean checaCnpj(String cnpj){
		TypedQuery<Entidade> selectQuery = manager.createQuery("SELECT e FROM Entidade e " +
				"WHERE e.cpfCnpj = :cnpj", Entidade.class);
		selectQuery.setParameter("cnpj", cnpj);
        return selectQuery.getResultList().size() != 0;
	}

    public List<NotaFiscal> getNotasFiscaisDaEntidade(Entidade entidade) {
		TypedQuery<NotaFiscal> selectQuery;
		if(entidade.getTipoEntidade().equals("T")){
			selectQuery = manager.createQuery("SELECT n FROM NotaFiscal n " +
					"WHERE n.transportadora.id = :id", NotaFiscal.class);
			selectQuery.setParameter("id", entidade.getId());
		} else {
			selectQuery = manager.createQuery("SELECT n FROM NotaFiscal n " +
					"WHERE n.entidade.id = :id", NotaFiscal.class);
		}
		selectQuery.setParameter("id", entidade.getId());
		return selectQuery.getResultList();
    }
}
