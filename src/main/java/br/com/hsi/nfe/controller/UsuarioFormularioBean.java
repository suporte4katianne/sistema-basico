package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.Usuario;
import br.com.hsi.nfe.service.GestaoEmpresa;
import br.com.hsi.nfe.service.GestaoUsuario;
import br.com.hsi.nfe.util.jsf.FacesUtil;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;


@Named
@ViewScoped
public class UsuarioFormularioBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GestaoUsuario gestaoUsuario;
	@Inject
	private GestaoEmpresa gestaoEmpresa;
	private Usuario usuario;

	
	public void inicializar(){
		if(usuario == null){
			usuario = new Usuario();
		}
	}
	
	public void salvar() throws IOException{
	    usuario.setEmpresa(gestaoEmpresa.emitentePorId((long) 1));
		gestaoUsuario.salvar(usuario);
		FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
		FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Usuario.xhtml");
	}


	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

