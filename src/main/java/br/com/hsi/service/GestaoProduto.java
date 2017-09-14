package br.com.hsi.service;

import br.com.hsi.model.Embalagem;
import br.com.hsi.model.Produto;
import br.com.hsi.model.dados.Ncm;
import br.com.hsi.model.dados.UnidadeMedida;
import br.com.hsi.model.dados.Cest;
import br.com.hsi.repository.ProdutoRepository;
import br.com.hsi.util.Transacional;

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
	public List<UnidadeMedida> listaUnidadeMedida(){
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

	@Transacional
	public List<Ncm> listaNcms() {
		return produtos.listarNcm();
	}

	@Transacional
	public List<Cest> listaCest() {
		return produtos.listarCest();
	}

	@Transacional
    public Embalagem embalagemPorId(long id) {
		return produtos.embalagemPorId(id);
    }

	@Transacional
	public UnidadeMedida unidadeMedidaPorId(long id) {
		return produtos.unidadeMedidaPorId(id);
	}

	@Transacional
	public void salvarUnidadeMedida(UnidadeMedida unidadeMedida) {
		produtos.salvarUnidadeMedida(unidadeMedida);
	}
}
