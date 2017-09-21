package br.com.hsi.util.nfe.eventos;

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

public class AssinaXmlEventos {

    private static final String INFCANC = "infEvento";

    private NotaFiscal notaFiscal;
    private String xml;
    private PrivateKey privateKey;
    private KeyInfo keyInfo;

    private KeyStore.PrivateKeyEntry privateKeyEntry;
    private KeyStore ks;
    private X509Certificate cert;
    private PrivateKey privateKeyA3;

    public AssinaXmlEventos(NotaFiscal notaFiscal, String xml) {
        this.notaFiscal = notaFiscal;
        this.xml = xml;
    }

    public AssinaXmlEventos() { }

    public NotaFiscal assinaXmlEventos() {
        try {
            String caminhoDoCertificadoDoCliente = "/HSI/Certificado/" + notaFiscal.getCaminhoCertificado();
            String senhaDoCertificadoDoCliente = notaFiscal.getSenhaCertificado();

            String xmlCancNFeAssinado = assinaCancNFe(xml, caminhoDoCertificadoDoCliente, senhaDoCertificadoDoCliente);

            FileWriter arquivo;
            arquivo = new FileWriter(new File("/HSI/Envio/" + notaFiscal.getChaveAcesso() + "-AssinadoEvento.xml"));
            arquivo.write(xmlCancNFeAssinado);
            arquivo.close();

            EnviXmlEventos xmlCancelamento = new EnviXmlEventos(notaFiscal);
            return xmlCancelamento.enviaEvento();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();
            return null;
        }
    }

    public String assinaCancNFe(String xml, String certificado, String senha) throws Exception {
        return assinaCancelametoInutilizacao(xml, certificado, senha, INFCANC);
    }

    public String assinaCancelametoInutilizacao(String xml, String certificado, String senha, String tagCancInut) throws Exception {
        Document document = documentFactory(xml);

        XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
        ArrayList<Transform> transformList = signatureFactory(signatureFactory);
        loadCertificates(certificado, senha, signatureFactory);

        NodeList elements = document.getElementsByTagName(tagCancInut);
        org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(0);
        String id = el.getAttribute("Id");
        el.setIdAttribute("Id", true);

        Reference ref = signatureFactory.newReference("#" + id,
                signatureFactory.newDigestMethod(DigestMethod.SHA1, null),
                transformList, null, null);

        SignedInfo si = signatureFactory.newSignedInfo(signatureFactory
                        .newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                                (C14NMethodParameterSpec) null), signatureFactory
                        .newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                Collections.singletonList(ref));

        XMLSignature signature = signatureFactory.newXMLSignature(si, keyInfo);

        if(tagCancInut.equals("infInut")) {
            DOMSignContext dsc = new DOMSignContext(privateKey, document.getFirstChild());
            signature.sign(dsc);
        } else {
            DOMSignContext dsc = new DOMSignContext(privateKey, document.getElementsByTagNameNS("http://www.portalfiscal.inf.br/nfe", "evento").item(0));
            signature.sign(dsc);
        }

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
        Document document = factory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
        return document;
    }

    private void loadCertificates(String certificado, String senha, XMLSignatureFactory signatureFactory)
            throws Exception {

        InputStream entrada = new FileInputStream(certificado);
        KeyStore ks = KeyStore.getInstance("pkcs12");
        try {
            ks.load(entrada, senha.toCharArray());
        } catch (IOException e) {
            throw new Exception("Senha do Certificado Digital incorreta ou Certificado inválido.");
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

    /******DAQUI PRA BAIXO A3*********/


    public AssinaXmlEventos(KeyStore.PrivateKeyEntry privateKeyEntry, KeyStore ks, PrivateKey privateKeyA3) {
        this.privateKeyEntry = privateKeyEntry;
        this.ks = ks;
        this.privateKeyA3 = privateKeyA3;
    }



    public NotaFiscal assinaXmlEventosA3(KeyStore ks, KeyStore.PrivateKeyEntry privateKeyEntry, PrivateKey privateKeyA3) {

        this.ks = ks;
        this.privateKeyEntry = privateKeyEntry;
        this.privateKeyA3 = privateKeyA3;

        try {
            String senhaDoCertificadoDoCliente = notaFiscal.getSenhaCertificado();

            String xmlCancNFeAssinado = assinaCancNFeA3(xml, senhaDoCertificadoDoCliente);

            FileWriter arquivo;
            arquivo = new FileWriter(new File("/HSI/Envio/" + notaFiscal.getChaveAcesso() + "-AssinadoEvento.xml"));
            arquivo.write(xmlCancNFeAssinado);
            arquivo.close();

            EnviXmlEventos xmlCancelamento = new EnviXmlEventos(notaFiscal);
            return xmlCancelamento.enviaEventoA3(cert, privateKeyA3);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();
            return null;
        }

    }


    public String assinaCancNFeA3(String xml, String senha) throws Exception {
        return assinaCancelametoInutilizacaoA3(xml, senha, INFCANC);
    }


    public static String removeAcentosA3(String str) {
        CharSequence cs = new StringBuilder(str == null ? "" : str);
        return Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }


    public String assinaCancelametoInutilizacaoA3(String xml, String senha, String tagCancInut) throws Exception {
        Document document = documentFactoryA3(xml);

        XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
        ArrayList<Transform> transformList = signatureFactoryA3(signatureFactory);
        loadCertificatesA3(senha, signatureFactory);

        NodeList elements = document.getElementsByTagName(tagCancInut);
        org.w3c.dom.Element el = (org.w3c.dom.Element) elements.item(0);
        String id = el.getAttribute("Id");
        el.setIdAttribute("Id", true);

        Reference ref = (Reference) signatureFactory.newReference("#" + id,
                signatureFactory.newDigestMethod(DigestMethod.SHA1, null),
                transformList, null, null);

        SignedInfo si = signatureFactory.newSignedInfo(signatureFactory
                        .newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
                                (C14NMethodParameterSpec) null), signatureFactory
                        .newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                Collections.singletonList(ref));

        XMLSignature signature = signatureFactory.newXMLSignature(si, keyInfo);

        if(tagCancInut.equals("infInut")) {
            DOMSignContext dsc = new DOMSignContext(privateKeyA3, document.getFirstChild());
            signature.sign(dsc);
        } else {
            DOMSignContext dsc = new DOMSignContext(privateKeyA3, document.getElementsByTagNameNS("http://www.portalfiscal.inf.br/nfe", "evento").item(0));
            signature.sign(dsc);
        }


        return outputXMLA3(document);
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

    public X509Certificate getCert() {
        return cert;
    }
}
