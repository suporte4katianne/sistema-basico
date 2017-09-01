package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.Empresa;
import br.com.hsi.nfe.service.GestaoEmpresa;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EmpresaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GestaoEmpresa gestaoEmpresa;


	private Empresa empresa = new Empresa();
	private List<Empresa> empresasFiltro;
	private List<Empresa> empresas;

	@PostConstruct
	public void init() {
		empresas = gestaoEmpresa.empresas();

	}
	
	//-------------- Getters and Setters --------------

	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public List<Empresa> getEmpresasFiltro() {
		return empresasFiltro;
	}
	public void setEmpresasFiltro(List<Empresa> empresasFiltro) {
		this.empresasFiltro = empresasFiltro;
	}
	
}
