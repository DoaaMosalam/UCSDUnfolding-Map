package Demos;

public  class Person implements Comparable<Person> {
 //veriable.
    private String name;
    //Constructor
    public Person(String n){
        this.name = n;
    }

    public String getName(){
        return name;
    }
    public boolean isAsleep(int hour){
        return 22 < hour || 7 > hour;
    }

  public void status(int hr){
        if (this.isAsleep(hr))
            System.out.println("Now offline " + this);
        else
            System.out.println("Now Online " + this);
  }

    @Override
    public String toString(){
        return  name;
    }

    @Override
    public int compareTo(Person o) {
        return this.getName().compareTo(o.getName()) ;
    }
 //   public abstract void monthlyStatement();
    //public void method1(){
//        System.out.println("Person 1");
//    }
//
//    public void method2(){
//        System.out.println("Person 2");
//    }
}

