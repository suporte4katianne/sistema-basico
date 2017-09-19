package br.com.hsi.model.dados.text;

public enum Origem {

    ORIGEM0("0", "Nacional, exceto as indicadas nos códigos 3 a 5"),
    ORIGEM1("1", "Estrangeira - Importação direta, exceto a indicada no código 6"),
    ORIGEM2("2", "Estrangeira - Adquirida no mercado interno, exceto a indicada no código 7"),
    ORIGEM3("3", "Nacional, mercadoria ou bem com Conteúdo de Importação superior a 40%"),
    ORIGEM4("4", "Nacional, cuja produção tenha sido feita em conformidade com os processos produtivos básicos"),
    ORIGEM5("5", "Nacional, mercadoria ou bem com Conteúdo de Importação inferior ou igual a 40%"),
    ORIGEM6("6", "Estrangeira - Importação direta, sem similar nacional, constante em lista de Resolução CAMEX"),
    ORIGEM7("7", "Estrangeira - Adquirida no mercado interno, sem similar nacional"),
    ORIGEM8("8", "Nacional, mercadoria ou bem com Conteúdo de Importação superior a 70%");

    private String codigo;
    private String descricao;

    Origem(String codigo, String descricao) {
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