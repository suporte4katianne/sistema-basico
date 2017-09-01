package br.com.hsi.nfe.converter;

import br.com.hsi.nfe.model.dados.Cfop;
import br.com.hsi.nfe.service.GestaoNotaFiscal;

import javax.faces.bean.ApplicationScoped;
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
