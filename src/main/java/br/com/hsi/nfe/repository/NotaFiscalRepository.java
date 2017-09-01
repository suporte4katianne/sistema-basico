package br.com.hsi.nfe.repository;

import br.com.hsi.nfe.model.Inutilizacao;
import br.com.hsi.nfe.model.NotaFiscal;
import br.com.hsi.nfe.model.Numeracao;
import br.com.hsi.nfe.model.dados.Cfop;

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
		TypedQuery<NotaFiscal> selectQuery = manager.createQuery("FROM NotaFiscal WHERE " +
				"empresa.id = :idEmitente ORDER BY id DESC", NotaFiscal.class);
		selectQuery.setParameter("idEmitente", idEmitente);
		return selectQuery.getResultList();
	}
	
	public List<NotaFiscal> notaFiscalPorTransportadora(Long idTransportadora){
		TypedQuery<NotaFiscal> selectQuery = manager.createQuery("FROM NotaFiscal WHERE " +
				"transportadora.id = :idTransportadora ORDER BY id DESC", NotaFiscal.class);
		selectQuery.setParameter("idTransportadora", idTransportadora);
		return selectQuery.getResultList();
	}


	public List<NotaFiscal> notaFiscalPorPeriodo(Long idEmitente, String inicio, String fim){
		TypedQuery<NotaFiscal> selectQuery = manager
				.createQuery("FROM NotaFiscal WHERE empresa.id = :idEmitente AND dataHoraEmissao >= :inicio AND " +
						"dataHoraEmissao <= :fim AND status =:status  ORDER BY id DESC", NotaFiscal.class);
		selectQuery.setParameter("idEmitente", idEmitente);
		selectQuery.setParameter("inicio", inicio);
		selectQuery.setParameter("fim", fim);
		selectQuery.setParameter("status", "A");
		return selectQuery.getResultList();
	}

	public NotaFiscal notaFiscalPorId(Long id) {
		return manager.find(NotaFiscal.class, id);
	}

	public List<NotaFiscal> listarNotasFiscais() {
		TypedQuery<NotaFiscal> selectQuery = manager.createQuery("FROM NotaFiscal ORDER BY id DESC", NotaFiscal.class);
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




	// ----------- Numeração -----------

	public Numeracao atualizaSequenciaNumeracao(Numeracao numeracao) {
		return manager.merge(numeracao);
	}

	public List<Numeracao> listarNumeracoes() {
		TypedQuery<Numeracao> selectQuery = manager.createQuery("FROM Numeracao", Numeracao.class);
		return selectQuery.getResultList();
	}




	// ----------- CFOP -----------
	
	public List<Cfop> cfopPorTipoeOperacao(String tipo, String operacao){
		TypedQuery<Cfop> selectQuery =  manager.createQuery("FROM Cfop WHERE tipo = :tipo AND operacao = :operacao", Cfop.class);
		selectQuery.setParameter("tipo", tipo);
		selectQuery.setParameter("operacao", operacao);
		return selectQuery.getResultList();
	}


	public Cfop cfopPorId(Long idCfop) {
		return manager.find(Cfop.class, idCfop);
	}


	public List<Cfop> listarCfops() {
		TypedQuery<Cfop> selectQuery = manager.createQuery("FROM Cfop", Cfop.class);
		return selectQuery.getResultList();
	}


    public Cfop salvarCfop(Cfop cfop) {
		return manager.merge(cfop);
    }
}
