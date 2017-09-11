package br.com.hsi.nfe.controller;

import br.com.hsi.nfe.model.Numeracao;
import br.com.hsi.nfe.security.Seguranca;
import br.com.hsi.nfe.service.GestaoNumeracao;
import br.com.hsi.nfe.util.exception.NegocioException;
import br.com.hsi.nfe.util.jsf.FacesUtil;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class NumeracaoFormularioBean implements Serializable {

    @Inject
    private GestaoNumeracao gestaoNumeracao;

    @Inject
    private Seguranca seguranca;

    private Numeracao numeracao;

    public void inicializar (){
        if(numeracao == null){
            numeracao = new Numeracao();
        }
    }
    public void salvar() throws IOException {
        try {
            numeracao.setEmpresa(seguranca.getUsuarioLogado().getUsuario().getEmpresa());
            gestaoNumeracao.salvar(numeracao);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/Numeracao.xhtml");
        } catch (NegocioException e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }

    public Numeracao getNumeracao() {
        return numeracao;
    }

    public void setNumeracao(Numeracao numeracao) {
        this.numeracao = numeracao;
    }
}
