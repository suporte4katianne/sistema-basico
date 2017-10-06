package br.com.hsi.controller;

import org.primefaces.context.RequestContext;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class MenuBean implements Serializable {
	private String outcome;

	public void init() {
		outcome = "Home";
	}

	public void segurancaFormulario(String outcome) throws IOException {
		this.outcome = outcome;

		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		if(viewId.contains("Formulario")) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('cancelarOperacao').show();");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/"+outcome+".xhtml");
		}
	}

	public void direcionaPage() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/HSI/Sistemas/"+outcome+".xhtml");
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
}
