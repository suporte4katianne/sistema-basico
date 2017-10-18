package br.com.hsi.controller;

import br.com.hsi.model.Remessa;
import br.com.hsi.service.GestaoRemessa;
import br.com.hsi.util.jsf.FacesUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Classe de controle para pagina Remessa.xhtml
 *
 * - {@link java.util.List}<{@link br.com.hsi.model.Remessa}></> cadastradas
 *
 * - Exclui {@link br.com.hsi.model.Remessa}
 *
 * @author Eriel Miquilino
 */

@Named
@ViewScoped
public class RemessaBean implements Serializable {

    @Inject
    private GestaoRemessa gestaoRemessa;

    private List<Remessa> remessas;
    private List<Remessa> remessasFiltro;
    private Remessa remessa;

    @PostConstruct
    public void init() {
        remessas = gestaoRemessa.listarRemessas(null);
    }

    public void excluir() {
        gestaoRemessa.excluir(remessa);
        FacesUtil.addInfoMessage("Registro removido com sucesso!");
    }

    public List<Remessa> getRemessas() {
        return remessas;
    }

    public void setRemessas(List<Remessa> remessas) {
        this.remessas = remessas;
    }

    public List<Remessa> getRemessasFiltro() {
        return remessasFiltro;
    }

    public void setRemessasFiltro(List<Remessa> remessasFiltro) {
        this.remessasFiltro = remessasFiltro;
    }

    public Remessa getRemessa() {
        return remessa;
    }

    public void setRemessa(Remessa remessa) {
        this.remessa = remessa;
    }
}
