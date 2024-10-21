import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;


public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "LaClauSecretaQueVulguis";

    public static byte[] xifraAES(String msg, String clau) throws Exception {
        //Obtenir els bytes de l’String
        byte[] stringBytes = msg.getBytes("UTF-8"); //convertimos el mensaje en un array de bytes
        byte[] claveBytes = clau.getBytes("UTF-8");//convertimos la clave en un array de bytes

        // Genera IvParameterSpec
        SecureRandom random = new SecureRandom();//generador de valores aleatorios seguros
        random.nextBytes(iv);//genero valores aleatorios para el IV
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);//utilizo el IV para crear un objeto de tipo IvParameterSpec

        // Genera hash
        MessageDigest md = MessageDigest.getInstance(ALGORISME_HASH);//inicializar el algoritmo de hash (SHA-256)
        claveBytes = md.digest(claveBytes);//calculamos el hash de la clave
       //tomamos los 16 primeros bytes(128 bites) del hash, ya que AES-128 requiere una clave 16 bytes
        byte[] hash = new byte[16];
        for(int i = 0; i < hash.length ; i++){
            if(i < claveBytes.length){
                hash[i] = claveBytes[i];//copiamos byte a byte
            }else{
                hash[i] = 0;//si el length del array de la clave es inferior a 16, complementamos los espacios restantes con 0
            }
        }
        // Configurar el cifrador AES en modo CBC
        Cipher cipher = Cipher.getInstance(FORMAT_AES);//crea una instancia del cifrador con formato AES
        SecretKeySpec secretKeySpec = new SecretKeySpec(hash, ALGORISME_XIFRAT);//crea la clave de cifrado a partir de los bytes del hash
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec ,ivParameterSpec);//incializamos el cifrador en modo cifrado, usando la clave y el IV

        // Encrypt.
        byte[] encrypt = cipher.doFinal(stringBytes);//cifra el mensaje y devuelve los bytes cifrados

        // Combinar IV i part xifrada.
        byte[] ivMsg = new byte[iv.length + encrypt.length];//combinamos los lengths de los dos arrays y creamos un nuevo array de bytes
        for(int i = 0; i < ivMsg.length ; i++){
            if(i < iv.length){//si el length es menor al lenght de iv
                ivMsg[i] = iv[i];//introducimos los bytes del iv
            }else{//el length de iv fue superado por el incremento y, por lo tanto, debemos emplear los bytes del otro array: el array con los bytes del mensaje encriptado
                ivMsg[i] = encrypt[i - iv.length];//introducimos los bytes del mensaje encriptado
            }
        }
        return ivMsg;
    }

    public static String desxifraAES (byte[] bIvIMsgXifrat , String clau) throws Exception {
        // Extreure l'IV.
        //extraemos los bytes del iv de los primeros 16 bytes del array que contiene los bytes del iv mezclado con los bytes del mensaje, ya que en la anterior función he puesto el iv en los 16 primeros bytes
        for(int i = 0; i < 16; i++){
            iv[i] = bIvIMsgXifrat[i];
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extreure la part xifrada.
        byte[] msg = new byte[bIvIMsgXifrat.length - iv.length];
        for(int i = 16; i < bIvIMsgXifrat.length; i++){//extraemos los últimos  bytes, que corresponden con los bytes del mensaje
                msg[i-16] = bIvIMsgXifrat[i];
        }

        // Fer hash de la clau
        MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] claveBytes = sha.digest(clau.getBytes("UTF-8"));
        //tomamos los 16 primeros bytes(128 bites) del hash, ya que AES-128 requiere una clave 16 bytes
        byte[] hash = new byte[16];
        for(int i = 0; i < hash.length ; i++){
            if(i < claveBytes.length){
                hash[i] = claveBytes[i];//copiamos byte a byte
            }else{
                hash[i] = 0;//si el length del array de la clave es inferior a 16, complementamos los espacios restantes con 0
            }
        }

        // Configurar el descifrador AES en modo CBC
        Cipher cipher = Cipher.getInstance(FORMAT_AES);//crea una instancia del cifrador con formato AES
        SecretKeySpec secretKeySpec = new SecretKeySpec(hash, ALGORISME_XIFRAT);//crea la clave de cifrado a partir de los bytes del hash
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);//incializamos el cifrador en modo descifrado, usando la clave y el IV

        // Desxifrar.
        byte[] decrypted = cipher.doFinal(msg);//descriframos el mensaje
        String desxifrat =  new String (decrypted, "UTF-8");//lo convertimos en una cada UTF-8

        // return String desxifrat
        return desxifrat;
    }
    public static void main(String[] args) {
            String msgs[] = {"Lorem ipsum dicet",

                    "Hola Andrés cómo está tu cuñado",
                    "Àgora ïlla Ôtto"};
            for (int i = 0; i < msgs.length; i++) {
                String msg = msgs[i];
                byte[] bXifrats = null;
                String desxifrat = "";
                try {
                    bXifrats = xifraAES(msg, CLAU);
                    desxifrat = desxifraAES(bXifrats, CLAU);
                } catch (Exception e) {
                    System.err.println("Error de xifrat: "
                            + e.getLocalizedMessage());

                }
                System.out.println("--------------------");
                System.out.println("Msg: " + msg);
                System.out.println("Enc: " + new String(bXifrats));
                System.out.println("DEC: " + desxifrat);
            }
        }
    }