package iticbcn.xifratge;

public class AlgorismePolialfabetic implements AlgorismeFactory { // Implementa la interfaz
    @Override
    public Xifrador creaXifrador() {
        return new XifradorPolialfabetic();
    }
}