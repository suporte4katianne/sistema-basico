package br.com.hsi.service;

import br.com.hsi.model.Kit;
import br.com.hsi.repository.KitRepository;
import br.com.hsi.util.Transacional;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * @author Eriel Miquilino
 */
public class GestaoKit implements Serializable {

    @Inject
    private KitRepository kitRepository;

    @Transacional
    public void salvarKit(Kit kit) {
        kitRepository.salvarKit(kit);
    }

    @Transacional
    public Kit kitPorId(Long id) {
        return kitRepository.kitPorId(id);
    }
}
