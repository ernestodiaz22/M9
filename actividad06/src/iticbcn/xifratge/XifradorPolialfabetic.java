package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {
    private static final char[] abecedario = {'a','b', 'c', 'd', 'e', 'f', 'g', 'h', 'i','j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z','á','à','ä','ç','é','è','ë','í','ì','ï','ó','ò','ö','ú','ù','ü'};
    private char[] alfabetoCifrado = new char[abecedario.length];
    private List<Character> alfabetoPermuta = new ArrayList<>();
    private Random random;


    private void initRandom(int clauSecreta) {
        random = new Random(clauSecreta);
    }


    private void permutaAlfabet() {
        alfabetoPermuta.clear();
        for (char c : abecedario) {
            alfabetoPermuta.add(c);
        }
        Collections.shuffle(alfabetoPermuta, random);
        for (int i = 0; i < abecedario.length; i++) {
            alfabetoCifrado[i] = alfabetoPermuta.get(i);
        }
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            int clauSecreta = Integer.parseInt(clau);
            initRandom(clauSecreta); // Inicializamos el generador aleatorio con la clave
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clave debe ser un número entero.");
        }
        return new TextXifrat(recorrerPalabra(msg, true).getBytes());
    }


    @Override
    public String desxifra(TextXifrat textXifrat, String clau) throws ClauNoSuportada {
        try {
            int clauSecreta = Integer.parseInt(clau);
            initRandom(clauSecreta); // Inicializamos el generador aleatorio con la clave
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clave debe ser un número entero.");
        }
        return recorrerPalabra(new String(textXifrat.getBytes()), false);
    }


    private String recorrerPalabra(String palabro, boolean cifrado) {
        StringBuilder newPalabro = new StringBuilder();
        for (int i = 0; i < palabro.length(); i++) {
            permutaAlfabet(); // Se genera una nueva permutación para cada carácter
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