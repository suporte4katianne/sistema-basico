package br.com.hsi.model;

import java.math.BigDecimal;

/**
 * @author Eriel Miquilino
 */
public class VendedorItem {

    private int codigo;
    private String descricao;
    private BigDecimal preco;


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
