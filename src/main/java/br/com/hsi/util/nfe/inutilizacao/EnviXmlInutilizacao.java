package br.com.hsi.util.nfe.inutilizacao;

import br.com.hsi.model.Inutilizacao;
import br.com.hsi.util.SocketFactoryDinamico;
import br.inf.portalfiscal.www.nfe.wsdl.nfeinutilizacao2.NfeInutilizacao2Stub;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.httpclient.protocol.Protocol;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Iterator;

public class EnviXmlInutilizacao {

    private Inutilizacao inutilizacao;
    private static final int SSL_PORT = 443;
    private String xml;
    private URL url;

    public EnviXmlInutilizacao (Inutilizacao inutilizacao, String xml){
        this.inutilizacao = inutilizacao;
        this.xml = xml;
    }

    public Inutilizacao enviaInutilizacao(){

        try {
            String codigoDoEstado = inutilizacao.getEmpresa().getCodigoIbgeEstado();


            if( inutilizacao.getAmbiente() == 1){
                url = new URL("https://nfe.svrs.rs.gov.br/ws/nfeinutilizacao/nfeinutilizacao2.asmx");
            }else{
                url = new URL("https://nfe-homologacao.svrs.rs.gov.br/ws/nfeinutilizacao/nfeinutilizacao2.asmx");
            }

            //--------------------------------------  Valida Certificado Digital   -------------------------------------------

            String caminhoDoCertificadoDoCliente = "/HSI/Certificado/" + inutilizacao.getEmpresa().getNomeCertificado();
            String senhaDoCertificado = inutilizacao.getEmpresa().getSenhaCertificado();
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

            String xmlEnvInutilizacao = xml.toString();
            OMElement ome = AXIOMUtil.stringToOM(xmlEnvInutilizacao);

            Iterator<?> children = ome.getChildrenWithLocalName("NFe");
            while (children.hasNext()) {
                OMElement omElement = (OMElement) children.next();
                if ((omElement != null) && ("NFe".equals(omElement.getLocalName()))) {
                    omElement.addAttribute("xmlns", "http://www.portalfiscal.inf.br/nfe", null);
                }
            }

            NfeInutilizacao2Stub.NfeDadosMsg dadosMsg = new NfeInutilizacao2Stub.NfeDadosMsg();
            dadosMsg.setExtraElement(ome);

            NfeInutilizacao2Stub.NfeCabecMsg nfeCabecMsg = new NfeInutilizacao2Stub.NfeCabecMsg();
            nfeCabecMsg.setCUF(codigoDoEstado);
            nfeCabecMsg.setVersaoDados("2.00");

            NfeInutilizacao2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeInutilizacao2Stub.NfeCabecMsgE();
            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

            NfeInutilizacao2Stub stub = new NfeInutilizacao2Stub(url.toString());
            NfeInutilizacao2Stub.NfeInutilizacaoNF2Result result = stub.nfeInutilizacaoNF2(dadosMsg, nfeCabecMsgE);

            inutilizacao.setStatus(retorno(result.getExtraElement().toString()));

            return inutilizacao;

        }catch (Exception e){
            return null;
        }

    }

    private String retorno(String retorno){
        String[] retornoNf_1 = retorno.split("<xMotivo>");
        String[] retornoNf_2 = retornoNf_1[retornoNf_1.length - 1].split("</xMotivo>");
        return retornoNf_2[0];
    }
}
