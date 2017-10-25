package br.com.hsi.controller;

import br.com.hsi.model.Entidade;
import br.com.hsi.model.Remessa;
import br.com.hsi.model.RemessaItem;
import br.com.hsi.model.VendedorItem;
import br.com.hsi.model.dados.text.StatusRemessa;
import br.com.hsi.security.Seguranca;
import br.com.hsi.service.GestaoEntidade;
import br.com.hsi.service.GestaoRemessa;
import br.com.hsi.util.exception.NegocioException;
import br.com.hsi.util.jsf.FacesUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe de controle para pagina Remessa.xhtml
 *
 * - {@link java.util.List}<{@link br.com.hsi.model.Remessa}></> cadastradas
 *
 * - Exclui {@link br.com.hsi.model.Remessa}
 *
 * @author Eriel Miquilino
 */

@Named
@ViewScoped
public class RemessaBean implements Serializable {

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    @Inject
    private GestaoRemessa gestaoRemessa;
    @Inject
    private GestaoEntidade gestaoEntidade;
    @Inject
    private Seguranca seguranca;

    private List<Remessa> remessas;
    private List<Remessa> remessasFiltro;
    private Remessa remessa;

    @PostConstruct
    public void init() {
        remessas = gestaoRemessa.listarRemessas(null);
    }

    public void excluir() {
        gestaoRemessa.excluir(remessa);
        FacesUtil.addInfoMessage("Registro removido com sucesso!");
    }

    public void autorizarSaida() {
        try {
            remover(new File("/HSI/temp/"));

            List<Entidade> vendedores = gestaoEntidade.entidadesPorPraca(remessa.getPraca());

            List<RemessaItem> remessaItens = gestaoRemessa.remessaItemPorRemessa(remessa);

            for (Entidade vendedor: vendedores ) {
                geraRelatorioProdutosPorVendedor(vendedor, vendedorItensConverter(remessaItens));
            }

            concatReports();

            remessa.setStatusRemessa(StatusRemessa.DISTRIBUICAO);
            gestaoRemessa.salvar(remessa);
        } catch (NegocioException | JRException | IOException e) {
            e.printStackTrace();
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }

    private List<VendedorItem> vendedorItensConverter (List<RemessaItem> remessaItens) {
        List<VendedorItem> vendedorItens = new ArrayList<>();
        for (RemessaItem remessaItem: remessaItens) {
            VendedorItem vendedorItem = new VendedorItem();
            vendedorItem.setCodigo(remessaItem.getProduto().getCodigo());
            vendedorItem.setDescricao(remessaItem.getDescricao());
            vendedorItem.setPreco(remessaItem.getPrecoUnitario());
            vendedorItens.add(vendedorItem);
        }
        return vendedorItens;
    }

    private File geraRelatorioProdutosPorVendedor(Entidade entidade, List<VendedorItem> vendedorItens) throws JRException, IOException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("nome", entidade.getNome());
        parametros.put("cpf", entidade.getCpfCnpj());
        parametros.put("endereco", entidade.getRua() + " Nº " +entidade.getNumero());
        parametros.put("telefones", entidade.getTelefone() + " " + entidade.getCelular());
        if(entidade.getCidade() != null) {
            parametros.put("cidade", entidade.getCidade().getCidade());
        }
        parametros.put("bairro", entidade.getBairro());
        parametros.put("observacao", entidade.getObservacao());
        parametros.put("representante", remessa.getRepresentante().getNome());
        parametros.put("telefone_representante", remessa.getRepresentante().getTelefone());
        parametros.put("logo", seguranca.getUsuarioLogado().getUsuario().getEmpresa().getLogo());

        InputStream relatorioStream = getClass().getResourceAsStream("/reports/hawker/produtos_por_vendedor_referencia.jasper");
        JasperPrint print = JasperFillManager.fillReport(relatorioStream, parametros, new JRBeanCollectionDataSource(vendedorItens));
        byte[] pdf = JasperExportManager.exportReportToPdf(print);
        File report = new File("/HSI/temp/" + "temp" + System.currentTimeMillis() + ".pdf");
        FileUtils.writeByteArrayToFile(report, pdf);
        return report;
    }

    /**
     * Concatena todos os arquivos PDF encontrados na pasta /HSI/temp/
     */
    private void concatReports() {
        File file = new File("/HSI/temp/");
        int files = file.listFiles().length;
        try {
            PDFMergerUtility ut = new PDFMergerUtility();
            for (int i = 0; i < files; i++) {
                ut.addSource(file.listFiles()[i]);
            }
            ut.setDestinationFileName("/HSI/temp/concat.pdf");
            ut.mergeDocuments(null);
            downloadPDF(new File("/HSI/temp/concat.pdf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre um arquivo PDF em uma nova aba do navegador
     * @param file - {@link File}: Arquivo que será exibido em outra aba de navegação
     * @throws IOException
     */

    private void downloadPDF(File file) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance ();
        ExternalContext externalContext = facesContext.getExternalContext ();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse ();

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
        } finally {
            close(output);
            close(input);
        }
        facesContext.responseComplete();
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Remove todos os arquivos de um diretório
     * @param f - {@link File}: Diretório onde ação será executada
     */

    private void remover (File f) {
        if (f.isDirectory()) {
            File[] sun = f.listFiles();
            for (File toDelete : sun) {
                toDelete.delete();
            }
        }
    }

    public void imprimeDadosEmBranco() {
        try {
            remover(new File("/HSI/temp/"));

            List<RemessaItem> remessaItens = gestaoRemessa.remessaItemPorRemessa(remessa);

            List<VendedorItem> vendedorItens = vendedorItensConverter(remessaItens);
            File report = geraRelatorioProdutosPorVendedor(new Entidade(), vendedorItens);

            downloadPDF(report);
        } catch (JRException | IOException e) {
            e.printStackTrace();
        }
    }

    public void segundo() {

    }


    public List<Remessa> getRemessas() {
        return remessas;
    }

    public void setRemessas(List<Remessa> remessas) {
        this.remessas = remessas;
    }

    public List<Remessa> getRemessasFiltro() {
        return remessasFiltro;
    }

    public void setRemessasFiltro(List<Remessa> remessasFiltro) {
        this.remessasFiltro = remessasFiltro;
    }

    public Remessa getRemessa() {
        return remessa;
    }

    public void setRemessa(Remessa remessa) {
        this.remessa = remessa;
    }
}
