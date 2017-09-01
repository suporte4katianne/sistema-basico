package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.Usuario;
import br.com.hsi.nfe.service.GestaoUsuario;
import br.com.hsi.nfe.util.jsf.FacesUtil;
import br.com.hsi.nfe.util.mail.Mailer;
import com.outjected.email.api.MailMessage;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class RecuperaSenhaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private GestaoUsuario gestaoUsuario;
	
	@Email
	private String email;
	@CNPJ
	private String cnpj;
	@Inject
	private Mailer mailer;
	
	
	public void enviaRecurepacaoSenha(){
		System.out.println(email);
		Usuario usuario = gestaoUsuario.porEmail(email);
		criaEmail(usuario);
		email =  "";
		cnpj = "";
		FacesUtil.addInfoMessage("Verifique sua Caixa de Entrada, no enderço de E-mail informado");
	}

	
	private void criaEmail(Usuario usuario){
		MailMessage message = mailer.novaMensagem();
		
		message
			.to(email)
			.from("emitirnota@hsiautomacao.com.br")
			.subject("Recuperação de Senha")
			.bodyText(usuario.getNome()+"\nSua Senha é: "+usuario.getSenha()+"\nSe você não solicitou esse envio entre em contato conosco\nHSI Automação Comercial\n(49) 3223-4366")
			.send();
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
}
