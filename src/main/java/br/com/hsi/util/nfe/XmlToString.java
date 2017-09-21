package br.com.hsi.util.nfe;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class XmlToString {

    public static String lerXML(String fileXML) throws IOException {
        String linha = "";
        StringBuilder xml = new StringBuilder();

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileXML)));
        while ((linha = in.readLine()) != null) {
            xml.append(linha);
        }
        in.close();

        return xml.toString();
    }
}
