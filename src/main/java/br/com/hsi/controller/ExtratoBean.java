package br.com.hsi.controller;


import br.com.hsi.model.Movimentacao;
import br.com.hsi.model.Produto;
import br.com.hsi.service.GestaoProduto;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Named
@ViewScoped
public class ExtratoBean implements Serializable {

    @Inject
    private GestaoProduto gestaoProduto;

    private List<Produto> produtos;
    private List<Produto> produtosFiltro;
    private Produto produto;

    private List<Movimentacao> movimentacoes;
    private List<Movimentacao> movimentacoesFiltro;


    @PostConstruct
    public void init() {
        produtos = gestaoProduto.listarProdutos();
    }

    public void carregaMovimentacao() {
        if(produto != null){
            movimentacoes =  gestaoProduto.movimentacoesPorProduto(produto);
        }
    }


    public BigDecimal saldoAtual() {
        BigDecimal saldoAtual = new BigDecimal(0);
        if(movimentacoes != null) {
            for (Movimentacao movimentacao : movimentacoes) {
                if (movimentacao.getTipoMovimentacao().equals("E")) {
                    saldoAtual = saldoAtual.add(movimentacao.getQunatidade());
                } else if (movimentacao.getTipoMovimentacao().equals("S")) {
                    saldoAtual = saldoAtual.subtract(movimentacao.getQunatidade());
                } else if (movimentacao.getTipoMovimentacao().equals("I")) {
                    saldoAtual = movimentacao.getQunatidade();
                }
                return saldoAtual;
            }
        }

        return saldoAtual;
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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<Movimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public List<Movimentacao> getMovimentacoesFiltro() {
        return movimentacoesFiltro;
    }

    public void setMovimentacoesFiltro(List<Movimentacao> movimentacoesFiltro) {
        this.movimentacoesFiltro = movimentacoesFiltro;
    }
}
