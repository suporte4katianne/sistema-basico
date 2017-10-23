package br.com.hsi.controller;

import br.com.hsi.model.Remessa;
import br.com.hsi.service.GestaoRemessa;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @author Eriel Miquilino
 */

@Named
@ViewScoped
public class EditaRemessaAbertaFormularioBean implements Serializable{

    @Inject
    private GestaoRemessa gestaoRemessa;

    private Remessa remessa;
    private Remessa remessaVinculada;

    private Long saldo;
    private Long saldoVinculado;
    private Long saldoTotal;

    private List<Remessa> remessas;
    private List<Remessa> remessasFiltro;

    public void inicializar() {
        saldo = gestaoRemessa.saldoRemessa(remessa);
        saldoTotal = saldo;
    }

    @PostConstruct
    public void init() {
        remessas = gestaoRemessa.listarRemessas(null);
    }

    public void carregaDadosDaRemessa() {
        saldoTotal = new Long(0);
        saldoVinculado = gestaoRemessa.saldoRemessa(remessaVinculada);
        saldoTotal = saldoVinculado + saldo;
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
}
