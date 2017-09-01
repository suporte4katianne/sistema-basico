package br.com.hsi.nfe.model.dados;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "estado")
public class Estado implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long idEstado;
	
	@Column(name = "codigo")
	private String siglaEstado;
	
	@Column(name = "nome")
	private String estado;
	
	@Column(name = "codigo_ibge")
	private String codigoIbge;
	
	@Column(name = "url_consulta_cadastro")
	private String urlConsultaCadastro;
	
	@OneToMany(mappedBy = "estado")
	private List<Cidade> cidade;
	

	
	public Long getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getSiglaEstado() {
		return siglaEstado;
	}
	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}
	public String getCodigoIbge() {
		return codigoIbge;
	}
	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
	}
	public List<Cidade> getCidade() {
		return cidade;
	}
	public void setCidade(List<Cidade> cidade) {
		this.cidade = cidade;
	}
	public String getUrlConsultaCadastro() {
		return urlConsultaCadastro;
	}
	public void setUrlConsultaCadastro(String urlConsultaCadastro) {
		this.urlConsultaCadastro = urlConsultaCadastro;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEstado == null) ? 0 : idEstado.hashCode());
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
		Estado other = (Estado) obj;
		if (idEstado == null) {
			if (other.idEstado != null)
				return false;
		} else if (!idEstado.equals(other.idEstado))
			return false;
		return true;
	}

}
