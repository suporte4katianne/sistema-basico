package br.com.hsi.converter;

import br.com.hsi.model.Praca;
import br.com.hsi.service.GestaoPraca;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Conversor de praça
 *
 * @author Eriel Miquilino
 */

@Named
@ApplicationScoped
public class PracaConverter implements Converter {

    @Inject
    private GestaoPraca gestaoPraca;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        try{
            Praca praca;
            praca = gestaoPraca.pracaPorId(Long.parseLong(s));
            return praca;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        try {
            return String.valueOf(((Praca) o).getId());
        } catch (Exception e) {
            return "";
        }
    }
}
