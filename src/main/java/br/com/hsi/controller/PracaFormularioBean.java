package br.com.hsi.controller;

import br.com.hsi.model.Praca;
import br.com.hsi.service.GestaoPraca;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * Classe de controle para a pagina PracaFormulario.xhtml
 *
 * - Salva nova Praca
 *
 * - Edita Praca
 *
 * @author Eriel Miquilino
 */

@Named
@ViewScoped
public class PracaFormularioBean implements Serializable {

    @Inject
    private GestaoPraca gestaoPraca;

    private Praca praca;

    public void inicializar() {
        if(praca == null) {
            praca = new Praca();
        }
    }


    public void salvar () throws IOException {
        gestaoPraca.salvar(praca);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Hawker/Praca.xhtml");
    }

    public Praca getPraca() {
        return praca;
    }

    public void setPraca(Praca praca) {
        this.praca = praca;
    }
}
