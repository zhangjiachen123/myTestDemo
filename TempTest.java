package com.ymkj.ams.common.util;

public class TempTest {
    abstract static class Human {

    }

    static class Woman extends Human {

    }

    static class Man extends Human {

    }

    public void sayHello(Human h) {
        System.out.println("i am human");
    }

    public void sayHello(Woman w) {
        System.out.println("i am Woman");
    }

    public void sayHello(Man m) {
        System.out.println("i am Man");
    }

    public void main(String[] args) {
        Human woman = new Woman();
        Human man = new Man();
        sayHello(woman);
        sayHello(man);
    }
}
