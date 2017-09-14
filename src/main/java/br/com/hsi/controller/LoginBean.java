package br.com.hsi.controller;

import br.com.hsi.util.jsf.FacesUtil;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletRequest request;

	@Inject
	private HttpServletResponse response;

	private String email;

	public void preRender() {
		if ("true".equals(request.getParameter("disabled"))) {
			FacesUtil.addErrorMessage("Seu Usuário esta Inativo");
		}else if ("true".equals(request.getParameter("invalid"))) {
			FacesUtil.addErrorMessage("Usuário ou Senha invalido");			
		}else if ("true".equals(request.getParameter("locked"))) {
			FacesUtil.addErrorMessage("Sua licença esta bloqueada, entre em contato com seu representante");
		}
	}

	public void login() throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Acesso");
		dispatcher.forward(request, response);

		facesContext.responseComplete();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}