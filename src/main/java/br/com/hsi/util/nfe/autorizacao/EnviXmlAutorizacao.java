package br.com.hsi.util.nfe.autorizacao;

import br.com.hsi.model.NotaFiscal;
import br.com.hsi.util.SocketFactoryDinamico;
import br.com.hsi.util.jsf.FacesUtil;
import br.com.hsi.util.nfe.ValidadorXML;
import br.inf.portalfiscal.www.nfe.wsdl.nfeautorizacao.NfeAutorizacaoStub;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.httpclient.protocol.Protocol;

import java.io.*;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;



public class EnviXmlAutorizacao implements Serializable {  
	private static final long serialVersionUID = 1L;
	private static final int SSL_PORT = 443;
    private NotaFiscal notaFiscal;
    private URL url;

    public EnviXmlAutorizacao(NotaFiscal notaFiscal) {
    	this.notaFiscal = notaFiscal;
    }
  
    public NotaFiscal autorizaNfe() { 

        try {  
            String codigoDoEstado = notaFiscal.getCodIbgeEstadoEmpresa();


        	if(notaFiscal.getAmbiente() == 1){
        		url = new URL("https://nfe.svrs.rs.gov.br/ws/NfeAutorizacao/NFeAutorizacao.asmx");  
        	}else{        		
        		url = new URL("https://nfe-homologacao.svrs.rs.gov.br/ws/NfeAutorizacao/NFeAutorizacao.asmx");  
        	}
  
            //--------------------------------------  Valida Certificado Digital   -------------------------------------------
              
            String caminhoDoCertificadoDoCliente = "/HSI/Certificado/" + notaFiscal.getCaminhoCertificado();
            String senhaDoCertificado = notaFiscal.getSenhaCertificado();
            String arquivoCacertsGeradoTodosOsEstados = "/HSI/NFeCacerts";
  
            InputStream entrada = new FileInputStream(caminhoDoCertificadoDoCliente);  
            KeyStore ks = KeyStore.getInstance("pkcs12");  
            try {  
                ks.load(entrada, senhaDoCertificado.toCharArray());  
            } catch (IOException e) {  
                throw new Exception("Senha do Certificado Digital esta incorreta ou Certificado inválido.");  
            }  
            
            //--------------------------------------  Valida Certificado Digital   -------------------------------------------
            
            
            //---------------------------------------  Elimina Erros no Certificado Digital  ------------------------------------

            String alias = "";    
            Enumeration<String> aliasesEnum = ks.aliases();    
            while (aliasesEnum.hasMoreElements()) {    
                alias = aliasesEnum.nextElement();
                if (ks.isKeyEntry(alias)) break;    
            }  
            X509Certificate certificate = (X509Certificate) ks.getCertificate(alias);  
            PrivateKey privateKey = (PrivateKey) ks.getKey(alias, senhaDoCertificado.toCharArray());  
            SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(certificate, privateKey);
            socketFactoryDinamico.setFileCacerts(arquivoCacertsGeradoTodosOsEstados);  
  
            Protocol protocol = new Protocol("https", socketFactoryDinamico, SSL_PORT);    
            Protocol.registerProtocol("https", protocol);             
              
            //---------------------------------------  Elimina Erros no Certificado Digital  ------------------------------------
            
            StringBuilder xml = new StringBuilder();  
            String linha;
            String caminhoArquivo = "/HSI/Envio/"+ notaFiscal.getChaveAcesso()+"-Assinado.xml";
            BufferedReader in = new BufferedReader(new InputStreamReader(  
                    new FileInputStream(caminhoArquivo), "ISO-8859-1"));  
            while ((linha = in.readLine()) != null) {  
                xml.append(linha);  
            }  
            in.close();  

            String xmlEnvNFe = xml.toString();  
            OMElement ome = AXIOMUtil.stringToOM(xmlEnvNFe);  

            Iterator<?> children = ome.getChildrenWithLocalName("NFe");    
            while (children.hasNext()) {  
                OMElement omElement = (OMElement) children.next();    
                if ((omElement != null) && ("NFe".equals(omElement.getLocalName()))) {    
                    omElement.addAttribute("xmlns", "http://www.portalfiscal.inf.br/nfe", null);    
                }  
            }  

            NfeAutorizacaoStub.NfeDadosMsg dadosMsg = new NfeAutorizacaoStub.NfeDadosMsg();
            dadosMsg.setExtraElement(ome);  
            
            NfeAutorizacaoStub.NfeCabecMsg nfeCabecMsg = new NfeAutorizacaoStub.NfeCabecMsg();
            nfeCabecMsg.setCUF(codigoDoEstado);  
            nfeCabecMsg.setVersaoDados("3.10"); 

            NfeAutorizacaoStub.NfeCabecMsgE nfeCabecMsgE = new NfeAutorizacaoStub.NfeCabecMsgE();  
            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);  

            NfeAutorizacaoStub stub = new NfeAutorizacaoStub(url.toString());  
            NfeAutorizacaoStub.NfeAutorizacaoLoteResult result = stub.nfeAutorizacaoLote(dadosMsg, nfeCabecMsgE);  
 
            new File("/HSI/Envio/"+ notaFiscal.getChaveAcesso()+"-Assinado.xml").delete();
                        
            notaFiscal.setMensagemRetorno(mensagemRetorno(result.getExtraElement().toString()));
            if(notaFiscal.getMensagemRetorno().contains("Autorizado")){
            	notaFiscal.setProtocoloAutorizacao(retornoProtocoloAutorizacao(result.getExtraElement().toString()));
            }else{
            	FacesUtil.addErrorMessage("Rejeição: " + notaFiscal.getMensagemRetorno());
            	List<String> erros = new ValidadorXML("/HSI/Schemas/enviNFe_v3.10.xsd", xmlEnvNFe).validaXML();
            	for (String erro : erros) {            		
            		FacesUtil.addErrorMessage(erro);
				}
            }
            
            ProcessaXmlAutorizacao gerandoDistribuicao = new ProcessaXmlAutorizacao(notaFiscal, xmlEnvNFe, result.getExtraElement().toString());
            
            return gerandoDistribuicao.GerandoNfe();
        } catch (Exception e) {  
        	e.printStackTrace();
        	return null;
        } 
    }
    
    private String retornoProtocoloAutorizacao(String retorno){
    	int inicio = retorno.indexOf("<nProt>") + 7;
    	int fim = retorno.indexOf("</nProt>");
    	
    	String protocolo = retorno.substring(inicio, fim);
    	
    	return protocolo;
    }
    
    private String mensagemRetorno(String retorno){
		String[] retornoNf_1 = retorno.split("<xMotivo>");
		String[] retornoNf_2 = retornoNf_1[retornoNf_1.length - 1].split("</xMotivo>");
		return retornoNf_2[0];
    }


    /*************DAQUI PRA BAIXO A3****************/

    public NotaFiscal enviXmlAturorizacaoA3(X509Certificate cert, PrivateKey privateKey) {
        try {
            URL url = null;
            if (notaFiscal.getAmbiente() == 1 ) {
                url = new URL("https://nfe.svrs.rs.gov.br/ws/NfeAutorizacao/NFeAutorizacao.asmx");
            } else {
                url = new URL("https://nfe-homologacao.svrs.rs.gov.br/ws/NfeAutorizacao/NFeAutorizacao.asmx");
            }

            String arquivoCacertsGeradoTodosOsEstados = "/HSI/NFeCacerts";

            SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(cert, privateKey);
            socketFactoryDinamico.setFileCacerts(arquivoCacertsGeradoTodosOsEstados);

            Protocol protocol = new Protocol("https", socketFactoryDinamico, SSL_PORT);
            Protocol.registerProtocol("https", protocol);

            StringBuilder xml = new StringBuilder();
            String linha;
            String caminhoArquivo = "/HSI/Envio/"+ notaFiscal.getChaveAcesso()+"-Assinado.xml";
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(caminhoArquivo), "ISO-8859-1"));
            while ((linha = in.readLine()) != null) {
                xml.append(linha);
            }
            in.close();

            String xmlEnvNFe = xml.toString();
            OMElement ome = AXIOMUtil.stringToOM(xmlEnvNFe);

            Iterator<?> children = ome.getChildrenWithLocalName("NFe");
            while (children.hasNext()) {
                OMElement omElement = (OMElement) children.next();
                if ((omElement != null) && ("NFe".equals(omElement.getLocalName()))) {
                    omElement.addAttribute("xmlns", "http://www.portalfiscal.inf.br/nfe", null);
                }
            }

            NfeAutorizacaoStub.NfeDadosMsg dadosMsg = new NfeAutorizacaoStub.NfeDadosMsg();
            dadosMsg.setExtraElement(ome);

            NfeAutorizacaoStub.NfeCabecMsg nfeCabecMsg = new NfeAutorizacaoStub.NfeCabecMsg();
            nfeCabecMsg.setCUF(notaFiscal.getCodIbgeEstadoEmpresa());
            nfeCabecMsg.setVersaoDados("3.10");

            NfeAutorizacaoStub.NfeCabecMsgE nfeCabecMsgE = new NfeAutorizacaoStub.NfeCabecMsgE();
            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

            NfeAutorizacaoStub stub = new NfeAutorizacaoStub(url.toString());
            NfeAutorizacaoStub.NfeAutorizacaoLoteResult result = stub.nfeAutorizacaoLote(dadosMsg, nfeCabecMsgE);

            new File("/HSI/Envio/"+ notaFiscal.getChaveAcesso()+"-Assinado.xml").delete();

            notaFiscal.setMensagemRetorno(mensagemRetorno(result.getExtraElement().toString()));
            if(notaFiscal.getMensagemRetorno().contains("Autorizado")){
                notaFiscal.setProtocoloAutorizacao(retornoProtocoloAutorizacao(result.getExtraElement().toString()));
            }else{
                FacesUtil.addErrorMessage("Rejeição: " + notaFiscal.getMensagemRetorno());
                List<String> erros = new ValidadorXML("/HSI/Schemas/enviNFe_v3.10.xsd", xmlEnvNFe).validaXML();
                for (String erro : erros) {
                    FacesUtil.addErrorMessage(erro);
                }
            }

            ProcessaXmlAutorizacao gerandoDistribuicao = new ProcessaXmlAutorizacao(notaFiscal, xmlEnvNFe, result.getExtraElement().toString());

            return gerandoDistribuicao.GerandoNfe();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}