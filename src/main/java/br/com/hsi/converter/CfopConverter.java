package br.com.hsi.converter;

import br.com.hsi.model.dados.Cfop;
import br.com.hsi.service.GestaoNotaFiscal;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class CfopConverter implements Converter {
	
	@Inject
	private GestaoNotaFiscal gestaoNfe;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			Cfop cfop;
			cfop = gestaoNfe.cfopPorId(Long.parseLong(value));
			return cfop;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			return String.valueOf(((Cfop) value).getId());
		} catch (Exception e) {
			return null;
		}
	}

}
