package br.com.hsi.converter;

import br.com.hsi.model.Kit;
import br.com.hsi.service.GestaoKit;
import br.com.hsi.service.GestaoNotaFiscal;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Eriel Miquilino
 */
@Named
@ApplicationScoped
public class KitConverter implements Converter{

    @Inject
    private GestaoKit gestaoKit;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Kit kit = gestaoKit.kitPorId(Long.parseLong(value));
            return kit;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return String.valueOf(((Kit) value).getId());
        } catch (Exception e) {
            return "";
        }
    }
}
