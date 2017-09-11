package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.*;
import br.com.hsi.nfe.model.dados.Cfop;
import br.com.hsi.nfe.security.Seguranca;
import br.com.hsi.nfe.service.GestaoEntidade;
import br.com.hsi.nfe.service.GestaoNotaFiscal;
import br.com.hsi.nfe.service.GestaoNumeracao;
import br.com.hsi.nfe.service.GestaoProduto;
import br.com.hsi.nfe.util.exception.NegocioException;
import br.com.hsi.nfe.util.jsf.FacesUtil;
import br.com.hsi.nfe.util.nfe.GeraChaveAcesso;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class NotaFiscalFormularioBean implements Serializable {

	private static final long serialVersionUID = 1L;

    @Inject
	private GestaoNotaFiscal gestaoNotaFiscal;
    @Inject
    private GestaoNumeracao gestaoNumeracao;
    @Inject
	private GestaoEntidade gestaoEntidade;
    @Inject
	private HttpServletRequest request;
    @Inject
	private GestaoProduto gestaoProduto;
    @Inject
	private Seguranca seguranca;

    private NotaFiscal notaFiscal;
    private NotaFiscalItem notaFiscalItem;
    private NotaFiscalReferencia notaFiscalReferencia;
    private Numeracao numeracao;
    private List<Numeracao> numeracoes;
    private List<Entidade> destinatarios;
    private List<Entidade> destinatariosFiltro;
    private List<Entidade> transportadoras;
    private List<Entidade> transportadorasFiltro;
    private List<Produto> produtos;
    private List<Produto> produtosFiltro;
    private List<Cfop> cfops;


    @PostConstruct
	public void init(){
		destinatarios = gestaoEntidade.listarEntidades("C");
		produtos = gestaoProduto.listarProdutos();
		transportadoras = gestaoEntidade.listarEntidades("T");
		numeracoes = gestaoNumeracao.numeracoes();
	}
	
	public void inicializar() {
		if(notaFiscal == null){
			System.out.println("Entrei aqui");
			limpar();
			carregaDadosDoEmitente();

		}else{
			notaFiscalItem = new NotaFiscalItem();
			notaFiscalReferencia = new NotaFiscalReferencia();
		}

		carregaCfopPorTipoEOperacao();
	}

	public String alteracao(){
		try{
			request.getParameter("notaFiscal").isEmpty();
			return "false";
		}catch (NullPointerException e){
			return "true";
		}
	}
	
	public void carregaDadosDoEmitente(){
		notaFiscal.setEmpresa(seguranca.getUsuarioLogado().getUsuario().getEmpresa());
		notaFiscal.setCreditoIcms(notaFiscal.getEmpresa().getCreditoIcms().toString());
		notaFiscal.setNomePaisEmpresa("Brasil");
		notaFiscal.setCodigoPaisEmpresa("1058");
		notaFiscal.setCnpjEmpresa(notaFiscal.getEmpresa().getCpfCnpj());
		notaFiscal.setNomeFantasiaEmpresa(notaFiscal.getEmpresa().getNome());
		notaFiscal.setSenhaCertificado(notaFiscal.getEmpresa().getSenhaCertificado());
        notaFiscal.setCaminhoCertificado(notaFiscal.getEmpresa().getNomeCertificado());
		notaFiscal.setCodIbgeEstadoEmpresa(notaFiscal.getEmpresa().getEstado().getCodigoIbge());
		notaFiscal.setEstadoEmpresa(notaFiscal.getEmpresa().getEstado().getSiglaEstado());
		notaFiscal.setCodIbgeCidadeEmpresa(notaFiscal.getEmpresa().getCidade().getCodigo());
		notaFiscal.setNomeCidadeEmpresa(notaFiscal.getEmpresa().getCidade().getCidade());
		notaFiscal.setRazaoSocialEmpresa(notaFiscal.getEmpresa().getNome());
		notaFiscal.setCnpjEmpresa(notaFiscal.getEmpresa().getCpfCnpj());
		notaFiscal.setInscricaoEstadualEmpresa(notaFiscal.getEmpresa().getInscricaoEstadual());
		notaFiscal.setEnquadramentoFiscalEmpresa(notaFiscal.getEmpresa().getEnquadramentoFiscal());
		notaFiscal.setCepEmpresa(notaFiscal.getEmpresa().getCep());
		notaFiscal.setRuaEmpresa(notaFiscal.getEmpresa().getRua());
		notaFiscal.setNumeroEmpresa(notaFiscal.getEmpresa().getNumero());
		notaFiscal.setBairroEmpresa(notaFiscal.getEmpresa().getBairro());
		notaFiscal.setTelefoneEmpresa(notaFiscal.getEmpresa().getTelefone());
		notaFiscal.setEmailEmpresa(notaFiscal.getEmpresa().getEmail());
		notaFiscal.setInformacoesComplementare(notaFiscal.getEmpresa().getInfoComplementar());
		notaFiscal.setEmpresa(notaFiscal.getEmpresa());
		numeracao = numeracoes.get(0);
		selecionaNumeracao();
	}
	
	public void carregaCfopPorTipoEOperacao(){
		cfops = gestaoNotaFiscal.cfopPorTipoeOperacao(notaFiscal.getTipoNf(), notaFiscal.getOperacao());
	}
	
	
	public void atualizaCfop(){
		notaFiscal.setFinalidadeNfe(notaFiscal.getCfop().getFinalidade());
		notaFiscal.setNomeNatureza(notaFiscal.getCfop().getCfop());
	}
	
	public void selecionaNumeracao() {
		notaFiscal.setSerieNota(numeracao.getSerie());
		notaFiscal.setNumeroNota(numeracao.getNumero());
	}

	public void carregaDadosDoDestinatario(){
		notaFiscal.setEstadoDestinatario(notaFiscal.getDestinatario().getEstado().getSiglaEstado());
		notaFiscal.setCodIbgeCidadeDestinatario(notaFiscal.getDestinatario().getCodigoIbgeCidade());
		notaFiscal.setNomeCidadeDestinatario(notaFiscal.getDestinatario().getCidade().getCidade());
		notaFiscal.setNomeDestinatario(notaFiscal.getDestinatario().getNome());
		notaFiscal.setTipoCadastro(notaFiscal.getDestinatario().getTipoModalidade().toString());
		notaFiscal.setTipoContribuinte(notaFiscal.getDestinatario().getTipoContribuinte());
		notaFiscal.setInscricaoEstadualDestinatario(notaFiscal.getDestinatario().getInscricaoEstadual());
		notaFiscal.setCpfCnpjDestinatario(notaFiscal.getDestinatario().getCpfCnpj());
		notaFiscal.setCepDestinatario(notaFiscal.getDestinatario().getCep());
		notaFiscal.setRuaDestinatario(notaFiscal.getDestinatario().getRua());
		notaFiscal.setNumeroDestinatario(notaFiscal.getDestinatario().getNumero());
		notaFiscal.setBairroDestinatario(notaFiscal.getDestinatario().getBairro());
		notaFiscal.setComplementoDestinatario(notaFiscal.getDestinatario().getComplemento());
		notaFiscal.setTelefoneDestinatario(notaFiscal.getDestinatario().getTelefone());
		notaFiscal.setEmailDestinatario(notaFiscal.getDestinatario().getEmail());
		notaFiscal.setNomePaisDestinatario("Brasil");
		notaFiscal.setCodPaisDestinatario("1058");
	}
	
	public void carregaDadosDoProduto() {

        notaFiscalItem.setCfop(notaFiscal.getCfop().getCodigo());
        notaFiscalItem.setCodProd(String.valueOf(notaFiscalItem.getProduto().getCodigo()));
        notaFiscalItem.setNomeProduto(notaFiscalItem.getProduto().getDescricao());
        notaFiscalItem.setNcm(notaFiscalItem.getProduto().getCodigo_ncm());
        notaFiscalItem.setCest(notaFiscalItem.getProduto().getCodigo_cest());
        notaFiscalItem.setCodigoBarras(notaFiscalItem.getProduto().getCodigoBrras());
        notaFiscalItem.setUnidadeMedida(notaFiscalItem.getProduto().getUnidadeMedida());
        notaFiscalItem.setValorUnitario(notaFiscalItem.getProduto().getPrecoVenda());
        notaFiscalItem.setCompoeValorNota("1");
        notaFiscalItem.setOrigem(notaFiscalItem.getProduto().getOrigem());
        notaFiscalItem.setCst(notaFiscalItem.getProduto().getCstIcms());
        notaFiscalItem.setValorBaseicmsStCobradoAnteriormente(notaFiscalItem.getProduto().getAliquotaIcms());
        notaFiscalItem.setValoricmsStCobradoAnteriormente(notaFiscalItem.getProduto().getAliquotaIcms());
        notaFiscalItem.setCstPis(notaFiscalItem.getProduto().getCstPis());
        notaFiscalItem.setCstCofins(notaFiscalItem.getProduto().getCstCofins());
        notaFiscalItem.setAliquotaPis(notaFiscalItem.getProduto().getAliquotaPis());
        notaFiscalItem.setAliquitaCofins(notaFiscalItem.getProduto().getAliquotaCofins());
        notaFiscalItem.setNfe(notaFiscal);

	}
	
	public void incluirProduto() {		
		notaFiscalItem.setValorTotal(notaFiscalItem.getQuantidade().multiply(notaFiscalItem.getValorUnitario()));		
		notaFiscal.setValorTotalDesconto(notaFiscal.getValorTotalDesconto().add(notaFiscalItem.getDesconto()));
		notaFiscal.setValorTotalProdutos(notaFiscal.getValorTotalProdutos().add(notaFiscalItem.getValorTotal()));
		notaFiscal.setValorTotalNfe(notaFiscal.getValorTotalNfe().add(notaFiscalItem.getValorTotal()));
		notaFiscal.getNotaFiscalItens().add(notaFiscalItem);
		notaFiscalItem = new NotaFiscalItem();
	}
	
	public void editarProduto(NotaFiscalItem notaFiscalItem) {
		this.notaFiscalItem = notaFiscalItem;
		notaFiscal.getNotaFiscalItens().remove(notaFiscalItem);
	}
	
	public void removerProduto(NotaFiscalItem notaFiscalItem) {
		notaFiscal.setValorTotalDesconto(notaFiscal.getValorTotalDesconto().subtract(notaFiscalItem.getDesconto()));
		notaFiscal.setValorTotalProdutos(notaFiscal.getValorTotalProdutos().subtract(notaFiscalItem.getValorTotal()));
		notaFiscal.setValorTotalNfe(notaFiscal.getValorTotalNfe().subtract(notaFiscalItem.getValorTotal()));
		notaFiscal.getNotaFiscalItens().remove(notaFiscalItem);
	}
	
		
	public void carregaDadosDaTransportadora(){
		notaFiscal.setNomeTransportadora(notaFiscal.getTransportadora().getNome());
		notaFiscal.setTipoCadastroTransportadora(String.valueOf(notaFiscal.getTransportadora().getTipoContribuinte()));
		notaFiscal.setCpfCnpjTransportadora(notaFiscal.getTransportadora().getCpfCnpj());
		notaFiscal.setInscricaoEstadualTransportadora(notaFiscal.getTransportadora().getInscricaoEstadual());
		notaFiscal.setEnderecoTransportadora( notaFiscal.getTransportadora().getRua()+ " - N" + notaFiscal.getTransportadora().getNumero());
		notaFiscal.setCidadeTransportadora(notaFiscal.getTransportadora().getCidade().getCidade());
		notaFiscal.setEstadoTransportadora(notaFiscal.getTransportadora().getEstado().getSiglaEstado());
	}
	
	public void limpaNotaFiscalReferencias() {
		notaFiscal.getNotaFiscalReferencias().clear();
		notaFiscalReferencia = new NotaFiscalReferencia();
	}

	public void incluirNotaFiscalReferenciaCupom(){
		notaFiscalReferencia.setNfe(notaFiscal);
		notaFiscalReferencia.setModelo("2D");
		notaFiscal.getNotaFiscalReferencias().add(notaFiscalReferencia);	
		notaFiscalReferencia = new NotaFiscalReferencia();
		notaFiscalReferencia.setTipoDocumento("0");
	}
	
	public void incluirNotaFiscalReferenciaNota(){
		notaFiscalReferencia.setNfe(notaFiscal);
		notaFiscal.getNotaFiscalReferencias().add(notaFiscalReferencia);	
		notaFiscalReferencia = new NotaFiscalReferencia();
		notaFiscalReferencia.setTipoDocumento("1");
	}
	
	public void editarNotaFiscalReferencia(NotaFiscalReferencia notaFiscalReferencia){
		this.notaFiscalReferencia = notaFiscalReferencia;
		notaFiscal.getNotaFiscalReferencias().remove(notaFiscalReferencia);
	}
	
	public void removerNotaFiscalReferencia(NotaFiscalReferencia notaFiscalReferencia){
		notaFiscal.getNotaFiscalReferencias().remove(notaFiscalReferencia);
	}
		
	public void salvar() throws NegocioException, IOException{
		contadorProduto();
		GeraChaveAcesso geraChaveAcesso = new GeraChaveAcesso(notaFiscal);
		gestaoNotaFiscal.salvar(geraChaveAcesso.chave());

		if(notaFiscal.getId() == null){
			gestaoNumeracao.atualizaSequenciaNumeracao(numeracao);
		}

		FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/NotaFiscal.xhtml");
		FacesUtil.addInfoMessage("Nota fiscal salva com sucesso!");
	}
	
	
	
	private void limpar() {
		notaFiscal = new NotaFiscal();
		notaFiscalItem = new NotaFiscalItem();
		notaFiscalReferencia = new NotaFiscalReferencia();
		
		notaFiscal.setStatus("N");
		notaFiscal.setCondicaoPagamento(2);
		notaFiscal.setModeloNota("55");
		notaFiscal.setVersaoXml("3.10");
		notaFiscal.setTipoEmissao(1);
		notaFiscal.setTipoConsumidor(1);
		notaFiscal.setPresencaConsumidor(1);
		notaFiscal.setModeloFrete("9");
		notaFiscal.setAmbiente(1);

	}
	
	private void contadorProduto(){		
		for(int i = 0; i < notaFiscal.getNotaFiscalItens().size(); i++ ){
			notaFiscal.getNotaFiscalItens().get(i).setNumeroItem((i+1));
		}
	}


	//getters and setters
	
	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public NotaFiscalItem getNotaFiscalItem() {
		return notaFiscalItem;
	}

	public void setNotaFiscalItem(NotaFiscalItem notaFiscalItem) {
		this.notaFiscalItem = notaFiscalItem;
	}

	public NotaFiscalReferencia getNotaFiscalReferencia() {
		return notaFiscalReferencia;
	}

	public void setNotaFiscalReferencia(NotaFiscalReferencia notaFiscalReferencia) {
		this.notaFiscalReferencia = notaFiscalReferencia;
	}

	public List<Entidade> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(List<Entidade> destinatarios) {
		this.destinatarios = destinatarios;
	}

	public List<Entidade> getTransportadoras() {
		return transportadoras;
	}

	public void setTransportadoras(List<Entidade> transportadoras) {
		this.transportadoras = transportadoras;
	}

	public List<Entidade> getDestinatariosFiltro() {
		return destinatariosFiltro;
	}

	public void setDestinatariosFiltro(List<Entidade> destinatariosFiltro) {
		this.destinatariosFiltro = destinatariosFiltro;
	}

	public List<Entidade> getTransportadorasFiltro() {
		return transportadorasFiltro;
	}

	public void setTransportadorasFiltro(List<Entidade> transportadorasFiltro) {
		this.transportadorasFiltro = transportadorasFiltro;
	}

	public List<Numeracao> getNumeracoes() {
		return numeracoes;
	}

	public void setNumeracoes(List<Numeracao> numeracoes) {
		this.numeracoes = numeracoes;
	}

	public List<Cfop> getCfops() {
		return cfops;
	}

	public void setCfops(List<Cfop> cfops) {
		this.cfops = cfops;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Produto> getProdutosFiltro() {
		return produtosFiltro;
	}

	public void setProdutosFiltro(List<Produto> produtosFiltro) {
		this.produtosFiltro = produtosFiltro;
	}

	public Numeracao getNumeracao(){
		return numeracao;
	}

	public void setNumeracao(Numeracao numeracao){
		this.numeracao = numeracao;
	}
}
