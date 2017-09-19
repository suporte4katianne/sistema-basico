package br.com.hsi.model;

import br.com.hsi.model.dados.UnidadeMedida;
import br.com.hsi.util.validation.CodigoBarras;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "embalagem")
public class Embalagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CodigoBarras
    @Column(name = "codigo_barras")
    private String codigoBarras;

    @OneToOne
    @JoinColumn
    private UnidadeMedida unidadeMedida;

    @Column(name = "fator_conversao")
    private BigDecimal fatorConversao;

    @ManyToOne
    @JoinColumn
    private Produto produto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public BigDecimal getFatorConversao() {
        return fatorConversao;
    }

    public void setFatorConversao(BigDecimal fatorConversao) {
        this.fatorConversao = fatorConversao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Embalagem embalagem = (Embalagem) o;

        if (id != null ? !id.equals(embalagem.id) : embalagem.id != null) return false;
        if (codigoBarras != null ? !codigoBarras.equals(embalagem.codigoBarras) : embalagem.codigoBarras != null)
            return false;
        if (unidadeMedida != null ? !unidadeMedida.equals(embalagem.unidadeMedida) : embalagem.unidadeMedida != null)
            return false;
        if (fatorConversao != null ? !fatorConversao.equals(embalagem.fatorConversao) : embalagem.fatorConversao != null)
            return false;
        return produto != null ? produto.equals(embalagem.produto) : embalagem.produto == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (codigoBarras != null ? codigoBarras.hashCode() : 0);
        result = 31 * result + (unidadeMedida != null ? unidadeMedida.hashCode() : 0);
        result = 31 * result + (fatorConversao != null ? fatorConversao.hashCode() : 0);
        result = 31 * result + (produto != null ? produto.hashCode() : 0);
        return result;
    }
}
