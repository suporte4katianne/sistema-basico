package br.com.hsi.converter;

import br.com.hsi.model.dados.UnidadeMedida;
import br.com.hsi.service.GestaoProduto;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class UnidadeMedidaConverter implements Converter {

    @Inject
    private GestaoProduto gestaoProduto;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        UnidadeMedida unidadeMedida;
        try {
            unidadeMedida = gestaoProduto.unidadeMedidaPorId(Long.parseLong(value));
            return unidadeMedida;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return String.valueOf(((UnidadeMedida) value).getId());
        } catch (Exception e) {
            return "";
        }
    }

}
