package br.com.hsi.controller;

import br.com.hsi.model.Empresa;
import br.com.hsi.model.Entidade;
import br.com.hsi.model.Praca;
import br.com.hsi.service.GestaoEntidade;
import br.com.hsi.service.GestaoPraca;
import br.com.hsi.util.exception.NegocioException;
import br.com.hsi.util.jsf.FacesUtil;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EntidadeBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private GestaoEntidade gestaoEntidade;
	@Inject
	private FacesContext facesContext;

	private Entidade entidade;
	private List<Entidade> entidades;
	private List<Entidade> entidadesFiltro;
	private Empresa empresa;
	
	@PostConstruct
	public void init() {
		if(facesContext.getViewRoot().getViewId().contains("Cliente")){
			entidades = gestaoEntidade.listarEntidades("C");
		}else if (facesContext.getViewRoot().getViewId().contains("Transportadora")) {
			entidades = gestaoEntidade.listarEntidades("T");
		}else if (facesContext.getViewRoot().getViewId().contains("Fornecedor")) {
			entidades = gestaoEntidade.listarEntidades("F");
		}else if (facesContext.getViewRoot().getViewId().contains("Representante")) {
			entidades = gestaoEntidade.listarEntidades("R");
		}else if (facesContext.getViewRoot().getViewId().contains("Vendedor")) {
			entidades = gestaoEntidade.listarEntidades("V");
		}
	}
	
	
	public void excluir(){
		try {
			gestaoEntidade.excluir(entidade);
			entidade = new Entidade();
			FacesUtil.addInfoMessage("Entidade removida com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	

	public Entidade getEntidade() {
		return entidade;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
		
		if(entidade == null){
			this.entidade = new Entidade();
		}
	}

	public List<Entidade> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<Entidade> entidades) {
		this.entidades = entidades;
	}

	public List<Entidade> getEntidadesFiltro() {
		return entidadesFiltro;
	}

	public void setEntidadesFiltro(List<Entidade> entidadesFiltro) {
		this.entidadesFiltro = entidadesFiltro;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	
}
