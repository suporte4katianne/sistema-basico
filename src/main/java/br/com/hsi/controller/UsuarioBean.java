package br.com.hsi.controller;

import br.com.hsi.model.Usuario;
import br.com.hsi.service.GestaoUsuario;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UsuarioBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private GestaoUsuario gestaoUsuario;

	
	private Usuario usuario;
	private List<Usuario> usuarios;
	private List<Usuario> usuariosFiltro;

	
	@PostConstruct
	private void init(){
	    usuarios = gestaoUsuario.listarUsuarios();
	}
	
	public Usuario getUsuario() {
		return usuario;	
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		
		if(usuario == null){
			usuario = new Usuario();
		}
	}


    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Usuario> getUsuariosFiltro() {
        return usuariosFiltro;
    }

    public void setUsuariosFiltro(List<Usuario> usuariosFiltro) {
        this.usuariosFiltro = usuariosFiltro;
    }
}
