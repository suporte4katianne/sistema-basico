package br.com.hsi.model.dados.text;

public enum  CSTPISCOFINS {

    CSTPISCOFINS01("01","Operação Tributável com Alíquota Básica"),
    CSTPISCOFINS02("02","Operação Tributável com Alíquota Diferenciada"),
    CSTPISCOFINS03("03","Operação Tributável com Alíquota por Unidade de Medida de Produto"),
    CSTPISCOFINS04("04","Operação Tributável Monofásica - Revenda a Alíquota Zero"),
    CSTPISCOFINS05("05","Operação Tributável por Substituição Tributária"),
    CSTPISCOFINS06("06","Operação Tributável a Alíquota Zero"),
    CSTPISCOFINS07("07","Operação Isenta da Contribuição"),
    CSTPISCOFINS08("08","Operação sem Incidência da Contribuição"),
    CSTPISCOFINS09("09","Operação com Suspensão da Contribuição"),
    CSTPISCOFINS49("49","Outras Operações de Saída"),



    CSTPISCOFINS50("50","Operação com Direito a Crédito - Vinculada Exclusivamente a Receita Tributada no Mercado Interno"),
    CSTPISCOFINS51("51","Operação com Direito a Crédito - Vinculada Exclusivamente a Receita Não Tributada no Mercado Interno"),
    CSTPISCOFINS52("52","Operação com Direito a Crédito - Vinculada Exclusivamente a Receita de Exportação"),
    CSTPISCOFINS53("53","Operação com Direito a Crédito - Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno"),
    CSTPISCOFINS54("54","Operação com Direito a Crédito - Vinculada a Receitas Tributadas no Mercado Interno e de Exportação"),
    CSTPISCOFINS55("55","Operação com Direito a Crédito - Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação"),
    CSTPISCOFINS56("56","Operação com Direito a Crédito - Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação"),
    CSTPISCOFINS60("60","Crédito Presumido - Operação de Aquisição Vinculada Exclusivamente a Receita Tributada no Mercado Interno"),
    CSTPISCOFINS61("61","Crédito Presumido - Operação de Aquisição Vinculada Exclusivamente a Receita Não-Tributada no Mercado Interno"),
    CSTPISCOFINS62("62","Crédito Presumido - Operação de Aquisição Vinculada Exclusivamente a Receita de Exportação"),
    CSTPISCOFINS63("63","Crédito Presumido - Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno"),
    CSTPISCOFINS64("64","Crédito Presumido - Operação de Aquisição Vinculada a Receitas Tributadas no Mercado Interno e de Exportação"),
    CSTPISCOFINS65("65","Crédito Presumido - Operação de Aquisição Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação"),
    CSTPISCOFINS66("66","Crédito Presumido - Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação"),
    CSTPISCOFINS67("67","Crédito Presumido - Outras Operações"),
    CSTPISCOFINS70("70","Operação de Aquisição sem Direito a Crédito"),
    CSTPISCOFINS71("71","Operação de Aquisição com Isenção"),
    CSTPISCOFINS72("72","Operação de Aquisição com Suspensão"),
    CSTPISCOFINS73("73","Operação de Aquisição a Alíquota Zero"),
    CSTPISCOFINS74("74","Operação de Aquisição sem Incidência da Contribuição"),
    CSTPISCOFINS75("75","Operação de Aquisição por Substituição Tributária"),
    CSTPISCOFINS98("98","Outras Operações de Entrada"),
    CSTPISCOFINS99("99","Outras Operações");

    private String codigo;

    private String descricao;

    CSTPISCOFINS(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static CSTPISCOFINS[] saidaPisCofins() {
        int j = 0;
        CSTPISCOFINS[] cstpiscofins = new CSTPISCOFINS[10];
        for (int i = 0; i < CSTPISCOFINS.values().length ; i++) {
            if(Integer.parseInt(CSTPISCOFINS.values()[i].getCodigo()) < 50 ){
                cstpiscofins[j] = CSTPISCOFINS.values()[i];
                j++;
            }
        }
        return cstpiscofins;
    }

    public static CSTPISCOFINS[] entradaPisCofins() {
        int j = 0;
        CSTPISCOFINS[] cstpiscofins = new CSTPISCOFINS[23];
        for (int i = 0; i < CSTPISCOFINS.values().length ; i++) {
            if(Integer.parseInt(CSTPISCOFINS.values()[i].getCodigo()) >= 50 ){
                cstpiscofins[j] = CSTPISCOFINS.values()[i];
                j++;
            }
        }
        return cstpiscofins;
    }


    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
