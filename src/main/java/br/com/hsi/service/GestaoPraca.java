package br.com.hsi.service;

import br.com.hsi.model.Praca;
import br.com.hsi.repository.PracaRepository;
import br.com.hsi.util.Transacional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Classe com regras de negôcio para cadastro de praças
 *
 * @author Eriel Miquilino
 */
public class GestaoPraca implements Serializable {

    @Inject
    private PracaRepository pracaRepository;

    @Transacional
    public void salvar(Praca praca) {
        pracaRepository.salvar(praca);
    }

    @Transacional
    public void excluir(Praca praca) {
        pracaRepository.excluir(praca);
    }

    @Transacional
    public List<Praca> listarPracas() {
        return pracaRepository.listarPracas();
    }

    @Transacional
    public Praca pracaPorId(Long id) {
        return pracaRepository.pracaPorId(id);
    }

}
