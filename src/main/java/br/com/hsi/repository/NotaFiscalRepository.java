package br.com.hsi.repository;

import br.com.hsi.model.Inutilizacao;
import br.com.hsi.model.NotaFiscal;
import br.com.hsi.model.dados.Cfop;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public class NotaFiscalRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;



	// ----------- Notas Fiscais -----------

	public NotaFiscal salvar(NotaFiscal nfe){
		return manager.merge(nfe);
	}
	
	public List<NotaFiscal> notaFiscalPorEmitente(Long idEmitente){
		TypedQuery<NotaFiscal> selectQuery = manager.createQuery("SELECT n FROM NotaFiscal n WHERE " +
				"n.empresa.id = :idEmitente ORDER BY n.id DESC", NotaFiscal.class);
		selectQuery.setParameter("idEmitente", idEmitente);
		return selectQuery.getResultList();
	}
	
	public List<NotaFiscal> notaFiscalPorTransportadora(Long idTransportadora){
		TypedQuery<NotaFiscal> selectQuery = manager.createQuery("SELECT n FROM NotaFiscal n WHERE " +
				"n.transportadora.id = :idTransportadora ORDER BY n.id DESC", NotaFiscal.class);
		selectQuery.setParameter("idTransportadora", idTransportadora);
		return selectQuery.getResultList();
	}


	public List<NotaFiscal> notaFiscalPorPeriodo(Long idEmitente, String inicio, String fim){
		TypedQuery<NotaFiscal> selectQuery = manager
				.createQuery("SELECT n FROM NotaFiscal n WHERE n.empresa.id = :idEmitente AND n.dataHoraEmissao >= :inicio AND " +
						"n.dataHoraEmissao <= :fim AND n.status =:status  ORDER BY n.id DESC", NotaFiscal.class);
		selectQuery.setParameter("idEmitente", idEmitente);
		selectQuery.setParameter("inicio", inicio);
		selectQuery.setParameter("fim", fim);
		selectQuery.setParameter("status", "A");
		return selectQuery.getResultList();
	}

	public NotaFiscal notaFiscalPorId(Long id) {
		return manager.find(NotaFiscal.class, id);
	}

	public List<NotaFiscal> listarNotasFiscais(String tipoNf) {
		TypedQuery<NotaFiscal> selectQuery = manager.createQuery(
		        "SELECT n FROM NotaFiscal n WHERE n.tipoNf = :tipoNf ORDER BY n.id DESC", NotaFiscal.class);
		selectQuery.setParameter("tipoNf", tipoNf);
		return selectQuery.getResultList();
	}


	public void excluirNotaFiscal(NotaFiscal notaFiscal) {
		Object object = manager.merge(notaFiscal);
		manager.remove(object);
	}


	// ----------- Inutilizações -----------

	public void salvarInutilizacao(Inutilizacao inutilizacao) {
		manager.merge(inutilizacao);
	}

	public List<Inutilizacao> listarInutilizacoes() {
		TypedQuery<Inutilizacao> selectQuery = manager.createQuery("FROM Inutilizacao", Inutilizacao.class);
		return selectQuery.getResultList();
	}

	public Inutilizacao inutilizacaoPorId(long id) {
		return manager.find(Inutilizacao.class, id);
	}


	// ----------- CFOP -----------
	
	public List<Cfop> cfopPorTipoeOperacao(String tipo, String operacao){
		TypedQuery<Cfop> selectQuery =  manager.createQuery("SELECT c FROM Cfop c WHERE c.tipo = :tipo AND c.operacao = :operacao", Cfop.class);
		selectQuery.setParameter("tipo", tipo);
		selectQuery.setParameter("operacao", operacao);
		return selectQuery.getResultList();
	}


	public Cfop cfopPorId(Long idCfop) {
		return manager.find(Cfop.class, idCfop);
	}


	public List<Cfop> listarCfops() {
		TypedQuery<Cfop> selectQuery = manager.createQuery("SELECT c FROM Cfop c", Cfop.class);
		return selectQuery.getResultList();
	}


    public void salvarCfop(Cfop cfop) {
		manager.merge(cfop);
    }
}
