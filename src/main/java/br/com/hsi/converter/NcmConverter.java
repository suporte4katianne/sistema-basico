package br.com.hsi.converter;

import br.com.hsi.model.dados.Ncm;
import br.com.hsi.service.GestaoProduto;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@ApplicationScoped
public class NcmConverter implements Converter {
	@Inject
	private GestaoProduto gestaoProduto;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try{
        	Ncm estado = new Ncm();
        	estado = gestaoProduto.ncmPorId(Long.parseLong(value));
        	return estado;
        }catch (Exception e) {
        	return null;        	
        }
        
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try{
			return String.valueOf(((Ncm) value).getId());
		}catch (Exception e) {
			return null;
		}
	}
}
