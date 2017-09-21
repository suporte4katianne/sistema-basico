package br.com.hsi.util.nfe.inutilizacao;

import br.com.hsi.model.Inutilizacao;
import br.com.hsi.util.exception.TransmissaoException;
import br.com.hsi.util.nfe.CarregaAssinaturaA3;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class GeraXmlInutilizacao implements Serializable {


    public Inutilizacao inutilizaSequencia (Inutilizacao inutilizacao) throws TransmissaoException {

        try{
            StringBuilder xml = new StringBuilder();

            SimpleDateFormat formato = new SimpleDateFormat("MM");
            String dataChave = formato.format(inutilizacao.getData());
            String cnpj = inutilizacao.getEmpresa().getCpfCnpj()
                    .replaceAll("\\.", "")
                    .replaceAll("-", "").replaceAll("/", "");
            String id = "ID" + inutilizacao.getEmpresa().getCodigoIbgeEstado() + dataChave + cnpj + "55" + serieNota(inutilizacao.getSerie()) +
                    numeroNota(inutilizacao.getNumeroInicio()) +
                    numeroNota(inutilizacao.getNumeroFim());

            xml.append("<inutNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"2.00\">")
                    .append("<infInut Id=\"").append(id).append("\">")
                    .append("<tpAmb>").append(inutilizacao.getAmbiente()).append("</tpAmb>")
                    .append("<xServ>INUTILIZAR</xServ>")
                    .append("<cUF>").append(inutilizacao.getEmpresa().getCodigoIbgeEstado()).append("</cUF>")
                    .append("<ano>").append(formato.format(inutilizacao.getData())).append("</ano>")
                    .append("<CNPJ>").append(cnpj).append("</CNPJ>")
                    .append("<mod>55</mod>")
                    .append("<serie>").append(inutilizacao.getSerie()).append("</serie>")
                    .append("<nNFIni>").append(inutilizacao.getNumeroInicio()).append("</nNFIni>")
                    .append("<nNFFin>").append(inutilizacao.getNumeroFim()).append("</nNFFin>")
                    .append("<xJust>").append(inutilizacao.getMotivo()).append("</xJust>")
                    .append("</infInut>")
                    .append("</inutNFe>");

            if(inutilizacao.getEmpresa().getTipoCertificado().equals("A1")) {
                AssinaXmlInutilizacao xmlInutilizacao = new AssinaXmlInutilizacao(inutilizacao, xml.toString());
                return xmlInutilizacao.assinaXmlInutilizacao();
            } else {
                CarregaAssinaturaA3 carregaAssinaturaA3 = new CarregaAssinaturaA3();
                carregaAssinaturaA3.carregaAssinatura(inutilizacao.getEmpresa().getSenhaCertificado());
                AssinaXmlInutilizacao xmlInutilizacao = new AssinaXmlInutilizacao(inutilizacao, xml.toString());
                return xmlInutilizacao.assinaXmlInutilizacaoA3(carregaAssinaturaA3.getKs(), carregaAssinaturaA3.getPrivateKeyEntry(), carregaAssinaturaA3.getPrivateKey());
            }


        }catch (Exception e){
            throw new TransmissaoException("Erro ao transmitir XML de Inutilização");
        }
    }


    private String numeroNota(int valor) {
        String numero = String.valueOf(valor);
        for (int i = 1; i <= numero.length(); i++) {
            if(numero.length() < 9) {
                numero = "0" + numero;
            }
        }
        return numero;
    }

    private String serieNota(int valor) {
        String serie = String.valueOf(valor);
        for (int i = 1; i <= serie.length(); i++) {
            if(serie.length() < 3) {
                serie = "0" + serie;
            }
        }
        return serie;
    }



}
