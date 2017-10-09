package br.com.hsi.controller;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author Eriel Miquilino
 */

@Named
@ViewScoped
public class GridBean implements Serializable {

    public void redrirect(String caminho, String parametro, String valor) throws IOException {
        FacesContext.getCurrentInstance().
                getExternalContext().redirect("/HSI/Sistemas/" + caminho + ".xhtml?" + parametro + "=" + valor);
    }

}
