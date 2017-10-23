package br.com.hsi.service;

import br.com.hsi.model.Remessa;
import br.com.hsi.model.RemessaItem;
import br.com.hsi.model.dados.text.StatusRemessa;
import br.com.hsi.repository.RemessaRepository;
import br.com.hsi.util.Transacional;
import br.com.hsi.util.exception.NegocioException;

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
    public void salvar(Remessa remessa) throws NegocioException{
        if(remessa.getRepresentante() == null) {
            throw new NegocioException("Representante deve ser informado");
        } else if (remessa.getRemessaItens().isEmpty()) {
            throw new NegocioException("Pelomenos um item deve ser informado");
        } else {
            remessaRepository.salvar(remessa);
        }
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
    public Long sequenciaCodigo() {
        try {
            return remessaRepository.listarRemessas().get(0).getCodigo() + 1;
        } catch (Exception e) {
            return new Long(1);
        }
    }

    @Transacional
    public Long saldoRemessa(Remessa remessa) {
        return remessaRepository.saldoRemessa(remessa).longValue();
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
