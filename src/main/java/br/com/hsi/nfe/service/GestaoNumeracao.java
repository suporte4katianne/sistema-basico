package br.com.hsi.nfe.service;

import br.com.hsi.nfe.model.Numeracao;
import br.com.hsi.nfe.repository.NumeracaoRepository;
import br.com.hsi.nfe.util.Transacional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class GestaoNumeracao implements Serializable {

    @Inject
    private NumeracaoRepository numeracaoRepository;

    @Transacional
    public void salvar(Numeracao numeraca) {
        numeracaoRepository.salvar(numeraca);
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

}
