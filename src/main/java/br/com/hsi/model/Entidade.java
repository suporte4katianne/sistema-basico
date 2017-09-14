package br.com.hsi.model;

import br.com.hsi.model.dados.Cidade;
import br.com.hsi.model.dados.Estado;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entidade")
public class Entidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String nome;
	@Column(name = "tipo_entidade")
	private String tipoEntidade; // C, F ou T
	@Column(name = "tipo_modalidade")
	private String tipoModalidade; // 1 ou 2
	@NotNull
	@Column(name = "cpf_cnpj")
	private String cpfCnpj;
	@NotNull
	@Column(name = "tipo_contribuinte")
	private int tipoContribuinte; // 1 2 ou 9
	@NotNull
	@Column(name = "inscricao_estadual")
	private String inscricaoEstadual;
	@NotNull
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
	@NotNull
	private String rua;
	private String numero;
	@NotNull
	private String bairro;
	private String complemento;
	@NotNull
	private String telefone;
	private String celular;
	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;
	@Email
	private String email;
	@Column(name = "produtor_rural")
	private Boolean produtorRural;

//	@OneToMany(mappedBy = "entidade", cascade = CascadeType.MERGE, orphanRemoval = true)
//	private List<NotaFiscal> notasFiscais = new ArrayList<NotaFiscal>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		if(nome != null){
		    return nome.trim();
        }else{
		    return nome;
        }
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
		if(email != null){
			return email.trim();
		}else{
			return email;
		}
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
		Entidade other = (Entidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
