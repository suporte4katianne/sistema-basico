package br.com.hsi.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Classe com atributos de produtos pertencentes a remessa
 *
 * @author Eriel Miquilino
 */

@Entity
@Table(name = "remessa_item")
public class RemessaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_remessa")
    private Remessa remessa;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    private BigDecimal quantidade;

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

    public Remessa getRemessa() {
        return remessa;
    }

    public void setRemessa(Remessa remessa) {
        this.remessa = remessa;
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

        RemessaItem that = (RemessaItem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (remessa != null ? !remessa.equals(that.remessa) : that.remessa != null) return false;
        if (produto != null ? !produto.equals(that.produto) : that.produto != null) return false;
        if (quantidade != null ? !quantidade.equals(that.quantidade) : that.quantidade != null) return false;
        if (descricao != null ? !descricao.equals(that.descricao) : that.descricao != null) return false;
        if (precoUnitario != null ? !precoUnitario.equals(that.precoUnitario) : that.precoUnitario != null)
            return false;
        return precoTotal != null ? precoTotal.equals(that.precoTotal) : that.precoTotal == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (remessa != null ? remessa.hashCode() : 0);
        result = 31 * result + (produto != null ? produto.hashCode() : 0);
        result = 31 * result + (quantidade != null ? quantidade.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + (precoUnitario != null ? precoUnitario.hashCode() : 0);
        result = 31 * result + (precoTotal != null ? precoTotal.hashCode() : 0);
        return result;
    }
}
