package br.com.hsi.controller;

import br.com.hsi.model.Entidade;
import br.com.hsi.model.Praca;
import br.com.hsi.model.dados.Cep;
import br.com.hsi.model.dados.Cidade;
import br.com.hsi.model.dados.Estado;
import br.com.hsi.service.GestaoEndereco;
import br.com.hsi.service.GestaoEntidade;
import br.com.hsi.service.GestaoPraca;
import br.com.hsi.util.consulta.ConsultaCadastro;
import br.com.hsi.util.exception.NegocioException;
import br.com.hsi.util.jsf.FacesUtil;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class EntidadeFormularioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	

	@Inject
	private GestaoEntidade gestaoEntidade;
	@Inject
	private GestaoEndereco gestaoEndereco;
	@Inject
	private GestaoPraca gestaoPraca;
	@Inject
	private FacesContext facesContext;

	private Entidade entidade;
	private List<Cidade> cidades;
	private List<Estado> estados;
	private List<Praca> pracas;
	private Estado estadoConsulta;
	private String cnpjConsulta;

	
	public void inicializar(){
		pracas = gestaoPraca.listarPracas();
		estados = gestaoEndereco.estados();

		if(entidade == null) {
			entidade = new Entidade();
			entidade.setEstado(estados.get(0));
		}

		cidades = gestaoEndereco.cidadePorEstado(entidade.getEstado().getIdEstado());
	}

	public void salvar() throws IOException {
		try {
            if(facesContext.getViewRoot().getViewId().contains("Cliente")){
                entidade.setTipoEntidade("C");
            } else if(facesContext.getViewRoot().getViewId().contains("Transportadora")){
                entidade.setTipoEntidade("T");
            } else if(facesContext.getViewRoot().getViewId().contains("Fornecedor")){
                entidade.setTipoEntidade("F");
            } else if(facesContext.getViewRoot().getViewId().contains("Representante")){
                entidade.setTipoEntidade("R");
            } else if(facesContext.getViewRoot().getViewId().contains("Vendedor")){
                entidade.setTipoEntidade("V");
            }
			entidade.setNomecidade(entidade.getCidade().getCidade());
			entidade.setCodigoIbgeCidade(entidade.getCidade().getCodigo());
			entidade.setNomeEstado(entidade.getEstado().getSiglaEstado());
			entidade.setCodigoIbgeEstado(entidade.getEstado().getCodigoIbge());
			gestaoEntidade.salvar(entidade);


			if(facesContext.getViewRoot().getViewId().contains("Cliente")){
				FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Cadastros/Cliente.xhtml");
			} else if(facesContext.getViewRoot().getViewId().contains("Transportadora")){
				FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Cadastros/Transportadora.xhtml");
			} else if(facesContext.getViewRoot().getViewId().contains("Fornecedor")){
				FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Cadastros/Fornecedor.xhtml");
			} else if(facesContext.getViewRoot().getViewId().contains("Representante")){
				FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Hawker/Representante.xhtml");
			} else if(facesContext.getViewRoot().getViewId().contains("Vendedor")){
				FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Hawker/Vendedor.xhtml");
			}

		} catch (NegocioException e) {
			FacesUtil.addErrorMessage("Erro: "+e.getMessage());
		}
	}

	public void carregaCidades(AjaxBehaviorEvent event) {
		cidades = new ArrayList<>();
		cidades = gestaoEndereco.cidadePorEstado(entidade.getEstado().getIdEstado());
	}
	
	public void consultarCadastro(){

        System.out.println(cnpjConsulta);

        Entidade cliente = new ConsultaCadastro().consultarDados(estadoConsulta, cnpjConsulta);
		if(cliente != null){
			cidades = gestaoEndereco.cidadePorEstado(cliente.getEstado().getIdEstado());
			this.entidade = cliente;
			FacesUtil.addInfoMessage("Consulta realizada com sucesso!");
		}else{
			FacesUtil.addErrorMessage("CNPJ não foi encontrado!");
		}
		
	}
	
	public void buscaEnderecoEntidade(){
		Cep cep = gestaoEndereco.cep(entidade.getCep().replaceAll("-", ""));
		entidade.setEstado(gestaoEndereco.estadoPorId(cep.getCidade().getEstado().getIdEstado()));
		cidades = gestaoEndereco.cidadePorEstado(entidade.getEstado().getIdEstado());
		entidade.setCidade(gestaoEndereco.cidadePorId(cep.getCidade().getIdCidade()));
		entidade.setBairro(cep.getBairro());
		entidade.setRua(cep.getEndereco());
	}

	//-------------- Getters and Setters --------------
	
	public Entidade getEntidade() {
		return entidade;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado getEstadoConsulta() {
		return estadoConsulta;
	}

	public void setEstadoConsulta(Estado estadoConsulta) {
		this.estadoConsulta = estadoConsulta;
	}

	public String getCnpjConsulta() {
		return cnpjConsulta;
	}

	public void setCnpjConsulta(String cnpjConsulta) {
		this.cnpjConsulta = cnpjConsulta;
	}

	public List<Praca> getPracas() {
		return pracas;
	}

	public void setPracas(List<Praca> pracas) {
		this.pracas = pracas;
	}
}
