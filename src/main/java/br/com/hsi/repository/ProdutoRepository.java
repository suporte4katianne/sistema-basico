package br.com.hsi.repository;

import br.com.hsi.model.Embalagem;
import br.com.hsi.model.Produto;
import br.com.hsi.model.dados.Cest;
import br.com.hsi.model.dados.Ncm;
import br.com.hsi.model.dados.UnidadeMedida;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public class ProdutoRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Produto salvar(Produto produto){
		return manager.merge(produto);
	}
	
	public void exclui(Produto produto){
		Object obejtoProduto =  manager.merge(produto);
		this.manager.remove(obejtoProduto);
	}
	
	public List<Produto> listarProdutos(){
		TypedQuery<Produto> selectQuery = manager.createQuery("FROM Produto ORDER BY id DESC", Produto.class);
		List<Produto> listaProduto = selectQuery.getResultList();
		return listaProduto;
	}
	
	public List<UnidadeMedida> listaUnidadeMedida(){
		TypedQuery<UnidadeMedida> selectQuery = manager.createQuery("FROM UnidadeMedida", UnidadeMedida.class);
		List<UnidadeMedida> listaUnidadeMeida =  selectQuery.getResultList();
		return listaUnidadeMeida;
	}

	public Produto produtoPorId(Long idProduto) {
		return manager.find(Produto.class, idProduto);
	}

	public int pesquisaUltimoCodigo() {
		return manager.createQuery("SELECT MAX (u.codigo) FROM Produto u", Integer.class).getSingleResult();
	}
	
	public List<Ncm> listarNcm() {
		TypedQuery<Ncm> selectQuery = manager.createQuery("FROM Ncm", Ncm.class);
		return selectQuery.getResultList();
	}
	
	public List<Cest> listarCest() {
		TypedQuery<Cest> selectQuery = manager.createQuery("FROM Cest", Cest.class);
		return selectQuery.getResultList();
	}

	public Ncm ncmPorId(long idNcm) {
		return manager.find(Ncm.class, idNcm);
	}

    public Embalagem embalagemPorId(long id) {
		return manager.find(Embalagem.class, id);
    }

    public UnidadeMedida unidadeMedidaPorId(long id) {
        return manager.find(UnidadeMedida.class, id);
    }

    public UnidadeMedida salvarUnidadeMedida(UnidadeMedida unidadeMedida) {
        return manager.merge(unidadeMedida);
    }
}
