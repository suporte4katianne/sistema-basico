package br.com.hsi.model.dados;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ibpt")
public class Ncm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer tipo;
	
	private String ncm;
	
	private BigDecimal nacionalFederal;
	
	private BigDecimal importadoFederal;
	
	private BigDecimal estadual;
	
	private BigDecimal municipal;

	private String versao;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public BigDecimal getNacionalFederal() {
		return nacionalFederal;
	}

	public void setNacionalFederal(BigDecimal nacionalFederal) {
		this.nacionalFederal = nacionalFederal;
	}

	public BigDecimal getImportadoFederal() {
		return importadoFederal;
	}

	public void setImportadoFederal(BigDecimal importadoFederal) {
		this.importadoFederal = importadoFederal;
	}

	public BigDecimal getEstadual() {
		return estadual;
	}

	public void setEstadual(BigDecimal estadual) {
		this.estadual = estadual;
	}

	public BigDecimal getMunicipal() {
		return municipal;
	}

	public void setMunicipal(BigDecimal municipal) {
		this.municipal = municipal;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}
	
	
}
