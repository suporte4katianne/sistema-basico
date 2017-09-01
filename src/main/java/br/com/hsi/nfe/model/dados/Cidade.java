package br.com.hsi.nfe.model.dados;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cidade")
public class Cidade implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idCidade;
	@Column(name = "nome")
	private String cidade;
	private String codigo;
	
	@OneToMany(mappedBy = "cidade")
	private List<Cep> cep;
	
	@ManyToOne
	@JoinColumn(name = "idestado")
	private Estado estado;
	
	public Long getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public List<Cep> getCep() {
		return cep;
	}
	public void setCep(List<Cep> cep) {
		this.cep = cep;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCidade == null) ? 0 : idCidade.hashCode());
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
		Cidade other = (Cidade) obj;
		if (idCidade == null) {
			if (other.idCidade != null)
				return false;
		} else if (!idCidade.equals(other.idCidade))
			return false;
		return true;
	}
	
	
	
}
