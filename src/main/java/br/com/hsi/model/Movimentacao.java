package br.com.hsi.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "movimentacao")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_movimentacao")
    private String tipoMovimentacao;

    private BigDecimal qunatidade;

    @Column(name = "tipo_documento")
    private String tipoDocumento; // Nota Fiscal, Ajuste de Estoque

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column
    private Date data;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    // Documentos

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_nota_fiscal_item")
    private NotaFiscalItem notaFiscalItem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ajuste_estoque_item")
    private AjusteEstoqueItem ajusteEstoqueItem;



    public Movimentacao() {

    }

    public Movimentacao(String tipoMovimentacao, BigDecimal qunatidade, String tipoDocumento, String numeroDocumento, Produto produto, Empresa empresa, NotaFiscalItem notaFiscalItem, Date data) {
        this.tipoMovimentacao = tipoMovimentacao;
        this.qunatidade = qunatidade;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.produto = produto;
        this.empresa = empresa;
        this.notaFiscalItem = notaFiscalItem;
        this.data = data;
    }

    public Movimentacao(String tipoMovimentacao, BigDecimal qunatidade, String tipoDocumento, String numeroDocumento, Produto produto, Empresa empresa, AjusteEstoqueItem ajusteEstoqueItem, Date data) {
        this.tipoMovimentacao = tipoMovimentacao;
        this.qunatidade = qunatidade;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.produto = produto;
        this.empresa = empresa;
        this.ajusteEstoqueItem = ajusteEstoqueItem;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public BigDecimal getQunatidade() {
        return qunatidade;
    }

    public void setQunatidade(BigDecimal qunatidade) {
        this.qunatidade = qunatidade;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public NotaFiscalItem getNotaFiscalItem() {
        return notaFiscalItem;
    }

    public void setNotaFiscalItem(NotaFiscalItem notaFiscalItem) {
        this.notaFiscalItem = notaFiscalItem;
    }

    public AjusteEstoqueItem getAjusteEstoqueItem() {
        return ajusteEstoqueItem;
    }

    public void setAjusteEstoqueItem(AjusteEstoqueItem ajusteEstoqueItem) {
        this.ajusteEstoqueItem = ajusteEstoqueItem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}

