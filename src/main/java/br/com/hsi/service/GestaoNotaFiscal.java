package br.com.hsi.service;

import br.com.hsi.model.NotaFiscal;
import br.com.hsi.model.Inutilizacao;
import br.com.hsi.model.dados.Cfop;
import br.com.hsi.repository.NotaFiscalRepository;
import br.com.hsi.util.Transacional;

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
	public List<NotaFiscal> listarNotasFiscais(String tipoNf) {
		return notasFiscais.listarNotasFiscais(tipoNf);
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
