package br.com.hsi.service;

import br.com.hsi.model.Empresa;
import br.com.hsi.repository.EmpresaRepository;
import br.com.hsi.repository.NotaFiscalRepository;
import br.com.hsi.util.Transacional;
import br.com.hsi.util.exception.NegocioException;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;


public class GestaoEmpresa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmpresaRepository empresas;
	@Inject 
	private NotaFiscalRepository notasFiscais;
	
	@Transacional
	public void salvar(Empresa emitente) throws NegocioException {
		if(emitente.getId() != null){
			if(!empresas.checaCnpj(emitente.getCpfCnpj())){
				throw new NegocioException("O Campo CNPJ não pode ser alterado");
			}else{
				empresas.salvar(emitente);				
			}
		}else{
			if(!empresas.checaCnpj(emitente.getCpfCnpj())){
				empresas.salvar(emitente);
			}else{
				throw new NegocioException("O CNPJ Informado ja consta no Banco de Dados");
			}
		}
	}
	
	@Transacional
	public void excluir(Empresa empresa) throws NegocioException{
		if (notasFiscais.getNotaFiscalPorEmpresa(empresa.getId()).size() == 0) {
			empresas.excluir(empresa);
		}else{
			throw new NegocioException("Não é possível excluir o emitente selecionado pois ele já possui notas emitidas");
		}
	}
	
	
	@Transacional
	public List<Empresa> empresas(){
		return empresas.empresas();
	}
	
	@Transacional
	public Empresa emitentePorId(Long id){
		return empresas.emitentePorId(id);
	}
	
}
