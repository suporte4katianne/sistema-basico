package br.com.hsi.nfe.service;

import br.com.hsi.nfe.model.Inutilizacao;
import br.com.hsi.nfe.model.NotaFiscal;
import br.com.hsi.nfe.model.Numeracao;
import br.com.hsi.nfe.model.dados.Cfop;
import br.com.hsi.nfe.repository.NotaFiscalRepository;
import br.com.hsi.nfe.util.Transacional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class GestaoNotaFiscal implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private NotaFiscalRepository notasFiscais;



	// ----------- Notas Fiscais -----------

	@Transacional
	public void salvar(NotaFiscal nfe) {
		notasFiscais.salvar(nfe);
	}

	@Transacional
	public void excluirNotaFiscal(NotaFiscal notaFiscal) {
		notasFiscais.excluirNotaFiscal(notaFiscal);
	}

	@Transacional
	public NotaFiscal notaFiscalPorId(Long id){
		return notasFiscais.notaFiscalPorId(id);
	}

	@Transacional
	public List<NotaFiscal> notaFiscalPorPeriodo(Long idEmitente, String inicio, String fim){
		return notasFiscais.notaFiscalPorPeriodo(idEmitente, inicio, fim);
	}

	@Transacional
	public List<NotaFiscal> listarNotasFiscais() {
		return notasFiscais.listarNotasFiscais();
	}



	// ----------- Inutilizações -----------


	@Transacional
	public void salvarInutilizacao(Inutilizacao inutilizacao) {
		notasFiscais.salvarInutilizacao(inutilizacao);
	}

	@Transacional
	public List<Inutilizacao> listarInutilizacoes() {
		return notasFiscais.listarInutilizacoes();
	}

	@Transacional
	public Inutilizacao inutilizacaoPorId(long id) {
		return notasFiscais.inutilizacaoPorId(id);
	}
	


	// ----------- Numeração -----------

	@Transacional
	public List<Numeracao> listarNumeracoes() {
		return notasFiscais.listarNumeracoes();
	}

	@Transacional
	public void atualizaSequenciaNumeracao(Numeracao numeracao) {
		numeracao.setNumero( (numeracao.getNumero() + 1) );
		notasFiscais.atualizaSequenciaNumeracao(numeracao);
	}



	// ----------- CFOP -----------

	@Transacional
	public List<Cfop> cfopPorTipoeOperacao(String tipo, String operacao){
		return notasFiscais.cfopPorTipoeOperacao(tipo, operacao);
	}

	@Transacional
	public Cfop cfopPorId(Long idCfop) {	
		return notasFiscais.cfopPorId(idCfop);
	}

	@Transacional
	public List<Cfop> listarCfops() {
		return notasFiscais.listarCfops();
	}

	@Transacional
	public void salvarCfop(Cfop cfop) {
		notasFiscais.salvarCfop(cfop);
	}
}
