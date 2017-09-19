package br.com.hsi.util.nfe.eventos;

import br.com.hsi.model.NotaFiscal;
import br.inf.portalfiscal.www.nfe.wsdl.nfeconsulta2.NfeConsulta2Stub;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;

import java.io.IOException;
import java.net.URL;
import java.security.Security;
import java.time.LocalDateTime;

public class GeraXmlEventos {
    private static NotaFiscal notaFiscal;

    public GeraXmlEventos(NotaFiscal notaFiscal) {
        GeraXmlEventos.notaFiscal = notaFiscal;
    }

    public NotaFiscal gerarCancelamento(String motivo) throws IOException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();

        String Id = "ID110111" + (notaFiscal.getChaveAcesso().replaceAll("NFe", "")) + "01";
        String estadoEmpresa = notaFiscal.getCodIbgeEstadoEmpresa();
        String cnpj = notaFiscal.getCnpjEmpresa()
                .replaceAll("\\.", "")
                .replaceAll("-", "")
                .replaceAll("/", "");
        String chave = (notaFiscal.getChaveAcesso().replaceAll("NFe", ""));
        String dataHoraEvento = now.withNano(0)+"-03:00";
        String protocoloNfe = notaFiscal.getProtocoloAutorizacao();

        StringBuilder xml = new StringBuilder();
        xml.append("<envEvento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">")
                .append("<idLote>1</idLote>")
                .append("<evento versao=\"1.00\">")
                .append("<infEvento Id=\"").append(Id).append("\">") //
                .append("<cOrgao>").append(estadoEmpresa).append("</cOrgao>")//
                .append("<tpAmb>").append(notaFiscal.getAmbiente()).append("</tpAmb>")//
                .append("<CNPJ>").append(cnpj).append("</CNPJ>")//
                .append("<chNFe>").append(chave).append("</chNFe>")//
                .append("<dhEvento>").append(dataHoraEvento).append("</dhEvento>")//
                .append("<tpEvento>110111</tpEvento>")
                .append("<nSeqEvento>1</nSeqEvento>")
                .append("<verEvento>1.00</verEvento>")
                .append("<detEvento versao=\"1.00\">")
                .append("<descEvento>Cancelamento</descEvento>")
                .append("<nProt>").append(protocoloNfe).append("</nProt>")//
                .append("<xJust>").append(motivo).append("</xJust>")//
                .append("</detEvento>")
                .append("</infEvento>")
                .append("</evento>")
                .append("</envEvento>");
        AssinaXmlEventos xmlCancelamento = new AssinaXmlEventos(notaFiscal, xml.toString());
        return xmlCancelamento.assinaXmlEventos();
    }

    public NotaFiscal gerarCartaDeCorrecao(String correcao) throws IOException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();


        String Id = "ID110110" + (notaFiscal.getChaveAcesso().replaceAll("NFe", "")) + "01";
        String estadoEmpresa = notaFiscal.getCodIbgeEstadoEmpresa();
        String cnpj = notaFiscal.getCnpjEmpresa()
                .replaceAll("\\.", "")
                .replaceAll("-", "").replaceAll("/", "");
        String chave = (notaFiscal.getChaveAcesso().replaceAll("NFe", ""));
        String dataHoraEvento = now.withNano(0)+"-03:00";
        String condicoesDeUso = "A Carta de Correcao e disciplinada pelo paragrafo 1o-A do art. 7o do Convenio S/N, de 15" +
                " de dezembro de 1970 e pode ser utilizada para regularizacao de erro ocorrido na emissao de documento fiscal," +
                " desde que o erro nao esteja relacionado com: I - as variaveis que determinam o valor do imposto tais como: base " +
                "de calculo, aliquota, diferenca de preco, quantidade, valor da operacao ou da prestacao; II - a correcao de dados " +
                "cadastrais que implique mudanca do remetente ou do destinatario; III - a data de emissao ou de saida.";
        StringBuilder xml = new StringBuilder();
        xml.append("<envEvento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">")
                .append("<idLote>1</idLote>")
                .append("<evento versao=\"1.00\">")
                .append("<infEvento Id=\"").append(Id).append("\">")
                .append("<cOrgao>").append(estadoEmpresa).append("</cOrgao>")
                .append("<tpAmb>").append(notaFiscal.getAmbiente()).append("</tpAmb>")
                .append("<CNPJ>").append(cnpj).append("</CNPJ>")
                .append("<chNFe>").append(chave).append("</chNFe>")
                .append("<dhEvento>").append(dataHoraEvento).append("</dhEvento>")
                .append("<tpEvento>110110</tpEvento>")
                .append("<nSeqEvento>1</nSeqEvento>")
                .append("<verEvento>1.00</verEvento>")
                .append("<detEvento versao=\"1.00\">")
                .append("<descEvento>Carta de Correcao</descEvento>")
                .append("<xCorrecao>").append(correcao).append("</xCorrecao>")
                .append("<xCondUso>").append(condicoesDeUso).append("</xCondUso>")
                .append("</detEvento>")
                .append("</infEvento>")
                .append("</evento>")
                .append("</envEvento>");
        AssinaXmlEventos xmlCancelamento = new AssinaXmlEventos(notaFiscal, xml.toString());
        return xmlCancelamento.assinaXmlEventos();
    }

    @Deprecated
    public void consultaNotaFiscal(){

        try{
            String codigoDoEstado = notaFiscal.getCodIbgeEstadoEmpresa();

            URL url;
            if (notaFiscal.getAmbiente() == 1) {
                url = new URL("https://nfe.svrs.rs.gov.br/ws/NfeConsulta/NfeConsulta2.asmx");
            } else {
                url = new URL("https://nfe-homologacao.svrs.rs.gov.br/ws/NfeConsulta/NfeConsulta2.asmx");
            }

            String caminhoDoCertificadoDoCliente = "/HSI/Certificado/"+ notaFiscal.getCaminhoCertificado();
            String senhaDoCertificado = notaFiscal.getSenhaCertificado();
            String arquivoCacertsGeradoTodosOsEstados = "/HSI/NFeCacerts";

            System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
            System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
            System.clearProperty("javax.net.ssl.keyStore");
            System.clearProperty("javax.net.ssl.keyStorePassword");
            System.clearProperty("javax.net.ssl.trustStore");
            System.setProperty("javax.net.ssl.keyStore", caminhoDoCertificadoDoCliente);
            System.setProperty("javax.net.ssl.keyStorePassword",senhaDoCertificado);
            System.setProperty("javax.net.ssl.trustStoreType", "JKS");
            System.setProperty("javax.net.ssl.trustStore", arquivoCacertsGeradoTodosOsEstados);

            StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                    .append("<consSitNFe versao=\"3.10\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
                    .append("<tpAmb>").append(notaFiscal.getAmbiente()).append("</tpAmb>")
                    .append("<xServ>CONSULTAR</xServ>")
                    .append("<chNFe>")
                    .append(notaFiscal.getChaveAcesso().replaceAll("NFe",""))
                    .append("</chNFe>")
                    .append("</consSitNFe>");

            OMElement ome = AXIOMUtil.stringToOM(xml.toString());

            NfeConsulta2Stub.NfeDadosMsg dadosMsg = new NfeConsulta2Stub.NfeDadosMsg();
            dadosMsg.setExtraElement(ome);

            NfeConsulta2Stub.NfeCabecMsg nfeCabecMsg = new NfeConsulta2Stub.NfeCabecMsg();
            nfeCabecMsg.setCUF(codigoDoEstado);
            nfeCabecMsg.setVersaoDados("3.10");
            NfeConsulta2Stub.NfeCabecMsgE nfeCabecMsgE = new NfeConsulta2Stub.NfeCabecMsgE();
            nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

            NfeConsulta2Stub stub = new NfeConsulta2Stub(url.toString());

            NfeConsulta2Stub.NfeConsultaNF2Result result = stub.nfeConsultaNF2(dadosMsg, nfeCabecMsgE);
            System.out.println(result.getExtraElement().toString());
        }catch (Exception e) {
            System.out.println("Erro de compilação, Chama o suporte!");
        }
    }
}
