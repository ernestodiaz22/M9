import java.util.InputMismatchException;
import java.util.Scanner;
public class Rot13 {
    private static final char[] abecedarioMin = {'a','b', 'c', 'd', 'e', 'f', 'g', 'h', 'i','j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z','á','à','ä','ç','é','è','ë','í','ì','ï','ó','ò','ö','ú','ù','ü'};
    private static final char[] abecedarioMay = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','Á','À','Ä','Ç','É','È','Ë','Í','Ì','Ï','Ó','Ò','Ö','Ú','Ù','Ü'};
    public static char cambiarCaracter(char[] abecedario, int x, int recorrido){
        int indexIn = recorrido + x;
        char nuevoCaracter;
        if(indexIn < abecedario.length && indexIn > -1){
            nuevoCaracter = abecedario[indexIn];
        }else{
            if(recorrido > -1){
                nuevoCaracter = abecedario[indexIn - abecedario.length];
            }else{
                nuevoCaracter = abecedario[abecedario.length + indexIn];
            }
        }
        return nuevoCaracter;
    }
    public static String recorrerAbc(String mensaje, int recorrido) {
        char caracter;
        int mensajeNuevoLength;
        StringBuilder mensajeNuevo = new StringBuilder();
        for (int i = 0; i < mensaje.length(); i++) {
            mensajeNuevoLength = mensajeNuevo.length();
            caracter = mensaje.charAt(i);
            for (int x = 0; x < abecedarioMin.length; x++) {
                if(caracter == abecedarioMay[x]){
                    mensajeNuevo.append(cambiarCaracter(abecedarioMay, x, recorrido));
                }else if(caracter == abecedarioMin[x]){
                    mensajeNuevo.append(cambiarCaracter(abecedarioMin, x, recorrido));
                }
            }
            if(mensajeNuevoLength == mensajeNuevo.length()){
                mensajeNuevo.append(caracter);
            }
        }
        return mensajeNuevo.toString();
    }
    //CIFRAR
    public static String xifraRot13(String mensaje, int recorrido) {
        return recorrerAbc(mensaje, recorrido);
    }
    //DESCIFRAR
    public static String desxifraRot13(String mensaje, int recorrido){
        return recorrerAbc(mensaje, -recorrido);
    }
    //ATAQUE BRUTO
    public static void forcaBrutaRotX(String mensaje){
        System.out.print("[");
        for(int i = 1; i < abecedarioMin.length; i++){
            if(i != abecedarioMay.length - 1){
                System.out.print(i + ":" +  desxifraRot13(mensaje, i) + ",");
            }else{
                System.out.print(i + ":" +  desxifraRot13(mensaje, i));
            }
        }
        System.out.print("]");
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String mensaje = "";
        boolean esNumero = false;
        System.out.print("Introduzca el mensaje que desea cifrar: ");
        mensaje = scanner.nextLine();
        if(mensaje == null || mensaje.isEmpty()){
            System.err.print("Introduzca un mensaje para cifrar");
        }else{
            try{
                Integer.parseInt(mensaje);
                 esNumero = true;
            }catch (NumberFormatException e){
                 esNumero = false;
            }
            if(!esNumero){
                try{
                    System.out.print("Introduzca el número de veces que desea desplazar cada letra del mensaje: ");
                    int recorrido = scanner.nextInt();
                    String mensajeCifrado = (xifraRot13(mensaje, recorrido));
                    String mensajeDescifrado = desxifraRot13(mensajeCifrado, recorrido);
                    System.out.println("El mensaje cifrado es: " + mensajeCifrado + "\nEl mensaje descifrado es: " + mensajeDescifrado);
                    forcaBrutaRotX(mensajeCifrado);
                }catch(InputMismatchException e){
                    System.err.println("Debe introducir un número como recorrido para el desplazamiento: " + e);
                }
            }else{
                System.err.println("Introduzca caracteres no numéricos");
            }
        }
    }
}