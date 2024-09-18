//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Rot13 {
    static char[] abecedarioMin = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    static char[] abecedarioMay = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
   //CIFRAR
    public static String xifraRot13(String mensaje){
        String mensajeNuevo = "";
        for(int i = 0; i < mensaje.length(); i++){
            boolean escaracter = true;
            char caracter = mensaje.charAt(i);
            for(int x = 0; x < abecedarioMin.length; x++){
                if(caracter == abecedarioMin[x]){
                    escaracter = false;
                    if((x+13) < abecedarioMin.length){
                        mensajeNuevo += abecedarioMin[x+13];
                    }else{
                        mensajeNuevo += abecedarioMin[(x+13) - abecedarioMin.length];
                    }
                }
                if(caracter == abecedarioMay[x]){
                    escaracter = false;
                    if((x+13) < abecedarioMin.length){
                        mensajeNuevo += abecedarioMay[x+13];
                    }else{
                        mensajeNuevo += abecedarioMay[(x+13) - abecedarioMay.length];
                    }
                }
            }
            if(escaracter){
                mensajeNuevo += caracter;
            }
        }

        return mensajeNuevo;
    }
    //DESCIFRAR
    public static String desxifraRot13(String mensaje){
        String mensajeNuevo = "";
        for(int i = 0; i < mensaje.length(); i++){
            boolean escaracter = true;
            char caracter = mensaje.charAt(i);
            for(int x = 0; x < abecedarioMin.length; x++){
                if(caracter == abecedarioMin[x]){
                    escaracter = false;
                    if((x-13) > -1){
                        mensajeNuevo += abecedarioMin[x-13];
                    }else{
                        mensajeNuevo += abecedarioMin[abecedarioMin.length + (x-13)];
                    }
                }
                if(caracter == abecedarioMay[x]){
                    escaracter = false;
                    if((x-13) > -1){
                        mensajeNuevo += abecedarioMay[x-13];
                    }else{
                        mensajeNuevo += abecedarioMay[abecedarioMay.length + (x-13)];
                    }
                }
            }
            if(escaracter){
                mensajeNuevo += caracter;
            }
        }
        return mensajeNuevo;
    }
    public static void main(String[] args) {
        String mensajeCifrado = xifraRot13("caBecera, cabecEra");
        String mensajeDescifrado = desxifraRot13(mensajeCifrado);
        System.out.println(mensajeCifrado);
        System.out.println(mensajeDescifrado);
    }
}