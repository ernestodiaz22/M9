package iticbcn.xifratge;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;


public class ClauPublica  {
    public KeyPair generaParellClausRSA() throws Exception{
        KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");//tipo de cifrado
        key.initialize(1024);//tamaño de la llave en bits
        KeyPair keyPair = key.generateKeyPair();//generamos la llave pública y privada, que se almacenan el esta variable
        return keyPair;
    }
    public byte[] xifraRSA(String msg, PublicKey clauPublica)throws Exception{
        byte[] inputBytes = msg.getBytes(StandardCharsets.UTF_8);//convertimos el mensaje en un array de bytes para cifrarlo
        try{
            Cipher cipher = Cipher.getInstance("RSA");//tipo de cifrado
            cipher.init(Cipher.ENCRYPT_MODE, clauPublica);//inicializamos el cipher en modo encriptado utilizando la llave pública
            return cipher.doFinal(inputBytes);//encriptamos usando los bytes del mensaje
        }catch(Exception ex){
            System.out.print(ex);
        }
        return null;
    }
    public String desxifraRSA(byte[] msgXifrat, PrivateKey ClauPrivada){
        try{
            Cipher cipher = Cipher.getInstance("RSA");//tipo de encriptado
            cipher.init(Cipher.DECRYPT_MODE, ClauPrivada);//inicializamos el cipher en modo desencriptado con la llave privada
            msgXifrat = cipher.doFinal(msgXifrat);//desencriptamos los bytes
            String msg = new String(msgXifrat, StandardCharsets.UTF_8);//creamos un string con los bytes desencriptados
            return msg;
        }catch(Exception ex){
            System.out.print(ex);
        }
        return null;
    }
}
