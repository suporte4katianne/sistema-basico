package br.com.hsi.controller;

import br.com.hsi.model.Inutilizacao;
import br.com.hsi.security.Seguranca;
import br.com.hsi.service.GestaoNotaFiscal;
import br.com.hsi.util.jsf.FacesUtil;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

@Named
@ViewScoped
public class InutilizacaoFormularioBean implements Serializable{

    @Inject
    private GestaoNotaFiscal gestaoNotaFiscal;
    @Inject
    private Seguranca seguranca;

    private Inutilizacao inutilizacao;

    public void inicializar (){
        if(inutilizacao == null){
            inutilizacao = new Inutilizacao();
        }
    }

    public void salvar () throws IOException {
        inutilizacao.setData(new Date(System.currentTimeMillis()));
        inutilizacao.setEmpresa(seguranca.getUsuarioLogado().getUsuario().getEmpresa());
        inutilizacao.setStatus("Normal");
        inutilizacao.setAmbiente(1);
        gestaoNotaFiscal.salvarInutilizacao(inutilizacao);
        FacesUtil.addInfoMessage("Inutilizaçao salva com sucesso!");
        FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Inutilizacao.xhtml");
    }

    public Inutilizacao getInutilizacao() {
        return inutilizacao;
    }

    public void setInutilizacao(Inutilizacao inutilizacao) {
        this.inutilizacao = inutilizacao;
    }
}
