package br.com.hsi.util.nfe.inutilizacao;

import br.com.hsi.model.Inutilizacao;
import br.com.hsi.util.nfe.eventos.AssinaXmlEventos;

import java.security.KeyStore;
import java.security.PrivateKey;


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


    public Inutilizacao assinaXmlInutilizacaoA3(KeyStore ks, KeyStore.PrivateKeyEntry privateKeyEntry, PrivateKey privateKey) {
        try {
            String senhaDoCertificadoDoCliente = inutilizacao.getEmpresa().getSenhaCertificado();

            AssinaXmlEventos assinaXmlEventos = new AssinaXmlEventos(privateKeyEntry, ks, privateKey);
            String xmlAssinado = assinaXmlEventos.assinaCancelametoInutilizacaoA3(xml, senhaDoCertificadoDoCliente, INFINUT);
            EnviXmlInutilizacao xmlInutilizacao = new EnviXmlInutilizacao(inutilizacao, xmlAssinado);
            return xmlInutilizacao.enviaInutilizacaoA3(assinaXmlEventos.getCert(), privateKey);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getCause();
            return null;
        }
    }
}
