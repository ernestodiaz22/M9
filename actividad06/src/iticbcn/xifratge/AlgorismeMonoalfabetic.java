package iticbcn.xifratge;

public class AlgorismeMonoalfabetic implements AlgorismeFactory { // Implementa la interfaz
    @Override
    public Xifrador creaXifrador() {
        return new XifradorPolialfabetic();
    }
}