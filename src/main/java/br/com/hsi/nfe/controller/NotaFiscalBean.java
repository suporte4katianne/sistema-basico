package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.Empresa;
import br.com.hsi.nfe.model.NotaFiscal;
import br.com.hsi.nfe.service.GestaoNotaFiscal;
import br.com.hsi.nfe.util.jsf.FacesUtil;
import br.com.hsi.nfe.util.mail.Mailer;
import br.com.hsi.nfe.util.mail.SimpleEmailConcat;
import br.com.hsi.nfe.util.nfe.autorizacao.GeraXmlAutorizacao;
import br.com.hsi.nfe.util.nfe.eventos.GeraXmlEventos;
import com.outjected.email.api.ContentDisposition;
import com.outjected.email.api.EmailContact;
import com.outjected.email.api.MailMessage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.*;
import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Named
@ViewScoped
public class NotaFiscalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GestaoNotaFiscal gestaoNotaFiscal;
	@Inject
	private HttpServletResponse response;
	@Inject
	private FacesContext facesContext;
	@Inject
	private Mailer mailer;

	private Empresa empresa;
	private NotaFiscal notaFiscal;
	private List<Empresa> emitentes;
	private List<NotaFiscal> notasFiscais;
	private List<NotaFiscal> notasFiscaisFiltro;
	private Date dataInicio;
	private Date dataFim;
	private String listaDeDestinatarios;
	private String textoPadrao = "\n\nPodemos conceituar a Nota Fiscal Eletrônica como um documento de existência apenas digital, "
			+ "emitido e armazenado eletronicamente, com o intuito de documentar, para fins fiscais, uma operação de circulação de mercadorias ocorrida entre as partes. \n\n"
			+ "Sua validade jurídica é garantida pela assinatura digital do remetente (garantia de autoria e integridade). "
			+ "Os registros ficais e contábeis devem ser feitos a partir do próprio arquivo da NF-e (anexo neste e-mail) ou utilizando o DANFE (anexo neste e-mail), "
			+ "que representa graficamente a Nota Fiscal Eletrônica.";
	private GeraXmlAutorizacao geranota;


	private String correcao;

	@PostConstruct
	public void init() {
		notasFiscais = gestaoNotaFiscal.listarNotasFiscais();
	}

	public boolean teste() {
        return geranota != null;
	}

	public void transmitirNfe() throws Exception {
		geranota = new GeraXmlAutorizacao(notaFiscal);
		notaFiscal = geranota.GerandoNfe();
		if (notaFiscal != null) {
			if (notaFiscal.getMensagemRetorno().contains("Autorizado")) {
				FacesUtil.addInfoMessage("Nota Fiscal Autorizada com Sucesso!");
				notaFiscal.setStatus("A");
				gestaoNotaFiscal.salvar(notaFiscal);
				gerarDanfe();
				listaDeDestinatarios = notaFiscal.getEmailEmpresa() + ";" + notaFiscal.getEmailDestinatario();
				enviarNotaPorEmail();
			} else {
				notaFiscal.setStatus("R");
				gestaoNotaFiscal.salvar(notaFiscal);
			}
		} else {
			FacesUtil.addErrorMessage("Ocorreu um erro inesperado durante o Envio, entre em contato com seu representante");
		}
		geranota = null;
		notaFiscal = null;
	}

	public void cancelarNotaFiscal() throws IOException, InterruptedException {
		GeraXmlEventos xmlCancelamento = new GeraXmlEventos(notaFiscal);
		notaFiscal = xmlCancelamento.gerarCancelamento("Cancelamento de NFe com erros de digitacao ou informacao");
		if (notaFiscal.getMensagemRetorno().contains("Evento registrado")) {
			notaFiscal.setMensagemRetorno("Cancelado! " + notaFiscal.getMensagemRetorno());
			notaFiscal.setStatus("C");
			gestaoNotaFiscal.salvar(notaFiscal);
			FacesUtil.addInfoMessage("Nota Fiscal Cancelada com Sucesso!");
		} else {
			FacesUtil.addErrorMessage("Data para envio do evento Expirou");
		}
		notasFiscais = gestaoNotaFiscal.listarNotasFiscais();
	}

	public void cartaDeCorrecao() throws IOException, InterruptedException {
		GeraXmlEventos geraXmlEventos = new GeraXmlEventos(notaFiscal);
		notaFiscal = geraXmlEventos.gerarCartaDeCorrecao(correcao);
		if (notaFiscal.getMensagemRetorno().contains("Evento registrado")) {
			notaFiscal.setMensagemRetorno("Carta de Correção vinculada! Autorizado o uso da NF-e");
			gestaoNotaFiscal.salvar(notaFiscal);
			FacesUtil.addInfoMessage("Carta de correção vinculada com sucesso Sucesso!");
		} else {
			FacesUtil.addErrorMessage("Data para envio do evento Expirou");
		}
		notasFiscais = gestaoNotaFiscal.listarNotasFiscais();
	}

	public void imprirmirDanfe() throws IOException, JRException, ParseException {
		InputStream relatorioStream = getClass().getResourceAsStream("/danfe/danfeR.jasper");
		JRXmlDataSource dataSource = new JRXmlDataSource(new File(
				"/HSI/Autorizado/" + notaFiscal.getChaveAcesso() + "-procNFE.xml"),
				"/nfeProc/NFe/infNFe/det");
		JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametrosDanfe(), dataSource);
		Exporter<ExporterInput, PdfReportConfiguration, PdfExporterConfiguration, OutputStreamExporterOutput> exportador = new JRPdfExporter();
		exportador.setExporterInput(new SimpleExporterInput(print));
		exportador.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + notaFiscal.getChaveAcesso() + "-procNFE.pdf" + "\"");
		exportador.exportReport();
		facesContext.responseComplete();
	}

	public void enviarNotaPorEmail() {
		MailMessage message = mailer.novaMensagem();
		message.to(getEmails(listaDeDestinatarios))
				.addAttachment(ContentDisposition.ATTACHMENT,
						new File("/HSI/Autorizado/" + notaFiscal.getChaveAcesso() + "-procNFE.xml"))
				.addAttachment(ContentDisposition.ATTACHMENT,
						new File("/HSI/Danfe/" + notaFiscal.getChaveAcesso() + "-procNFE.pdf"))
				.from("emitirnota@hsiautomacao.com.br").subject("NFe-e - " + notaFiscal.getRazaoSocialEmpresa())
				.bodyText("Você está recebendo a Nota Fiscal Eletrônica (NF-e) número " + notaFiscal.getNumeroNota() + " de "
						+ notaFiscal.getRazaoSocialEmpresa() + " no valor de R$ " + notaFiscal.getValorTotalNfe() + textoPadrao)
				.send();
		FacesUtil.addInfoMessage("Nota Fiscal enviada com sucesso!");
	}

	public void enviarLoteXmlPorEmail() throws IOException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		MailMessage message = mailer.novaMensagem();
		message.to(getEmails(listaDeDestinatarios)).from("emitirnota@hsiautomacao.com.br")
				.subject("NFe-e - " + notaFiscal.getRazaoSocialEmpresa())
				.bodyText("Lote de XML do Emitente  " + empresa.getNome() + " titular do CNPJ " + empresa.getCpfCnpj()
						+ " no período de " + format.format(getDataInicio()) + " à " + format.format(getDataFim())
						+ textoPadrao);

		for (NotaFiscal nfe : gestaoNotaFiscal.notaFiscalPorPeriodo(empresa.getId(), format.format(getDataInicio()),
				format.format(getDataFim()))) {
			message.addAttachment(ContentDisposition.ATTACHMENT, new File("/HSI/Autorizado/" + nfe.getChaveAcesso() + "-procNFE.xml"));
		}

		message.send();

		FacesUtil.addInfoMessage("Nota Fiscal enviada com sucesso!");
	}

	public void excluirNotaFiscal() {
		gestaoNotaFiscal.excluirNotaFiscal(notaFiscal);
		FacesUtil.addInfoMessage("Nota fiscal removida com sucesso!");
	}

	// Metodos Privados

	private void gerarDanfe() throws JRException, IOException, ParseException {
		InputStream relatorioStream = getClass().getResourceAsStream("/danfe/danfeR.jasper");
		JRXmlDataSource dataSource = new JRXmlDataSource(new File(
				"/HSI/Autorizado/" + notaFiscal.getChaveAcesso() + "-procNFE.xml"),
				"/nfeProc/NFe/infNFe/det");
		JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametrosDanfe(), dataSource);
		byte[] pdf = JasperExportManager.exportReportToPdf(print);
		FileUtils.writeByteArrayToFile(new File(
				"/HSI/Danfe/" + notaFiscal.getChaveAcesso() + "-procNFE.pdf"), pdf);
	}


	private Map<String, Object> parametrosDanfe() throws ParseException {
		Map<String, Object> parametros = new HashMap<>();
		if (notaFiscal.getEmpresa().getLogo() != null) {
			parametros.put("Logo", new ByteArrayInputStream(notaFiscal.getEmpresa().getLogo()));
		}
		return parametros;
	}

	private Collection<? extends EmailContact> getEmails(String email) {
		String[] emails = email.split(";");
		List<SimpleEmailConcat> simpleEmails = new ArrayList<>();
		for (int i = 0; i < emails.length; i++) {
			emails[i] = emails[i].replaceAll(" ", "").toLowerCase();
			if (validaEmail(emails[i])) {
				simpleEmails.add(new SimpleEmailConcat(emails[i], emails[i]));
			}
		}
		return simpleEmails;
	}

	private boolean validaEmail(String email) {
		if (email.contains("@") && email.contains(".") && !email.contains(" ")) {
			String usuario = new String(email.substring(0, email.lastIndexOf('@')));
			String dominio = new String(email.substring(email.lastIndexOf('@') + 1, email.length()));
            return (usuario.length() >= 1) && (!usuario.contains("@")) && (dominio.contains("."))
                    && (!dominio.contains("@")) && (dominio.indexOf(".") >= 1)
                    && (dominio.lastIndexOf(".") < dominio.length() - 1);
		} else {
			return false;
		}
	}




	// Getters and Setters

	public Empresa getEmitente() {
		return empresa;
	}

	public void setEmitente(Empresa emitente) {
		this.empresa = emitente;
	}

	public List<Empresa> getEmitentes() {
		return emitentes;
	}

	public void setEmitentes(List<Empresa> emitentes) {
		this.emitentes = emitentes;
	}

	public List<NotaFiscal> getNfes() {
		return notasFiscais;
	}

	public void setNfes(List<NotaFiscal> nfes) {
		this.notasFiscais = nfes;
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public String getListaDeDestinatarios() {
		return listaDeDestinatarios;
	}

	public void setListaDeDestinatarios(String listaDeDestinatarios) {
		this.listaDeDestinatarios = listaDeDestinatarios;
	}

	public String getTextoPadrao() {
		return textoPadrao;
	}

	public void setTextoPadrao(String textoPadrao) {
		this.textoPadrao = textoPadrao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<NotaFiscal> getNfesFiltro() {
		return notasFiscaisFiltro;
	}

	public void setNfesFiltro(List<NotaFiscal> nfesFiltro) {
		this.notasFiscaisFiltro = nfesFiltro;
	}

	public GeraXmlAutorizacao getGeranota() {
		return geranota;
	}

	public void setGeranota(GeraXmlAutorizacao geranota) {
		this.geranota = geranota;
	}

	public String getCorrecao() {
		return correcao;
	}

	public void setCorrecao(String correcao) {
		this.correcao = correcao;
	}


}