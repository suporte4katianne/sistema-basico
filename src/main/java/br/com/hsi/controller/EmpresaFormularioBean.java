package br.com.hsi.controller;

import br.com.hsi.model.Empresa;
import br.com.hsi.model.dados.Cep;
import br.com.hsi.model.dados.Cidade;
import br.com.hsi.model.dados.Estado;
import br.com.hsi.service.GestaoEmpresa;
import br.com.hsi.service.GestaoEndereco;
import br.com.hsi.util.exception.NegocioException;
import br.com.hsi.util.jsf.FacesUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Named
@ViewScoped
public class EmpresaFormularioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	@Inject
	private GestaoEndereco gestaoEndereco;
	@Inject
	private GestaoEmpresa gestaoEmpresa;

	
	private Empresa empresa;
	private List<Cidade> cidades;
	private List<Estado> estados;
	private UploadedFile logo;
	private UploadedFile arquivoCertificado;
	private String razaoSocialCertificado;
	private String cnpjCertificado;
	private String validadeInicialCertificado;
	private String validadeFinalCertificado;
	private byte[] certificado;

	
	public void inicializar() throws IOException{
		estados = gestaoEndereco.estados();
		if(empresa.getId() != null){
			cidades = gestaoEndereco.cidadePorEstado(empresa.getEstado().getIdEstado());
			if(empresa.getTipoCertificado().equals("A1")){
				try {
					validaCertificado(FileUtils.openInputStream(new File("/HSI/Certificado/" + empresa.getNomeCertificado())));
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
				}
			}
		}else{
			empresa = new Empresa();
		}
	}


	public void buscaEndereco(){
		Cep cep = gestaoEndereco.cep(empresa.getCep().replaceAll("-", ""));
		empresa.setEstado(gestaoEndereco.estadoPorId(cep.getCidade().getEstado().getIdEstado()));
		cidades = gestaoEndereco.cidadePorEstado(empresa.getEstado().getIdEstado());
		empresa.setCidade(gestaoEndereco.cidadePorId(cep.getCidade().getIdCidade()));
		empresa.setRua(cep.getEndereco());
		empresa.setBairro(cep.getBairro());
	}
	
	public void carregaCidades(AjaxBehaviorEvent event) {
		cidades = new ArrayList<>();
		cidades = gestaoEndereco.cidadePorEstado(empresa.getEstado().getIdEstado());
	}
	
	public void carregaArquivo(FileUploadEvent event) throws IOException {
		arquivoCertificado = event.getFile();
		certificado = arquivoCertificado.getContents();
		empresa.setNomeCertificado(FilenameUtils.getName(arquivoCertificado.getFileName()));
		validaCertificado(arquivoCertificado.getInputstream());
	}
	
	public void carregaLogo(FileUploadEvent event){
		logo = event.getFile();
		empresa.setLogo(logo.getContents());
	}

	public void salvar() throws IOException {
		empresa.setNomecidade(empresa.getCidade().getCidade());
		empresa.setCodigoIbgeCidade(empresa.getCidade().getCodigo());
		empresa.setNomeEstado(empresa.getEstado().getSiglaEstado());
		empresa.setCodigoIbgeEstado(empresa.getEstado().getCodigoIbge());

		if(certificado != null){
			FileUtils.writeByteArrayToFile(new File("/HSI/Certificado/" + empresa.getNomeCertificado()),certificado);
		}

		try {
			gestaoEmpresa.salvar(empresa);
			FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Empresa.xhtml");
		} catch (NegocioException e) {
			empresa = new Empresa();
			FacesUtil.addErrorMessage("Erro: "+e.getMessage());
		}
	}
	
    //-------------- Métodos privados --------------
	private void validaCertificado(InputStream inputCertificado){
		try{
			KeyStore ks = KeyStore.getInstance("pkcs12");
			ks.load(inputCertificado, empresa.getSenhaCertificado().toCharArray());
			Enumeration<String> eAliases = ks.aliases();    
			while (eAliases.hasMoreElements()) {    
				String alias = eAliases.nextElement();
				Certificate certificado = ks.getCertificate(alias);
				X509Certificate cert = (X509Certificate) certificado;    
				String nome = cert.getSubjectDN().getName();
				
	    		int posicaoDoisPontos = nome.indexOf(":");
	    		int posicaoFimCnpj = nome.indexOf(", OU=AR");
	    		
	    		razaoSocialCertificado = nome.substring(3, posicaoDoisPontos);
	    		cnpjCertificado = nome.substring(posicaoDoisPontos+1, posicaoFimCnpj);
	    		validadeInicialCertificado = dateFormat.format(cert.getNotBefore());
	    		validadeFinalCertificado = dateFormat.format(cert.getNotAfter());
	    		if(cert.getNotAfter().after(new Date(System.currentTimeMillis()))){
	    			empresa.setStatusCertificado("VALIDO");
	    			FacesUtil.addInfoMessage("O Certificado digital é valido!");
	    		}else{
	    			empresa.setStatusCertificado("VENCIDO");
	    			FacesUtil.addAlertMessage("O Certificado digital informado esta vencido!");
	    		}
			} 
			
		} catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
			empresa.setStatusCertificado("INVALIDO");
			if(empresa.getNomeCertificado().length() == 0){
				FacesUtil.addInfoMessage("Certificado digital deste empresa ainda não foi informado");
			}else{				
				FacesUtil.addErrorMessage("Erro de validação do Certificado - Senha Incorreta");
			}
		}
	}
	
	//-------------- Getters and Setters --------------
	

	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public List<Cidade> getCidades() {
		return cidades;
	}
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	public List<Estado> getEstados() {
		return estados;
	}
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	public UploadedFile getFile() {
		return arquivoCertificado;
	}
	public void setFile(UploadedFile file) {
		this.arquivoCertificado = file;
	}
	public String getRazaoSocialCertificado() {
		return razaoSocialCertificado;
	}
	public void setRazaoSocialCertificado(String razaoSocialCertificado) {
		this.razaoSocialCertificado = razaoSocialCertificado;
	}
	public String getValidadeInicialCertificado() {
		return validadeInicialCertificado;
	}
	public void setValidadeInicialCertificado(String validadeInicialCertificado) {
		this.validadeInicialCertificado = validadeInicialCertificado;
	}
	public String getValidadeFinalCertificado() {
		return validadeFinalCertificado;
	}
	public void setValidadeFinalCertificado(String validadeFinalCertificado) {
		this.validadeFinalCertificado = validadeFinalCertificado;
	}
	public String getCnpjCertificado() {
		return cnpjCertificado;
	}
	public void setCnpjCertificado(String cnpjCertificado) {
		this.cnpjCertificado = cnpjCertificado;
	}
	
	
	

}
