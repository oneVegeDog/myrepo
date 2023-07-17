
class A{
    public void print(){
        System.out.println("A");
    }
}

class B extends A{
    @Override
    public void print(){
        System.out.println("B");
    }
}

public class Test {
    public static void main(String[] args) {
        A b = new B();
        b.print();
    }
}
