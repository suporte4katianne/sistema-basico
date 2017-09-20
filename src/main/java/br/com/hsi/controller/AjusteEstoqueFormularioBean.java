package br.com.hsi.controller;


import br.com.hsi.model.AjusteEstoque;
import br.com.hsi.model.AjusteEstoqueItem;
import br.com.hsi.model.Movimentacao;
import br.com.hsi.model.Produto;
import br.com.hsi.security.Seguranca;
import br.com.hsi.service.GestaoAjusteEstoque;
import br.com.hsi.service.GestaoProduto;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class AjusteEstoqueFormularioBean implements Serializable {

    @Inject
    private GestaoAjusteEstoque gestaoAjusteEstoque;
    @Inject
    private GestaoProduto gestaoProduto;
    @Inject
    private Seguranca seguranca;

    private AjusteEstoque ajusteEstoque;

    private AjusteEstoqueItem ajusteEstoqueItem;

    private List<Produto> produtos;
    private List<Produto> produtosFiltro;

    public void inicializar() {
        if(ajusteEstoque == null) {
            ajusteEstoque = new AjusteEstoque();
            ajusteEstoque.setEmpresa(seguranca.getUsuarioLogado().getUsuario().getEmpresa());
            ajusteEstoque.setData(new Date(System.currentTimeMillis()));
            ajusteEstoqueItem = new AjusteEstoqueItem();
        }
        produtos = gestaoProduto.listarProdutos();
    }

    public void carregaDadosDoProduto() {
        ajusteEstoqueItem.setAjusteEstoque(ajusteEstoque);
    }

    public void incluirProduto() {
        ajusteEstoque.getAjusteEstoqueItens().add(ajusteEstoqueItem);
        ajusteEstoqueItem = new AjusteEstoqueItem();
    }

    public void removerProduto(AjusteEstoqueItem ajusteEstoqueItem) {
        ajusteEstoque.getAjusteEstoqueItens().remove(ajusteEstoqueItem);
    }

    public void editarProduto(AjusteEstoqueItem ajusteEstoqueItem) {
        this.ajusteEstoqueItem = ajusteEstoqueItem;
        ajusteEstoque.getAjusteEstoqueItens().remove(ajusteEstoqueItem);
    }

    public void salvar() throws IOException {
        geraMovimentacao();
        gestaoAjusteEstoque.salvar(ajusteEstoque);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/AjusteEstoque.xhtml");
    }

    private void geraMovimentacao() {
        for (int i = 0; i < ajusteEstoque.getAjusteEstoqueItens().size(); i++) {

            BigDecimal quantidade;
            if(ajusteEstoque.getTipo().equals("I")) {
                quantidade = gestaoProduto.saldoAtualProduto(ajusteEstoque.getAjusteEstoqueItens().get(i).getProduto())
                        .subtract(ajusteEstoque.getAjusteEstoqueItens().get(i).getQuantidade());

                String tipo;
                if(quantidade.doubleValue() > 0) {
                    tipo = "S";
                } else {
                    tipo = "E";
                    quantidade = quantidade.multiply(new BigDecimal(-1));
                }

                ajusteEstoque.getAjusteEstoqueItens().get(i).setMovimentacao(new Movimentacao(tipo, quantidade,
                        "Ajuste de Estoque", ajusteEstoque.getDocumento(),
                        ajusteEstoque.getAjusteEstoqueItens().get(i).getProduto(),
                        ajusteEstoque.getEmpresa(), ajusteEstoque.getAjusteEstoqueItens().get(i), ajusteEstoque.getData())
                );

            } else {
                ajusteEstoque.getAjusteEstoqueItens().get(i).setMovimentacao(new Movimentacao(ajusteEstoque.getTipo(),
                        ajusteEstoque.getAjusteEstoqueItens().get(i).getQuantidade(),
                        "Ajuste de Estoque", ajusteEstoque.getDocumento(),
                        ajusteEstoque.getAjusteEstoqueItens().get(i).getProduto(),
                        ajusteEstoque.getEmpresa(), ajusteEstoque.getAjusteEstoqueItens().get(i), ajusteEstoque.getData())
                );
            }


        }
    }


    public AjusteEstoque getAjusteEstoque() {
        return ajusteEstoque;
    }

    public void setAjusteEstoque(AjusteEstoque ajusteEstoque) {
        this.ajusteEstoque = ajusteEstoque;
    }

    public AjusteEstoqueItem getAjusteEstoqueItem() {
        return ajusteEstoqueItem;
    }

    public void setAjusteEstoqueItem(AjusteEstoqueItem ajusteEstoqueItem) {
        this.ajusteEstoqueItem = ajusteEstoqueItem;
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
