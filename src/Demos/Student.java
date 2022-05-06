package Demos;

public class Student extends Person {

    public Student(String n) {
        super(n);
    }

    @Override
    public boolean isAsleep(int hour) {
        return  2< hour && 8 >hour;
    }

    public static void main(String[]args){
        Person p = new Student("Sully");
        p.status(1);
        System.out.println(p);
    }

//    public void method1(){
//        System.out.println("Student 1");
//        super.method1();
//        method2();
//    }
//    public void method2() {
//        System.out.print("Student 2 ");
//    }
}
