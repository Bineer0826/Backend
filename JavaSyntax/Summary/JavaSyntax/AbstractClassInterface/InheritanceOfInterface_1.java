package JavaSyntax.AbstractClassInterface;

interface F {}
interface G {}

//단일 인터페이스 상속
class AA implements F {

}
//다중 인터페이스 상속
class BB implements F,G {

}
//클래스와 인터페이스 같이 상속
class CC extends AA implements G{
    //F가 상속되고 g가 상속되었다.
}
public interface InheritanceOfInterface_1 {
}
