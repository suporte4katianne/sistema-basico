package br.com.hsi.controller;

import br.com.hsi.model.Embalagem;
import br.com.hsi.model.Empresa;
import br.com.hsi.model.Produto;
import br.com.hsi.model.dados.Cest;
import br.com.hsi.model.dados.Cfop;
import br.com.hsi.model.dados.Ncm;
import br.com.hsi.model.dados.UnidadeMedida;
import br.com.hsi.model.dados.text.CSTICMS;
import br.com.hsi.model.dados.text.CSTIPI;
import br.com.hsi.model.dados.text.CSTPISCOFINS;
import br.com.hsi.model.dados.text.Origem;
import br.com.hsi.service.GestaoNotaFiscal;
import br.com.hsi.service.GestaoProduto;
import br.com.hsi.util.jsf.FacesUtil;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProdutoFormularioBean implements Serializable {

	@Inject
	private GestaoProduto gestaoProduto;
	@Inject
	private GestaoNotaFiscal gestaoNotaFiscal;
	
	private Produto produto;
	private Embalagem embalagem;
	private List<Empresa> emitentes;
	private List<Produto> produtos;
	private List<UnidadeMedida> unidadeMedida;
	private List<Ncm> ncms;
	private List<Ncm> ncmsFiltro;
	private List<Cest> cest;
	private List<Cest> cestFiltro;
	private List<Cfop> cfopsEstaduais;
	private List<Cfop> cfopsInterestaduais;

	public void inicializar(){
		cfopsEstaduais = gestaoNotaFiscal.cfopPorTipoeOperacao("1", "1");
		cfopsInterestaduais = gestaoNotaFiscal.cfopPorTipoeOperacao("1", "2");
		unidadeMedida = gestaoProduto.listaUnidadeMedida();
		ncms = gestaoProduto.listaNcms();
		cest = gestaoProduto.listaCest();
        embalagem = new Embalagem();
		if(produto == null){
			produto = new Produto();
			try {
				produto.setCodigo(gestaoProduto.pesquisaUltimoCodigo() + 1);
			}catch (NullPointerException e){
				produto.setCodigo(1);

			}
		}
	}
	
	public void salvar() throws IOException {
		if(produto.getCfopEstadual() != null){
			produto.setCodigoCfopEstadual(produto.getCfopEstadual().getCodigo());
		}
		if(produto.getCfopInterestadual() != null){
			produto.setCodigoCfopInterestadual(produto.getCfopInterestadual().getCodigo());
		}
		try{
			gestaoProduto.salvar(produto);
			FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Cadastros/Produto.xhtml");
		}catch (PersistenceException e) {
			FacesUtil.addErrorMessage("Este valor já existe no banco de dados");
		}
	}

    public void geraEAN() {
	    if (String.valueOf(produto.getCodigo()).length() <= 11){
	        if ( !(produto.getCodigo() == 0) ) {
                String barcode = String.valueOf(produto.getCodigo());
                int dif = 12 - barcode.length();
                for (int i = 0; i < dif; i++) {
                    if(i == (dif-1)) {
                        barcode = "8" + barcode;
                    } else {
                        barcode = "0" + barcode;
                    }
                }
                int[] digitos = new int[12];
                for(int i=0; i < 12 ; i++) {
                    digitos[i] = Integer.parseInt(barcode.substring(i, i+1));
                }

                int aux1 = digitos[1] + digitos[3] + digitos[5] + digitos[7] + digitos[9] + digitos[11];
                aux1 = aux1 * 3;
                int aux2 = digitos[0] + digitos[2] + digitos[4] + digitos[6] + digitos[8] + digitos[10];
                int aux3 = aux1 + aux2;
                int aux4 = 10 - (aux3 % 10);
                if(aux4 == 10) {
                    aux4 = 1;
                }
                produto.setCodigoBrras(barcode + aux4);
            }
        } else {
	        FacesUtil.addErrorMessage("Campo Código esta em branco");
        }

    }

    public void incluirEmbalagem() {
		embalagem.setProduto(produto);
        produto.getEmbalagens().add(embalagem);
        embalagem = new Embalagem();
    }

    public void alterarEmbalagem(Embalagem embalagem) {
        produto.getEmbalagens().remove(embalagem);
        this.embalagem = embalagem;
    }

    public void refreshNcm() {
		ncms = gestaoProduto.listaNcms();
	}

	public void refreshCest() {
		cest = gestaoProduto.listaCest();
	}

    public void excluirEmbalagem(Embalagem embalagem) {
        produto.getEmbalagens().remove(embalagem);
    }


    public void selecionaNcm() {
		produto.setCodigo_ncm(produto.getNcm().getNcm());
	}
	
	public void selecionaCest() {
		produto.setCodigo_cest(produto.getCest().getCest());
	}

	// Getters and Setters //

	public Produto getProduto() {
		return produto;
	}

	public List<UnidadeMedida> getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(List<UnidadeMedida> unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Empresa> getEmitentes() {
		
		return emitentes;
	}

	public void setEmitentes(List<Empresa> emitentes) {
		this.emitentes = emitentes;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Ncm> getNcms() {
		return ncms;
	}

	public void setNcms(List<Ncm> ncms) {
		this.ncms = ncms;
	}

	public List<Ncm> getNcmsFiltro() {
		return ncmsFiltro;
	}

	public void setNcmsFiltro(List<Ncm> ncmsFiltro) {
		this.ncmsFiltro = ncmsFiltro;
	}

	public List<Cest> getCest() {
		return cest;
	}

	public void setCest(List<Cest> cest) {
		this.cest = cest;
	}

	public List<Cest> getCestFiltro() {
		return cestFiltro;
	}

	public void setCestFiltro(List<Cest> cestFiltro) {
		this.cestFiltro = cestFiltro;
	}

	public List<Cfop> getCfopsEstaduais() {
		return cfopsEstaduais;
	}

	public void setCfopsEstaduais(List<Cfop> cfopsEstaduais) {
		this.cfopsEstaduais = cfopsEstaduais;
	}

	public List<Cfop> getCfopsInterestaduais() {
		return cfopsInterestaduais;
	}

	public void setCfopsInterestaduais(List<Cfop> cfopsInterestaduais) {
		this.cfopsInterestaduais = cfopsInterestaduais;
	}

    public Embalagem getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(Embalagem embalagem) {
        this.embalagem = embalagem;
    }

	public Origem[] getOrigens() {
		return Origem.values();
	}

	public CSTICMS[] getCsts() {
		return CSTICMS.csosns();
	}

	public CSTPISCOFINS[] getCstsPisCofins() {
		return CSTPISCOFINS.saidaPisCofins();
	}

	public CSTIPI[] getCstsIpi() {
		return CSTIPI.saidaIpi();
	}



}
