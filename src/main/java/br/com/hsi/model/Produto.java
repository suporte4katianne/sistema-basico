package br.com.hsi.model;

import br.com.hsi.model.dados.Cest;
import br.com.hsi.model.dados.Cfop;
import br.com.hsi.model.dados.Ncm;
import br.com.hsi.model.dados.text.CSTICMS;
import br.com.hsi.model.dados.text.CSTIPI;
import br.com.hsi.model.dados.text.CSTPISCOFINS;
import br.com.hsi.model.dados.text.Origem;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "produto")
public class Produto{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tipo_cadastro")
	private String tipoCadastro;

	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	@Column(name = "codigo_barras")
	private String codigoBrras;

	private int codigo;

	private String referencia;

	@NotBlank
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "ncm_id")
	private Ncm ncm;

	@ManyToOne
	@JoinColumn(name = "cest_id")
	private Cest cest;

	@NotBlank
	private String codigo_ncm;

	private BigDecimal comissao = new BigDecimal(0.0);

	@NotBlank
	private String codigo_cest;

	@Enumerated(EnumType.STRING)
	private Origem origem;

	@NotNull
	@Column(name = "unidade_medida")
	private String unidadeMedida;

	@Column(name = "preco_venda")
	private BigDecimal precoVenda = new BigDecimal(0.0);	

	@NotNull
	@Column(name = "aliquota_icms")
	private BigDecimal aliquotaIcms =  new BigDecimal(0.0);

	@Column(name = "aliquota_ipi")
	private BigDecimal aliquotaIpi =  new BigDecimal(0.0);

	@Column(name = "aliquota_pis")
	private BigDecimal aliquotaPis  =  new BigDecimal(0.0);

	@Column(name = "aliquota_cofins")
	private BigDecimal aliquotaCofins  =  new BigDecimal(0.0);

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "cst_icms")
	private CSTICMS cstIcms;

	@Enumerated(EnumType.STRING)
	@Column(name = "cst_ipi")
	private CSTIPI cstIpi;

	@Enumerated(EnumType.STRING)
	@Column(name = "cst_cofins")
	private CSTPISCOFINS cstPisCofins;

	private String iss;

	@Column(name = "aliquota_iss")
	private BigDecimal AliquotaIss;

	@Column(name = "codigo_lc")
	private String codigoLc;

	@ManyToOne
    @JoinColumn(name = "id_cfop_estadual")
	private Cfop cfopEstadual;

	@ManyToOne
    @JoinColumn(name = "id_cfop_interestadual")
	private Cfop cfopInterestadual;

	@Column(name = "cfop_estadual")
	private String codigoCfopEstadual;

	@Column(name = "cfop_interestadual")
	private String codigoCfopInterestadual;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Embalagem> embalagens = new ArrayList<>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoCadastro() {
		return tipoCadastro;
	}

	public void setTipoCadastro(String tipoCadastro) {
		this.tipoCadastro = tipoCadastro;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getCodigoBrras() {
		return codigoBrras;
	}

	public void setCodigoBrras(String codigoBrras) {
		this.codigoBrras = codigoBrras;
	}

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

	public Ncm getNcm() {
		return ncm;
	}

	public void setNcm(Ncm ncm) {
		this.ncm = ncm;
	}

	public Cest getCest() {
		return cest;
	}

	public void setCest(Cest cest) {
		this.cest = cest;
	}

	public String getCodigo_ncm() {
		return codigo_ncm;
	}

	public void setCodigo_ncm(String codigo_ncm) {
		this.codigo_ncm = codigo_ncm;
	}

	public String getCodigo_cest() {
		return codigo_cest;
	}

	public void setCodigo_cest(String codigo_cest) {
		this.codigo_cest = codigo_cest;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public BigDecimal getAliquotaIcms() {
		return aliquotaIcms;
	}

	public void setAliquotaIcms(BigDecimal aliquotaIcms) {
		this.aliquotaIcms = aliquotaIcms;
	}

	public BigDecimal getAliquotaIpi() {
		return aliquotaIpi;
	}

	public void setAliquotaIpi(BigDecimal aliquotaIpi) {
		this.aliquotaIpi = aliquotaIpi;
	}

	public BigDecimal getAliquotaPis() {
		return aliquotaPis;
	}

	public void setAliquotaPis(BigDecimal aliquotaPis) {
		this.aliquotaPis = aliquotaPis;
	}

	public BigDecimal getAliquotaCofins() {
		return aliquotaCofins;
	}

	public void setAliquotaCofins(BigDecimal aliquotaCofins) {
		this.aliquotaCofins = aliquotaCofins;
	}

	public CSTICMS getCstIcms() {
		return cstIcms;
	}

	public void setCstIcms(CSTICMS cstIcms) {
		this.cstIcms = cstIcms;
	}

	public CSTIPI getCstIpi() {
		return cstIpi;
	}

	public void setCstIpi(CSTIPI cstIpi) {
		this.cstIpi = cstIpi;
	}

	public CSTPISCOFINS getCstPisCofins() {
		return cstPisCofins;
	}

	public void setCstPisCofins(CSTPISCOFINS cstPisCofins) {
		this.cstPisCofins = cstPisCofins;
	}

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public BigDecimal getAliquotaIss() {
		return AliquotaIss;
	}

	public void setAliquotaIss(BigDecimal aliquotaIss) {
		AliquotaIss = aliquotaIss;
	}

	public String getCodigoLc() {
		return codigoLc;
	}

	public void setCodigoLc(String codigoLc) {
		this.codigoLc = codigoLc;
	}

	public Cfop getCfopEstadual() {
		return cfopEstadual;
	}

	public void setCfopEstadual(Cfop cfopEstadual) {
		this.cfopEstadual = cfopEstadual;
	}

	public Cfop getCfopInterestadual() {
		return cfopInterestadual;
	}

	public void setCfopInterestadual(Cfop cfopInterestadual) {
		this.cfopInterestadual = cfopInterestadual;
	}

	public String getCodigoCfopEstadual() {
		return codigoCfopEstadual;
	}

	public void setCodigoCfopEstadual(String codigoCfopEstadual) {
		this.codigoCfopEstadual = codigoCfopEstadual;
	}

	public String getCodigoCfopInterestadual() {
		return codigoCfopInterestadual;
	}

	public void setCodigoCfopInterestadual(String codigoCfopInterestadual) {
		this.codigoCfopInterestadual = codigoCfopInterestadual;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public List<Embalagem> getEmbalagens() {
		return embalagens;
	}

	public void setEmbalagens(List<Embalagem> embalagens) {
		this.embalagens = embalagens;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Produto produto = (Produto) o;

		if (codigo != produto.codigo) return false;
		if (id != null ? !id.equals(produto.id) : produto.id != null) return false;
		if (tipoCadastro != null ? !tipoCadastro.equals(produto.tipoCadastro) : produto.tipoCadastro != null)
			return false;
		if (empresa != null ? !empresa.equals(produto.empresa) : produto.empresa != null) return false;
		if (codigoBrras != null ? !codigoBrras.equals(produto.codigoBrras) : produto.codigoBrras != null) return false;
		if (referencia != null ? !referencia.equals(produto.referencia) : produto.referencia != null) return false;
		if (descricao != null ? !descricao.equals(produto.descricao) : produto.descricao != null) return false;
		if (ncm != null ? !ncm.equals(produto.ncm) : produto.ncm != null) return false;
		if (cest != null ? !cest.equals(produto.cest) : produto.cest != null) return false;
		if (codigo_ncm != null ? !codigo_ncm.equals(produto.codigo_ncm) : produto.codigo_ncm != null) return false;
		if (comissao != null ? !comissao.equals(produto.comissao) : produto.comissao != null) return false;
		if (codigo_cest != null ? !codigo_cest.equals(produto.codigo_cest) : produto.codigo_cest != null) return false;
		if (origem != produto.origem) return false;
		if (unidadeMedida != null ? !unidadeMedida.equals(produto.unidadeMedida) : produto.unidadeMedida != null)
			return false;
		if (precoVenda != null ? !precoVenda.equals(produto.precoVenda) : produto.precoVenda != null) return false;
		if (aliquotaIcms != null ? !aliquotaIcms.equals(produto.aliquotaIcms) : produto.aliquotaIcms != null)
			return false;
		if (aliquotaIpi != null ? !aliquotaIpi.equals(produto.aliquotaIpi) : produto.aliquotaIpi != null) return false;
		if (aliquotaPis != null ? !aliquotaPis.equals(produto.aliquotaPis) : produto.aliquotaPis != null) return false;
		if (aliquotaCofins != null ? !aliquotaCofins.equals(produto.aliquotaCofins) : produto.aliquotaCofins != null)
			return false;
		if (cstIcms != produto.cstIcms) return false;
		if (cstIpi != produto.cstIpi) return false;
		if (cstPisCofins != produto.cstPisCofins) return false;
		if (iss != null ? !iss.equals(produto.iss) : produto.iss != null) return false;
		if (AliquotaIss != null ? !AliquotaIss.equals(produto.AliquotaIss) : produto.AliquotaIss != null) return false;
		if (codigoLc != null ? !codigoLc.equals(produto.codigoLc) : produto.codigoLc != null) return false;
		if (cfopEstadual != null ? !cfopEstadual.equals(produto.cfopEstadual) : produto.cfopEstadual != null)
			return false;
		if (cfopInterestadual != null ? !cfopInterestadual.equals(produto.cfopInterestadual) : produto.cfopInterestadual != null)
			return false;
		if (codigoCfopEstadual != null ? !codigoCfopEstadual.equals(produto.codigoCfopEstadual) : produto.codigoCfopEstadual != null)
			return false;
		if (codigoCfopInterestadual != null ? !codigoCfopInterestadual.equals(produto.codigoCfopInterestadual) : produto.codigoCfopInterestadual != null)
			return false;
		return embalagens != null ? embalagens.equals(produto.embalagens) : produto.embalagens == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (tipoCadastro != null ? tipoCadastro.hashCode() : 0);
		result = 31 * result + (empresa != null ? empresa.hashCode() : 0);
		result = 31 * result + (codigoBrras != null ? codigoBrras.hashCode() : 0);
		result = 31 * result + codigo;
		result = 31 * result + (referencia != null ? referencia.hashCode() : 0);
		result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
		result = 31 * result + (ncm != null ? ncm.hashCode() : 0);
		result = 31 * result + (cest != null ? cest.hashCode() : 0);
		result = 31 * result + (codigo_ncm != null ? codigo_ncm.hashCode() : 0);
		result = 31 * result + (comissao != null ? comissao.hashCode() : 0);
		result = 31 * result + (codigo_cest != null ? codigo_cest.hashCode() : 0);
		result = 31 * result + (origem != null ? origem.hashCode() : 0);
		result = 31 * result + (unidadeMedida != null ? unidadeMedida.hashCode() : 0);
		result = 31 * result + (precoVenda != null ? precoVenda.hashCode() : 0);
		result = 31 * result + (aliquotaIcms != null ? aliquotaIcms.hashCode() : 0);
		result = 31 * result + (aliquotaIpi != null ? aliquotaIpi.hashCode() : 0);
		result = 31 * result + (aliquotaPis != null ? aliquotaPis.hashCode() : 0);
		result = 31 * result + (aliquotaCofins != null ? aliquotaCofins.hashCode() : 0);
		result = 31 * result + (cstIcms != null ? cstIcms.hashCode() : 0);
		result = 31 * result + (cstIpi != null ? cstIpi.hashCode() : 0);
		result = 31 * result + (cstPisCofins != null ? cstPisCofins.hashCode() : 0);
		result = 31 * result + (iss != null ? iss.hashCode() : 0);
		result = 31 * result + (AliquotaIss != null ? AliquotaIss.hashCode() : 0);
		result = 31 * result + (codigoLc != null ? codigoLc.hashCode() : 0);
		result = 31 * result + (cfopEstadual != null ? cfopEstadual.hashCode() : 0);
		result = 31 * result + (cfopInterestadual != null ? cfopInterestadual.hashCode() : 0);
		result = 31 * result + (codigoCfopEstadual != null ? codigoCfopEstadual.hashCode() : 0);
		result = 31 * result + (codigoCfopInterestadual != null ? codigoCfopInterestadual.hashCode() : 0);
		result = 31 * result + (embalagens != null ? embalagens.hashCode() : 0);
		return result;
	}
}
