package AbstractClassInterface;
// 방법 2 : 익명이너클래스 사용

abstract class C {
            abstract void bcd();
        }
public class AbstractClass_2 {
    C c1 = new C(){
        @Override
        void bcd() {
            System.out.println("방법 2: 익명이너클래스로 출력");
        }
    };
}
