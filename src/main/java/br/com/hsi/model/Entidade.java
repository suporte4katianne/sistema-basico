package br.com.hsi.model;

import br.com.hsi.model.dados.Cidade;
import br.com.hsi.model.dados.Estado;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "entidade")
public class Entidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nome;

	@Column(name = "tipo_entidade")
	private String tipoEntidade; // C, F ou T

	@Column(name = "tipo_modalidade")
	private String tipoModalidade; // 1 ou 2

	@NotBlank
	@Column(name = "cpf_cnpj")
	private String cpfCnpj;

	@NotNull
	@Column(name = "tipo_contribuinte")
	private int tipoContribuinte; // 1 2 ou 9

	@NotNull
	@Column(name = "inscricao_estadual")
	private String inscricaoEstadual;

	private String cep;

	@ManyToOne
	@JoinColumn(name = "cidade_id")
	@NotNull
	private Cidade cidade;

	@Column(name = "cidade")
	private String nomecidade;

	@Column(name = "codigo_ibge_cidade")
	private String codigoIbgeCidade;

	@ManyToOne
	@JoinColumn(name = "estado_id")
	@NotNull
	private Estado estado;

	@Column(name = "estado")
	private String nomeEstado;

	@Column(name = "codigo_ibge_estado")
	private String codigoIbgeEstado;

	@NotNull
	private String rua;
	private String numero;

	private String bairro;

	private String complemento;

	@NotBlank
	private String telefone;

	private String celular;

	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	@Email
	private String email;

	@Column(name = "produtor_rural")
	private Boolean produtorRural;

	@OneToMany(mappedBy = "entidade")
	private List<EntidadePraca> pracas;

	@ManyToOne
	@JoinColumn(name = "id_praca")
	private Praca praca;


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

	public String getTipoEntidade() {
		return tipoEntidade;
	}

	public void setTipoEntidade(String tipoEntidade) {
		this.tipoEntidade = tipoEntidade;
	}

	public String getTipoModalidade() {
		return tipoModalidade;
	}

	public void setTipoModalidade(String tipoModalidade) {
		this.tipoModalidade = tipoModalidade;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public int getTipoContribuinte() {
		return tipoContribuinte;
	}

	public void setTipoContribuinte(int tipoContribuinte) {
		this.tipoContribuinte = tipoContribuinte;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
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

	public String getCodigoIbgeCidade() {
		return codigoIbgeCidade;
	}

	public void setCodigoIbgeCidade(String codigoIbgeCidade) {
		this.codigoIbgeCidade = codigoIbgeCidade;
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

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getProdutorRural() {
		return produtorRural;
	}

	public void setProdutorRural(Boolean produtorRural) {
		this.produtorRural = produtorRural;
	}

	public List<EntidadePraca> getPracas() {
		return pracas;
	}

	public void setPracas(List<EntidadePraca> pracas) {
		this.pracas = pracas;
	}

	public Praca getPraca() {
		return praca;
	}

	public void setPraca(Praca praca) {
		this.praca = praca;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Entidade entidade = (Entidade) o;

		return id != null ? id.equals(entidade.id) : entidade.id == null;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
