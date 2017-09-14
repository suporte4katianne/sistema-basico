package br.com.hsi.util.importacao.xml;

import br.com.hsi.model.*;
import br.com.hsi.util.exception.NegocioException;
import procNFe_v310.TNfeProc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ImportacaoXmlNfe {

    private Empresa empresa;
    private TNfeProc proc;


    public ImportacaoXmlNfe(String xml, Empresa empresa) throws JAXBException, IOException {
        this.empresa = empresa;
        JAXBContext context = JAXBContext.newInstance(TNfeProc.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        JAXBElement<TNfeProc> element = unmarshaller.
                unmarshal(new StreamSource(new StringReader(
                        lerXML(xml) )),TNfeProc.class);
        this.proc = element.getValue();
    }


    public static String lerXML(String fileXML) throws IOException {
        String linha;
        StringBuilder xml = new StringBuilder();

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileXML)));
        while ((linha = in.readLine()) != null) {
            xml.append(linha);
        }
        in.close();

        return xml.toString();
    }

    public NotaFiscal geraNotaFiscal() throws NegocioException {
        NotaFiscal notaFiscal = new NotaFiscal();
        String cnpj = empresa.getCpfCnpj().replaceAll("\\.","")
                .replaceAll("/","").replaceAll("-","");


        if(proc.getNFe().getInfNFe().getEmit().getCNPJ() != null &&
                proc.getNFe().getInfNFe().getEmit().getCNPJ().equals(cnpj)){

            notaFiscal = carregaEmpresa(notaFiscal);
            notaFiscal = carregaCabecalho(notaFiscal);

        } else {
            throw new NegocioException("O Emitente do XML é difere do cadastro da Empresa!");
        }

//        notaFiscal. entidade;
//        notaFiscal. cpfCnpjEntidade;
//        notaFiscal. nomeEntidade;
//        notaFiscal. tipoContribuinte; //1 - 2 ou 9
//        notaFiscal. tipoCadastro;
//        notaFiscal. inscricaoEstadualEntidade;
//        notaFiscal. ruaEntidade;
//        notaFiscal. complementoEntidade;
//        notaFiscal. numeroEntidade;
//        notaFiscal. bairroEntidade;
//        notaFiscal. codIbgeCidadeEntidade;
//        notaFiscal. nomeCidadeEntidade;
//        notaFiscal. estadoEntidade;
//        notaFiscal. cepEntidade;
//        notaFiscal. codPaisEntidade;
//        notaFiscal. nomePaisEntidade;
//        notaFiscal. telefoneEntidade;
//        notaFiscal. emailEntidade;
//        notaFiscal. transportadora;
//        notaFiscal. modeloFrete;
//        notaFiscal. tipoCadastroTransportadora;
//        notaFiscal. cpfCnpjTransportadora;
//        notaFiscal. nomeTransportadora;
//        notaFiscal. inscricaoEstadualTransportadora;
//        notaFiscal. enderecoTransportadora;
//        notaFiscal. cidadeTransportadora;
//        notaFiscal. estadoTransportadora;
//        notaFiscal. quantidadeVolume;
//        notaFiscal. numeroVolume;
//        notaFiscal. pesoLiquido;
//        notaFiscal. pesoBruto;
//        notaFiscal. valorTotalIcmsDeson = new BigDecimal("0.0");
//        notaFiscal. valorTotalIcms = new BigDecimal("0.0");
//        notaFiscal. valorTotalBaseIcms = new BigDecimal("0.0");
//        notaFiscal. valorTotalIcmsS = new BigDecimal("0.0");
//        notaFiscal. valorTotalBaseIcmsSt = new BigDecimal("0.0");
//        notaFiscal. valorTotalProdutos = new BigDecimal("0.0");
//        notaFiscal. valorTotalFrete = new BigDecimal("0.0");
//        notaFiscal. valorTotalSeguro = new BigDecimal("0.0");
//        notaFiscal. valorTotalDesconto = new BigDecimal("0.0");
//        notaFiscal. valorTotalImpostosAproximados = new BigDecimal("0.0");
//        notaFiscal. valorTotalIpi = new BigDecimal("0.0");
//        notaFiscal. valorTotalPis = new BigDecimal("0.0");
//        notaFiscal. valorTotalCofins = new BigDecimal("0.0");
//        notaFiscal. valorTotalOutros = new BigDecimal("0.0");
//        notaFiscal. valorTotalNfe = new BigDecimal("0.0");
//        notaFiscal. informacoesComplementare;
//        notaFiscal. notaFiscalItens = new ArrayList<>();
//        notaFiscal. notaFiscalReferencias = new ArrayList<>();

        return notaFiscal;
    }


    private NotaFiscal carregaEmpresa(NotaFiscal notaFiscal) {
        notaFiscal.setEmpresa(empresa);
        notaFiscal.setCnpjEmpresa(empresa.getCpfCnpj());
        notaFiscal.setRazaoSocialEmpresa(empresa.getNome());
        notaFiscal.setNomeFantasiaEmpresa(empresa.getNome());
        notaFiscal.setRuaEmpresa(empresa.getRua());
        notaFiscal.setComplementoEmpresa(empresa.getComplemento());
        notaFiscal.setNumeroEmpresa(empresa.getNumero());
        notaFiscal.setBairroEmpresa(empresa.getBairro());
        notaFiscal.setNomeCidadeEmpresa(empresa.getNomecidade());
        notaFiscal.setEstadoEmpresa(empresa.getNomeEstado());
        notaFiscal.setCepEmpresa(empresa.getCep());
        notaFiscal.setNomePaisEmpresa("Brasil");
        notaFiscal.setTelefoneEmpresa(empresa.getTelefone());
        notaFiscal.setEmailEmpresa(empresa.getEmail());
        notaFiscal.setInscricaoEstadualEmpresa(empresa.getInscricaoEstadual());
        notaFiscal.setEnquadramentoFiscalEmpresa(empresa.getEnquadramentoFiscal());
        notaFiscal.setCaminhoCertificado(empresa.getNomeCertificado());
        notaFiscal.setSenhaCertificado(empresa.getSenhaCertificado());
        notaFiscal.setCreditoIcms(empresa.getCreditoIcms().toString());
        return notaFiscal;
    }

    private NotaFiscal carregaCabecalho(NotaFiscal notaFiscal) {
        SimpleDateFormat format = new SimpleDateFormat();


//        notaFiscal.setChaveAcesso(proc.getNFe().getInfNFe().getId());
//        notaFiscal.setCodIbgeEstadoEmpresa(proc.getNFe().getInfNFe().getIde().getCUF());
//        notaFiscal.setNumeroSequenciaAleatorio(proc.getNFe().getInfNFe().getIde().getCNF());
//        notaFiscal.setCfop();
//        notaFiscal.setNomeNatureza(proc.getNFe().getInfNFe().getIde().getNatOp());
//        notaFiscal.setCondicaoPagamento(Integer.parseInt(proc.getNFe().getInfNFe().getIde().getIndPag()));
//        notaFiscal.setDataHoraEmissao();
//        notaFiscal.setDataHoraSaidaEntrada(proc.getNFe().getInfNFe().getIde().getDhSaiEnt());
//        notaFiscal.setTipoNf(proc.getNFe().getInfNFe().getIde().getTpNF());
//        notaFiscal.setCodIbgeCidadeEmpresa(proc.getNFe().getInfNFe().getIde().getCMunFG());
//        notaFiscal.setDigitoVerificadorChaveAcesso(Integer.parseInt(proc.getNFe().getInfNFe().getIde().getCDV()));
//        notaFiscal.setAmbiente(Integer.parseInt(proc.getNFe().getInfNFe().getIde().getTpAmb()));
//
//        notaFiscal.setTipoEmissao(Integer.parseInt(proc.getNFe().getInfNFe().getIde().getTpEmis()));
//        notaFiscal.setOperacao(proc.getNFe().getInfNFe().getIde().get);
//        notaFiscal.setFinalidadeNfe(proc.getNFe().getInfNFe().getIde().);
//        notaFiscal.setTipoConsumidor(proc.getNFe().getInfNFe().getIde().);
//        notaFiscal.setPresencaConsumidor(proc.getNFe().getInfNFe().getIde().);
//
//        notaFiscal.setEmissao(false);
//        notaFiscal.setVersaoXml(proc.getNFe().getInfNFe().getIde().getVerProc());
//        notaFiscal.setMensagemRetorno(proc.getProtNFe().getInfProt().getXMotivo());
//        notaFiscal.setStatus("A");
//        notaFiscal.setProtocoloAutorizacao(proc.getProtNFe().getInfProt().getNProt());
//        notaFiscal.setModeloNota(proc.getNFe().getInfNFe().getIde().getMod());
//        notaFiscal.setSerieNota(Integer.parseInt(proc.getNFe().getInfNFe().getIde().getSerie()));
        notaFiscal.setNumeroNota(Integer.parseInt(proc.getNFe().getInfNFe().getIde().getNNF()));
        return notaFiscal;
    }

}
