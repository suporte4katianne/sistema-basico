package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.Empresa;
import br.com.hsi.nfe.model.Entidade;
import br.com.hsi.nfe.service.GestaoEntidade;
import br.com.hsi.nfe.util.exception.NegocioException;
import br.com.hsi.nfe.util.jsf.FacesUtil;

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
		}else{
			entidades = gestaoEntidade.listarEntidades("T");
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
			entidade = new Entidade();
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
