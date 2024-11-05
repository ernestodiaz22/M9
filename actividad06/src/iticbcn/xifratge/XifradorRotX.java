package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {
    private final char[] abecedarioMin = {'a','b', 'c', 'd', 'e', 'f', 'g', 'h', 'i','j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z','á','à','ä','ç','é','è','ë','í','ì','ï','ó','ò','ö','ú','ù','ü'};
    private final char[] abecedarioMay = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','Á','À','Ä','Ç','É','È','Ë','Í','Ì','Ï','Ó','Ò','Ö','Ú','Ù','Ü'};

    private char cambiarCaracter(char[] abecedario, int index, int recorrido) {
        int newIndex = (index + recorrido) % abecedario.length;
        if (newIndex < 0) newIndex += abecedario.length; // Ajuste para índices negativos
        return abecedario[newIndex];
    }


    private String recorrerAbc(String mensaje, int recorrido) {
        StringBuilder mensajeNuevo = new StringBuilder();
        for (char caracter : mensaje.toCharArray()) {
            boolean encontrado = false;
            for (int x = 0; x < abecedarioMin.length; x++) {
                if (caracter == abecedarioMay[x]) {
                    mensajeNuevo.append(cambiarCaracter(abecedarioMay, x, recorrido));
                    encontrado = true;
                    break;
                } else if (caracter == abecedarioMin[x]) {
                    mensajeNuevo.append(cambiarCaracter(abecedarioMin, x, recorrido));
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                mensajeNuevo.append(caracter);
            }
        }
        return mensajeNuevo.toString();
    }


    @Override
    public TextXifrat xifra(String mensaje, String clau) throws ClauNoSuportada {
        int recorrido;
        try {
            recorrido = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clave debe ser un número entero.");
        }
        return new TextXifrat(recorrerAbc(mensaje, recorrido).getBytes());
    }


    @Override
    public String desxifra(TextXifrat textXifrat, String clau) throws ClauNoSuportada {
        int recorrido;
        try {
            recorrido = Integer.parseInt(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clave debe ser un número entero.");
        }
        return recorrerAbc(new String(textXifrat.getBytes()), -recorrido);
    }


}