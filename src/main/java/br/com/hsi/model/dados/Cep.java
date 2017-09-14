package br.com.hsi.model.dados;

import javax.persistence.*;


@Entity
@Table(name = "cep")
public class Cep {

	@Id
	@Column(name = "id")
	private long idcep;
	@Column(name = "endereco")
	private String endereco;
	@Column(name = "bairro")
	private String bairro;
	@Column(name = "cep")
	private String cep;
	
	
	@ManyToOne
	@JoinColumn(name = "idcidade")
	private Cidade cidade;
	
	public long getIdcep() {
		return idcep;
	}
	public void setIdcep(long idcep) {
		this.idcep = idcep;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
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

	
	
	
	
}
