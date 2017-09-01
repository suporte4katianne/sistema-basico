package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.Usuario;
import br.com.hsi.nfe.service.GestaoUsuario;
import br.com.hsi.nfe.util.jsf.FacesUtil;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class AlterarSenhaBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GestaoUsuario gestaoUsuario;
	
	private Usuario usuario;
	private String senhaAtual;
	private String confirmaSenhaAtual;
	
	public void inicializar() throws IOException{
		senhaAtual = usuario.getSenha();
	}
	
	public void alterarSenha() {
		if (senhaAtual.equals(confirmaSenhaAtual)) {
			gestaoUsuario.salvar(usuario);
			FacesUtil.addInfoMessage("Senha alterada com sucesso!");
		} else {
			FacesUtil.addErrorMessage("Sua senha atual esta incorreta");
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getConfirmaSenhaAtual() {
		return confirmaSenhaAtual;
	}

	public void setConfirmaSenhaAtual(String confirmaSenhaAtual) {
		this.confirmaSenhaAtual = confirmaSenhaAtual;
	}
}
