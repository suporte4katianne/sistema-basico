package br.com.hsi.model.dados.text;

public enum CSTICMS {

    CST00("00", "Tributada integralmente"),
    CST10("10", "Tributada e com cobrança do ICMS por ST"),
    CST20("20", "ICMS com redução de Base de Cálculo"),
    CST30("30", "Isenta / não tributada e com cobrança do ICMS por ST"),
    CST40("40", "Isenta"),
    CST41("41", "Não tributada"),
    CST50("50", "ICMS Com suspensão"),
    CST51("51", "ICMS diferimento"),
    CST60("60", "ICMS cobrado anteriormente por ST"),
    CST70("70", "ICMS com redução da BC e cobrança do ICMS por ST"),
    CST90("90", "Outras"),

    CST101("101", "Tributada pelo Simples Nacional com permissão de crédito"),
    CST102("102", "Tributada pelo Simples Nacional sem permissão de crédito"),
    CST103("103", "Isenção do ICMS no Simples Nacional para faixa de receita bruta"),
    CST201("201", "Tributada pelo SN com permissão de crédito e com cobrança do ICMS por ST"),
    CST202("202", "Tributada pelo SN sem permissão de crédito e com cobrança do ICMS por ST"),
    CST203("203", "Isenção do ICMS no SN para faixa de receita bruta e com cobrança do ICMS por ST"),
    CST300("300", "Imune"),
    CST400("400", "Não tributada pelo Simples Nacional"),
    CST500("500", "ICMS cobrado anteriormente por ST (substituído) ou por antecipação"),
    CST900("900", "Outros");

    private String codigo;
    private String descricao;

    CSTICMS(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static CSTICMS[] csosns (){
        int j = 0;
        CSTICMS[] csticms = new CSTICMS[10];
        for (int i = 0; i < CSTICMS.values().length; i++) {
            if( Integer.parseInt(CSTICMS.values()[i].getCodigo()) > 90){
                csticms[j] = CSTICMS.values()[i];
                j++;
            }
        }
        return csticms;
    }

    public static CSTICMS[] csts() {
        int j = 0;
        CSTICMS[] csticms = new CSTICMS[11];
        for (int i = 0; i < CSTICMS.values().length; i++) {
            if( Integer.parseInt(CSTICMS.values()[i].getCodigo()) <= 90){
                csticms[j] = CSTICMS.values()[i];
                j++;
            }
        }
        return csticms;
    }


    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }



}

