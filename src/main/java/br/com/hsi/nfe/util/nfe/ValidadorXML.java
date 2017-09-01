package br.com.hsi.nfe.util.nfe;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class ValidadorXML implements ErrorHandler {
	
	private List<String> listaComErrosDeValidacao; 
	private String xml;
	private String xsd;
    
    public ValidadorXML(String xsd, String xml) {  
        this.listaComErrosDeValidacao = new ArrayList<String>();  
        this.xml = xml;
        this.xsd = xsd;
    }
    
    
    public List<String> validaXML(){
    	try {
    		List<String> errosValidacao = validateXml(normalizeXML(xml), xsd);  
    		return errosValidacao;
        } catch (Exception e) {  
        	e.printStackTrace();
        	return null;
        }     
//            if ((errosValidacao != null) && (errosValidacao.size() > 0)) {  
//                for (String msgError : errosValidacao) {  
//                    info("| Erro XML: " + msgError);  
//                }  
//            }  
//            else {  
//                info("| OK: XML Validado com Sucesso!");  
//            }  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
    }
    
    
    private static String normalizeXML(String xml) {  
        if ((xml != null) && (!"".equals(xml))) {  
            xml = xml.replaceAll("\\r\\n", "");  
            xml = xml.replaceAll(" standalone=\"no\"", "");  
        }  
        return xml;  
    }  
  
    private List<String> validateXml(String xml, String xsd) throws Exception {  
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        factory.setNamespaceAware(true);  
        factory.setValidating(true);  
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");  
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", xsd);  
        DocumentBuilder builder = null;  
        try {  
            builder = factory.newDocumentBuilder();  
            builder.setErrorHandler(this);  
        } catch (ParserConfigurationException ex) {  
            error("| validateXml():");  
            throw new Exception(ex.toString());  
        }  
  
        org.w3c.dom.Document document;  
        try {  
            document = builder.parse(new ByteArrayInputStream(xml.getBytes()));  
            org.w3c.dom.Node rootNode  = document.getFirstChild();  
            info("| Validando Node: " + rootNode.getNodeName());  
            return this.getListaComErrosDeValidacao();  
        } catch (Exception ex) {  
            error("| validateXml():");  
            throw new Exception(ex.toString());  
        }  
    }  
  
    @Override  
    public void error(SAXParseException exception) throws SAXException {  
        if (isError(exception)) {  
            listaComErrosDeValidacao.add(tratamentoRetorno(exception.getMessage()));  
        }  
    }  
  
    @Override  
    public void fatalError(SAXParseException exception) throws SAXException {  
        listaComErrosDeValidacao.add(tratamentoRetorno(exception.getMessage()));  
    }  
  
    @Override  
    public void warning(SAXParseException exception) throws SAXException {  
        listaComErrosDeValidacao.add(tratamentoRetorno(exception.getMessage()));  
    }  
  
    private String tratamentoRetorno(String message) {  
        message = message.replaceAll("cvc-type.3.1.3:", "");  
        message = message.replaceAll("cvc-complex-type.2.4.a:", "");  
        message = message.replaceAll("cvc-complex-type.2.4.b:", "");  
        message = message.replaceAll("The value", "O valor");  
        message = message.replaceAll("of element", "do campo");  
        message = message.replaceAll("is not valid", "não é valido");  
        message = message.replaceAll("Invalid content was found starting with element", "Encontrado o campo");  
        message = message.replaceAll("One of", "Campo(s)");  
        message = message.replaceAll("is expected", "é obrigatorio");  
        message = message.replaceAll("\\{", "");  
        message = message.replaceAll("\\}", "");  
        message = message.replaceAll("\"", "");  
        message = message.replaceAll("http://www.portalfiscal.inf.br/nfe:", "");  
        return message.trim();  
    }  
  
    private List<String> getListaComErrosDeValidacao() {  
        return listaComErrosDeValidacao;  
    }  
  
    private boolean isError(SAXParseException exception) {
        return !exception.getMessage().startsWith("cvc-pattern-valid") &&
                !exception.getMessage().startsWith("cvc-maxLength-valid") &&
                !exception.getMessage().startsWith("cvc-datatype");
    }  
      
    private static void error(String error) {  
        System.out.println("ERROR: " + error);  
    }  
  
    private static void info(String info) {  
        System.out.println("INFO: " + info);  
    }  
  
}

