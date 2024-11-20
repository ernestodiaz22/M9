import java.security.MessageDigest;

import java.util.HexFormat;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    public int npass = 0; // Variable de classe per comptar passwords provats

    public String getSHA512AmbSalt(String pw, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes());
        byte[] bytes = md.digest(pw.getBytes());
        HexFormat hex = HexFormat.of();
        return hex.formatHex(bytes);
    }

    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {
        int iterations = 65536;
        int keyLength = 128;
        char[] passwordChars = pw.toCharArray();
        byte[] saltBytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        HexFormat hex = HexFormat.of();
        return hex.formatHex(hash);
    }
    public String forcaBruta(String alg, String hash, String salt) throws Exception{
        char[] charset = "abcdefABCDEF1234567890!".toCharArray();
        char[] prueba;
        for(int a = 0; a < charset.length; a++){
            prueba = new char[1];
            prueba[0] = charset[a];
            if(probarAlgoritmo( alg, hash, prueba, salt)){
                return new String(prueba);
            }
            for(int b = 0; b < charset.length; b++){
                prueba = new char[2];
                prueba[0] = charset[a];
                prueba[1] = charset[b];
                if(probarAlgoritmo( alg, hash, prueba, salt)){
                    return new String(prueba);
                }
                for(int c = 0; c < charset.length; c++){
                    prueba = new char[3];
                    prueba[0] = charset[a];
                    prueba[1] = charset[b];
                    prueba[2] = charset[c];
                    if(probarAlgoritmo( alg, hash, prueba, salt)){
                        return new String(prueba);
                    }
                    for(int d = 0; d < charset.length; d++){
                        prueba = new char[4];
                        prueba[0] = charset[a];
                        prueba[1] = charset[b];
                        prueba[2] = charset[c];
                        prueba[3] = charset[d];
                        if(probarAlgoritmo( alg, hash, prueba, salt)){
                            return new String(prueba);
                        }
                        for(int e = 0; e < charset.length; e++){
                            prueba = new char[5];
                            prueba[0] = charset[a];
                            prueba[1] = charset[b];
                            prueba[2] = charset[c];
                            prueba[3] = charset[d];
                            prueba[4] = charset[e];
                            if(probarAlgoritmo( alg, hash, prueba, salt)){
                                return new String(prueba);
                            }
                            for(int f = 0; f < charset.length; f++){
                                prueba = new char[6];
                                prueba[0] = charset[a];
                                prueba[1] = charset[b];
                                prueba[2] = charset[c];
                                prueba[3] = charset[d];
                                prueba[4] = charset[e];
                                prueba[5] = charset[f];
                                if(probarAlgoritmo( alg, hash, prueba, salt)){
                                    return new String(prueba);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    public boolean probarAlgoritmo(String alg,String hash,char[] prueba, String salt) throws Exception{
        npass++;
        String hashPrueba;
        if(alg.equals("SHA-512")){
            hashPrueba= getSHA512AmbSalt(new String(prueba),salt);
        }else{
            hashPrueba = getPBKDF2AmbSalt(new String(prueba), salt);
        }
        return hashPrueba.equals(hash);
    }

    public String getInterval(long t1, long t2) {
        long diferencia = t2 - t1;
        long milis = diferencia % 1000;
        diferencia /= 1000;
        long segundos = diferencia % 60;
        diferencia /= 60;
        long minutos = diferencia % 60;
        diferencia /= 60;
        long horas = diferencia % 24;
        long dias = diferencia / 24;
        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis", dias, horas, minutos, segundos, milis);
    }

    public static void main(String[] args) throws Exception {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt), h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = {"SHA-512", "PBKDF2"};

        for (int i = 0; i < aHashes.length; i++) {
            System.out.printf("===========================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("---------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("---------------------------\n\n");
        }
    }
}