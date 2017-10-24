package br.com.hsi.converter;

import br.com.hsi.model.Empresa;
import br.com.hsi.service.GestaoEmpresa;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class EmitenteConverter implements Converter {

	@Inject
	private GestaoEmpresa gestaoEmitente;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Empresa emitente = new Empresa();
		try {
			emitente = gestaoEmitente.emitentePorId(Long.parseLong(value));
			return emitente;
		} catch (Exception e) {
			return new Empresa();
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			return String.valueOf(((Empresa) value).getId());
		} catch (Exception e) {
			return "";
		}
	}

}
