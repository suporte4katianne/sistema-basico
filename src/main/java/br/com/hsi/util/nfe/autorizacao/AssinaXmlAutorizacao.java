package br.com.hsi.util.nfe.autorizacao;

import br.com.hsi.model.NotaFiscal;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.*;
import java.security.cert.X509Certificate;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class AssinaXmlAutorizacao {
	private static AssinaXmlAutorizacao instance;
	private static final String INFINUT = "infInut";
	private static final String INFCANC = "infCanc";
	private static final String NFE = "NFe";

	private NotaFiscal notaFiscal;
	private String xml;
	private PrivateKey privateKey;
	private KeyInfo keyInfo;

	private KeyStore.PrivateKeyEntry privateKeyEntry;
	private KeyStore ks;
	private X509Certificate cert;
	private PrivateKey privateKeyA3;

	public AssinaXmlAutorizacao(NotaFiscal notaFiscal, String xml) {
		this.notaFiscal = notaFiscal;
		this.xml = xml;
	}

	public NotaFiscal AssinaXml() {
		try {
			String caminhoDoCertificadoDoCliente = "/HSI/Certificado/" + notaFiscal.getCaminhoCertificado();
			String senhaDoCertificadoDoCliente = notaFiscal.getSenhaCertificado();

			String xmlEnviNFeAssinado = assinaEnviNFe(xml, caminhoDoCertificadoDoCliente, senhaDoCertificadoDoCliente);
			geraXML(xmlEnviNFeAssinado);

			EnviXmlAutorizacao autorizaNfe = new EnviXmlAutorizacao(notaFiscal);
			return autorizaNfe.autorizaNfe();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("teste |ERRO: " + e.getMessage());
			return null;
		}
	}

	public String assinaEnviNFe(String xml, String certificado, String senha) throws Exception {
		Document document = documentFactory(xml);
		XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
		ArrayList<Transform> transformList = signatureFactory(signatureFactory);
		loadCertificates(certificado, senha, signatureFactory);

		for (int i = 0; i < document.getDocumentElement().getElementsByTagName(NFE).getLength(); i++) {
			assinarNFe(signatureFactory, transformList, privateKey, keyInfo, document, i);
		}

		return outputXML(document);
	}


	public String assinaCancNFe(String xml, String certificado, String senha) throws Exception {
		return assinaCancelametoInutilizacao(xml, certificado, senha, INFCANC);
	}

	public String assinaInutNFe(String xml, String certificado, String senha) throws Exception {
		return assinaCancelametoInutilizacao(xml, certificado, senha, INFINUT);
	}

	public static String removeAcentos(String str) {
		CharSequence cs = new StringBuilder(str == null ? "" : str);
		return Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	private void assinarNFe(XMLSignatureFactory fac, ArrayList<Transform> transformList, PrivateKey privateKey,
			KeyInfo ki, Document document, int indexNFe) throws Exception {
		NodeList elements = document.getElementsByTagName("infNFe");
		org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(indexNFe);
		el.setIdAttribute("Id", true);
		String id = el.getAttribute("Id");

		Reference ref = fac.newReference("#" + id, fac.newDigestMethod(DigestMethod.SHA1, null), transformList, null,
				null);

		SignedInfo si = fac.newSignedInfo(
				fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
				fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

		XMLSignature signature = fac.newXMLSignature(si, ki);

		DOMSignContext dsc = new DOMSignContext(privateKey,
				document.getDocumentElement().getElementsByTagName(NFE).item(indexNFe));
		signature.sign(dsc);
	}

	private String assinaCancelametoInutilizacao(String xml, String certificado, String senha, String tagCancInut)
			throws Exception {
		Document document = documentFactory(xml);

		XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
		ArrayList<Transform> transformList = signatureFactory(signatureFactory);
		loadCertificates(certificado, senha, signatureFactory);

		NodeList elements = document.getElementsByTagName(tagCancInut);
		org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(0);
		String id = el.getAttribute("Id");

		Reference ref = signatureFactory.newReference("#" + id,
				signatureFactory.newDigestMethod(DigestMethod.SHA1, null), transformList, null, null);

		SignedInfo si = signatureFactory.newSignedInfo(
				signatureFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
						(C14NMethodParameterSpec) null),
				signatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

		XMLSignature signature = signatureFactory.newXMLSignature(si, keyInfo);

		DOMSignContext dsc = new DOMSignContext(privateKey, document.getFirstChild());
		signature.sign(dsc);

		return outputXML(document);
	}

	private ArrayList<Transform> signatureFactory(XMLSignatureFactory signatureFactory)
			throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		ArrayList<Transform> transformList = new ArrayList<Transform>();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = signatureFactory.newTransform(Transform.ENVELOPED, tps);
		Transform c14NTransform = signatureFactory.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);
		return transformList;
	}

	private Document documentFactory(String xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		Document document = factory.newDocumentBuilder().parse(new ByteArrayInputStream(removeAcentos(xml).getBytes()));
		return document;
	}


	private void loadCertificates(String certificado, String senha, XMLSignatureFactory signatureFactory)
			throws Exception {

		InputStream entrada = new FileInputStream(certificado);
		KeyStore ks = KeyStore.getInstance("pkcs12");
		try {
			ks.load(entrada, senha.toCharArray());
		} catch (IOException e) {
			throw new Exception("Senha do Certificado Digital incorreta ou Certificado inv�lido.");
		}

		KeyStore.PrivateKeyEntry pkEntry = null;
		Enumeration<String> aliasesEnum = ks.aliases();
		while (aliasesEnum.hasMoreElements()) {
			String alias = aliasesEnum.nextElement();
			if (ks.isKeyEntry(alias)) {
				pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias,
						new KeyStore.PasswordProtection(senha.toCharArray()));
				privateKey = pkEntry.getPrivateKey();
				break;
			}
		}

		X509Certificate cert = (X509Certificate) pkEntry.getCertificate();

		KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
		List<X509Certificate> x509Content = new ArrayList<X509Certificate>();

		x509Content.add(cert);
		X509Data x509Data = keyInfoFactory.newX509Data(x509Content);
		keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));
	}

	private String outputXML(Document doc) throws TransformerException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		String xml = os.toString();
		if ((xml != null) && (!"".equals(xml))) {
			xml = xml.replaceAll("\\r\\n", "");
			xml = xml.replaceAll(" standalone=\"no\"", "");
		}
		return xml;
	}


	/*******DAQUI PRA BAIXO A3******/


	public NotaFiscal AssinaXmlCertificadoA3(KeyStore ks, KeyStore.PrivateKeyEntry privateKeyEntry, PrivateKey privateKeyA3) throws Exception {
		this.ks = ks;
		this.privateKeyEntry = privateKeyEntry;
		this.privateKeyA3 = privateKeyA3;

		try {
			String senhaDoCertificadoDoCliente = notaFiscal.getSenhaCertificado();

			String xmlEnviNFeAssinado = assinaEnviNFeA3(xml, senhaDoCertificadoDoCliente);

			geraXML(xmlEnviNFeAssinado);

			EnviXmlAutorizacao autorizaNfe = new EnviXmlAutorizacao(notaFiscal);
			return autorizaNfe.enviXmlAturorizacaoA3(cert, privateKeyA3);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("teste |ERRO: " + e.getMessage());
			return null;
		}
	}


	public static String removeAcentosA3(String str) {
		CharSequence cs = new StringBuilder(str == null ? "" : str);
		return Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	private String assinaEnviNFeA3(String xml, String senha) throws Exception {
		Document document = documentFactoryA3(xml);
		XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
		ArrayList<Transform> transformList = signatureFactoryA3(signatureFactory);
		loadCertificatesA3(senha, signatureFactory);

		for (int i = 0; i < document.getDocumentElement().getElementsByTagName(NFE).getLength(); i++) {
			assinarNFeA3(signatureFactory, transformList, privateKeyA3, keyInfo, document, i);
		}

		return outputXMLA3(document);
	}

	private void assinarNFeA3(XMLSignatureFactory fac, ArrayList<Transform> transformList,
							PrivateKey privateKey, KeyInfo ki, Document document, int indexNFe) throws Exception {


		NodeList elements = document.getElementsByTagName("infNFe");
		org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(indexNFe);
		String id = el.getAttribute("Id");
		el.setIdAttribute("Id", true);

		Reference ref = fac.newReference("#" + id, fac.newDigestMethod(DigestMethod.SHA1, null), transformList, null, null);

		SignedInfo si = fac.newSignedInfo(
				fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
				fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

		XMLSignature signature = fac.newXMLSignature(si, ki);

		System.out.println(document.getDocumentElement().getElementsByTagName(NFE).item(indexNFe));
		System.out.println(privateKey);

		DOMSignContext dsc = new DOMSignContext(privateKey, document.getDocumentElement().getElementsByTagName(NFE).item(indexNFe));
		signature.sign(dsc);

	}

	private ArrayList<Transform> signatureFactoryA3(XMLSignatureFactory signatureFactory)
			throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		ArrayList<Transform> transformList = new ArrayList<Transform>();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = signatureFactory.newTransform(Transform.ENVELOPED, tps);
		Transform c14NTransform = signatureFactory.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);

		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);
		return transformList;
	}

	private Document documentFactoryA3(String xml) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		Document document = factory.newDocumentBuilder().parse(new ByteArrayInputStream(removeAcentosA3(xml).getBytes()));
		return document;
	}

	@SuppressWarnings("restriction")
	private void loadCertificatesA3(String senha, XMLSignatureFactory signatureFactory) throws Exception {

		cert = (X509Certificate) privateKeyEntry.getCertificate();

		KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
		List<X509Certificate> x509Content = new ArrayList<X509Certificate>();

		x509Content.add(cert);
		X509Data x509Data = keyInfoFactory.newX509Data(x509Content);
		keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));
		Provider p = new sun.security.pkcs11.SunPKCS11("/HSI/properites.cfg");
		Security.addProvider(p);
		ks = KeyStore.getInstance("pkcs11", p);
	}

	private String outputXMLA3(Document doc) throws TransformerException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(doc), new StreamResult(os));
		String xml = os.toString();
		if ((xml != null) && (!"".equals(xml))) {
			xml = xml.replaceAll("\\r\\n", "");
			xml = xml.replaceAll(" standalone=\"no\"", "");
		}
		return xml;
	}



	private void geraXML(String xmlEnviNFeAssinado) {
		FileWriter arquivo;
		try {
			arquivo = new FileWriter(new File("/HSI/Envio/" + notaFiscal.getChaveAcesso() + "-Assinado.xml"));
			arquivo.write(xmlEnviNFeAssinado);
			arquivo.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
