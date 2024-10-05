import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Main {
    private static final char[] abecedario = {'a','b', 'c', 'd', 'e', 'f', 'g', 'h', 'i','j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z','á','à','ä','ç','é','è','ë','í','ì','ï','ó','ò','ö','ú','ù','ü'};
    private static List<Character> abecedarioCifrado = new ArrayList<Character>();
    public static  void mostrarAbc(boolean lista) {
        for (int i = 0; i < abecedario.length; i++) {
            if (lista) {
                System.out.print(abecedarioCifrado.get(i) + " ");
            } else {
                System.out.print(abecedario[i] + " ");
            }
        }
        System.out.println();
    }
    public static void permutaAlfabet(char[] abecedario){
        for(int i = 0; i < abecedario.length; i++ ){
            abecedarioCifrado.add(abecedario[i]);
        }
        Collections.shuffle(abecedarioCifrado);
    }
    public static String xifraMonoAlfa(String palabro){
        return recorrerPalabra(palabro,true);
    }
    public static String desxifraMonoAlfa(String palabro){
        return recorrerPalabra(palabro,false);
    }
    public static String recorrerPalabra(String palabro, boolean cifrado){
        StringBuilder newPalabro = new StringBuilder();
        for(int i = 0; i < palabro.length(); i++){
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
                        newCaracter = esMayus ? Character.toUpperCase(abecedarioCifrado.get(i)) : abecedarioCifrado.get(i);
                    }
                }else{
                    if(abecedarioCifrado.get(i) == caracter){
                        newCaracter = esMayus ? Character.toUpperCase(abecedario[i]) :  abecedario[i];
                    }
                }
        }
        return newCaracter;
    }
    public static void main(String[] args) {
        boolean esInt = false;
        int menu = 1;
        permutaAlfabet(abecedario);
            try(BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in))){
                while( menu != 3) {
                    try{                    System.out.print("1.Mostrar alfabeto cifrado\n2.cifrar \n3.salir:\nIntroduzca una opción:");
                        menu = Integer.parseInt(reader1.readLine());
                        if (menu == 1) {
                            mostrarAbc(false);
                            mostrarAbc(true);
                        } else if (menu == 2) {
                            System.out.print("Introduzca el mensaje que deseas cifrar: ");
                            String palabra = reader1.readLine();
                            if (palabra == null || palabra.isEmpty()) {
                                System.err.println("El contenido del mensaje no puede estar vacío");
                            } else {
                                try {
                                    Integer.parseInt(palabra);
                                    esInt = true;
                                } catch (NumberFormatException e) {
                                    esInt = false;
                                }
                                if (!esInt) {
                                    String palabraCifrada = xifraMonoAlfa(palabra);
                                    String palabraDescifrada = desxifraMonoAlfa(palabraCifrada);
                                    System.out.println("La palabra es: " + palabra);
                                    System.out.println("La palabra cifrada es: " + palabraCifrada);
                                    System.out.println("La palabra descifrada es: " + palabraDescifrada);
                                } else {
                                    System.err.println("Introduzca un valor no numérico.");
                                }
                            }
                        } else if(menu == 3){
                            System.out.println("Saliendo...");
                        }else{
                            System.err.println("Introduzca una opción válida: 1.Mostrar alfabeto cifrado, 2.cifrar o 3.salir.");
                        }
                    }catch (NumberFormatException e){
                        System.err.println("Introduzca un valor númerico: 1.Mostrar alfabeto cifrado, 2.cifrar o 3.salir.");
                    }
                    System.out.println();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
    }
}