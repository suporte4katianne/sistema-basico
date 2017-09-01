package br.com.hsi.nfe.repository;

import br.com.hsi.nfe.model.Usuario;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public class UsuarioRepository implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario salvar(Usuario usuario) {
		return manager.merge(usuario);

	}

	public Usuario validaUsuario(String usuario) {
		try{			
			TypedQuery<Usuario> selectQuery = manager.createQuery("FROM Usuario WHERE lower(email) = :usuario", Usuario.class);
			selectQuery.setParameter("usuario", usuario.toLowerCase());
			return selectQuery.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Usuario> listarUsuarios(){
		TypedQuery<Usuario> selectQuery = manager.createQuery("FROM Usuario ORDER BY id DESC", Usuario.class);
		return selectQuery.getResultList();
	}

	public Usuario porEmail(String email) {
		Usuario usuario = null;

		try {
			usuario = this.manager.createQuery("from Usuario where lower(email) = :email", Usuario.class).setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			System.out.println(e.getMessage());
		}

		return usuario;
	}

	public Usuario usuarioPorId(Long parseLong) {
		return manager.find(Usuario.class, parseLong);
	}
}
