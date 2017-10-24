package br.com.hsi.converter;

import br.com.hsi.model.AjusteEstoque;
import br.com.hsi.service.GestaoAjusteEstoque;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class AjusteEstoqueConverter implements Converter {

    @Inject
    private GestaoAjusteEstoque gestaoAjusteEstoque;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        try{
            AjusteEstoque ajusteEstoque;
            ajusteEstoque = gestaoAjusteEstoque.ajusteEstoquePorId(Long.parseLong(s));
            return ajusteEstoque;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        try {
            return String.valueOf(((AjusteEstoque) o).getId());
        } catch (Exception e) {
            return null;
        }

    }
}
