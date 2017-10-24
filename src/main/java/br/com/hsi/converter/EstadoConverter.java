package br.com.hsi.converter;

import br.com.hsi.model.dados.Estado;
import br.com.hsi.service.GestaoEndereco;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@ApplicationScoped
public class EstadoConverter implements Converter {
	
	@Inject
	private GestaoEndereco gestaoEndereco;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try{
        	Estado estado = new Estado();
        	estado = gestaoEndereco.estadoPorId(Long.parseLong(value));
        	return estado;
        }catch (Exception e) {
        	return null;        	
        }
        
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try{
			return String.valueOf(((Estado) value).getIdEstado());
		}catch (Exception e) {
			return null;
		}
	}

}
