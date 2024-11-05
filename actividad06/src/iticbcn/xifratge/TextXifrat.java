package iticbcn.xifratge;

public class TextXifrat {
    private byte[] data;
    public TextXifrat(byte[] data){
        this.data = data;
    }
    @Override
    public String toString(){
        return new String(data);
    }

    public byte[] getBytes(){
        return data;
    }
}
