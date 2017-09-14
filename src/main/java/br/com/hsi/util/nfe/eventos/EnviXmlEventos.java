package br.com.hsi.util.nfe.eventos;

import br.com.hsi.model.NotaFiscal;
import br.com.hsi.util.SocketFactoryDinamico;
import br.inf.portalfiscal.www.nfe.wsdl.recepcaoevento.RecepcaoEventoStub;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.httpclient.protocol.Protocol;

import java.io.*;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class EnviXmlEventos implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int SSL_PORT = 443;
    private NotaFiscal notaFiscal;

    public EnviXmlEventos(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public NotaFiscal enviaEvento() {
        try {
            String codigoDoEstado = notaFiscal.getCodIbgeEstadoEmpresa();

            URL url;
            if (notaFiscal.getAmbiente() == 1) {
                url = new URL("https://nfe.svrs.rs.gov.br/ws/recepcaoevento/recepcaoevento.asmx");
            } else {
                url = new URL("https://nfe-homologacao.svrs.rs.gov.br/ws/recepcaoevento/recepcaoevento.asmx");
            }


            //--------------------------------------  Valida Certificado Digital   -------------------------------------------

            String caminhoDoCertificadoDoCliente = "/HSI/Certificado/"+ notaFiscal.getCaminhoCertificado();
            String senhaDoCertificado = notaFiscal.getSenhaCertificado();
            String arquivoCacertsGeradoTodosOsEstados = "/HSI/NFeCacerts";

            InputStream entrada = new FileInputStream(caminhoDoCertificadoDoCliente);
            KeyStore ks = KeyStore.getInstance("pkcs12");
            try {
                ks.load(entrada, senhaDoCertificado.toCharArray());
            } catch (IOException e) {
                throw new Exception("Senha do Certificado Digital esta incorreta ou Certificado inválido.");
            }

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
            String caminhoArquivo = "/HSI/Envio/"+ notaFiscal.getChaveAcesso() + "-AssinadoEvento.xml";
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(caminhoArquivo), "ISO-8859-1"));
            while ((linha = in.readLine()) != null) {
                xml.append(linha);
            }
            in.close();

            String xmlCanNFe = xml.toString();

            System.out.println("xmlCanNFe = " + xmlCanNFe);

            OMElement ome = AXIOMUtil.stringToOM(xmlCanNFe);

            RecepcaoEventoStub.NfeDadosMsg dadosMsg = new RecepcaoEventoStub.NfeDadosMsg();
            dadosMsg.setExtraElement(ome);

            RecepcaoEventoStub.NfeCabecMsg nfeCabecMsg = new RecepcaoEventoStub.NfeCabecMsg();
            nfeCabecMsg.setCUF(codigoDoEstado);
            nfeCabecMsg.setVersaoDados("1.00");

            RecepcaoEventoStub.NfeCabecMsgE nfeCabecMsgE = new RecepcaoEventoStub.NfeCabecMsgE();
            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);


            RecepcaoEventoStub stub = new RecepcaoEventoStub(url.toString());
            RecepcaoEventoStub.NfeRecepcaoEventoResult result = stub.nfeRecepcaoEvento(dadosMsg, nfeCabecMsgE);

            new File("/HSI/Envio/"+ notaFiscal.getChaveAcesso() + "-AssinadoEvento.xml").delete();

            notaFiscal.setMensagemRetorno("Evento: " +retorno(result.getExtraElement().toString()));

            return notaFiscal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String retorno(String retorno){
        String[] retornoNf_1 = retorno.split("<xMotivo>");
        String[] retornoNf_2 = retornoNf_1[retornoNf_1.length - 1].split("</xMotivo>");
        return retornoNf_2[0];
    }
}
