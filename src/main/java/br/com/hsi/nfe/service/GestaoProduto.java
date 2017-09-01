package br.com.hsi.nfe.service;

import br.com.hsi.nfe.model.Produto;
import br.com.hsi.nfe.model.dados.Cest;
import br.com.hsi.nfe.model.dados.Ncm;
import br.com.hsi.nfe.model.dados.UnidadeMedida;
import br.com.hsi.nfe.repository.ProdutoRepository;
import br.com.hsi.nfe.util.Transacional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class GestaoProduto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProdutoRepository produtos;
	
	
	@Transacional
	public void salvar(Produto produto) {
		produtos.salvar(produto);
	}
	
	@Transacional
	public void excluir(Produto produto){
		produtos.exclui(produto);
	}
	
	@Transacional
	public List<Produto> listarProdutos(){
		return produtos.listarProdutos();
	}
	
	@Transacional
	public List<UnidadeMedida> listaUnidadeMeida(){
		return produtos.listaUnidadeMedida();
	}

	@Transacional
	public Produto produtoPorId(Long idProduto) {
		return produtos.produtoPorId(idProduto);
	}
	
	@Transacional
	public int pesquisaUltimoCodigo() {
		return produtos.pesquisaUltimoCodigo();
	}
	
	@Transacional
	public Ncm ncmPorId(long idNcm) {
		return produtos.ncmPorId(idNcm);
	}

	public List<Ncm> listaNcms() {
		return produtos.listarNcm();
	}

	public List<Cest> listaCest() {
		return produtos.listarCest();
	}

}
