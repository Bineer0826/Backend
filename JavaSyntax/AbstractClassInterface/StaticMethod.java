package JavaSyntax.AbstractClassInterface;

interface Aa {
    static void abc() {
        System.out.println("Aa 인터페이스의 정적메서드abc()");
    }
}
public class StaticMethod {
    public static void main(String[] args) {
        //정적메서드 호출
        Aa.abc();
    }
}
