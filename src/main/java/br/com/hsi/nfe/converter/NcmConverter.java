package br.com.hsi.nfe.converter;

import br.com.hsi.nfe.model.dados.Ncm;
import br.com.hsi.nfe.service.GestaoProduto;

import javax.faces.bean.ApplicationScoped;
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
