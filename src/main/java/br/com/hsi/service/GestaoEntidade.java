package br.com.hsi.service;

import br.com.hsi.model.Entidade;
import br.com.hsi.model.Praca;
import br.com.hsi.repository.EntidadeRepository;
import br.com.hsi.util.Transacional;
import br.com.hsi.util.exception.NegocioException;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class GestaoEntidade implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntidadeRepository entidades;
	
	
	@Transacional
	public void salvar(Entidade destinatario) throws NegocioException {

		if(!entidades.checaCnpj(destinatario.getCpfCnpj())){
			entidades.salvar(destinatario);
		}else if(destinatario.getId() > 0){
			entidades.salvar(destinatario);
		}else{
			throw new NegocioException("O CNPJ Informado ja consta no Banco de Dados");
		}
	}
	
	@Transacional
	public void excluir(Entidade entidade) throws NegocioException{
		if(entidades.getNotasFiscaisDaEntidade(entidade).size() > 0) {
			entidades.excluir(entidade);
		} else {
			throw new NegocioException("Este cadastro possui vinculo com notas fiscais, não é possivel removelo");
		}
	}
	
	@Transacional
	public List<Entidade> listarEntidades(String tipoEntidade){
		return entidades.listarEntidades(tipoEntidade);
	}
	
	@Transacional
	public Entidade entidadePorId(Long id){
		return entidades.entidadePorId(id);
	}

	@Transacional
    public List<Entidade> entidadesPorPraca(Praca praca) throws NegocioException {
		return entidades.entidadesPorPraca(praca);
    }
}
