package br.com.hsi.converter;

import br.com.hsi.model.Remessa;
import br.com.hsi.service.GestaoRemessa;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Conversor para {@link br.com.hsi.model.Remessa}
 *
 * @author Eriel Miquilino
 */

@Named
@ApplicationScoped
public class RemessaConverter implements Converter {

    @Inject
    private GestaoRemessa gestaoRemessa;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        try{
            Remessa remessa = gestaoRemessa.remessaPorId(Long.parseLong(s));
            return remessa;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        try {
            return String.valueOf(((Remessa) o).getId());
        } catch (Exception e) {
            return "";
        }
    }
}

