package br.com.hsi.controller;

import br.com.hsi.model.Empresa;
import br.com.hsi.service.GestaoEmpresa;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class RelatorioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private GestaoEmpresa gestaoEmitente;

	private List<SelectItem> categories;
	private DualListModel<Empresa> emitentesLista;
	private String nomeRelatorio;

	@PostConstruct
	public void init() {
		categories = new ArrayList<SelectItem>();
		SelectItemGroup emitentes = new SelectItemGroup("Emitentes");
		SelectItemGroup destinatarios = new SelectItemGroup("Destinatários");
		SelectItemGroup transportadoras = new SelectItemGroup("Transportadoras");
		SelectItemGroup produtos = new SelectItemGroup("Produtos");
		SelectItemGroup notasFiscais = new SelectItemGroup("Notas Fiscais");
		SelectItemGroup graficos = new SelectItemGroup("Gráficos");

		//Emitentes
		SelectItemGroup emitentesListaEmitentes = new SelectItemGroup("Lista de Emitentes Ativos");
		
		//Destinatarios
		SelectItemGroup destinatariosListaDestinatarios = new SelectItemGroup("Lista de Destinatários Ativos");

		//Transportadoras
		SelectItem transportadorasListaTransportadoras = new SelectItem("Lista de Transportadoras Ativas");

		//Produtos
		SelectItem prdutosListaProdutos = new SelectItem("Lista de Produtos Ativos");
		
		//Notas Fiscais
		SelectItem notasFiscaisListaNostas =  new SelectItem("Lista de Notas Fiscais");
		
		//Graficos

		emitentes.setSelectItems(new SelectItem[] { emitentesListaEmitentes });
		destinatarios.setSelectItems(new SelectItem[] { destinatariosListaDestinatarios });
		transportadoras.setSelectItems(new SelectItem[] { transportadorasListaTransportadoras });
		produtos.setSelectItems(new SelectItem[] { prdutosListaProdutos });
		notasFiscais.setSelectItems(new SelectItem[] { notasFiscaisListaNostas });

		categories.add(emitentes);
		categories.add(destinatarios);
		categories.add(transportadoras);
		categories.add(produtos);
		categories.add(notasFiscais);
		categories.add(graficos);
		
		
		//Emitentes
		
        List<Empresa> emitenteSource = gestaoEmitente.empresas();
        List<Empresa> emitenteTarget = new ArrayList<Empresa>();
         
        emitentesLista = new DualListModel<Empresa>(emitenteSource, emitenteTarget);
	}

	public List<SelectItem> getCategories() {
		return categories;
	}

	public String getNomeRelatorio() {
		return nomeRelatorio;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public DualListModel<Empresa> getEmitentesLista() {
		return emitentesLista;
	}

	public void setEmitentesLista(DualListModel<Empresa> emitentesLista) {
		this.emitentesLista = emitentesLista;
	}


}
