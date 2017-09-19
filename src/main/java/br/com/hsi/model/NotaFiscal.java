package br.com.hsi.model;

import br.com.hsi.model.dados.Cfop;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "chave_acesso", length = 60)
	private String chaveAcesso;
	
	@Column(name = "cod_estado_empresa", length = 10)
    private String codIbgeEstadoEmpresa;
	
	@Column(name = "sequencia_chave")
    private String numeroSequenciaAleatorio;
	
	@ManyToOne
	@JoinColumn(name = "id_cfop")
	private Cfop cfop;
	
	@Column(name = "natureza_operacao", length = 150)
    private String nomeNatureza;

	@Column(name = "condicao_pagamento")
    private int condicaoPagamento;
	
	@Column(name = "data_emissao")
	private Date dataHoraEmissao;
	
	@Column(name = "data_saida_entrada")
	private Date dataHoraSaidaEntrada;
	
	@Column(name = "operacao", length = 1)
	private String operacao = "1"; // forma de operacao 1 - Op Interna, 2 - Op Interestadual, 3 - Op com o exterior
	
	@Column(name = "tipo_nfe", length = 1)
	private String tipoNf; //0 Entrada 1 Saida
	
	@Column(name = "cod_cidade_empresa", length = 20)
	private String codIbgeCidadeEmpresa;
	
	@Column(name = "tipo_emissao")
	private int tipoEmissao;     // 1 - Emissão normal (não em contingência);
	
	@Column(name = "digito_verificador")
	private int digitoVerificadorChaveAcesso;
	
	@Column(name = "ambiente")
	private int ambiente; //1 - Producao 2 - Homologação
	
	@Column(name = "finalidade")
	private int finalidadeNfe; //1 - NF-e normal, 2 - NF-e complementar, 3 - NF-e de ajuste, 4 - Devolução ;
	
	@Column(name = "tipo_consumidor")
	private int tipoConsumidor; // 0 - Não Consumidor Final  1 - Consumidor final;
	
	@Column(name = "presenca_consumidor")
	private int presencaConsumidor; //0 - Não se aplica (por exemplo, Nota Fiscal complementar ou de ajuste) 1 - Operação presencial 2 - Operação não presencial, pela Internet 3 - Operação não presencial, Teleatendimento 4 - NFC-e em operação com entrega a domicílio 9 - Operação não presencial, outros.

	private boolean emissao;

	@Column(name = "versao", length = 5)
	private String versaoXml; //vesrão 3.10
	
	@Column(name = "retorno")
	private String mensagemRetorno;
	
	private String status;
	
	@Column(name = "protocolo", length = 100)
	private String protocoloAutorizacao;

	
	
	//Numeracao
	
	@Column(name = "modelo", length = 2)
    private String modeloNota; // 55 Nota Eletronica ou - 01 Nota Manual
	
	@Column(name = "serie")
    private int serieNota;
	
	@Column(name = "numero")
    private int numeroNota;
	
	
    
    //Empresa;
    
	@ManyToOne
	@JoinColumn(name = "id_empresa")
    private Empresa empresa;
	
    @Column(name = "cnpj_empresa")
    private String cnpjEmpresa;
    
    @Column(name = "razao_social_empresa")
    private String razaoSocialEmpresa;
    
    @Column(name = "nome_fantasia_empresa")
    private String nomeFantasiaEmpresa;
    
    @Column(name = "rua_empresa")
    private String ruaEmpresa;
    
    @Column(name = "complemento_empresa")
    private String complementoEmpresa;
    
    @Column(name = "numero_empresa")
    private String numeroEmpresa; 
    
    @Column(name = "bairro_empresa")
    private String bairroEmpresa;
    
    @Column(name = "cidade_empresa")
    private String NomeCidadeEmpresa;
    
    @Column(name = "estado_empresa")
    private String estadoEmpresa;
    
    @Column(name = "cep_empresa")
    private String cepEmpresa; 
    
    @Column(name = "cod_pais_empresa")
    private String codigoPaisEmpresa;
    
    @Column(name = "pais_empresa")
    private String nomePaisEmpresa; 
    
    @Column(name = "telefone_empresa")
    private String telefoneEmpresa;
    
    @Column(name = "email_empresa")
    private String emailEmpresa;
    
    @Column(name = "ie_empresa")
    private String inscricaoEstadualEmpresa;
    
    @Column(name = "enquadramento_empresa")
    private String enquadramentoFiscalEmpresa;
    
    @Column(name = "caminho_certificado")
    private String caminhoCertificado;
    
    @Column(name = "senha_certificado")
    private String SenhaCertificado;
    
    @Column(name = "credito_icms")
    private String creditoIcms;
    
    //Entidade;
    
	@ManyToOne
	@JoinColumn(name = "id_entidade")
    private Entidade entidade;
	
    @Column(name = "cpf_cnpj_entidade")
    private String cpfCnpjEntidade;
    
    @Column(name = "nome_entidade")
    private String nomeEntidade; 
    
    @Column(name = "tipo_contribuinte_entidade")
    private int tipoContribuinte; //1 - 2 ou 9
    
    @Column(name = "tipo_cadastro")
    private String tipoCadastro;
    
    @Column(name = "ie_entidade")
    private String inscricaoEstadualEntidade;
    
    @Column(name = "rua_entidade")
    private String ruaEntidade; 
    
    @Column(name = "complemento_entidade")
    private String complementoEntidade;
    
    @Column(name = "numero_entidade")
    private String numeroEntidade; 
    
    @Column(name = "bairro_entidade")
    private String bairroEntidade;
    
    @Column(name = "cod_cidade_entidade")
    private String codIbgeCidadeEntidade;
    
    @Column(name = "cidade_entidade")
    private String nomeCidadeEntidade;
    
    @Column(name = "estado_entidade")
    private String estadoEntidade; 
    
    @Column(name = "cep_entidade")
    private String cepEntidade;
    
    @Column(name = "cod_pais_entidade")
    private String codPaisEntidade;
    
    @Column(name = "pais_entidade")
    private String nomePaisEntidade;
    
    @Column(name = "telefone_entidade")
    private String telefoneEntidade;
    
    @Column(name = "email_entidade")
    private String emailEntidade; 
    

    
    //Transportadora
    
	@ManyToOne
	@JoinColumn(name = "id_transportadora")
	private Entidade transportadora;
	
    @Column(name = "modelo_frete")
    private String modeloFrete;
    
    @Column(name = "tipo_cadastro_transportadora")
    private String tipoCadastroTransportadora;
    
    @Column(name = "cpfCnpj_transportadora")
    private String cpfCnpjTransportadora;
    
    @Column(name = "transportadora")
    private String nomeTransportadora;
    
    @Column(name = "ie_transportadora")
    private String inscricaoEstadualTransportadora;
    
    @Column(name = "endereco_transportadora")
    private String enderecoTransportadora;
    
    @Column(name = "cidade_transportadora")
    private String cidadeTransportadora;
    
    @Column(name = "estado_transportadora")
    private String estadoTransportadora;       
 
    // Carga
    
    @Column(name = "quantidade_volume")
    private BigDecimal quantidadeVolume;
    
    @Column(name = "numero_volume")
    private BigDecimal numeroVolume;
    
    @Column(name = "peso_liquido")
    private BigDecimal pesoLiquido;
    
    @Column(name = "peso_bruto")
    private BigDecimal pesoBruto;
    
    
    //Valores Totais
 
    @Column(name = "icms_deson")
    private BigDecimal valorTotalIcmsDeson = new BigDecimal("0.0");
    
    @Column(name = "icms")
    private BigDecimal valorTotalIcms = new BigDecimal("0.0");  
    
    @Column(name = "base_icms")
    private BigDecimal valorTotalBaseIcms = new BigDecimal("0.0");
    
    @Column(name = "icms_st")
    private BigDecimal valorTotalIcmsS = new BigDecimal("0.0");
    
    @Column(name = "base_icms_st")
    private BigDecimal valorTotalBaseIcmsSt = new BigDecimal("0.0");
    
    @Column(name = "total_produtos")
    private BigDecimal valorTotalProdutos = new BigDecimal("0.0");
    
    @Column(name = "total_frete")
    private BigDecimal valorTotalFrete = new BigDecimal("0.0");
    
    @Column(name = "total_seguro")
    private BigDecimal valorTotalSeguro = new BigDecimal("0.0");
    
    @Column(name = "total_desconto")
    private BigDecimal valorTotalDesconto = new BigDecimal("0.0");
    
    @Column(name = "imposto_aproximado")
    private BigDecimal valorTotalImpostosAproximados = new BigDecimal("0.0");
    
    @Column(name = "total_ipi")
    private BigDecimal valorTotalIpi = new BigDecimal("0.0");
    
    @Column(name = "total_pis")
    private BigDecimal valorTotalPis = new BigDecimal("0.0");
    
    @Column(name = "total_cofins")
    private BigDecimal valorTotalCofins = new BigDecimal("0.0");
    
    @Column(name = "total_outros")
    private BigDecimal valorTotalOutros = new BigDecimal("0.0");
    
    @Column(name = "total_nfe")
    private BigDecimal valorTotalNfe = new BigDecimal("0.0");
    
    // Informações Complementares
   
    @Column(name = "dados_complementares", columnDefinition = "TEXT")
    private String informacoesComplementare;
    
    // Produtos
    
    @OneToMany(mappedBy = "notaFiscal", cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<NotaFiscalItem> notaFiscalItens = new ArrayList<>();
    
    //Documento Referenciado
    
    @OneToMany(mappedBy = "nfe", cascade = {CascadeType.ALL}, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
    private List<NotaFiscalReferencia> notaFiscalReferencias = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChaveAcesso() {
		return chaveAcesso;
	}

	public void setChaveAcesso(String chaveAcesso) {
		this.chaveAcesso = chaveAcesso;
	}

	public String getCodIbgeEstadoEmpresa() {
		return codIbgeEstadoEmpresa;
	}

	public void setCodIbgeEstadoEmpresa(String codIbgeEstadoEmpresa) {
		this.codIbgeEstadoEmpresa = codIbgeEstadoEmpresa;
	}

	public String getNumeroSequenciaAleatorio() {
		return numeroSequenciaAleatorio;
	}

	public void setNumeroSequenciaAleatorio(String numeroSequenciaAleatorio) {
		this.numeroSequenciaAleatorio = numeroSequenciaAleatorio;
	}

	public Cfop getCfop() {
		return cfop;
	}

	public void setCfop(Cfop cfop) {
		this.cfop = cfop;
	}

	public String getNomeNatureza() {
		return nomeNatureza;
	}

	public void setNomeNatureza(String nomeNatureza) {
		this.nomeNatureza = nomeNatureza;
	}

	public int getCondicaoPagamento() {
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(int condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}

	public Date getDataHoraEmissao() {
		return dataHoraEmissao;
	}

	public void setDataHoraEmissao(Date dataHoraEmissao) {
		this.dataHoraEmissao = dataHoraEmissao;
	}

	public Date getDataHoraSaidaEntrada() {
		return dataHoraSaidaEntrada;
	}

	public void setDataHoraSaidaEntrada(Date dataHoraSaidaEntrada) {
		this.dataHoraSaidaEntrada = dataHoraSaidaEntrada;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getTipoNf() {
		return tipoNf;
	}

	public void setTipoNf(String tipoNf) {
		this.tipoNf = tipoNf;
	}

	public String getCodIbgeCidadeEmpresa() {
		return codIbgeCidadeEmpresa;
	}

	public void setCodIbgeCidadeEmpresa(String codIbgeCidadeEmpresa) {
		this.codIbgeCidadeEmpresa = codIbgeCidadeEmpresa;
	}

	public int getTipoEmissao() {
		return tipoEmissao;
	}

	public void setTipoEmissao(int tipoEmissao) {
		this.tipoEmissao = tipoEmissao;
	}

	public int getDigitoVerificadorChaveAcesso() {
		return digitoVerificadorChaveAcesso;
	}

	public void setDigitoVerificadorChaveAcesso(int digitoVerificadorChaveAcesso) {
		this.digitoVerificadorChaveAcesso = digitoVerificadorChaveAcesso;
	}

	public int getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(int ambiente) {
		this.ambiente = ambiente;
	}

	public int getFinalidadeNfe() {
		return finalidadeNfe;
	}

	public void setFinalidadeNfe(int finalidadeNfe) {
		this.finalidadeNfe = finalidadeNfe;
	}

	public int getTipoConsumidor() {
		return tipoConsumidor;
	}

	public void setTipoConsumidor(int tipoConsumidor) {
		this.tipoConsumidor = tipoConsumidor;
	}

	public int getPresencaConsumidor() {
		return presencaConsumidor;
	}

	public void setPresencaConsumidor(int presencaConsumidor) {
		this.presencaConsumidor = presencaConsumidor;
	}

	public String getVersaoXml() {
		return versaoXml;
	}

	public void setVersaoXml(String versaoXml) {
		this.versaoXml = versaoXml;
	}

	public String getMensagemRetorno() {
		return mensagemRetorno;
	}

	public void setMensagemRetorno(String mensagemRetorno) {
		this.mensagemRetorno = mensagemRetorno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProtocoloAutorizacao() {
		return protocoloAutorizacao;
	}

	public void setProtocoloAutorizacao(String protocoloAutorizacao) {
		this.protocoloAutorizacao = protocoloAutorizacao;
	}

	public String getModeloNota() {
		return modeloNota;
	}

	public void setModeloNota(String modeloNota) {
		this.modeloNota = modeloNota;
	}

	public int getSerieNota() {
		return serieNota;
	}

	public void setSerieNota(int serieNota) {
		this.serieNota = serieNota;
	}

	public int getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(int numeroNota) {
		this.numeroNota = numeroNota;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getCnpjEmpresa() {
		return cnpjEmpresa;
	}

	public void setCnpjEmpresa(String cnpjEmpresa) {
		this.cnpjEmpresa = cnpjEmpresa;
	}

	public String getRazaoSocialEmpresa() {
		return razaoSocialEmpresa;
	}

	public void setRazaoSocialEmpresa(String razaoSocialEmpresa) {
		this.razaoSocialEmpresa = razaoSocialEmpresa;
	}

	public String getNomeFantasiaEmpresa() {
		return nomeFantasiaEmpresa;
	}

	public void setNomeFantasiaEmpresa(String nomeFantasiaEmpresa) {
		this.nomeFantasiaEmpresa = nomeFantasiaEmpresa;
	}

	public String getRuaEmpresa() {
		return ruaEmpresa;
	}

	public void setRuaEmpresa(String ruaEmpresa) {
		this.ruaEmpresa = ruaEmpresa;
	}

	public String getComplementoEmpresa() {
		return complementoEmpresa;
	}

	public void setComplementoEmpresa(String complementoEmpresa) {
		this.complementoEmpresa = complementoEmpresa;
	}

	public String getNumeroEmpresa() {
		return numeroEmpresa;
	}

	public void setNumeroEmpresa(String numeroEmpresa) {
		this.numeroEmpresa = numeroEmpresa;
	}

	public String getBairroEmpresa() {
		return bairroEmpresa;
	}

	public void setBairroEmpresa(String bairroEmpresa) {
		this.bairroEmpresa = bairroEmpresa;
	}

	public String getNomeCidadeEmpresa() {
		return NomeCidadeEmpresa;
	}

	public void setNomeCidadeEmpresa(String nomeCidadeEmpresa) {
		NomeCidadeEmpresa = nomeCidadeEmpresa;
	}

	public String getEstadoEmpresa() {
		return estadoEmpresa;
	}

	public void setEstadoEmpresa(String estadoEmpresa) {
		this.estadoEmpresa = estadoEmpresa;
	}

	public String getCepEmpresa() {
		return cepEmpresa;
	}

	public void setCepEmpresa(String cepEmpresa) {
		this.cepEmpresa = cepEmpresa;
	}

	public String getCodigoPaisEmpresa() {
		return codigoPaisEmpresa;
	}

	public void setCodigoPaisEmpresa(String codigoPaisEmpresa) {
		this.codigoPaisEmpresa = codigoPaisEmpresa;
	}

	public String getNomePaisEmpresa() {
		return nomePaisEmpresa;
	}

	public void setNomePaisEmpresa(String nomePaisEmpresa) {
		this.nomePaisEmpresa = nomePaisEmpresa;
	}

	public String getTelefoneEmpresa() {
		return telefoneEmpresa;
	}

	public void setTelefoneEmpresa(String telefoneEmpresa) {
		this.telefoneEmpresa = telefoneEmpresa;
	}

	public String getEmailEmpresa() {
		return emailEmpresa;
	}

	public void setEmailEmpresa(String emailEmpresa) {
		this.emailEmpresa = emailEmpresa;
	}

	public String getInscricaoEstadualEmpresa() {
		return inscricaoEstadualEmpresa;
	}

	public void setInscricaoEstadualEmpresa(String inscricaoEstadualEmpresa) {
		this.inscricaoEstadualEmpresa = inscricaoEstadualEmpresa;
	}

	public String getEnquadramentoFiscalEmpresa() {
		return enquadramentoFiscalEmpresa;
	}

	public void setEnquadramentoFiscalEmpresa(String enquadramentoFiscalEmpresa) {
		this.enquadramentoFiscalEmpresa = enquadramentoFiscalEmpresa;
	}

	public String getCaminhoCertificado() {
		return caminhoCertificado;
	}

	public void setCaminhoCertificado(String caminhoCertificado) {
		this.caminhoCertificado = caminhoCertificado;
	}

	public String getSenhaCertificado() {
		return SenhaCertificado;
	}

	public void setSenhaCertificado(String senhaCertificado) {
		SenhaCertificado = senhaCertificado;
	}

	public String getCreditoIcms() {
		return creditoIcms;
	}

	public void setCreditoIcms(String creditoIcms) {
		this.creditoIcms = creditoIcms;
	}

	public Entidade getEntidade() {
		return entidade;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	public String getCpfCnpjEntidade() {
		return cpfCnpjEntidade;
	}

	public void setCpfCnpjEntidade(String cpfCnpjEntidade) {
		this.cpfCnpjEntidade = cpfCnpjEntidade;
	}

	public String getNomeEntidade() {
		return nomeEntidade;
	}

	public void setNomeEntidade(String nomeEntidade) {
		this.nomeEntidade = nomeEntidade;
	}

	public int getTipoContribuinte() {
		return tipoContribuinte;
	}

	public void setTipoContribuinte(int tipoContribuinte) {
		this.tipoContribuinte = tipoContribuinte;
	}

	public String getTipoCadastro() {
		return tipoCadastro;
	}

	public void setTipoCadastro(String tipoCadastro) {
		this.tipoCadastro = tipoCadastro;
	}

	public String getInscricaoEstadualEntidade() {
		return inscricaoEstadualEntidade;
	}

	public void setInscricaoEstadualEntidade(String inscricaoEstadualEntidade) {
		this.inscricaoEstadualEntidade = inscricaoEstadualEntidade;
	}

	public String getRuaEntidade() {
		return ruaEntidade;
	}

	public void setRuaEntidade(String ruaEntidade) {
		this.ruaEntidade = ruaEntidade;
	}

	public String getComplementoEntidade() {
		return complementoEntidade;
	}

	public void setComplementoEntidade(String complementoEntidade) {
		this.complementoEntidade = complementoEntidade;
	}

	public String getNumeroEntidade() {
		return numeroEntidade;
	}

	public void setNumeroEntidade(String numeroEntidade) {
		this.numeroEntidade = numeroEntidade;
	}

	public String getBairroEntidade() {
		return bairroEntidade;
	}

	public void setBairroEntidade(String bairroEntidade) {
		this.bairroEntidade = bairroEntidade;
	}

	public String getCodIbgeCidadeEntidade() {
		return codIbgeCidadeEntidade;
	}

	public void setCodIbgeCidadeEntidade(String codIbgeCidadeEntidade) {
		this.codIbgeCidadeEntidade = codIbgeCidadeEntidade;
	}

	public String getNomeCidadeEntidade() {
		return nomeCidadeEntidade;
	}

	public void setNomeCidadeEntidade(String nomeCidadeEntidade) {
		this.nomeCidadeEntidade = nomeCidadeEntidade;
	}

	public String getEstadoEntidade() {
		return estadoEntidade;
	}

	public void setEstadoEntidade(String estadoEntidade) {
		this.estadoEntidade = estadoEntidade;
	}

	public String getCepEntidade() {
		return cepEntidade;
	}

	public void setCepEntidade(String cepEntidade) {
		this.cepEntidade = cepEntidade;
	}

	public String getCodPaisEntidade() {
		return codPaisEntidade;
	}

	public void setCodPaisEntidade(String codPaisEntidade) {
		this.codPaisEntidade = codPaisEntidade;
	}

	public String getNomePaisEntidade() {
		return nomePaisEntidade;
	}

	public void setNomePaisEntidade(String nomePaisEntidade) {
		this.nomePaisEntidade = nomePaisEntidade;
	}

	public String getTelefoneEntidade() {
		return telefoneEntidade;
	}

	public void setTelefoneEntidade(String telefoneEntidade) {
		this.telefoneEntidade = telefoneEntidade;
	}

	public String getEmailEntidade() {
		return emailEntidade;
	}

	public void setEmailEntidade(String emailEntidade) {
		this.emailEntidade = emailEntidade;
	}

	public Entidade getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Entidade transportadora) {
		this.transportadora = transportadora;
	}

	public String getModeloFrete() {
		return modeloFrete;
	}

	public void setModeloFrete(String modeloFrete) {
		this.modeloFrete = modeloFrete;
	}

	public String getTipoCadastroTransportadora() {
		return tipoCadastroTransportadora;
	}

	public void setTipoCadastroTransportadora(String tipoCadastroTransportadora) {
		this.tipoCadastroTransportadora = tipoCadastroTransportadora;
	}

	public String getCpfCnpjTransportadora() {
		return cpfCnpjTransportadora;
	}

	public void setCpfCnpjTransportadora(String cpfCnpjTransportadora) {
		this.cpfCnpjTransportadora = cpfCnpjTransportadora;
	}

	public String getNomeTransportadora() {
		return nomeTransportadora;
	}

	public void setNomeTransportadora(String nomeTransportadora) {
		this.nomeTransportadora = nomeTransportadora;
	}

	public String getInscricaoEstadualTransportadora() {
		return inscricaoEstadualTransportadora;
	}

	public void setInscricaoEstadualTransportadora(String inscricaoEstadualTransportadora) {
		this.inscricaoEstadualTransportadora = inscricaoEstadualTransportadora;
	}

	public String getEnderecoTransportadora() {
		return enderecoTransportadora;
	}

	public void setEnderecoTransportadora(String enderecoTransportadora) {
		this.enderecoTransportadora = enderecoTransportadora;
	}

	public String getCidadeTransportadora() {
		return cidadeTransportadora;
	}

	public void setCidadeTransportadora(String cidadeTransportadora) {
		this.cidadeTransportadora = cidadeTransportadora;
	}

	public String getEstadoTransportadora() {
		return estadoTransportadora;
	}

	public void setEstadoTransportadora(String estadoTransportadora) {
		this.estadoTransportadora = estadoTransportadora;
	}

	public BigDecimal getQuantidadeVolume() {
		return quantidadeVolume;
	}

	public void setQuantidadeVolume(BigDecimal quantidadeVolume) {
		this.quantidadeVolume = quantidadeVolume;
	}

	public BigDecimal getNumeroVolume() {
		return numeroVolume;
	}

	public void setNumeroVolume(BigDecimal numeroVolume) {
		this.numeroVolume = numeroVolume;
	}

	public BigDecimal getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(BigDecimal pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public BigDecimal getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public BigDecimal getValorTotalIcmsDeson() {
		return valorTotalIcmsDeson;
	}

	public void setValorTotalIcmsDeson(BigDecimal valorTotalIcmsDeson) {
		this.valorTotalIcmsDeson = valorTotalIcmsDeson;
	}

	public BigDecimal getValorTotalIcms() {
		return valorTotalIcms;
	}

	public void setValorTotalIcms(BigDecimal valorTotalIcms) {
		this.valorTotalIcms = valorTotalIcms;
	}

	public BigDecimal getValorTotalBaseIcms() {
		return valorTotalBaseIcms;
	}

	public void setValorTotalBaseIcms(BigDecimal valorTotalBaseIcms) {
		this.valorTotalBaseIcms = valorTotalBaseIcms;
	}

	public BigDecimal getValorTotalIcmsS() {
		return valorTotalIcmsS;
	}

	public void setValorTotalIcmsS(BigDecimal valorTotalIcmsS) {
		this.valorTotalIcmsS = valorTotalIcmsS;
	}

	public BigDecimal getValorTotalBaseIcmsSt() {
		return valorTotalBaseIcmsSt;
	}

	public void setValorTotalBaseIcmsSt(BigDecimal valorTotalBaseIcmsSt) {
		this.valorTotalBaseIcmsSt = valorTotalBaseIcmsSt;
	}

	public BigDecimal getValorTotalProdutos() {
		return valorTotalProdutos;
	}

	public void setValorTotalProdutos(BigDecimal valorTotalProdutos) {
		this.valorTotalProdutos = valorTotalProdutos;
	}

	public BigDecimal getValorTotalFrete() {
		return valorTotalFrete;
	}

	public void setValorTotalFrete(BigDecimal valorTotalFrete) {
		this.valorTotalFrete = valorTotalFrete;
	}

	public BigDecimal getValorTotalSeguro() {
		return valorTotalSeguro;
	}

	public void setValorTotalSeguro(BigDecimal valorTotalSeguro) {
		this.valorTotalSeguro = valorTotalSeguro;
	}

	public BigDecimal getValorTotalDesconto() {
		return valorTotalDesconto;
	}

	public void setValorTotalDesconto(BigDecimal valorTotalDesconto) {
		this.valorTotalDesconto = valorTotalDesconto;
	}

	public BigDecimal getValorTotalImpostosAproximados() {
		return valorTotalImpostosAproximados;
	}

	public void setValorTotalImpostosAproximados(BigDecimal valorTotalImpostosAproximados) {
		this.valorTotalImpostosAproximados = valorTotalImpostosAproximados;
	}

	public BigDecimal getValorTotalIpi() {
		return valorTotalIpi;
	}

	public void setValorTotalIpi(BigDecimal valorTotalIpi) {
		this.valorTotalIpi = valorTotalIpi;
	}

	public BigDecimal getValorTotalPis() {
		return valorTotalPis;
	}

	public void setValorTotalPis(BigDecimal valorTotalPis) {
		this.valorTotalPis = valorTotalPis;
	}

	public BigDecimal getValorTotalCofins() {
		return valorTotalCofins;
	}

	public void setValorTotalCofins(BigDecimal valorTotalCofins) {
		this.valorTotalCofins = valorTotalCofins;
	}

	public BigDecimal getValorTotalOutros() {
		return valorTotalOutros;
	}

	public void setValorTotalOutros(BigDecimal valorTotalOutros) {
		this.valorTotalOutros = valorTotalOutros;
	}

	public BigDecimal getValorTotalNfe() {
		return valorTotalNfe;
	}

	public void setValorTotalNfe(BigDecimal valorTotalNfe) {
		this.valorTotalNfe = valorTotalNfe;
	}

	public String getInformacoesComplementare() {
		return informacoesComplementare;
	}

	public void setInformacoesComplementare(String informacoesComplementare) {
		this.informacoesComplementare = informacoesComplementare;
	}

	public List<NotaFiscalItem> getNotaFiscalItens() {
		return notaFiscalItens;
	}

	public void setNotaFiscalItens(List<NotaFiscalItem> notaFiscalItens) {
		this.notaFiscalItens = notaFiscalItens;
	}

	public List<NotaFiscalReferencia> getNotaFiscalReferencias() {
		return notaFiscalReferencias;
	}

	public void setNotaFiscalReferencias(List<NotaFiscalReferencia> notaFiscalReferencias) {
		this.notaFiscalReferencias = notaFiscalReferencias;
	}

	public boolean isEmissao() {
		return emissao;
	}

	public void setEmissao(boolean emissao) {
		this.emissao = emissao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotaFiscal other = (NotaFiscal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}
