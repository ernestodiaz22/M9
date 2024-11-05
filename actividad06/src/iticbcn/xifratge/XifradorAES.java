package iticbcn.xifratge;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class XifradorAES implements Xifrador {
    private static final String ALGORISME_XIFRAT = "AES";
    private static final String ALGORISME_HASH = "SHA-256";
    private static final String FORMAT_AES = "AES/CBC/PKCS5Padding";
    private static final int MIDA_IV = 16;
    private byte[] iv = new byte[MIDA_IV];

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            // Generar IV aleatorio
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // Preparar la clave de cifrado
            byte[] claveBytes = generarClave(clau);

            // Configurar el cifrador AES en modo CBC
            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            SecretKeySpec secretKeySpec = new SecretKeySpec(claveBytes, ALGORISME_XIFRAT);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            // Cifrar el mensaje
            byte[] encryptedMessage = cipher.doFinal(msg.getBytes("UTF-8"));

            // Combinar IV y mensaje cifrado en un solo array
            byte[] ivAndEncryptedMessage = new byte[iv.length + encryptedMessage.length];
            System.arraycopy(iv, 0, ivAndEncryptedMessage, 0, iv.length);
            System.arraycopy(encryptedMessage, 0, ivAndEncryptedMessage, iv.length, encryptedMessage.length);

            return new TextXifrat(ivAndEncryptedMessage);
        } catch (Exception e) {
            throw new ClauNoSuportada("Error durante el cifrado: " + e.getMessage());
        }
    }

    @Override
    public String desxifra(TextXifrat textXifrat, String clau) throws ClauNoSuportada {
        try {
            byte[] ivAndEncryptedMessage = textXifrat.getBytes();

            // Extraer el IV
            System.arraycopy(ivAndEncryptedMessage, 0, iv, 0, MIDA_IV);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // Extraer el mensaje cifrado
            byte[] encryptedMessage = new byte[ivAndEncryptedMessage.length - MIDA_IV];
            System.arraycopy(ivAndEncryptedMessage, MIDA_IV, encryptedMessage, 0, encryptedMessage.length);

            // Preparar la clave de descifrado
            byte[] claveBytes = generarClave(clau);

            // Configurar el descifrador AES en modo CBC
            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            SecretKeySpec secretKeySpec = new SecretKeySpec(claveBytes, ALGORISME_XIFRAT);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            // Descifrar el mensaje
            byte[] decryptedMessage = cipher.doFinal(encryptedMessage);

            return new String(decryptedMessage, "UTF-8");
        } catch (Exception e) {
            throw new ClauNoSuportada("Error durante el descifrado: " + e.getMessage());
        }
    }

    private byte[] generarClave(String clau) throws ClauNoSuportada {
        try {
            // Crear un hash de la clave utilizando SHA-256
            MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
            byte[] claveHash = sha.digest(clau.getBytes("UTF-8"));

            // Obtener los primeros 16 bytes del hash (para AES-128)
            byte[] claveFinal = new byte[16];
            System.arraycopy(claveHash, 0, claveFinal, 0, claveFinal.length);
            return claveFinal;
        } catch (Exception e) {
            throw new ClauNoSuportada("Error al generar la clave: " + e.getMessage());
        }
    }
}