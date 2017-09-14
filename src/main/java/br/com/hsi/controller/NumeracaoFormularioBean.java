package br.com.hsi.controller;

import br.com.hsi.model.Numeracao;
import br.com.hsi.security.Seguranca;
import br.com.hsi.service.GestaoNumeracao;
import br.com.hsi.util.exception.NegocioException;
import br.com.hsi.util.jsf.FacesUtil;

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
