package br.com.hsi.nfe.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class MenuBean {

	public String getItemCssClass(String viewId) {
		FacesContext context = FacesContext.getCurrentInstance();
		String currentViewId = context.getViewRoot().getViewId();

		
		viewId = "/" + viewId + ".xhtml";

		return currentViewId.equals(viewId) ? "is-selected" : null;
	}

}
