package br.com.hsi.util.nfe;

/**
 * Selecao de tipos de Certificados Digitais.
 *
 * @author Copyright (c) 2012 Maciel Gonçalves
 *
 * Este programa é software livre, você pode redistribuí-lo e ou modificá-lo
 * sob os termos da Licença Pública Geral GNU como publicada pela Free
 * Software Foundation, tanto a versão 2 da Licença, ou (a seu critério)
 * qualquer versão posterior.
 *
 * http://www.gnu.org/licenses/gpl.txt
 *
 */
public enum TipoCertificado {

    /**
     * Certificado Modelo A1 Todos os Tipos. 
     */
    A1_TODOS (0),

    /**
     * Cartão Certisign - Leitor GemPC Twin 
     */
    A3_CERTISIGN_GEMPCTWIN (1),

    /**
     * Token Certisign Aladdin Pro (Azul) 
     */
    A3_CERTISIGN_ALADDINAZUL (2),

    /**
     * Token Certisign Aladdin (Laranja) 
     */
    A3_CERTISIGN_ALASSINLARANJA (3),

    /**
     * Cartão SafeWeb - Leitor SCR 3310 
     */
    A3_SAFEWEB_SCR3310 (4),

    /**
     * Cartão Serasa - Leitor Perto 
     */
    A3_SERASA_PERTO (5);

    private final int value;

    private TipoCertificado(int tipo) {
        this.value = tipo;
    }

    public int value() {
        return value;
    }

    public static TipoCertificado valueOf(int tipo) {
        switch (tipo) {
            case 0: return TipoCertificado.A1_TODOS;
            case 1: return TipoCertificado.A3_CERTISIGN_GEMPCTWIN;
            case 2: return TipoCertificado.A3_CERTISIGN_ALADDINAZUL;
            case 3: return TipoCertificado.A3_CERTISIGN_ALASSINLARANJA;
            case 4: return TipoCertificado.A3_SAFEWEB_SCR3310;
            case 5: return TipoCertificado.A3_SERASA_PERTO;

            default: return TipoCertificado.A1_TODOS;
        }
    }

}  
