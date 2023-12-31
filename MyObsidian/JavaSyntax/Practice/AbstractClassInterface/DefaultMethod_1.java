package JavaSyntax.AbstractClassInterface;
interface M{
    void abc();//2020년 생성
    //void bcd();//2030년 생성할때 구현이 안 돼서 오류가 난다
    default void bcd() { //인터페이스 내에서 완성된 메서드로 활용한다
        System.out.println("M인터페이스의 bcd()");
    }
}
class T implements M {
    @Override
    public void abc() {//2020년 생성
        System.out.println("T클래스의 abc()");
    }
}
class O implements M {
    @Override
    public void abc() {
        System.out.println("O클래스의 abc()");
    }

    @Override
    public void bcd() {
        System.out.println("O클래스의 bcd()");
    }
}
public class DefaultMethod_1 {
    public static void main(String[] args) {
        //객체생성
        M m1 = new T();
        M m2 = new O();

        //메서드 호출
        m1.abc();
        m1.bcd();

        m2.abc();
        m2.bcd();
    }
}
