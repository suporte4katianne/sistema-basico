package br.com.hsi.nfe.util.nfe.autorizacao;

import br.com.hsi.nfe.model.NotaFiscal;
import br.com.hsi.nfe.model.NotaFiscalItem;
import br.com.hsi.nfe.model.NotaFiscalReferencia;
import enviNFe_v310.*;
import enviNFe_v310.TNFe.InfNFe;
import enviNFe_v310.TNFe.InfNFe.*;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto.COFINS;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto.COFINS.COFINSNT;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto.ICMS;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto.ICMS.ICMS51;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN101;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN102;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto.ICMS.ICMSSN500;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto.PIS;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto.PIS.PISAliq;
import enviNFe_v310.TNFe.InfNFe.Det.Imposto.PIS.PISNT;
import enviNFe_v310.TNFe.InfNFe.Det.Prod;
import enviNFe_v310.TNFe.InfNFe.Ide.NFref;
import enviNFe_v310.TNFe.InfNFe.Ide.NFref.RefECF;
import enviNFe_v310.TNFe.InfNFe.Ide.NFref.RefNF;
import enviNFe_v310.TNFe.InfNFe.Ide.NFref.RefNFP;
import enviNFe_v310.TNFe.InfNFe.Total.ICMSTot;
import enviNFe_v310.TNFe.InfNFe.Transp.Transporta;
import enviNFe_v310.TNFe.InfNFe.Transp.Vol;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GeraXmlAutorizacao {
	private static NotaFiscal notaFiscal;

	public GeraXmlAutorizacao(NotaFiscal notaFiscal) {
		GeraXmlAutorizacao.notaFiscal = notaFiscal;
	}

	public NotaFiscal GerandoNfe() throws Exception {
		try {

			TNFe nFe = new TNFe();
			InfNFe infNFe = new InfNFe();
			infNFe.setId(notaFiscal.getChaveAcesso());
			infNFe.setVersao(notaFiscal.getVersaoXml());

			infNFe.setIde(dadosDeIdentificacao());
			infNFe.setEmit(dadosDoEmpresa());
			infNFe.setDest(dadosDoDestinatario());

			infNFe.getDet().addAll(dadosDoProduto());

			if (dadosCobranca() != null)
				infNFe.setCobr(dadosCobranca());

			infNFe.setTotal(totaisDaNFe());
			infNFe.setTransp(dadosDoTransporte());
			infNFe.setInfAdic(informacoesAdicionais());

			nFe.setInfNFe(infNFe);

			TEnviNFe enviNFe = new TEnviNFe();
			enviNFe.setVersao(notaFiscal.getVersaoXml());
			enviNFe.setIdLote("0000001");
			enviNFe.setIndSinc("1");
			enviNFe.getNFe().add(nFe);

			if (notaFiscal.getEmpresa().getTipoCertificado().equals("A1")) {
				AssinaXmlAutorizacao assinaXml = new AssinaXmlAutorizacao(notaFiscal, strValueOf(enviNFe));
                System.out.println("strValueOf(enviNFe) = " + strValueOf(enviNFe));
                return assinaXml.AssinaXml();
			}
			else {
//				String cnjpEmpresa = notaFiscal.getCnpjEmpresa().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-","");
//
//				FileWriter arquivo;
//				arquivo = new FileWriter(new File("/var/HSI/Sistemas/NF-e/" + cnjpEmpresa + "/Envio/" + notaFiscal.getChaveAcesso() + "-enviNfe.xml"));
//				arquivo.write(strValueOf(enviNFe));
//				arquivo.close();
//
//				File xmlEnviNfe = new File("/var/HSI/Sistemas/NF-e/" + cnjpEmpresa + "/Envio/" + notaFiscal.getChaveAcesso() + "-enviNfe.xml");
//				xmlEnviNfe.setReadable(Boolean.TRUE, Boolean.FALSE);
//				xmlEnviNfe.setWritable(Boolean.TRUE, Boolean.FALSE);
//				xmlEnviNfe.setExecutable(Boolean.TRUE, Boolean.FALSE);
//
//				long now = System.currentTimeMillis();
//				long expectedElapsedTime = now + 15000;
//				while(now < expectedElapsedTime){
//				    now = System.currentTimeMillis();
//				}
//
//				String xmlRetConsReciNFe = "";
//				String xmlEnviNFe = "";
//				try{
//					xmlRetConsReciNFe = lerXML("/var/HSI/Sistemas/NF-e/" + cnjpEmpresa + "/Envio/" + notaFiscal.getChaveAcesso() + "-Retorno.xml");
//					xmlEnviNFe = lerXML("/var/HSI/Sistemas/NF-e/" + cnjpEmpresa + "/Envio/" + notaFiscal.getChaveAcesso() + "-Assinado.xml");
//				}catch (FileNotFoundException e) {
//					FacesUtil.addErrorMessage("Arquivos assinados não foram encontrados, verifique se o aplicativo de assinatura esta aberto em sua maquina");
//				}
//				xmlEnviNfe.delete();
//				ProcessaXmlAutorizacao xmlAutorizacao = new ProcessaXmlAutorizacao(notaFiscal, xmlEnviNFe, xmlRetConsReciNFe);
//				return xmlAutorizacao.GerandoNfe();
                return null;
			}

		} catch (JAXBException e) {
            System.out.println(e.getMessage());
            return null;
		}
	}

	private static Ide dadosDeIdentificacao() {
		LocalDateTime now = LocalDateTime.now();

		String hora = now.withNano(0) + "-02:00";

		notaFiscal.setDataHoraEmissao(Date.from(now.withNano(0).atZone(ZoneId.of("America/Sao_Paulo")).toInstant()));
		notaFiscal.setDataHoraSaidaEntrada(Date.from(now.withNano(0).atZone(ZoneId.of("America/Sao_Paulo")).toInstant()));

		System.out.println(notaFiscal.getFinalidadeNfe());
		
		Ide ide = new Ide();
		ide.setCUF(notaFiscal.getCodIbgeEstadoEmpresa());
		ide.setCNF(notaFiscal.getNumeroSequenciaAleatorio());
		ide.setNatOp(notaFiscal.getNomeNatureza());
		ide.setIndPag(String.valueOf(notaFiscal.getCondicaoPagamento()));
		ide.setMod(notaFiscal.getModeloNota());
		ide.setSerie(String.valueOf(notaFiscal.getSerieNota()));
		ide.setNNF(String.valueOf(notaFiscal.getNumeroNota()));
		ide.setDhEmi(hora);
		ide.setDhSaiEnt(hora);
		ide.setIdDest(notaFiscal.getOperacao());
		ide.setTpNF(notaFiscal.getTipoNf());
		ide.setCMunFG(notaFiscal.getCodIbgeCidadeEmpresa());
		ide.setTpImp("1");
		ide.setTpEmis(String.valueOf(notaFiscal.getTipoEmissao()));
		ide.setCDV(String.valueOf(notaFiscal.getDigitoVerificadorChaveAcesso()));
		ide.setTpAmb(String.valueOf(notaFiscal.getAmbiente()));
		ide.setFinNFe(String.valueOf(notaFiscal.getFinalidadeNfe()));
		ide.setProcEmi("0");
		ide.setIndFinal("0");
		ide.setIndPres(String.valueOf(notaFiscal.getPresencaConsumidor()));
		ide.setVerProc("3.0");
		
		if(notaFiscal.getFinalidadeNfe() == 4){
			List<NFref> nfrefs = new ArrayList<NFref>();
			if(notaFiscal.getNotaFiscalReferencias().size() > 0){
				for (NotaFiscalReferencia notaFiscalReferencia : notaFiscal.getNotaFiscalReferencias()) {
					NFref fref = new NFref();
					if(notaFiscalReferencia.getTipoDocumento().equals("2D")){

						RefECF refECF = new RefECF();
						refECF.setMod(notaFiscalReferencia.getTipoDocumento());
						refECF.setNCOO(notaFiscalReferencia.getCoo());
						refECF.setNECF(notaFiscalReferencia.getPdv());
						fref.setRefECF(refECF);

					}else if(notaFiscalReferencia.getTipoDocumento().equals("01")){

						SimpleDateFormat dataFormatada = new SimpleDateFormat("yyMM");
						RefNF refNF = new RefNF();
						refNF.setAAMM(dataFormatada.format(notaFiscalReferencia.getData()));
						refNF.setCNPJ(notaFiscal.getCpfCnpjDestinatario().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", ""));
						refNF.setCUF(notaFiscal.getDestinatario().getCodigoIbgeEstado());
						refNF.setNNF(notaFiscalReferencia.getNumero());
						refNF.setSerie(notaFiscalReferencia.getSerie());
						refNF.setMod(notaFiscalReferencia.getTipoDocumento());
						fref.setRefNF(refNF);

					}else if(notaFiscalReferencia.getTipoDocumento().equals("04")){

						SimpleDateFormat dataFormatada = new SimpleDateFormat("yyMM");
						RefNFP refNFP = new RefNFP();
						refNFP.setAAMM(dataFormatada.format(notaFiscalReferencia.getData()));

						if(notaFiscal.getTipoContribuinte() == 2 || notaFiscal.getTipoContribuinte() == 9 ){

							if(notaFiscal.getTipoCadastro().equals("2")){
								refNFP.setCPF(notaFiscal.getCpfCnpjDestinatario().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", ""));
							}else{
								refNFP.setCNPJ(notaFiscal.getCpfCnpjDestinatario().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", ""));
							}

						}else{
							refNFP.setCNPJ(notaFiscal.getCpfCnpjDestinatario().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", ""));
							refNFP.setIE(notaFiscal.getInscricaoEstadualDestinatario());
						}

						refNFP.setCUF(notaFiscal.getDestinatario().getCodigoIbgeEstado());
                        refNFP.setNNF(notaFiscalReferencia.getNumero());
						refNFP.setSerie(notaFiscalReferencia.getSerie());
						refNFP.setMod(notaFiscalReferencia.getTipoDocumento());
						fref.setRefNFP(refNFP);

					}else if(notaFiscalReferencia.getTipoDocumento().equals("55")
							|| notaFiscalReferencia.getTipoDocumento().equals("59") || notaFiscalReferencia.getTipoDocumento().equals("65")){


						fref.setRefNFe(notaFiscalReferencia.getChave());

					}
					nfrefs.add(fref);
				}
			}
			ide.getNFref().addAll(nfrefs);
		}
		return ide;
	}

	private static Emit dadosDoEmpresa() {
		Emit emit = new Emit();
		emit.setCNPJ(notaFiscal.getCnpjEmpresa().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", ""));
		emit.setXNome(notaFiscal.getRazaoSocialEmpresa());
		emit.setXFant(notaFiscal.getNomeFantasiaEmpresa());

		TEnderEmi enderEmit = new TEnderEmi();
		enderEmit.setXLgr(notaFiscal.getRuaEmpresa());
		enderEmit.setNro(notaFiscal.getNumeroEmpresa());
		enderEmit.setXBairro(notaFiscal.getBairroEmpresa());
		enderEmit.setCMun(notaFiscal.getCodIbgeCidadeEmpresa());
		enderEmit.setXMun(notaFiscal.getNomeCidadeEmpresa());
		enderEmit.setUF(TUfEmi.valueOf(notaFiscal.getEstadoEmpresa()));
		enderEmit.setCEP(notaFiscal.getCepEmpresa().replaceAll("-", ""));
		enderEmit.setCPais(notaFiscal.getCodigoPaisEmpresa());
		enderEmit.setXPais(notaFiscal.getNomePaisEmpresa());
		enderEmit.setFone(notaFiscal.getTelefoneEmpresa().replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "").replaceAll("-", ""));
		emit.setEnderEmit(enderEmit);

		emit.setIE(notaFiscal.getInscricaoEstadualEmpresa());
		emit.setCRT("1");
		return emit;
	}

	private static Dest dadosDoDestinatario() {
		Dest dest = new Dest();
		if (notaFiscal.getAmbiente() == 2) {
			dest.setXNome("NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL");
		} else {
			dest.setXNome(notaFiscal.getNomeDestinatario());
		}

		
		if(notaFiscal.getTipoContribuinte() == 2 || notaFiscal.getTipoContribuinte() == 9){
			dest.setIndIEDest("2");
			
			if(notaFiscal.getTipoCadastro().equals("2")){
				dest.setCPF(notaFiscal.getCpfCnpjDestinatario().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", "").trim());
			}else{
				dest.setCNPJ(notaFiscal.getCpfCnpjDestinatario().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", "").trim());
			}
		
		}else{			
			dest.setIndIEDest("1");
			dest.setCNPJ(notaFiscal.getCpfCnpjDestinatario().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", "").trim());
			dest.setIE(notaFiscal.getInscricaoEstadualDestinatario().trim());
		}
		

		TEndereco enderDest = new TEndereco();
		enderDest.setXLgr(notaFiscal.getRuaDestinatario().trim());
		enderDest.setNro(notaFiscal.getNumeroDestinatario().trim());
		enderDest.setXBairro(notaFiscal.getBairroDestinatario().trim());
		enderDest.setCMun(notaFiscal.getCodIbgeCidadeDestinatario().trim());
		enderDest.setXMun(notaFiscal.getNomeCidadeDestinatario().trim());
		enderDest.setUF(TUf.valueOf(notaFiscal.getEstadoDestinatario().trim()));
		enderDest.setCEP(notaFiscal.getCepDestinatario().replaceAll("-", "").trim());
		enderDest.setCPais(notaFiscal.getCodPaisDestinatario().trim());
		enderDest.setXPais(notaFiscal.getNomePaisDestinatario().trim());
		enderDest.setFone(notaFiscal.getTelefoneDestinatario().replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "").replaceAll("-", "").trim());
		dest.setEnderDest(enderDest);

		dest.setEmail(notaFiscal.getEmailDestinatario());
		return dest;
	}

	private static List<Det> dadosDoProduto() {
		List<Det> dets = new ArrayList<Det>();

		for (NotaFiscalItem notaFiscalItem : notaFiscal.getNotaFiscalItens()) {
			Det det = new Det();
			det.setNItem(String.valueOf(notaFiscalItem.getNumeroItem()));

			Prod prod = new Prod();
			prod.setCProd(notaFiscalItem.getCodProd());
			prod.setCEAN(notaFiscalItem.getCodigoBarras());
			prod.setXProd(notaFiscalItem.getNomeProduto().trim());
			prod.setNCM(notaFiscalItem.getNcm());
			prod.setCFOP(notaFiscalItem.getCfop());
			prod.setUCom(notaFiscalItem.getUnidadeMedida());
			prod.setQCom(String.valueOf(notaFiscalItem.getQuantidade()));
			prod.setVUnCom(String.valueOf(notaFiscalItem.getValorUnitario()));
			prod.setVProd(String.valueOf(notaFiscalItem.getValorTotal()));
			prod.setUTrib(notaFiscalItem.getUnidadeMedida());
			prod.setQTrib(String.valueOf(notaFiscalItem.getQuantidade()));
			prod.setCEANTrib(notaFiscalItem.getCodigoBarras());
			prod.setCEST(notaFiscalItem.getCest().replaceAll("\\.", ""));
			prod.setVUnTrib(String.valueOf(notaFiscalItem.getValorUnitario()));
			prod.setIndTot(notaFiscalItem.getCompoeValorNota());
			if (notaFiscalItem.getDesconto().doubleValue() > new Double(0)) {
				prod.setVDesc(String.valueOf(notaFiscalItem.getDesconto()));

				double valorUnitario = notaFiscalItem.getValorUnitario().doubleValue() + ((notaFiscalItem.getDesconto().doubleValue()) / notaFiscalItem.getQuantidade().doubleValue());

				double valorTotal = (notaFiscalItem.getValorTotal().doubleValue()) + (notaFiscalItem.getDesconto().doubleValue());

				prod.setVUnCom(String.format("%.2f", valorUnitario).replaceAll(",", "."));
				prod.setVProd(String.format("%.2f", valorTotal).replaceAll(",", "."));
				prod.setVUnTrib(String.format("%.2f", valorUnitario).replaceAll(",", "."));
			}

			det.setProd(prod);

			Imposto imposto = new Imposto();

			ICMS icms = new ICMS();


			if (notaFiscalItem.getCst().equals("101")) {
				ICMSSN101 icmssn101 = new ICMSSN101();
				icmssn101.setOrig(notaFiscalItem.getOrigem());
				icmssn101.setCSOSN(notaFiscalItem.getCst());
				icmssn101.setPCredSN(notaFiscal.getCreditoIcms());
				Double creditoIcms = Double.parseDouble(notaFiscal.getCreditoIcms());
				Double valorTotalItem = notaFiscalItem.getValorTotal().doubleValue();
				icmssn101.setVCredICMSSN(String.format("%.2f", ((valorTotalItem * (creditoIcms / 100)))).replaceAll(",", "\\."));
				icms.setICMSSN101(icmssn101);
			} else if (notaFiscalItem.getCst().equals("102")) {
				ICMSSN102 icmssn102 = new ICMSSN102();
				icmssn102.setOrig(notaFiscalItem.getOrigem());
				icmssn102.setCSOSN(notaFiscalItem.getCst());
				icms.setICMSSN102(icmssn102);
			} else if (notaFiscalItem.getCst().equals("103")){


			} else if (notaFiscalItem.getCst().equals("201")){


			} else if (notaFiscalItem.getCst().equals("202")){


			} else if (notaFiscalItem.getCst().equals("203")){


			} else if (notaFiscalItem.getCst().equals("300")){


			} else if (notaFiscalItem.getCst().equals("400")){


			} else if (notaFiscalItem.getCst().equals("500")){
				ICMSSN500 icmssn500 = new ICMSSN500();
				icmssn500.setCSOSN(notaFiscalItem.getCst());
				icmssn500.setOrig(notaFiscalItem.getOrigem());
				icmssn500.setVBCSTRet("0.00");
				icmssn500.setVICMSSTRet("0.00");
				icms.setICMSSN500(icmssn500);
			} else if (notaFiscalItem.getCst().equals("900")){


			} else if (notaFiscalItem.getCst().equals("00")){


			} else if (notaFiscalItem.getCst().equals("10")){


			} else if (notaFiscalItem.getCst().equals("20")){


			} else if (notaFiscalItem.getCst().equals("30")){


			} else if (notaFiscalItem.getCst().equals("40")){


			} else if (notaFiscalItem.getCst().equals("41")){


			} else if (notaFiscalItem.getCst().equals("50")){


			} else if (notaFiscalItem.getCst().equals("51")){
				ICMS51 icms51 = new ICMS51();
				icms51.setOrig(notaFiscalItem.getOrigem());
				icms51.setCST(notaFiscalItem.getCst());
				icms51.setModBC("0");
				icms.setICMS51(icms51);
			} else if (notaFiscalItem.getCst().equals("60")){


			} else if (notaFiscalItem.getCst().equals("70")){


			} else if (notaFiscalItem.getCst().equals("90")){


			}





			PIS pis = new PIS();
			COFINS cofins = new COFINS();

			if(notaFiscalItem.getCstPis().equals("02")){
				PISAliq pisaliq = new PISAliq();
				pisaliq.setCST(notaFiscalItem.getCstPis());
				pisaliq.setVBC(String.valueOf(notaFiscal.getValorTotalNfe()));
				pisaliq.setPPIS(notaFiscalItem.getAliquotaPis().toString());
				pisaliq.setVPIS(notaFiscalItem.getValorTotalPis().toString());
				pis.setPISAliq(pisaliq);
			}else{
				PISNT pisnt = new PISNT();
				pisnt.setCST(notaFiscalItem.getCstPis());
				pis.setPISNT(pisnt);
			}

			if(notaFiscalItem.getCstPis().equals("02")){
				COFINSAliq cofinsaliq = new COFINSAliq();
				cofinsaliq.setCST(notaFiscalItem.getCstPis());
				cofinsaliq.setVBC(String.valueOf(notaFiscal.getValorTotalNfe()));
				cofinsaliq.setPCOFINS(notaFiscalItem.getAliquotaPis().toString());
				cofinsaliq.setVCOFINS(notaFiscalItem.getValorTotalPis().toString());
				cofins.setCOFINSAliq(cofinsaliq);
			}else{
				COFINSNT cofinsnt = new COFINSNT();
				cofinsnt.setCST(notaFiscalItem.getCstCofins());
				cofins.setCOFINSNT(cofinsnt);
			}





			imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoICMS(icms));
			imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoPIS(pis));
			imposto.getContent().add(new ObjectFactory().createTNFeInfNFeDetImpostoCOFINS(cofins));

			det.setImposto(imposto);

			dets.add(det);
		}

		return dets;
	}

	private static Cobr dadosCobranca() {
		Cobr cobr = new Cobr();

//		Dup dup = null;
//		SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
//		for (NfeFinanceiro nfeFinanceiro : notaFiscal.getNfeFinanceiros()) {
//			dup = new Dup();
//			dup.setDVenc(formatoData.format(nfeFinanceiro.getDataVencimento()));
//			dup.setNDup(nfeFinanceiro.getNumeroDocumento());
//			dup.setVDup(nfeFinanceiro.getValorLiquido());
//			cobr.getDup().add(dup);
//		}

		return cobr;
	}

	private static Total totaisDaNFe() {
		Total total = new Total();

		double valorTotalProduto = notaFiscal.getValorTotalProdutos().doubleValue()
				+ notaFiscal.getValorTotalDesconto().doubleValue();

		ICMSTot icmstot = new ICMSTot();
		icmstot.setVICMSDeson(String.valueOf(notaFiscal.getValorTotalIcmsDeson()));
		icmstot.setVBC(String.valueOf(notaFiscal.getValorTotalBaseIcms()));
		icmstot.setVICMS(String.valueOf(notaFiscal.getValorTotalIcms()));
		icmstot.setVBCST(String.valueOf(notaFiscal.getValorTotalBaseIcmsSt()));
		icmstot.setVST(String.valueOf(notaFiscal.getValorTotalIcmsS()));
		icmstot.setVProd(String.format("%.2f", valorTotalProduto).replaceAll(",", "."));
		icmstot.setVFrete(String.valueOf(notaFiscal.getValorTotalFrete()));
		icmstot.setVSeg(String.valueOf(notaFiscal.getValorTotalSeguro()));
		icmstot.setVDesc(String.valueOf(notaFiscal.getValorTotalDesconto()));
		icmstot.setVII("0.00");
		icmstot.setVIPI(String.valueOf(notaFiscal.getValorTotalIpi()));
		icmstot.setVPIS(String.valueOf(notaFiscal.getValorTotalPis()));
		icmstot.setVCOFINS(String.valueOf(notaFiscal.getValorTotalCofins()));
		icmstot.setVOutro(String.valueOf(notaFiscal.getValorTotalOutros()));
		icmstot.setVNF(String.valueOf(notaFiscal.getValorTotalNfe()));
		total.setICMSTot(icmstot);

		return total;
	}

	private static Transp dadosDoTransporte() {
		Transp transp = new Transp();
		transp.setModFrete(notaFiscal.getModeloFrete());
		if (!notaFiscal.getModeloFrete().equals("9")) {
			Transporta transporta = new Transporta();
			transporta
					.setCNPJ(notaFiscal.getCpfCnpjTransportadora().replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", ""));
			transporta.setXNome(notaFiscal.getNomeTransportadora());
			transporta.setIE(notaFiscal.getInscricaoEstadualTransportadora());
			transporta.setXEnder(notaFiscal.getEnderecoTransportadora());
			transporta.setXMun(notaFiscal.getCidadeTransportadora());
			transporta.setUF(TUf.valueOf(notaFiscal.getEstadoTransportadora()));
			transp.setTransporta(transporta);

			Vol vol = new Vol();
			vol.setQVol(String.valueOf(notaFiscal.getQuantidadeVolume()));
			vol.setNVol(String.valueOf(notaFiscal.getNumeroVolume()));
			vol.setPesoL(String.valueOf(notaFiscal.getPesoLiquido()));
			vol.setPesoB(String.valueOf(notaFiscal.getPesoBruto()));
			transp.getVol().add(vol);
		}
		return transp;
	}

	private static InfAdic informacoesAdicionais() {
		InfAdic infAdic = new InfAdic();
		String infoComplementar = "";
		if(notaFiscal.getNotaFiscalReferencias().size() > 0){
			for (NotaFiscalReferencia notaFiscalReferencia : notaFiscal.getNotaFiscalReferencias()) {
				if(notaFiscalReferencia.getTipoDocumento().equals("2D")){
					infoComplementar = infoComplementar + notaFiscalReferencia.toStringCupomFiscal();
				}else
				if(notaFiscalReferencia.getTipoDocumento().equals("01") || notaFiscalReferencia.getTipoDocumento().equals("04")){
					infoComplementar = infoComplementar + notaFiscalReferencia.toStringDocumentosManuais();
				}else{
					infoComplementar = infoComplementar + notaFiscalReferencia.toStringDocumentosFiscais();
				}
			}
		}
		infAdic.setInfCpl( (notaFiscal.getInformacoesComplementare() + infoComplementar).trim());
		return infAdic;
	}

	private static String strValueOf(TEnviNFe enviNFe) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(TEnviNFe.class);
		Marshaller marshaller = context.createMarshaller();
		JAXBElement<TEnviNFe> element = new ObjectFactory().createEnviNFe((enviNFe));
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		marshaller.marshal(element, sw);

		String xml = sw.toString();
		xml = xml.replaceAll("xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\" ", "");
		xml = xml.replaceAll("<NFe>", "<NFe xmlns=\"http://www.portalfiscal.inf.br/nfe\">");
		xml = xml.replaceAll(
				"<enviNFe versao=\"3.10\" xmlns=\"http://www.portalfiscal.inf.br/nfe\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\">",
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?><enviNFe versao=\"3.10\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">");

		return xml;
	}

	
	public static String lerXML(String fileXML) throws IOException {
		String linha = "";
		StringBuilder xml = new StringBuilder();

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileXML)));
		while ((linha = in.readLine()) != null) {
			xml.append(linha);
		}
		in.close();

		return xml.toString();
	}
	


}