package JavaSyntax.AbstractClassInterface;
//방법 2 : 익명 이너클래스로 객체생성
interface HH{
    int a = 3;
    void abc();
}
public class CreateObjectOfInterface_2 {
    public static void main(String[] args) {
        HH h = new HH() {
            @Override
            public void abc() {
                System.out.println("방법 2 : 이너클래스로 객체생성");
            }
        };
        //메서드 호출
        h.abc();
    }

}
