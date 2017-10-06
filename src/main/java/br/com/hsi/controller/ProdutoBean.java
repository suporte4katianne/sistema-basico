package br.com.hsi.controller;

import br.com.hsi.model.Produto;
import br.com.hsi.service.GestaoProduto;
import br.com.hsi.util.exception.NegocioException;
import br.com.hsi.util.jsf.FacesUtil;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private GestaoProduto gestaoProduto;

	private List<Produto> produtos;
	private List<Produto> produtosFiltro;
	private Produto produto;
	
	@PostConstruct
	public void init(){
		if(produtosFiltro != null){ 
			produtosFiltro.clear();
		}
		produtos = gestaoProduto.listarProdutos();
	}
	
	public void excluir() {
		try {
			gestaoProduto.excluir(produto);
			produtos = gestaoProduto.listarProdutos();
			produto = new Produto();
		} catch (NegocioException e){
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	

	// Getters and Setters //

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
		
		if(produto == null){
			produto = new Produto();
		}
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Produto> getProdutosFiltro() {
		return produtosFiltro;
	}

	public void setProdutosFiltro(List<Produto> produtosFiltro) {
		this.produtosFiltro = produtosFiltro;
	}
	
	
}
