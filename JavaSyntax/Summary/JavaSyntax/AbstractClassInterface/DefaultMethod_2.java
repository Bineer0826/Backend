package JavaSyntax.AbstractClassInterface;
interface MM {
    default void abc(){
        System.out.println("MM인터페이스의 abc()");
    }
}
class TT implements MM {
    //완성된 메서드만 있어서 인터페이스를 클래스가 상속해도 문제가 없다

    @Override
    public void abc() {
        MM.super.abc();//TT에서 MM 메서드 호출
        System.out.println("TT클래스의 abc()");
}
public static class DefaultMethod_2 {
    public static void main(String[] args) {
        //객체 생성
        TT t1 = new TT();
        //메서드 호출
        t1.abc();//TT클래스가 나온다

    }
}
}
