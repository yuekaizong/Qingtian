package kaizone.songmaya.xuanguan.controller;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyTest {

    @Test
    public void test0(){
        System.out.println("hello test");
    }

    public void test1(){
    }

    public static void main(String[] args) {
        String password = "asd123";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode(password));
    }
}
