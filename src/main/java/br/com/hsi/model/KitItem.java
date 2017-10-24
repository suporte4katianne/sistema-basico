package br.com.hsi.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Eriel Miquilino
 */
@Entity
@Table(name = "kit_item")
public class KitItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_kit")
    private Kit kit;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    private BigDecimal quantidade;

    private BigDecimal devolvido;

    private BigDecimal vendido;

    private String descricao;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    @Column(name = "preco_total")
    private BigDecimal precoTotal;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getDevolvido() {
        return devolvido;
    }

    public void setDevolvido(BigDecimal devolvido) {
        this.devolvido = devolvido;
    }

    public BigDecimal getVendido() {
        return vendido;
    }

    public void setVendido(BigDecimal vendido) {
        this.vendido = vendido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KitItem kitItem = (KitItem) o;

        if (id != null ? !id.equals(kitItem.id) : kitItem.id != null) return false;
        if (kit != null ? !kit.equals(kitItem.kit) : kitItem.kit != null) return false;
        if (produto != null ? !produto.equals(kitItem.produto) : kitItem.produto != null) return false;
        if (quantidade != null ? !quantidade.equals(kitItem.quantidade) : kitItem.quantidade != null) return false;
        if (devolvido != null ? !devolvido.equals(kitItem.devolvido) : kitItem.devolvido != null) return false;
        if (vendido != null ? !vendido.equals(kitItem.vendido) : kitItem.vendido != null) return false;
        if (descricao != null ? !descricao.equals(kitItem.descricao) : kitItem.descricao != null) return false;
        if (precoUnitario != null ? !precoUnitario.equals(kitItem.precoUnitario) : kitItem.precoUnitario != null)
            return false;
        return precoTotal != null ? precoTotal.equals(kitItem.precoTotal) : kitItem.precoTotal == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (kit != null ? kit.hashCode() : 0);
        result = 31 * result + (produto != null ? produto.hashCode() : 0);
        result = 31 * result + (quantidade != null ? quantidade.hashCode() : 0);
        result = 31 * result + (devolvido != null ? devolvido.hashCode() : 0);
        result = 31 * result + (vendido != null ? vendido.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + (precoUnitario != null ? precoUnitario.hashCode() : 0);
        result = 31 * result + (precoTotal != null ? precoTotal.hashCode() : 0);
        return result;
    }
}
