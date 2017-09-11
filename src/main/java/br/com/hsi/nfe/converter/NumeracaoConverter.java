package br.com.hsi.nfe.converter;

import br.com.hsi.nfe.model.Numeracao;
import br.com.hsi.nfe.service.GestaoNumeracao;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class NumeracaoConverter implements Converter {
	
	@Inject
	private GestaoNumeracao gestaoNumeracao;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			Numeracao numeracao;
			numeracao = gestaoNumeracao.numeracaoPorId(Long.parseLong(value));
			return numeracao;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			return String.valueOf(((Numeracao) value).getId());
		} catch (Exception e) {
			return "";
		}
	}

}