package br.com.hsi.converter;

import br.com.hsi.model.Embalagem;
import br.com.hsi.service.GestaoProduto;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class EmbalagemConverter implements Converter {

    @Inject
    private GestaoProduto gestaoProduto;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Embalagem embalagem;
        try {
            embalagem = gestaoProduto.embalagemPorId(Long.parseLong(value));
            return embalagem;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return String.valueOf(((Embalagem) value).getId());
        } catch (Exception e) {
            return "";
        }
    }
}
