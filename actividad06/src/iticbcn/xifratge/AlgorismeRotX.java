package iticbcn.xifratge;

public class AlgorismeRotX implements AlgorismeFactory { // Implementa la interfaz
    @Override
    public Xifrador creaXifrador() {
        return new XifradorPolialfabetic();
    }
}