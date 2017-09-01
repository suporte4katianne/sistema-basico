package br.com.hsi.nfe.converter;

import br.com.hsi.nfe.model.Inutilizacao;
import br.com.hsi.nfe.service.GestaoNotaFiscal;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class InutilizacaoConverter implements Converter {

    @Inject
    private GestaoNotaFiscal gestaoNotaFiscal;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Inutilizacao inutilizacao;
            inutilizacao = gestaoNotaFiscal.inutilizacaoPorId(Long.parseLong(value));
            return inutilizacao;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return String.valueOf(((Inutilizacao) value).getId());
        } catch (Exception e) {

        }
        return null;
    }
}
