package JavaSyntax.AbstractClassInterface;

//접근지정자
interface DD {
    public abstract void abc();
}
interface EE {
    void bcd(); //public 으로 접근지정자 선언됨
}
class FF implements DD {
    @Override
    public void abc() {
        //구현
    }
}
class GG implements EE {
    @Override
//  void bcd(){ } public이 아닌 default 메서드로 접근지정자 오류
    public void bcd() {
    //public으로 범위 수동설정함
    }
}
public interface InheritanceOfInterface_2 {
}
