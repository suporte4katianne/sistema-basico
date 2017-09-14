package br.com.hsi.converter;

import br.com.hsi.model.NotaFiscal;
import br.com.hsi.service.GestaoNotaFiscal;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class NotaFiscalConverter implements Converter{

	@Inject
	private GestaoNotaFiscal gestaoNotaFiscal;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			NotaFiscal notaFiscal;
			notaFiscal = gestaoNotaFiscal.notaFiscalPorId(Long.parseLong(value));
			return notaFiscal;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			return String.valueOf(((NotaFiscal) value).getId());
		} catch (Exception e) {
			return null;
		}
	}



}
