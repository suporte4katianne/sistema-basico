package br.com.hsi.service;

import br.com.hsi.model.Entidade;
import br.com.hsi.repository.NotaFiscalRepository;
import br.com.hsi.repository.TransportadoraRepository;
import br.com.hsi.util.Transacional;
import br.com.hsi.util.exception.NegocioException;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class GestaoTransportadora implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TransportadoraRepository transportadoras;
	@Inject
	private NotaFiscalRepository nfes;
	
	@Transacional
	public void salvar(Entidade transportadora) throws NegocioException {
		if(transportadora.getId() == null){			
			if(!transportadoras.checaCpfCnpj(transportadora.getCpfCnpj())){
				transportadoras.salvar(transportadora);
			}else{
				throw new NegocioException("O CNPJ Inofrmado ja consta na base de dados");
			}
		}else{
			transportadoras.salvar(transportadora);
		}
	}
	
	@Transacional
	public void excluir(Entidade transportadora) throws NegocioException{
		if(nfes.notaFiscalPorTransportadora(transportadora.getId()).size() == 0){
			transportadoras.excluir(transportadora);
		}else{
			throw new NegocioException("Não é possível excluir a transportadora selecionada pois o mesmo já possui notas emitidas");
		}
	}
	
	@Transacional
	public List<Entidade> listarTransportadoras(){
		return transportadoras.listarTransportadora();
	}
	
	@Transacional
	public Entidade transportadoraPorId(Long idTransportadora) {
		return transportadoras.transportadoraPorId(idTransportadora);
	}

}