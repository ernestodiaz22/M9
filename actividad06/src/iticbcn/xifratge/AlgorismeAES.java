package iticbcn.xifratge;

public class AlgorismeAES implements AlgorismeFactory { // Implementa la interfaz
    @Override
    public Xifrador creaXifrador() {
        return new XifradorPolialfabetic();
    }
}