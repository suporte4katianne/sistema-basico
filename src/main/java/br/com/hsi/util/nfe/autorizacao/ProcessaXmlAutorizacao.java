package br.com.hsi.util.nfe.autorizacao;

import br.com.hsi.model.NotaFiscal;
import br.com.hsi.util.jsf.FacesUtil;
import br.com.hsi.util.nfe.ValidadorXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

public class ProcessaXmlAutorizacao {
	private NotaFiscal notaFiscal;
	private String xmlRetConsReciNFe;
	private String xmlEnviNFe;

	public ProcessaXmlAutorizacao(NotaFiscal notaFiscal, String xmlEnviNFe, String xmlRetConsReciNFe) {
		this.xmlRetConsReciNFe = xmlRetConsReciNFe;
		this.xmlEnviNFe = xmlEnviNFe;
		this.notaFiscal = notaFiscal;
	}

	public NotaFiscal GerandoNfe() {
		try {
			Document document = documentFactory(xmlEnviNFe);
			NodeList nodeListNfe = document.getDocumentElement().getElementsByTagName("NFe");
			NodeList nodeListInfNfe = document.getElementsByTagName("infNFe");

			for (int i = 0; i < nodeListNfe.getLength(); i++) {
				Element el = (Element) nodeListInfNfe.item(i);
				String chaveNFe = el.getAttribute("Id");

				String xmlNFe = outputXML(nodeListNfe.item(i));
				String xmlProtNFe = getProtNFe(xmlRetConsReciNFe, chaveNFe);
				
				FileWriter arquivo;
				try {
					if(mensagemRetorno(xmlRetConsReciNFe).contains("Autorizado")){
						arquivo = new FileWriter(new File("/HSI/Autorizado/"+ notaFiscal.getChaveAcesso()+"-procNFE.xml"));
						arquivo.write(buildNFeProc(xmlNFe, xmlProtNFe));
						arquivo.close();
						
						
					}else{
						arquivo = new FileWriter(new File("/HSI/Regeitado/"+ notaFiscal.getChaveAcesso()+"-rejeitado.xml"));
						arquivo.write(xmlEnviNFe);
						arquivo.close();
					}
				} catch (IOException e) {
					System.out.println(e);
				}
			}
			
			
            notaFiscal.setMensagemRetorno(mensagemRetorno(xmlRetConsReciNFe));
            if(notaFiscal.getMensagemRetorno().contains("Autorizado")){
            	notaFiscal.setProtocoloAutorizacao(retornoProtocoloAutorizacao(xmlRetConsReciNFe));
            }else{
            	FacesUtil.addErrorMessage("Rejeição: " + notaFiscal.getMensagemRetorno());
            	List<String> erros = new ValidadorXML("/HSI/Schemas/enviNFe_v3.10.xsd", xmlEnviNFe).validaXML();
            	for (String erro : erros) {            		
            		FacesUtil.addErrorMessage(erro);
				}
            }
            
            if(notaFiscal.getEmpresa().getTipoCertificado().equals("A3")){
            	new File("/HSI/Envio/"+ notaFiscal.getChaveAcesso()+"-Assinado.xml").delete();
            	new File("/HSI/Envio/"+ notaFiscal.getChaveAcesso()+"-Retorno.xml").delete();
            }
            
			return notaFiscal;
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


	private static String buildNFeProc(String xmlNFe, String xmlProtNFe) {
		StringBuilder nfeProc = new StringBuilder();
		nfeProc.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
				.append("<nfeProc versao=\"3.10\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">").append(xmlNFe)
				.append(xmlProtNFe).append("</nfeProc>");
		return nfeProc.toString();
	}

	private static String getProtNFe(String xml, String chaveNFe)
			throws SAXException, IOException, ParserConfigurationException, TransformerException {
		Document document = documentFactory(xml);
		NodeList nodeListProtNFe = document.getDocumentElement().getElementsByTagName("protNFe");
		NodeList nodeListChNFe = document.getElementsByTagName("chNFe");
		for (int i = 0; i < nodeListProtNFe.getLength(); i++) {
			Element el = (Element) nodeListChNFe.item(i);
			String chaveProtNFe = el.getTextContent();
			if (chaveNFe.contains(chaveProtNFe)) {
				return outputXML(nodeListProtNFe.item(i));
			}
		}
		return "";
	}

	private static String outputXML(Node node) throws TransformerException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(node), new StreamResult(os));
		String xml = os.toString();
		if ((xml != null) && (!"".equals(xml))) {
			xml = xml.replaceAll("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "");
		}
		return xml;
	}

	private static Document documentFactory(String xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		Document document = factory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
		return document;
	}

	public static String lerXML(String fileXML) throws IOException {
		String linha = "";
		StringBuilder xml = new StringBuilder();

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileXML)));
		while ((linha = in.readLine()) != null) {
			xml.append(linha);
		}
		in.close();

		return xml.toString();
	}

}