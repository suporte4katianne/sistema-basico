package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.Empresa;
import br.com.hsi.nfe.model.Produto;
import br.com.hsi.nfe.model.dados.Cest;
import br.com.hsi.nfe.model.dados.Cfop;
import br.com.hsi.nfe.model.dados.Ncm;
import br.com.hsi.nfe.model.dados.UnidadeMedida;
import br.com.hsi.nfe.service.GestaoNotaFiscal;
import br.com.hsi.nfe.service.GestaoProduto;
import br.com.hsi.nfe.util.jsf.FacesUtil;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProdutoFormularioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private GestaoProduto gestaoProduto;
	@Inject
	private GestaoNotaFiscal gestaoNotaFiscal;
	
	private Produto produto;
	private List<Empresa> emitentes;
	private List<Produto> produtos;
	private List<UnidadeMedida> unidadeMedida;
	private List<Ncm> ncms;
	private List<Ncm> ncmsFiltro;
	private List<Cest> cest;
	private List<Cest> cestFiltro;
	private List<Cfop> cfopsEstaduais;
	private List<Cfop> cfopsInterestaduais;

	public void inicializar(){
		cfopsEstaduais = gestaoNotaFiscal.cfopPorTipoeOperacao("1", "1");
		cfopsInterestaduais = gestaoNotaFiscal.cfopPorTipoeOperacao("1", "2");
		unidadeMedida = gestaoProduto.listaUnidadeMeida();
		ncms = gestaoProduto.listaNcms();
		cest = gestaoProduto.listaCest();
		if(produto == null){
			produto = new Produto();
			try {
				produto.setCodigo(gestaoProduto.pesquisaUltimoCodigo() + 1);
			}catch (NullPointerException e){
				produto.setCodigo(1);

			}
		}
	}
	
	public void salvar() throws IOException {
		if(produto.getCfopEstadual() != null){
			produto.setCodigoCfopEstadual(produto.getCfopEstadual().getCodigo());
		}
		if(produto.getCfopInterestadual() != null){
			produto.setCodigoCfopInterestadual(produto.getCfopInterestadual().getCodigo());
		}
		gestaoProduto.salvar(produto);
		produto = new Produto();
		FacesUtil.addInfoMessage("Produto Salvo com sucesso!");
		FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Produto.xhtml");
	}

	public void selecionaNcm() {
		produto.setCodigo_ncm(produto.getNcm().getNcm());
	}
	
	public void selecionaCest() {
		produto.setCodigo_cest(produto.getCest().getCest());
	}

	// Getters and Setters //

	public Produto getProduto() {
		return produto;
	}

	public List<UnidadeMedida> getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(List<UnidadeMedida> unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Empresa> getEmitentes() {
		
		return emitentes;
	}

	public void setEmitentes(List<Empresa> emitentes) {
		this.emitentes = emitentes;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Ncm> getNcms() {
		return ncms;
	}

	public void setNcms(List<Ncm> ncms) {
		this.ncms = ncms;
	}

	public List<Ncm> getNcmsFiltro() {
		return ncmsFiltro;
	}

	public void setNcmsFiltro(List<Ncm> ncmsFiltro) {
		this.ncmsFiltro = ncmsFiltro;
	}

	public List<Cest> getCest() {
		return cest;
	}

	public void setCest(List<Cest> cest) {
		this.cest = cest;
	}

	public List<Cest> getCestFiltro() {
		return cestFiltro;
	}

	public void setCestFiltro(List<Cest> cestFiltro) {
		this.cestFiltro = cestFiltro;
	}

	public List<Cfop> getCfopsEstaduais() {
		return cfopsEstaduais;
	}

	public void setCfopsEstaduais(List<Cfop> cfopsEstaduais) {
		this.cfopsEstaduais = cfopsEstaduais;
	}

	public List<Cfop> getCfopsInterestaduais() {
		return cfopsInterestaduais;
	}

	public void setCfopsInterestaduais(List<Cfop> cfopsInterestaduais) {
		this.cfopsInterestaduais = cfopsInterestaduais;
	}
}
