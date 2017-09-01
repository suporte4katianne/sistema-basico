package br.com.hsi.nfe.model;

import br.com.hsi.nfe.model.dados.Cidade;
import br.com.hsi.nfe.model.dados.Estado;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	@CNPJ
	@Column(name = "cpf_cnpj")
	private String cpfCnpj;
	@NotNull
	@Column(name = "inscricao_estadual")
	private String inscricaoEstadual;
	@NotNull
	@Column(name = "enquadramento_fiscal")
	private String enquadramentoFiscal;
	@NotEmpty
	private String cep;
	@ManyToOne
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;
	@Column(name = "cidade")
	private String nomecidade;
	@Column(name = "codigo_ibge_cidade")
	private String codigoIbgeCidade;
	@ManyToOne
	@JoinColumn(name = "estado_id")
	private Estado estado;
	@Column(name = "estado")
	private String nomeEstado;
	@Column(name = "codigo_ibge_estado")
	private String codigoIbgeEstado;
	@NotEmpty
	private String rua;
	private String numero;
	@NotEmpty
	private String bairro;
	private String complemento;
	@NotEmpty
	private String telefone;
	private String celular;
	@Email
	private String email;
	@Column(name = "nome_certificado")
	private String nomeCertificado;
	@Column(name = "senha_certificado")
	private String senhaCertificado;
	private Boolean inativo;
	@Column(name = "credito_icms")
	private BigDecimal creditoIcms = new BigDecimal("0.0");
	private byte[] logo;
	@Column(name = "informacao_complementar")
	private String infoComplementar;
	@Column(name = "status_certificado")
	private String statusCertificado;
	@Column(name = "tipo_certificado")
	private String tipoCertificado;
	
	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Entidade> destinatarios = new ArrayList<>();
	
	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Usuario> usuarios = new ArrayList<>();
	
	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Produto> produtos = new ArrayList<Produto>();
	
	@OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<NotaFiscal> nfes = new ArrayList<NotaFiscal>();
	
	@OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Numeracao> numeracao = new ArrayList<Numeracao>();
	
	
	public String getInfoComplementar() {
		return infoComplementar;
	}
	public void setInfoComplementar(String infoComplementar) {
		this.infoComplementar = infoComplementar;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public String getNomecidade() {
		return nomecidade;
	}
	public void setNomecidade(String nomecidade) {
		this.nomecidade = nomecidade;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public String getNomeEstado() {
		return nomeEstado;
	}
	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}
	public String getCodigoIbgeCidade() {
		return codigoIbgeCidade;
	}
	public void setCodigoIbgeCidade(String codigoIbgeCidade) {
		this.codigoIbgeCidade = codigoIbgeCidade;
	}	
	public String getCodigoIbgeEstado() {
		return codigoIbgeEstado;
	}
	public void setCodigoIbgeEstado(String codigoIbgeEstado) {
		this.codigoIbgeEstado = codigoIbgeEstado;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
	public String getEnquadramentoFiscal() {
		return enquadramentoFiscal;
	}
	public void setEnquadramentoFiscal(String enquadramentoFiscal) {
		this.enquadramentoFiscal = enquadramentoFiscal;
	}
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	public List<Entidade> getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(List<Entidade> destinatarios) {
		this.destinatarios = destinatarios;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCelular() {
		return celular;
	}
	public String getSenhaCertificado() {
		return senhaCertificado;
	}
	public void setSenhaCertificado(String senhaCertificado) {
		this.senhaCertificado = senhaCertificado;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getNomeCertificado() {
		return nomeCertificado;
	}
	public void setNomeCertificado(String nomeCertificado) {
		this.nomeCertificado = nomeCertificado;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getInativo() {
		return inativo;
	}
	public void setInativo(Boolean inativo) {
		this.inativo = inativo;
	}
	public List<NotaFiscal> getNfes() {
		return nfes;
	}
	public void setNfes(List<NotaFiscal> nfes) {
		this.nfes = nfes;
	}
	public BigDecimal getCreditoIcms() {
		return creditoIcms;
	}
	public void setCreditoIcms(BigDecimal creditoIcms) {
		this.creditoIcms = creditoIcms;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public String getStatusCertificado() {
		return statusCertificado;
	}
	public void setStatusCertificado(String statusCertificado) {
		this.statusCertificado = statusCertificado;
	}
	public String getTipoCertificado() {
		return tipoCertificado;
	}
	public void setTipoCertificado(String tipoCertificado) {
		this.tipoCertificado = tipoCertificado;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public List<Numeracao> getNumeracao() {
		return numeracao;
	}
	public void setNumeracao(List<Numeracao> numeracao) {
		this.numeracao = numeracao;
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
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
