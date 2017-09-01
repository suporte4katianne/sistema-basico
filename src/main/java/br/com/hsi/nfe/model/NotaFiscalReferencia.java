package br.com.hsi.nfe.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "nota_fiscal_referencia")
public class NotaFiscalReferencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_nfe")
	private NotaFiscal nfe;
	@Column(name = "tipo_documento")
	private String tipoDocumento;  //0 cupom fiscal ou 1 para notaFiscal

	private String modelo;
	private String serie; 
	private String numero;
	private String chave;
	
	private String coo;
	private String pdv;
	private String ecfSerie;
	private Date data;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public NotaFiscal getNfe() {
		return nfe;
	}
	public void setNfe(NotaFiscal nfe) {
		this.nfe = nfe;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public String getCoo() {
		return coo;
	}
	public void setCoo(String coo) {
		this.coo = coo;
	}
	public String getPdv() {
		return pdv;
	}
	public void setPdv(String pdv) {
		this.pdv = pdv;
	}
	public String getEcfSerie() {
		return ecfSerie;
	}
	public void setEcfSerie(String ecfSerie) {
		this.ecfSerie = ecfSerie;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	
	public String toStringCupomFiscal() {
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		return "COO: " + coo + "; PDV: " + pdv + "; Série ECF: " + ecfSerie + "; Data: " + dataFormatada.format(data) + ";";
	}
	
	public String toStringDocumentosManuais(){
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		return "Número: " + numero + "; Série: " + serie + "; Emissão: " + dataFormatada.format(data) + ";";
	}
	
	public String toStringDocumentosFiscais(){
		return "Número: " + numero + "; Modelo: " + modelo + "; Série: " + serie + "; Chave: " + chave + ";";
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
		NotaFiscalReferencia other = (NotaFiscalReferencia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
