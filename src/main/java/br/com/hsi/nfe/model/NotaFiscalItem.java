package br.com.hsi.nfe.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "nota_fiscal_item")
public class NotaFiscalItem{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_nfe")
	private NotaFiscal nfe;
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
	private String origem;   
	private String cst;
	@Column(name = "valor_base_st")
	private BigDecimal valorBaseicmsStCobradoAnteriormente;
	@Column(name = "valor_st")
	private BigDecimal valoricmsStCobradoAnteriormente;
	@Column(name = "cst_pis")
	private String cstPis;
	@Column(name = "cst_cofins")
	private String cstCofins;
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
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getCompoeValorNota() {
		return compoeValorNota;
	}
	public void setCompoeValorNota(String compoeValorNota) {
		this.compoeValorNota = compoeValorNota;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getCst() {
		return cst;
	}
	public void setCst(String cst) {
		this.cst = cst;
	}
	public String getCstPis() {
		return cstPis;
	}
	public void setCstPis(String cstPis) {
		this.cstPis = cstPis;
	}
	public String getCstCofins() {
		return cstCofins;
	}
	public void setCstCofins(String cstCofins) {
		this.cstCofins = cstCofins;
	}
	public String getInformacaoAdcional() {
		return informacaoAdcional;
	}
	public void setInformacaoAdcional(String informacaoAdcional) {
		this.informacaoAdcional = informacaoAdcional;
	}
	public NotaFiscal getNfe() {
		return nfe;
	}
	public void setNfe(NotaFiscal nfe) {
		this.nfe = nfe;
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
	public BigDecimal getValorBaseicmsStCobradoAnteriormente() {
		return valorBaseicmsStCobradoAnteriormente;
	}
	public void setValorBaseicmsStCobradoAnteriormente(BigDecimal valorBaseicmsStCobradoAnteriormente) {
		this.valorBaseicmsStCobradoAnteriormente = valorBaseicmsStCobradoAnteriormente;
	}
	public BigDecimal getValoricmsStCobradoAnteriormente() {
		return valoricmsStCobradoAnteriormente;
	}
	public void setValoricmsStCobradoAnteriormente(BigDecimal valoricmsStCobradoAnteriormente) {
		this.valoricmsStCobradoAnteriormente = valoricmsStCobradoAnteriormente;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
