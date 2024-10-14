import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Polialfabetic {
    private  static int clauSecreta = 7;
    private static Random random = new Random();
    private static final char[] abecedario = {'a','b', 'c', 'd', 'e', 'f', 'g', 'h', 'i','j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z','á','à','ä','ç','é','è','ë','í','ì','ï','ó','ò','ö','ú','ù','ü'};
    private static char[] alfabetoCifrado = new char[43];
    private static List<Character> alfabetoPermuta = new ArrayList<Character>();
    public static void initRandom(int clauSecreta){
        random = new Random(7);
    }
    public static void permutaAlfabet(char[] abecedario){
        alfabetoPermuta.clear();
        for(int i = 0; i < abecedario.length; i++ ){
            alfabetoPermuta.add(abecedario[i]);
        }
        Collections.shuffle(alfabetoPermuta,random);
        for(int i = 0; i < abecedario.length; i++ ){
            alfabetoCifrado[i] = alfabetoPermuta.get(i);
        }
    }
    public static String xifraPoliAlfa(String palabro){
        return recorrerPalabra(palabro,true);
    }
    public static String desxifraPoliAlfa(String palabro){
        return recorrerPalabra(palabro,false);
    }
    public static String recorrerPalabra(String palabro, boolean cifrado){
        StringBuilder newPalabro = new StringBuilder();
        for(int i = 0; i < palabro.length(); i++){
            permutaAlfabet(abecedario);
            boolean esMayus = false;
            char newCaracter = '1';
            char caracter = palabro.charAt(i);
            newCaracter = recorrerAbc(cifrado, esMayus, caracter, newCaracter);
            if(newCaracter == '1'){
                caracter = Character.toLowerCase(caracter);
                esMayus = true;
                newCaracter = recorrerAbc(cifrado, esMayus, caracter, newCaracter);
            }
            newPalabro.append(newCaracter == '1' ? caracter : newCaracter);
        }
        return newPalabro.toString();
    }
    public static char recorrerAbc(boolean cifrar, boolean esMayus,char caracter, char newCaracter){
        for(int i = 0; i < abecedario.length; i++){
            if(cifrar){
                if(abecedario[i] == caracter){
                    newCaracter = esMayus ? Character.toUpperCase(alfabetoCifrado[i]) : alfabetoCifrado[i];
                }
            }else{
                if(alfabetoCifrado[i] == caracter){
                    newCaracter = esMayus ? Character.toUpperCase(abecedario[i]) :  abecedario[i];
                }
            }
        }
        return newCaracter;
    }
    public static void main(String[] args) {
       String msgs[] = {"Test 01 àrbritre, coixí, Perímetre",

                "Test 02 Taüll, DÍA, año",
                "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats [] = new String[msgs.length];
        System.out.println("Xifratge:\n--------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats [i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats [i]);
        }
        System.out.println("Desxifratge: \n-----------" );
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats [i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats [i], msg);
            }
    }
}