package br.com.hsi.nfe.model;

import br.com.hsi.nfe.model.dados.Cest;
import br.com.hsi.nfe.model.dados.Cfop;
import br.com.hsi.nfe.model.dados.Ncm;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


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
	@NotNull
	@Column(name = "codigo_barras")
	private String codigoBrras;
	private int codigo;
	@NotNull
	private String descricao;
	@ManyToOne
	@JoinColumn(name = "ncm_id")
	private Ncm ncm;
	@ManyToOne
	@JoinColumn(name = "cest_id")
	private Cest cest;
	@NotNull
	private String codigo_ncm;
	@NotNull
	private String codigo_cest;
	@NotNull
	private String origem;
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
	@Column(name = "cst_icms")
	private String cstIcms;
	@Column(name = "cst_ipi")
	private String cstIpi;
	@Column(name = "cst_pis")
	private String cstPis;
	@Column(name = "cst_cofins")
	private String cstCofins;
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
	private String codigoCfopEstadual;
	private String codigoCfopInterestadual;

	


	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
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

	public String getCstIcms() {
		return cstIcms;
	}

	public void setCstIcms(String cstIcms) {
		this.cstIcms = cstIcms;
	}

	public String getCstIpi() {
		return cstIpi;
	}

	public void setCstIpi(String cstIpi) {
		this.cstIpi = cstIpi;
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

	public String getCodigoBrras() {
		return codigoBrras;
	}

	public void setCodigoBrras(String codigoBrras) {
		this.codigoBrras = codigoBrras;
	}

	public String getTipoCadastro() {
		return tipoCadastro;
	}

	public void setTipoCadastro(String tipoCadastro) {
		this.tipoCadastro = tipoCadastro;
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

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
