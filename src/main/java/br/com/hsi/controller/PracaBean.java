package br.com.hsi.controller;

import br.com.hsi.model.Praca;
import br.com.hsi.service.GestaoPraca;
import br.com.hsi.util.jsf.FacesUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Classe de controle para pagina Praca.xhtml
 *
 * - Lista praças cadastradas
 *
 * - Exclui praças
 *
 * @author Eriel Miquilino
 */

@Named
@ViewScoped
public class PracaBean implements Serializable {

    @Inject
    private GestaoPraca gestaoPraca;

    private List<Praca> pracas;
    private List<Praca> pracasFiltro;
    private Praca praca;

    @PostConstruct
    public void init() {
        pracas = gestaoPraca.listarPracas();
    }

    public void excluir() {
        gestaoPraca.excluir(praca);
        FacesUtil.addInfoMessage("Registro removido com sucesso!");
    }

    public List<Praca> getPracas() {
        return pracas;
    }

    public void setPracas(List<Praca> pracas) {
        this.pracas = pracas;
    }

    public List<Praca> getPracasFiltro() {
        return pracasFiltro;
    }

    public void setPracasFiltro(List<Praca> pracasFiltro) {
        this.pracasFiltro = pracasFiltro;
    }

    public Praca getPraca() {
        return praca;
    }

    public void setPraca(Praca praca) {
        this.praca = praca;
    }
}
