package br.com.hsi.controller;

import br.com.hsi.model.*;
import br.com.hsi.model.dados.Cfop;
import br.com.hsi.model.dados.text.CSTICMS;
import br.com.hsi.model.dados.text.CSTIPI;
import br.com.hsi.model.dados.text.CSTPISCOFINS;
import br.com.hsi.model.dados.text.Origem;
import br.com.hsi.security.Seguranca;
import br.com.hsi.service.GestaoEntidade;
import br.com.hsi.service.GestaoNotaFiscal;
import br.com.hsi.service.GestaoNumeracao;
import br.com.hsi.service.GestaoProduto;
import br.com.hsi.util.exception.NegocioException;
import br.com.hsi.util.nfe.GeraChaveAcesso;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
    @Inject
    private FacesContext facesContext;

    private NotaFiscal notaFiscal;
    private NotaFiscalItem notaFiscalItem;
    private NotaFiscalReferencia notaFiscalReferencia;
    private Numeracao numeracao;
    private Embalagem embalagem;
    private List<Numeracao> numeracoes;
    private List<Entidade> entidades;
    private List<Entidade> entidadesFiltro;
    private List<Entidade> transportadoras;
    private List<Entidade> transportadorasFiltro;
    private List<Produto> produtos;
    private List<Produto> produtosFiltro;
    private List<Cfop> cfops;


    @PostConstruct
	public void init(){
        if(facesContext.getViewRoot().getViewId().contains("Saida")){
            entidades = gestaoEntidade.listarEntidades("C");
        }else {
            entidades = gestaoEntidade.listarEntidades("F");
        }
		produtos = gestaoProduto.listarProdutos();
		transportadoras = gestaoEntidade.listarEntidades("T");
		numeracoes = gestaoNumeracao.numeracoes();
	}
	
	public void inicializar() {
		if(notaFiscal == null){
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

    public String emissao(){
		if(notaFiscal.isEmissao()){
			numeracao = numeracoes.get(0);
			selecionaNumeracao();
		} else {
			numeracao = null;
		}
        return String.valueOf(notaFiscal.isEmissao());
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
        if(facesContext.getViewRoot().getViewId().contains("Saida")){
            notaFiscal.setInformacoesComplementare(notaFiscal.getEmpresa().getInfoComplementar());
        }
		notaFiscal.setEmpresa(notaFiscal.getEmpresa());
	}
	
	public void carregaCfopPorTipoEOperacao(){
		cfops = gestaoNotaFiscal.cfopPorTipoeOperacao(notaFiscal.getTipoNf(), notaFiscal.getOperacao());
	}
	
	
	public void atualizaCfop(){
		notaFiscal.setFinalidadeNfe(notaFiscal.getCfop().getFinalidade());
		notaFiscal.setNomeNatureza(notaFiscal.getCfop().getCfop());
	}
	
	public boolean selecionaNumeracao() {
        System.out.println("numeracao = " + numeracao);
        if(numeracao != null){
            notaFiscal.setSerieNota(numeracao.getSerie());
            notaFiscal.setNumeroNota(numeracao.getNumero());
            return true;
        }else {
            notaFiscal.setSerieNota(0);
            notaFiscal.setNumeroNota(0);
	        return false;
        }
	}

	public void carregaDadosDaEntidade(){
		notaFiscal.setEstadoEntidade(notaFiscal.getEntidade().getEstado().getSiglaEstado());
		notaFiscal.setCodIbgeCidadeEntidade(notaFiscal.getEntidade().getCodigoIbgeCidade());
		notaFiscal.setNomeCidadeEntidade(notaFiscal.getEntidade().getCidade().getCidade());
		notaFiscal.setNomeEntidade(notaFiscal.getEntidade().getNome());
		notaFiscal.setTipoCadastro(notaFiscal.getEntidade().getTipoModalidade().toString());
		notaFiscal.setTipoContribuinte(notaFiscal.getEntidade().getTipoContribuinte());
		notaFiscal.setInscricaoEstadualEntidade(notaFiscal.getEntidade().getInscricaoEstadual());
		notaFiscal.setCpfCnpjEntidade(notaFiscal.getEntidade().getCpfCnpj());
		notaFiscal.setCepEntidade(notaFiscal.getEntidade().getCep());
		notaFiscal.setRuaEntidade(notaFiscal.getEntidade().getRua());
		notaFiscal.setNumeroEntidade(notaFiscal.getEntidade().getNumero());
		notaFiscal.setBairroEntidade(notaFiscal.getEntidade().getBairro());
		notaFiscal.setComplementoEntidade(notaFiscal.getEntidade().getComplemento());
		notaFiscal.setTelefoneEntidade(notaFiscal.getEntidade().getTelefone());
		notaFiscal.setEmailEntidade(notaFiscal.getEntidade().getEmail());
		notaFiscal.setNomePaisEntidade("Brasil");
		notaFiscal.setCodPaisEntidade("1058");
	}
	
	public void carregaDadosDoProduto() {

	    if(notaFiscal.getOperacao().equals("1")){
            if(notaFiscalItem.getProduto().getCfopEstadual() != null ){
                notaFiscalItem.setCfop(notaFiscalItem.getProduto().getCodigoCfopEstadual());
            } else {
                notaFiscalItem.setCfop(notaFiscal.getCfop().getCodigo());
            }
        } else {
            if(notaFiscalItem.getProduto().getCfopInterestadual() != null ){
                notaFiscalItem.setCfop(notaFiscalItem.getProduto().getCodigoCfopInterestadual());
            } else {
                notaFiscalItem.setCfop(notaFiscal.getCfop().getCodigo());
            }
        }

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
        notaFiscalItem.setBaseIcmsSt(notaFiscalItem.getProduto().getAliquotaIcms());
        notaFiscalItem.setIcmsSt(notaFiscalItem.getProduto().getAliquotaIcms());
        notaFiscalItem.setCstPisCofins(notaFiscalItem.getProduto().getCstPisCofins());
        notaFiscalItem.setAliquotaPis(notaFiscalItem.getProduto().getAliquotaPis());
        notaFiscalItem.setAliquitaCofins(notaFiscalItem.getProduto().getAliquotaCofins());
        notaFiscalItem.setNotaFiscal(notaFiscal);

	}

	public void carregaDadosDoProdutoEntrada() {
	    if(notaFiscal.getCfop() != null) {
            notaFiscalItem.setCfop(notaFiscal.getCfop().getCodigo());
        }
        notaFiscalItem.setCodProd(String.valueOf(notaFiscalItem.getProduto().getCodigo()));
        notaFiscalItem.setNomeProduto(notaFiscalItem.getProduto().getDescricao());
        notaFiscalItem.setNcm(notaFiscalItem.getProduto().getCodigo_ncm());
        notaFiscalItem.setCest(notaFiscalItem.getProduto().getCodigo_cest());
        notaFiscalItem.setCodigoBarras(notaFiscalItem.getProduto().getCodigoBrras());
        notaFiscalItem.setCompoeValorNota("1");
        notaFiscalItem.setNotaFiscal(notaFiscal);
    }

	private String tipoMovimentacao() {
		if(notaFiscal.getTipoNf() == "0"){
			return "E";
		} else {
			return "S";
		}
	}

	
	public void incluirProduto() {

        BigDecimal estoqueConversao;

        if(embalagem != null){
            estoqueConversao = embalagem.getFatorConversao().multiply(notaFiscalItem.getQuantidade());
        } else {
            estoqueConversao = notaFiscalItem.getQuantidade();
        }

        notaFiscalItem.setMovimentacao(new Movimentacao(tipoMovimentacao(), estoqueConversao,
                "Nota Fiscal", String.valueOf(notaFiscal.getNumeroNota()), notaFiscalItem.getProduto(),
                notaFiscal.getEmpresa(), notaFiscalItem, new Date(System.currentTimeMillis())));


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

		if(facesContext.getViewRoot().getViewId().contains("Saida")) {
			gestaoNotaFiscal.salvar(geraChaveAcesso.chave());
		} else {
			gestaoNotaFiscal.salvar(notaFiscal);
		}

		if(notaFiscal.getId() == null){
		    if(numeracao != null){
                gestaoNumeracao.atualizaSequenciaNumeracao(numeracao);
            }
		}
        if(facesContext.getViewRoot().getViewId().contains("Saida")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Saida.xhtml");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Entrada.xhtml");
        }
	}

	
	
	private void limpar() {
		notaFiscal = new NotaFiscal();
		notaFiscalItem = new NotaFiscalItem();
		notaFiscalReferencia = new NotaFiscalReferencia();

        if(facesContext.getViewRoot().getViewId().contains("Saida")){
			notaFiscal.setTipoNf("1");
		} else {
			notaFiscal.setTipoNf("0");
		}

		notaFiscal.setStatus("N");
		notaFiscal.setCondicaoPagamento(2);
		notaFiscal.setModeloNota("55");
		notaFiscal.setVersaoXml("3.10");
		notaFiscal.setTipoEmissao(1);
		notaFiscal.setTipoConsumidor(1);
		notaFiscal.setPresencaConsumidor(1);
		notaFiscal.setModeloFrete("9");
		notaFiscal.setAmbiente(2);

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

	public List<Entidade> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<Entidade> entidades) {
		this.entidades = entidades;
	}

	public List<Entidade> getTransportadoras() {
		return transportadoras;
	}

	public void setTransportadoras(List<Entidade> transportadoras) {
		this.transportadoras = transportadoras;
	}

	public List<Entidade> getEntidadesFiltro() {
		return entidadesFiltro;
	}

	public void setEntidadesFiltro(List<Entidade> entidadesFiltro) {
		this.entidadesFiltro = entidadesFiltro;
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

    public Embalagem getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(Embalagem embalagem) {
        this.embalagem = embalagem;
    }

    public CSTICMS[] getCsts() {
		return CSTICMS.values();
	}

	public Origem[] getOrigens() {
	    return Origem.values();
    }

    public CSTPISCOFINS[] getCstsPisCofins() {
	    return  CSTPISCOFINS.entradaPisCofins();
    }

    public CSTIPI[] getCstsIpi() {
        return  CSTIPI.entradaIpi();
    }
}
