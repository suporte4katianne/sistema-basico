package br.com.hsi.converter;

import br.com.hsi.model.Entidade;
import br.com.hsi.service.GestaoEntidade;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class EntidadeConverter implements Converter{
	
	@Inject
	private GestaoEntidade gestaoEntidade;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			Entidade entidade;
			entidade = gestaoEntidade.entidadePorId(Long.parseLong(value));
			return entidade;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			return String.valueOf(((Entidade) value).getId());
		} catch (Exception e) {
			return "";
		}
	}

}
