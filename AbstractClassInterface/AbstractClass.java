package AbstractClassInterface;
//추상클래스의 객체생성 방법
//자식클래스 생성해서 객체생성
abstract class A {
    abstract void abc();
}
class B extends A {
    void abc() { //상속받아서 오버라이딩 해야 오류가 안남
        System.out.println("방법1. 자식클래스 생성 및 추상메서드 구현");
    }
}

public class AbstractClass {
    public static void main(String[] args) {
        //1.객체생성
        B b1 = new B();//추상메서드를 포함한 클래스로 객체생성
        A b2 = new B();//A타입으로 해도 B가 A를 상속받아서 상관없이 가능하다
        //2.메서드호출
        b1.abc();
        b2.abc();
    }
}
