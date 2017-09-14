package br.com.hsi.service;

import br.com.hsi.model.Numeracao;
import br.com.hsi.repository.NumeracaoRepository;
import br.com.hsi.util.Transacional;
import br.com.hsi.util.exception.NegocioException;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class GestaoNumeracao implements Serializable {

    @Inject
    private NumeracaoRepository numeracaoRepository;

    @Transacional
    public void salvar(Numeracao numeracao) throws NegocioException {
        if(numeracao.getId() == null){
            if(!numeracaoRepository.verificaExistentes(numeracao)){
                numeracaoRepository.salvar(numeracao);
            } else {
                throw new NegocioException("Este valor já existe no banco de dados");
            }
        } else {
            numeracaoRepository.salvar(numeracao);
        }
    }

    @Transacional
    public void excluir(Numeracao numeraca) {
        numeracaoRepository.excluir(numeraca);
    }

    @Transacional
    public Numeracao numeracaoPorId(long id) {
        return numeracaoRepository.numeracaoPorId(id);
    }

    @Transacional
    public List<Numeracao> numeracoes() {
        return numeracaoRepository.numeracoes();
    }

    @Transacional
    public void atualizaSequenciaNumeracao(Numeracao numeracao) {
        numeracao.setNumero( (numeracao.getNumero() + 1) );
        numeracaoRepository.salvar(numeracao);
    }
}
