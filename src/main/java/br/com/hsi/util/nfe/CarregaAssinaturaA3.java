package br.com.hsi.util.nfe;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Enumeration;

public class CarregaAssinaturaA3 {
    private static CarregaAssinaturaA3 instance;

    private KeyStore.PrivateKeyEntry privateKeyEntry;
    private PrivateKey privateKey;
    private KeyStore ks;

    public CarregaAssinaturaA3() {
    }

    public static synchronized CarregaAssinaturaA3 getInstance(){
        if(instance == null){
            instance = new CarregaAssinaturaA3();
        }
        return instance;
    }

    public void carregaAssinatura(String senha) throws Exception {
        KeyStore ks = CertificadoFactory.instanceOfA3(TipoCertificado.A3_SERASA_PERTO, senha);

        Enumeration<String> aliasesEnum = ks.aliases();
        while (aliasesEnum.hasMoreElements()) {
            String alias = aliasesEnum.nextElement();
            if (ks.isKeyEntry(alias)) {
                if(privateKeyEntry == null || privateKeyEntry.equals(null)){
                    System.out.println(alias);
                    privateKeyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias, new KeyStore.PasswordProtection(senha.toCharArray()));
                    privateKey = privateKeyEntry.getPrivateKey();
                    break;
                }
            }
        }
    }


    public KeyStore.PrivateKeyEntry getPrivateKeyEntry() {
        return privateKeyEntry;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public KeyStore getKs() {
        return ks;
    }
}
