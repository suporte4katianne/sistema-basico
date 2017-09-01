package br.com.hsi.nfe.converter;

import br.com.hsi.nfe.model.dados.Cidade;
import br.com.hsi.nfe.service.GestaoEndereco;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class CidadeConverter implements Converter{
	
	@Inject
	private GestaoEndereco gestaoEndereco;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			Cidade cidade = new Cidade();
			cidade = gestaoEndereco.cidadePorId(Long.parseLong(value));
			return cidade;
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			return String.valueOf(((Cidade) value).getIdCidade());
		} catch (Exception e) {

		}
		return null;
	}
}
