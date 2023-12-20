package JavaSyntax.AbstractClassInterface;
interface D {
    public static final int a = 3;
    public abstract void abc();
}
interface E {
    int b = 3;//컴파일러가 자동으로 public abstract final생성
    void abc();//abstract 자동생성
}
public interface InterfaceCharacteristics {
    public static void main(String[] args) {
        // static 자동추가 체크
        System.out.println(D.a);
        System.out.println(E.b);
        // final 체크
       /* D.a = 5;
        E.b = 5;*/
    }
}
