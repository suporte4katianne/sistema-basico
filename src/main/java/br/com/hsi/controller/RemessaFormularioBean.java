package br.com.hsi.controller;

import br.com.hsi.model.*;
import br.com.hsi.model.dados.text.StatusRemessa;
import br.com.hsi.service.GestaoEntidade;
import br.com.hsi.service.GestaoPraca;
import br.com.hsi.service.GestaoProduto;
import br.com.hsi.service.GestaoRemessa;
import br.com.hsi.util.exception.NegocioException;
import br.com.hsi.util.jsf.FacesUtil;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Classe de controle para a pagina RemessaAbertaFormulario.xhtml
 *
 * - Salva nova {@link br.com.hsi.model.Remessa}
 *
 * - Edita {@link br.com.hsi.model.Remessa}
 *
 * @author Eriel Miquilino
 */

@Named
@ViewScoped
public class RemessaFormularioBean implements Serializable {

    @Inject
    private GestaoRemessa gestaoRemessa;

    @Inject
    private GestaoEntidade gestaoEntidade;

    @Inject
    private GestaoPraca gestaoPraca;

    @Inject
    private GestaoProduto gestaoProduto;

    private List<Produto> produtos;
    private List<Produto> produtosFiltro;

    private List<Entidade> representantes;
    private List<Entidade> representantesFiltro;
    private List<Praca> pracas;

    private Remessa remessa;
    private RemessaItem remessaItem;

    public void inicializar() {
        produtos = gestaoProduto.listarProdutos();
        representantes = gestaoEntidade.listarEntidades("R");
        pracas = gestaoPraca.listarPracas();
        remessaItem = new RemessaItem();
        if(remessa == null) {
            remessa = new Remessa();
            remessa.setCodigo(gestaoRemessa.sequenciaCodigo());
        }
    }

    public void carregaDadosDoProduto() {
        remessaItem.setDescricao(remessaItem.getProduto().getDescricao());
        remessaItem.setPrecoUnitario(remessaItem.getProduto().getPrecoVenda());
        remessaItem.setRemessa(remessa);
    }

    public void incluirRemessaItem() {
        boolean gravar = true;
        for (RemessaItem remessaItem : remessa.getRemessaItens()) {
            if (remessaItem.getProduto().equals(this.remessaItem.getProduto())) {
                FacesUtil.addErrorMessage("Este item já existe em sua remessa, altere ou exclua o registro para continuar");
                gravar = false;
            }
        }

        if (gravar) {
            remessaItem.setPrecoTotal(remessaItem.getQuantidade().multiply(remessaItem.getPrecoUnitario()));
            remessa.setTotal(remessa.getTotal().add(remessaItem.getPrecoTotal()));
            remessa.getRemessaItens().add(remessaItem);
            remessaItem = new RemessaItem();
        }
    }

    public void removerRemessaItem(RemessaItem remessaItem) {
        remessa.setTotal(remessa.getTotal().subtract(remessaItem.getPrecoTotal()));
        remessa.getRemessaItens().remove(remessaItem);
    }

    public void salvar () throws IOException {
        try {
            remessa.setStatusRemessa(StatusRemessa.ABERTA);
            gestaoRemessa.salvar(remessa);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Hawker/Remessa.xhtml");
        } catch (NegocioException e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }

    public Remessa getRemessa() {
        return remessa;
    }

    public void setRemessa(Remessa remessa) {
        this.remessa = remessa;
    }

    public List<Entidade> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(List<Entidade> representantes) {
        this.representantes = representantes;
    }

    public List<Entidade> getRepresentantesFiltro() {
        return representantesFiltro;
    }

    public void setRepresentantesFiltro(List<Entidade> representantesFiltro) {
        this.representantesFiltro = representantesFiltro;
    }

    public List<Praca> getPracas() {
        return pracas;
    }

    public void setPracas(List<Praca> pracas) {
        this.pracas = pracas;
    }

    public RemessaItem getRemessaItem() {
        return remessaItem;
    }

    public void setRemessaItem(RemessaItem remessaItem) {
        this.remessaItem = remessaItem;
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
}
