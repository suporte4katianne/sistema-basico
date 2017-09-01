package br.com.hsi.nfe.service;

import br.com.hsi.nfe.model.Usuario;
import br.com.hsi.nfe.repository.UsuarioRepository;
import br.com.hsi.nfe.util.Transacional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class GestaoUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarios;

	@Transacional
	public Usuario porEmail(String email) {
		Usuario usuario = usuarios.validaUsuario(email);
		return usuario;
	}

	
	@Transacional
	public void salvar(Usuario usuario) {
		usuarios.salvar(usuario);
	}
	
	
	@Transacional	
	public List<Usuario> listarUsuarios(){
		return usuarios.listarUsuarios();
	}

	public Usuario usuarioPorId(Long parseLong) {
		return usuarios.usuarioPorId(parseLong);
	}
	
}
