package br.com.hsi.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.*;

@Named
@ApplicationScoped
public class HomeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public InputStream getImage() throws FileNotFoundException {
	    return new FileInputStream(new File("/var/HSI/Marketing", "FUNDO_SISTEMA.png"));
	}
}
