package JavaSyntax.AbstractClassInterface;
//추상클래스의 객체생성 방법
//방법 1 : 자식클래스 생성해서 객체생성
abstract class A {
    abstract void abc();
}
class B extends A {
    void abc() { //상속받아서 오버라이딩 해야 오류가 안남
        System.out.println("방법1. 자식클래스 생성 및 추상메서드 구현");
    }
}

public class AbstractClass_1 {
    public static void main(String[] args) {
        //1.객체생성
        B b1 = new B();//b는 a여서 a를 포함하므로 a객체를 만든 것과 같다
        //2. 메서드 호출
        b1.abc();
    }
}