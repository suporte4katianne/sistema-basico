package br.com.hsi.nfe.util.nfe;

import br.com.hsi.nfe.model.NotaFiscal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GeraChaveAcesso {
	
	private NotaFiscal notaFiscal;
	private String sequencia;

	public GeraChaveAcesso(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	
	public NotaFiscal chave(){
		int soma = 0;

		
		char[] vetChaveAcesso;
		vetChaveAcesso = criaChave().toCharArray(); //retorna chave com 43 digitos
		
		Integer[] numeroChaveAcesso = new Integer[vetChaveAcesso.length];
		Integer[] pessoCalculo = {4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2,9,8,7,6,5,4,3,2};
		
		for (int i = 0; i < vetChaveAcesso.length; i++) {
			numeroChaveAcesso[i] = Character.getNumericValue(vetChaveAcesso[i]);
		}
		for (int i = 0; i < pessoCalculo.length; i++) {
			soma = soma + (numeroChaveAcesso[i] * pessoCalculo[i]);
		}
		
		soma = soma % 11;
		soma = 11 - soma;
		
		if(soma >= 10){
			return chave();
		}else{
			notaFiscal.setChaveAcesso("NFe"+String.valueOf(vetChaveAcesso)+soma);
			notaFiscal.setDigitoVerificadorChaveAcesso(soma);
			notaFiscal.setNumeroSequenciaAleatorio(sequencia);
			System.out.println(notaFiscal.getChaveAcesso());
			return notaFiscal;
		}
	}
	
	private String criaChave(){
		SimpleDateFormat formato = new SimpleDateFormat("yyMM");
		String cnpj = notaFiscal.getCnpjEmpresa().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", "");
		String dataChave = formato.format(new Date(System.currentTimeMillis()));
		sequencia = geraNumeroAleatorio();
		
		return notaFiscal.getCodIbgeEstadoEmpresa()+
				dataChave+
				cnpj+
				notaFiscal.getModeloNota()+
				serieNota()+
				numeroNota()+
				notaFiscal.getTipoEmissao()+
				sequencia;
	}
	
	private String geraNumeroAleatorio(){
		Random gerador = new Random();
		String sequencia = String.valueOf(gerador.nextInt(99999999));
		if(sequencia.length() < 8) {
			for (int i = 1; i <= sequencia.length(); i++) {
				if(sequencia.length() < 8) {
					sequencia = "0" + sequencia;
				}
			}
		}
		return sequencia;
	}
	
	private String numeroNota() {
		String numero = String.valueOf(notaFiscal.getNumeroNota());
		for (int i = 1; i <= numero.length(); i++) {
			if(numero.length() < 9) {
				numero = "0" + numero;
			}
		}
		return numero;
	}
	
	private String serieNota() {
		String serie = String.valueOf(notaFiscal.getSerieNota());
		for (int i = 1; i <= serie.length(); i++) {
			if(serie.length() < 3) {
				serie = "0" + serie;
			}
		}
		return serie;
	}
}
