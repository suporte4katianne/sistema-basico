package br.com.hsi.service;

import br.com.hsi.model.Remessa;
import br.com.hsi.model.RemessaItem;
import br.com.hsi.model.dados.text.StatusRemessa;
import br.com.hsi.repository.RemessaRepository;
import br.com.hsi.util.Transacional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Classe com regras de negócio para cadastro {@link br.com.hsi.model.Remessa} e {@link br.com.hsi.model.RemessaItem}
 *
 * @author Eriel Miquilino
 */

public class GestaoRemessa implements Serializable{

    @Inject
    private RemessaRepository remessaRepository;

    @Transacional
    public void salvar(Remessa remessa) {
        remessaRepository.salvar(remessa);
    }

    @Transacional
    public void excluir(Remessa remessa) {
        remessaRepository.excluir(remessa);
    }


    /**
     * Listar Remessas cadastradas
     *
     * @param statusRemessa {@link StatusRemessa} - Enum com status da remessa, null para retornar todas as remessas
     * @return List<RemessaItem></> - Lista de remessas
     */

    @Transacional
    public List<Remessa> listarRemessas(StatusRemessa statusRemessa) {
        if(statusRemessa != null) {
            return remessaRepository.listarRemessas(statusRemessa);
        } else {
            return remessaRepository.listarRemessas();
        }
    }

    @Transacional
    public Remessa remessaPorId(Long id) {
        return remessaRepository.remessaPorId(id);
    }

    @Transacional
    public List<RemessaItem> remessaItemPorRemessa(Remessa remessa) {
        return remessaRepository.remessaItemPorRemessa(remessa);
    }

}
