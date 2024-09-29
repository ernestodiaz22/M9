//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;
public class Rot13 {
    private static final char[] abecedarioMin = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] abecedarioMay = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static String CompararAbc(String mensajeNuevo, char caracter, char[] abecedario, int x, int recorrido){
        int indexIn = recorrido + x;
        if(caracter == abecedario[x]){
            if(indexIn < abecedario.length && indexIn > -1){
                mensajeNuevo += abecedario[indexIn];
            }else{
                if(recorrido == 13){
                    mensajeNuevo += abecedario[indexIn - abecedario.length];
                }else{
                    mensajeNuevo += abecedario[abecedario.length + indexIn];
                }
            }
        }
        return mensajeNuevo;
    }
    public static String recorrerAbc(String mensajeNuevo,String mensaje, int recorrido){
        char caracter = 'e';
        int mensajeNuevoLength = 0;
            for (int i = 0; i < mensaje.length(); i++) {
                mensajeNuevoLength = mensajeNuevo.length();
                caracter = mensaje.charAt(i);
                for (int x = 0; x < abecedarioMin.length; x++) {
                    mensajeNuevo = CompararAbc(mensajeNuevo, caracter, abecedarioMin, x,recorrido);
                    mensajeNuevo = CompararAbc(mensajeNuevo, caracter, abecedarioMay, x,recorrido);
                }
                if(mensajeNuevoLength == mensajeNuevo.length()){
                    mensajeNuevo += caracter;
                }
        }
        return mensajeNuevo;
    }

    //CIFRAR
    public static String xifraRot13(String mensaje) {
        String mensajeNuevo = "";
        mensajeNuevo = recorrerAbc(mensajeNuevo, mensaje, 13);
        return mensajeNuevo;
    }
    //DESCIFRAR
    public static String desxifraRot13(String mensaje){
        String mensajeNuevo = "";
        mensajeNuevo = recorrerAbc(mensajeNuevo,mensaje, -13);
        return mensajeNuevo;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String mensajeCifrado = "";
        System.out.print("Introduzca el mensaje que desea cifrar: ");
        mensajeCifrado = xifraRot13(scanner.nextLine());
        String mensajeDescifrado = desxifraRot13(mensajeCifrado);
        System.out.println("El mensaje cifrado es: " + mensajeCifrado + "\nEl mensaje descifrado es: " + mensajeDescifrado);
    }
}