package br.com.hsi.model.dados.text;

public enum CSTIPI {

    CSTIPI00("00","Entrada com Recuperação de Crédito"),
    CSTIPI01("01","Entrada Tributada com Alíquota Zero"),
    CSTIPI02("02","Entrada Isenta"),
    CSTIPI03("03","Entrada Não Tributada"),
    CSTIPI04("04","Entrada Imune"),
    CSTIPI05("05","Entrada com Suspensão"),
    CSTIPI49("49","Outras Entradas"),


    CSTIPI50("50","Saída Tributada"),
    CSTIPI51("51","Saída Tributável com Alíquota Zero"),
    CSTIPI52("52","Saída Isenta"),
    CSTIPI53("53","Saída Não Tributada"),
    CSTIPI54("54","Saída Imune"),
    CSTIPI55("55","Saída com Suspensão"),
    CSTIPI99("99","Outras Saídas");

    private String codigo;

    private String descircao;

    CSTIPI(String codigo, String descircao) {
        this.codigo = codigo;
        this.descircao = descircao;
    }

    public static CSTIPI[] saidaIpi() {
        int j = 0;
        CSTIPI[] cstipis = new CSTIPI[7];
        for (int i = 0; i < CSTIPI.values().length ; i++) {
            if(Integer.parseInt(CSTIPI.values()[i].getCodigo()) >= 50 ){
                cstipis[j] = CSTIPI.values()[i];
                j++;
            }
        }
        return cstipis;
    }

    public static CSTIPI[] entradaIpi() {
        int j = 0;
        CSTIPI[] cstipis = new CSTIPI[7];
        for (int i = 0; i < CSTIPI.values().length ; i++) {
            if(Integer.parseInt(CSTIPI.values()[i].getCodigo()) < 50 ){
                cstipis[j] = CSTIPI.values()[i];
                j++;
            }
        }
        return cstipis;
    }




    public String getCodigo() {
        return codigo;
    }

    public String getDescircao() {
        return descircao;
    }
}
