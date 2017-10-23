package br.com.hsi.controller;

import br.com.hsi.model.Etiqueta;
import br.com.hsi.model.Produto;
import br.com.hsi.service.GestaoProduto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.*;
import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de controle da pagina Etiqueta.xhtml
 *
 * - Imprime etiquetas para produtos cadastrados
 *
 * @author Eriel Miquilino
 */

@Named
@ViewScoped
public class EtiquetaBean implements Serializable {

    @Inject
    private GestaoProduto gestaoProduto;
    @Inject
    private HttpServletResponse response;
    @Inject
    private FacesContext facesContext;

    private List<Produto> produtos;
    private List<Produto> produtosFiltro;

    private Produto produto;

    private List<Etiqueta> etiquetas;
    private Etiqueta etiqueta;

    @PostConstruct
    public void init() {
        etiquetas = new ArrayList<>();
        produtos = gestaoProduto.listarProdutos();
        produto = new Produto();
        etiqueta = new Etiqueta();
    }

    public void carregaDadosDoProduto() {
        etiqueta.setDescricao(produto.getDescricao());
        etiqueta.setCodigo(produto.getCodigo());
        etiqueta.setCodigoBarras(produto.getCodigoBrras());
    }

    public void incluirEtiqueta() {
        etiquetas.add(etiqueta);
        etiqueta = new Etiqueta();
    }

    public void imprimirEtiquetas() throws IOException, JRException, ParseException {

        List<Etiqueta> etiquetas = new ArrayList<>();
        for(Etiqueta etiqueta: getEtiquetas()) {
            for (int i = 0; i < etiqueta.getQuantidade(); i++) {
                etiquetas.add(etiqueta);
            }
        }

        InputStream relatorioStream = getClass().getResourceAsStream("/reports/atendimento/etiqueta.jasper");
        JasperPrint print = JasperFillManager.fillReport(relatorioStream, null, new JRBeanCollectionDataSource(etiquetas));
        byte[] pdf = JasperExportManager.exportReportToPdf(print);
        FileUtils.writeByteArrayToFile(new File("/HSI/teste.pdf"), pdf);
        Exporter<ExporterInput, PdfReportConfiguration, PdfExporterConfiguration, OutputStreamExporterOutput> exportador = new JRPdfExporter();
        exportador.setExporterInput(new SimpleExporterInput(print));
        exportador.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

        response.setHeader("Content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"" + "etiquetas.pdf" + "\"");

        exportador.exportReport();
        facesContext.responseComplete();

    }

    public void removerEtiqueta(Etiqueta etiqueta) {
        etiquetas.remove(etiqueta);
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Produto> getProdutosFiltro() {
        return produtosFiltro;
    }

    public void setProdutosFiltro(List<Produto> produtosFiltro) {
        this.produtosFiltro = produtosFiltro;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Etiqueta getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(Etiqueta etiqueta) {
        this.etiqueta = etiqueta;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }


}




