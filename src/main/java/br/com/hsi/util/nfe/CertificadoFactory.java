package br.com.hsi.util.nfe;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.util.Enumeration;


public class CertificadoFactory {

    private static KeyStore keyStoreA3;

    @SuppressWarnings("restriction")
    public static KeyStore instanceOfA3(TipoCertificado tipoCertificado, String pin) throws Exception {
        if (keyStoreA3 == null) {
            InputStream conf = getConfigCertsA3(tipoCertificado);
            Provider p = new sun.security.pkcs11.SunPKCS11(conf);
            Security.addProvider(p);
            keyStoreA3 = KeyStore.getInstance("pkcs11", p);

            try {
                keyStoreA3.load(null, pin.toCharArray());
            } catch (IOException e) {
                throw new Exception("Senha do Certificado Digital esta incorreta ou Certificado inválido.");
            }
        }
        return keyStoreA3;
    }

    private static InputStream getConfigCertsA3(TipoCertificado tipoCertificado) throws UnsupportedEncodingException {
        switch (tipoCertificado.value()) {
            case 1: return leitorGemPC_Perto();
            case 2: return tokenAladdin();
            case 3: return tokenAladdin();
            case 4: return leitorSCR3310();
            case 5: return leitorGemPC_Perto();

            default: return leitorGemPC_Perto();
        }
    }

    private static InputStream defaultConfig(String name, String library)
            throws UnsupportedEncodingException {
        StringBuilder conf = new StringBuilder();
        conf.append("name = ")
                .append(name)
                .append("\n\r")
                .append("library = ")
                .append(library)
                .append("\n\r")
                .append("showInfo = true");
        return new ByteArrayInputStream(conf.toString().getBytes("UTF-8"));
    }

    /**
     * Compatível com:
     * Cartão SafeWeb - Serasa Experian
     * Leitor SCR 3310;
     * @return
     * @throws UnsupportedEncodingException
     */
    public static InputStream leitorSCR3310() throws UnsupportedEncodingException {
        return defaultConfig("SafeWeb", "c:/windows/system32/cmp11.dll");
    }

    /**
     * Compatível com:
     * eToken Pro - Certisign
     * Token Laranja e Azul;
     * @return
     * @throws UnsupportedEncodingException
     */
    public static InputStream tokenAladdin() throws UnsupportedEncodingException {
        return defaultConfig("eToken", "c:/windows/system32/eTpkcs11.dll");
    }

    /**
     * Compatível com:
     * Cartão Certisign - Leitor GemPC Twin;
     * Cartão Serasa - Leitor Perto;
     * @return
     * @throws UnsupportedEncodingException
     */
    public static InputStream leitorGemPC_Perto() throws UnsupportedEncodingException {
        return defaultConfig("SmartCard", "c:/windows/system32/aetpkss1.dll");
    }

} 
