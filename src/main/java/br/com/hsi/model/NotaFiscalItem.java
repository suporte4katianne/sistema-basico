package br.com.hsi.model;

import br.com.hsi.model.dados.text.CSTICMS;
import br.com.hsi.model.dados.text.CSTIPI;
import br.com.hsi.model.dados.text.CSTPISCOFINS;
import br.com.hsi.model.dados.text.Origem;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "nota_fiscal_item")
public class NotaFiscalItem{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_nota_fiscal")
	private NotaFiscal notaFiscal;

	@Column(name = "numero_item")
	private int numeroItem;

	@Column(name = "cod_produto")
	private String codProd;

	@Column(name = "nome_produto")
	private String nomeProduto; 

	private String ncm;

	private String cest;

	private String cfop;

	@Column(name = "codigo_barras")
	private String codigoBarras;

	@Column(name = "unidade_medida")
	private String unidadeMedida;

	private BigDecimal quantidade;

	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario;

	@Column(name = "valor_total")
	private BigDecimal valorTotal;

	@Column(name = "compoe_valor_nota")
	private String compoeValorNota; // 0 - Não compoe o total da nota 1 - compoe o total da nota          

    @Enumerated(EnumType.STRING)
    private Origem origem;

    @Enumerated(EnumType.STRING)
    private CSTICMS cst;

    @Column(name = "reducao_base_icms")
    private BigDecimal reducaoBaseIcms;

    @Column(name = "valor_base_icms")
    private BigDecimal baseIcms;

    @Column(name = "valor_icms")
    private BigDecimal icms;

    @Column(name = "valor_base_icms_st")
	private BigDecimal baseIcmsSt;

	@Column(name = "valor_icms_st")
	private BigDecimal icmsSt;

	@Column(name = "cst_pis_cofins")
	private CSTPISCOFINS cstPisCofins;

	@Column(name = "informacao_adcional")
	private String informacaoAdcional;

	private BigDecimal desconto = new BigDecimal("0.0");

	@Column(name = "aliquota_pis")
	private BigDecimal aliquotaPis;

	@Column(name = "aliquota_cofins")
	private BigDecimal aliquitaCofins;

	@Column(name = "valor_total_pis")
	private BigDecimal valorTotalPis;

	@Column(name = "valor_total_cofins")
	private BigDecimal valorTotalCofins;

	@Column(name = "cst_ipi")
    private CSTIPI cstIpi;

	@Column(name = "aliquota_ipi")
    private BigDecimal aliquotaIpi;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_produto")
	private Produto produto;

	@OneToOne(cascade = {CascadeType.ALL})
    private Movimentacao movimentacao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public int getNumeroItem() {
        return numeroItem;
    }

    public void setNumeroItem(int numeroItem) {
        this.numeroItem = numeroItem;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getCest() {
        return cest;
    }

    public void setCest(String cest) {
        this.cest = cest;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getCompoeValorNota() {
        return compoeValorNota;
    }

    public void setCompoeValorNota(String compoeValorNota) {
        this.compoeValorNota = compoeValorNota;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public CSTICMS getCst() {
        return cst;
    }

    public void setCst(CSTICMS cst) {
        this.cst = cst;
    }

    public BigDecimal getBaseIcms() {
        return baseIcms;
    }

    public void setBaseIcms(BigDecimal baseIcms) {
        this.baseIcms = baseIcms;
    }

    public BigDecimal getIcms() {
        return icms;
    }

    public void setIcms(BigDecimal icms) {
        this.icms = icms;
    }

    public BigDecimal getBaseIcmsSt() {
        return baseIcmsSt;
    }

    public void setBaseIcmsSt(BigDecimal baseIcmsSt) {
        this.baseIcmsSt = baseIcmsSt;
    }

    public BigDecimal getIcmsSt() {
        return icmsSt;
    }

    public void setIcmsSt(BigDecimal icmsSt) {
        this.icmsSt = icmsSt;
    }

    public CSTPISCOFINS getCstPisCofins() {
        return cstPisCofins;
    }

    public void setCstPisCofins(CSTPISCOFINS cstPisCofins) {
        this.cstPisCofins = cstPisCofins;
    }

    public String getInformacaoAdcional() {
        return informacaoAdcional;
    }

    public void setInformacaoAdcional(String informacaoAdcional) {
        this.informacaoAdcional = informacaoAdcional;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getAliquotaPis() {
        return aliquotaPis;
    }

    public void setAliquotaPis(BigDecimal aliquotaPis) {
        this.aliquotaPis = aliquotaPis;
    }

    public BigDecimal getAliquitaCofins() {
        return aliquitaCofins;
    }

    public void setAliquitaCofins(BigDecimal aliquitaCofins) {
        this.aliquitaCofins = aliquitaCofins;
    }

    public BigDecimal getValorTotalPis() {
        return valorTotalPis;
    }

    public void setValorTotalPis(BigDecimal valorTotalPis) {
        this.valorTotalPis = valorTotalPis;
    }

    public BigDecimal getValorTotalCofins() {
        return valorTotalCofins;
    }

    public void setValorTotalCofins(BigDecimal valorTotalCofins) {
        this.valorTotalCofins = valorTotalCofins;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Movimentacao getMovimentacao() {
        return movimentacao;
    }

    public void setMovimentacao(Movimentacao movimentacao) {
        this.movimentacao = movimentacao;
    }

    public BigDecimal getReducaoBaseIcms() {
        return reducaoBaseIcms;
    }

    public void setReducaoBaseIcms(BigDecimal reducaoBaseIcms) {
        this.reducaoBaseIcms = reducaoBaseIcms;
    }

    public CSTIPI getCstIpi() {
        return cstIpi;
    }

    public void setCstIpi(CSTIPI cstIpi) {
        this.cstIpi = cstIpi;
    }

    public BigDecimal getAliquotaIpi() {
        return aliquotaIpi;
    }

    public void setAliquotaIpi(BigDecimal aliquotaIpi) {
        this.aliquotaIpi = aliquotaIpi;
    }
}
