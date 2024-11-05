package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XifradorMonoalfabetic implements Xifrador {
    private final char[] abecedario = {'a','b', 'c', 'd', 'e', 'f', 'g', 'h', 'i','j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z','á','à','ä','ç','é','è','ë','í','ì','ï','ó','ò','ö','ú','ù','ü'};
    private final char[] alfabetoCifrado = new char[abecedario.length];
    private final List<Character> alfabetoPermuta = new ArrayList<>();

    public XifradorMonoalfabetic() {
        for (char c : abecedario) {
            alfabetoPermuta.add(c);
        }
        Collections.shuffle(alfabetoPermuta);
        for (int i = 0; i < abecedario.length; i++) {
            alfabetoCifrado[i] = alfabetoPermuta.get(i);
        }
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("XifradorMonoalfabetic no admite clave");
        }
        return new TextXifrat(recorrerPalabra(msg, true).getBytes());
    }

    @Override
    public String desxifra(TextXifrat textXifrat, String clau) throws ClauNoSuportada {
        if (clau != null) {
            throw new ClauNoSuportada("XifradorMonoalfabetic no admite clave");
        }
        return recorrerPalabra(new String(textXifrat.getBytes()), false);
    }

    private String recorrerPalabra(String palabro, boolean cifrado) {
        StringBuilder newPalabro = new StringBuilder();
        for (int i = 0; i < palabro.length(); i++) {
            boolean esMayus = Character.isUpperCase(palabro.charAt(i));
            char caracter = Character.toLowerCase(palabro.charAt(i));
            char newCaracter = recorrerAbc(cifrado, esMayus, caracter);
            newPalabro.append(newCaracter == '1' ? palabro.charAt(i) : newCaracter);
        }
        return newPalabro.toString();
    }

    private char recorrerAbc(boolean cifrar, boolean esMayus, char caracter) {
        for (int i = 0; i < abecedario.length; i++) {
            if (cifrar && abecedario[i] == caracter) {
                return esMayus ? Character.toUpperCase(alfabetoCifrado[i]) : alfabetoCifrado[i];
            } else if (!cifrar && alfabetoCifrado[i] == caracter) {
                return esMayus ? Character.toUpperCase(abecedario[i]) : abecedario[i];
            }
        }
        return '1';
    }
}