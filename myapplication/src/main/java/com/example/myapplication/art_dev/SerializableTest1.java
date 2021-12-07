package com.example.myapplication.art_dev;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableTest1 {

    public static void main(String[] args) {
//        User user = new User("我是王者","男",true);
//        serial(user);

        User user = unSerial();
        System.out.println(user.toString());
    }


    /**
     * 序列化
     */
    private static void serial(User user){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Serial.txt"));
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 反序列化
     */
    private static User unSerial(){
        User user = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Serial.txt"));
            System.out.println("123");
             user = (User) objectInputStream.readObject();
             objectInputStream.close();
            return user;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }  finally {
            return user;
        }
    }
}
