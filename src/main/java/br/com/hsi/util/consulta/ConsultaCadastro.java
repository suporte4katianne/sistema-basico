package br.com.hsi.util.consulta;

import br.com.hsi.model.Empresa;
import br.com.hsi.model.Entidade;
import br.com.hsi.model.dados.Cidade;
import br.com.hsi.model.dados.Estado;
import br.com.hsi.service.GestaoEmpresa;
import br.com.hsi.service.GestaoEndereco;
import br.com.hsi.util.SocketFactoryDinamico;
import br.com.hsi.util.cdi.CDIServiceLocator;
import br.com.hsi.util.nfe.CarregaAssinaturaA3;
import br.inf.portalfiscal.www.nfe.wsdl.cadconsultacadastro2.CadConsultaCadastro2Stub;
import org.apache.commons.httpclient.protocol.Protocol;

import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.*;

public class ConsultaCadastro {

	private static final int SSL_PORT = 443;
    private static final String NFE_CACERTS = "/HSI/NFeCacerts";
    private Empresa empresa;
    private Entidade entidade;
    private GestaoEndereco gestaoEndereco = CDIServiceLocator.getBean(GestaoEndereco.class);
    private GestaoEmpresa gestaoEmpresa = CDIServiceLocator.getBean(GestaoEmpresa.class);

	public ConsultaCadastro() {
		entidade = new Entidade();
		this.empresa = gestaoEmpresa.empresas().get(0);
	}
	
	public Entidade consultarDados(Estado estado, String cnpjConsulta){
		try {
			entidade.setCpfCnpj(cnpjConsulta);
			cnpjConsulta = cnpjConsulta.replace(".", "").replace("/", "").replace("-","");
			String caminhoCertificado = "/HSI/Certificado/" + empresa.getNomeCertificado();
			String senhaCertificado = empresa.getSenhaCertificado();
			
			Properties properties = new Properties();
			FileInputStream arquivoIn = new FileInputStream("/HSI/WS_SEFAZ.properties");
			properties.load(arquivoIn);
			arquivoIn.close();
			URL url = new URL(properties.getProperty(estado.getUrlConsultaCadastro()));


			if(empresa.getTipoCertificado().equals("A1")) {
				InputStream entrada = new FileInputStream(caminhoCertificado);
				KeyStore ks = KeyStore.getInstance("pkcs12");
				try {
					ks.load(entrada, senhaCertificado.toCharArray());
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
				PrivateKey privateKey = (PrivateKey) ks.getKey(alias, senhaCertificado.toCharArray());
				SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(certificate, privateKey);
				socketFactoryDinamico.setFileCacerts(NFE_CACERTS);

				Protocol protocol = new Protocol("https", socketFactoryDinamico, SSL_PORT);
				Protocol.registerProtocol("https", protocol);
			} else {
				CarregaAssinaturaA3 carregaAssinaturaA3 = new CarregaAssinaturaA3();
				carregaAssinaturaA3.carregaAssinatura(empresa.getSenhaCertificado());

				X509Certificate cert = (X509Certificate) carregaAssinaturaA3.getPrivateKeyEntry().getCertificate();

				SocketFactoryDinamico socketFactoryDinamico = new SocketFactoryDinamico(cert, carregaAssinaturaA3.getPrivateKey());
				socketFactoryDinamico.setFileCacerts(NFE_CACERTS);

				Protocol protocol = new Protocol("https", socketFactoryDinamico, SSL_PORT);
				Protocol.registerProtocol("https", protocol);

			}

            


			StringBuilder xml = new StringBuilder();
			xml.append("<nfeDadosMsg>")
					.append("<ConsCad versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">")
					.append("<infCons>")
					.append("<xServ>CONS-CAD</xServ>")
					.append("<UF>")
					.append(estado.getSiglaEstado())
					.append("</UF>")
					.append("<CNPJ>")
					.append(cnpjConsulta)
					.append("</CNPJ>")
					.append("</infCons>")
					.append("</ConsCad>")
					.append("</nfeDadosMsg>");

			System.out.println(xml);

			XMLStreamReader dadosXML = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader(xml.toString()));
			CadConsultaCadastro2Stub.NfeDadosMsg dadosMsg = CadConsultaCadastro2Stub.NfeDadosMsg.Factory.parse(dadosXML);
			
			CadConsultaCadastro2Stub.NfeCabecMsg nfeCabecMsg = new CadConsultaCadastro2Stub.NfeCabecMsg();
			nfeCabecMsg.setCUF(estado.getCodigoIbge());
			nfeCabecMsg.setVersaoDados("2.00");

			CadConsultaCadastro2Stub.NfeCabecMsgE nfeCabecMsgE = new CadConsultaCadastro2Stub.NfeCabecMsgE();
			nfeCabecMsgE.setNfeCabecMsg(nfeCabecMsg);

			CadConsultaCadastro2Stub stub = new CadConsultaCadastro2Stub(url.toString());
			CadConsultaCadastro2Stub.ConsultaCadastro2Result result = stub.consultaCadastro2(dadosMsg, nfeCabecMsgE);


			String xmlRetorno = result.getExtraElement().toString();

			System.out.println(xmlRetorno);

			String motivo = xmlRetorno.substring(xmlRetorno.indexOf("<xMotivo>") + 9 , xmlRetorno.indexOf("</xMotivo>"));
			if(motivo.equals("Consulta cadastro com uma ocorrencia")){
				String inscricaoEstadual = xmlRetorno.substring(xmlRetorno.indexOf("<IE>") + 4 , xmlRetorno.indexOf("</IE>"));
				String razaoSocial = xmlRetorno.substring(xmlRetorno.indexOf("<xNome>") + 7 , xmlRetorno.indexOf("</xNome>"));
				String rua = xmlRetorno.substring(xmlRetorno.indexOf("<xLgr>") + 6 , xmlRetorno.indexOf("</xLgr>"));
				String numeroEndereco = xmlRetorno.substring(xmlRetorno.indexOf("<nro>") + 5 , xmlRetorno.indexOf("</nro>"));
				String complemento = "";
				if(!xmlRetorno.contains("<xCpl />")){
					complemento = xmlRetorno.substring(xmlRetorno.indexOf("<xCpl>") + 6 , xmlRetorno.indexOf("</xCpl>"));
				}
				String bairro = xmlRetorno.substring(xmlRetorno.indexOf("<xBairro>") + 9 , xmlRetorno.indexOf("</xBairro>"));
				String cep = xmlRetorno.substring(xmlRetorno.indexOf("<CEP>") + 5 , xmlRetorno.indexOf("</CEP>"));
				Cidade cidade = gestaoEndereco.cidadePorCodigoIbge(xmlRetorno.substring(xmlRetorno.indexOf("<cMun>") + 6 , xmlRetorno.indexOf("</cMun>")));

				entidade.setTipoModalidade("1");
				entidade.setCpfCnpj(entidade.getCpfCnpj());
				entidade.setInscricaoEstadual(inscricaoEstadual);
				entidade.setNome(razaoSocial);
				entidade.setRua(rua);
				entidade.setNumero(numeroEndereco);
				entidade.setComplemento(complemento);
				entidade.setBairro(bairro);
				entidade.setEstado(estado);
				entidade.setCidade(cidade);
				entidade.setCep(cep);
				
				return entidade;
			
			}else{
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
