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
}
