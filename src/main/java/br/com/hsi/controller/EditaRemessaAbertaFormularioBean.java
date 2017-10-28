package br.com.hsi.controller;

import br.com.hsi.model.*;
import br.com.hsi.model.dados.text.StatusRemessa;
import br.com.hsi.service.GestaoEntidade;
import br.com.hsi.service.GestaoKit;
import br.com.hsi.service.GestaoProduto;
import br.com.hsi.service.GestaoRemessa;
import br.com.hsi.util.exception.NegocioException;
import br.com.hsi.util.jsf.FacesUtil;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Eriel Miquilino
 */

@Named
@ViewScoped
public class EditaRemessaAbertaFormularioBean implements Serializable{

    @Inject
    private GestaoRemessa gestaoRemessa;
    @Inject
    private GestaoEntidade gestaoEntidade;
    @Inject
    private GestaoKit gestaoKit;
    @Inject
    private GestaoProduto gestaoProduto;

    private Remessa remessa;
    private Remessa remessaVinculada;

    private Kit kit;
    private KitItem kitItem;
    private List<Kit> kits;

    private Long saldo;
    private Long saldoVinculado;
    private Long saldoTotal;

    private List<Produto> produtos;
    private List<Produto> produtosFiltro;
    private List<Entidade> vendedores;
    private List<Entidade> vendedoreFiltro;
    private List<Remessa> remessas;
    private List<Remessa> remessasFiltro;

    public void inicializar() {
        remessa.setRemessaItens(gestaoRemessa.remessaItemPorRemessa(remessa));
        produtos = gestaoProduto.listarProdutos();
        vendedores = gestaoEntidade.listarEntidades("V");
        saldo = gestaoRemessa.saldoRemessa(remessa);
        remessas = gestaoRemessa.listarRemessas(StatusRemessa.FINALIZADA);
        saldoTotal = saldo;
        kit = new Kit();
        kitItem = new KitItem();
        kits = new ArrayList<>();
    }


    public void carregaDadosDaRemessa() {
        saldoTotal = new Long(0);
        saldoVinculado = gestaoRemessa.saldoRemessa(remessaVinculada);
        saldoTotal = saldoVinculado + saldo;
    }

    public void carregaDadosDoProduto() {
        kitItem.setDescricao(kitItem.getProduto().getDescricao());
        kitItem.setPrecoUnitario(kitItem.getProduto().getPrecoVenda());
        kitItem.setKit(kit);
    }

    public void incluirKitItem() {
        boolean verificaRegistro = true;
        for (RemessaItem remessaItem: remessa.getRemessaItens()) {
            if (Objects.equals(remessaItem.getProduto().getId(), kitItem.getProduto().getId())) {
                verificaRegistro = false;
                if(kitItem.getQuantidade().doubleValue() <= saldoItemRemessa(remessaItem).doubleValue()) {
                    boolean gravar = true;
                    for (KitItem kitItem : kit.getKitItens()) {
                        if (kitItem.getProduto().equals(this.kitItem.getProduto())) {
                            gravar = false;
                        }
                    }
                    if (gravar) {
                        kitItem.setPrecoTotal(kitItem.getQuantidade().multiply(kitItem.getPrecoUnitario()));
                        kit.setTotal(kit.getTotal().add(kitItem.getPrecoTotal()));
                        kit.getKitItens().add(kitItem);
                    } else {
                        FacesUtil.addErrorMessage("Este item já existe em seu kit, altere ou exclua o registro para continuar");
                    }
                } else {
                    FacesUtil.addErrorMessage("Quantidade informada acima do saldo disponivel na remessa!");
                }
            }
        }
        if (verificaRegistro) {
            FacesUtil.addErrorMessage("Este item não possoui saldo disponivel nesta remessa!");
        } else {
            kitItem = new KitItem();
        }
    }




    public void removerKitItem(KitItem kitItem) {
        kit.setTotal(kit.getTotal().subtract(kitItem.getPrecoTotal()));
        kit.getKitItens().remove(kitItem);
    }

    public void incluirKit() {
        boolean gravar = true;
        for (Kit kit : kits) {
            if(kit.getCodigo() == this.kit.getCodigo()) {
                FacesUtil.addErrorMessage("Já possui um kit usando este código");
                gravar = false;
            }
        }

        if (gravar) {
            kit.setRemessa(remessa);
            kits.add(kit);
            kit = new Kit();
            kitItem = new KitItem();
        }

    }

    public void removerKit(Kit kit) {
        kits.remove(kit);
    }

    public void alterarKit(Kit kit) {
        this.kit = kit;
        kits.remove(kit);
    }

    public void salvar() {
        try {
            remessa.setStatusRemessa(StatusRemessa.EDITADA);
            gestaoRemessa.salvar(remessa);
            for (Kit kit: kits) {
                gestaoKit.salvarKit(kit);
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Hawker/Remessa.xhtml");
        } catch (NegocioException | IOException e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }


    private BigDecimal saldoItemRemessa (RemessaItem remessaItem) {
        BigDecimal saldoLancado = remessaItem.getQuantidade();
        for (Kit kit : kits) {
            for (KitItem item : kit.getKitItens()) {
                if (Objects.equals(remessaItem.getProduto().getId(), item.getProduto().getId())) {
                    saldoLancado = saldoLancado.subtract(item.getQuantidade());
                }
            }
        }
        return saldoLancado;
    }

    public void refreshProdutos() {
        produtos = gestaoProduto.listarProdutos();
    }

    public void refreshVendedores() {
        vendedores = gestaoEntidade.listarEntidades("V");
    }

    public void refreshRemessaVinculada() {
        remessas = gestaoRemessa.listarRemessas(StatusRemessa.FINALIZADA);
    }


    public Remessa getRemessa() {
        return remessa;
    }

    public void setRemessa(Remessa remessa) {
        this.remessa = remessa;
    }

    public Remessa getRemessaVinculada() {
        return remessaVinculada;
    }

    public void setRemessaVinculada(Remessa remessaVinculada) {
        this.remessaVinculada = remessaVinculada;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }

    public Long getSaldoVinculado() {
        return saldoVinculado;
    }

    public void setSaldoVinculado(Long saldoVinculado) {
        this.saldoVinculado = saldoVinculado;
    }

    public Long getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(Long saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public List<Remessa> getRemessas() {
        return remessas;
    }

    public void setRemessas(List<Remessa> remessas) {
        this.remessas = remessas;
    }

    public List<Remessa> getRemessasFiltro() {
        return remessasFiltro;
    }

    public void setRemessasFiltro(List<Remessa> remessasFiltro) {
        this.remessasFiltro = remessasFiltro;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public List<Kit> getKits() {
        return kits;
    }

    public void setKits(List<Kit> kits) {
        this.kits = kits;
    }

    public List<Entidade> getVendedores() {
        return vendedores;
    }

    public void setVendedores(List<Entidade> vendedores) {
        this.vendedores = vendedores;
    }

    public List<Entidade> getVendedoreFiltro() {
        return vendedoreFiltro;
    }

    public void setVendedoreFiltro(List<Entidade> vendedoreFiltro) {
        this.vendedoreFiltro = vendedoreFiltro;
    }

    public KitItem getKitItem() {
        return kitItem;
    }

    public void setKitItem(KitItem kitItem) {
        this.kitItem = kitItem;
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
