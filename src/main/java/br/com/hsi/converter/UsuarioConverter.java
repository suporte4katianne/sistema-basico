package br.com.hsi.converter;

import br.com.hsi.model.Usuario;
import br.com.hsi.service.GestaoUsuario;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class UsuarioConverter implements Converter {

	@Inject
	private GestaoUsuario gestaoUsuario;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario usuario = new Usuario();
		try {
			usuario = gestaoUsuario.usuarioPorId(Long.parseLong(value));
			return usuario;
		} catch (Exception e) {
			return usuario = null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			return String.valueOf(((Usuario) value).getId());
		} catch (Exception e) {
			return "";
		}
	}

}
