package br.com.hsi.service;

import br.com.hsi.model.dados.Cep;
import br.com.hsi.model.dados.Cidade;
import br.com.hsi.model.dados.Estado;
import br.com.hsi.repository.EnderecoRepository;
import br.com.hsi.util.Transacional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class GestaoEndereco implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EnderecoRepository enderecos;
	
	@Transacional
	public Estado estadoPorId(Long id){
		Estado estado = enderecos.estadoPorId(id);
		return estado;
	}
	
	@Transacional
	public Cidade cidadePorId(Long id){
		Cidade cidade = enderecos.cidadePorId(id);
		return cidade;
	}
	
	@Transacional
	public Cidade cidadePorCodigoIbge(String codigoIbge){
		Cidade cidade = enderecos.cidadePorCodigoIbge(codigoIbge);
		return cidade;
	}
	
	@Transacional
	public Estado estadoPorCodigoIbge(String codigoIbge){
		Estado estado = enderecos.estadoPorCodigoIbge(codigoIbge);
		return estado;	
	}
	
	@Transacional
	public List<Estado> estados(){
		List<Estado> estados = enderecos.estados();
		return estados;
	}
	
	@Transacional
	public List<Cidade> cidades(){
		List<Cidade> cidades = enderecos.cidades();
		return cidades;
	}
	
	@Transacional
	public List<Cidade> cidadePorEstado(Long id){
		List<Cidade> cidades = enderecos.cidadePorEstado(id);
		return cidades;
	}
	
	public Cep cep(String cep){
		Cep retCep = enderecos.cep(cep);
		return retCep;
	}

}
