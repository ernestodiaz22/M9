package iticbcn.xifratge;

public interface Xifrador {
    TextXifrat xifra(String msg, String clau) throws ClauNoSuportada;
    String desxifra(TextXifrat tx, String clau) throws ClauNoSuportada;
}