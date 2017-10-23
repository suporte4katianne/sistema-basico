package br.com.hsi.model.dados.text;

/**
 * @author Eriel Miquilino
 */
public enum  StatusRemessa {

    ABERTA("0", "Aberta"),
    DISTRIBUICAO("1", "Em distribuição"),
    FINALIZADA("2", "Finalizada");

    private String codigo;
    private String descricao;

    StatusRemessa(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }


    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
