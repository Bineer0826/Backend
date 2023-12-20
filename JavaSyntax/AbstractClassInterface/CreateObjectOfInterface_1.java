package JavaSyntax.AbstractClassInterface;
//방법 1 : 자식클래스를 만들어서 객체생성
interface H{
    int a = 3;
    void abc();
}
class I implements H{
    @Override
    public void abc() {
        System.out.println("자식클래스로 객체 생성");
    }
}

public class CreateObjectOfInterface_1 {
    public static void main(String[] args) {
        //객체생성
        I i = new I();

        //메서드호출
        i.abc();
    }
}
