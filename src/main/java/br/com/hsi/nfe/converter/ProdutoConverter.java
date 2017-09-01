package br.com.hsi.nfe.converter;

import br.com.hsi.nfe.model.Produto;
import br.com.hsi.nfe.service.GestaoProduto;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ProdutoConverter implements Converter{

	@Inject
	private GestaoProduto gestaoProduto;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			Produto produto;
			produto = gestaoProduto.produtoPorId(Long.parseLong(value));
			return produto;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			return String.valueOf(((Produto) value).getId());
		} catch (Exception e) {
			return null;
		}
	}



}
