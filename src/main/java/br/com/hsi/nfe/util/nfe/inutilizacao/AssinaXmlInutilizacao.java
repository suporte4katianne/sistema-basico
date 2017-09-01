package br.com.hsi.nfe.util.nfe.inutilizacao;

import br.com.hsi.nfe.model.Inutilizacao;
import br.com.hsi.nfe.util.nfe.eventos.AssinaXmlEventos;


public class AssinaXmlInutilizacao {

    private static final String INFINUT = "infInut";
    private Inutilizacao inutilizacao;
    private String xml;

    public AssinaXmlInutilizacao (Inutilizacao inutilizacao, String xml) {
        this.inutilizacao = inutilizacao;
        this.xml = xml;
    }

    public Inutilizacao assinaXmlInutilizacao (){
        try {
            String caminhoDoCertificadoDoCliente = "/HSI/Certificado/" + inutilizacao.getEmpresa().getNomeCertificado();
            String senhaDoCertificadoDoCliente = inutilizacao.getEmpresa().getSenhaCertificado();

            String xmlAssinado = new AssinaXmlEventos().assinaCancelametoInutilizacao(xml, caminhoDoCertificadoDoCliente, senhaDoCertificadoDoCliente, INFINUT);
            EnviXmlInutilizacao xmlInutilizacao = new EnviXmlInutilizacao(inutilizacao, xmlAssinado);
            return xmlInutilizacao.enviaInutilizacao();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();
            return null;
        }
    }




}
