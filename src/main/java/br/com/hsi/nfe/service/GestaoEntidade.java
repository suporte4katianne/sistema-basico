package br.com.hsi.nfe.service;

import br.com.hsi.nfe.model.Entidade;
import br.com.hsi.nfe.repository.EntidadeRepository;
import br.com.hsi.nfe.util.Transacional;
import br.com.hsi.nfe.util.exception.NegocioException;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class GestaoEntidade implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntidadeRepository clientes;
	
	
	@Transacional
	public void salvar(Entidade destinatario) throws NegocioException {

		if(!clientes.checaCnpj(destinatario.getCpfCnpj())){
			clientes.salvar(destinatario);
		}else if(destinatario.getId() > 0){
			clientes.salvar(destinatario);
		}else{
			throw new NegocioException("O CNPJ Informado ja consta no Banco de Dados");
		}
	}
	
	@Transacional
	public void excluir(Entidade destinatario) throws NegocioException{
		clientes.exclui(destinatario);
	}
	
	@Transacional
	public List<Entidade> listarEntidades(String tipoEntidade){
		return clientes.listarEntidades(tipoEntidade);
	}
	
	@Transacional
	public Entidade clientePorId(Long id){
		return clientes.clientePorId(id);
	}

}
