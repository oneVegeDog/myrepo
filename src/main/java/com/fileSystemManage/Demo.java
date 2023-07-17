package com.fileSystemManage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.fileSystemManage.model.*;

public class Demo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File file = new File("src/main/java/com/fileSystemManage/model/Test.java");
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write("package com.fileSystemManage.model; public class Test { String username;Integer age;public String getUsername() {return username;}public void print(){System.out.println(\"test\");}public void setUsername(String username) {this.username = username;}public Integer getAge() {return age;}public void setAge(Integer age) {this.age = age;}}".getBytes());
        Process process = Runtime.getRuntime().exec("javac -d D:\\Projects\\FileManageSystem2.0\\target\\classes D:\\Projects\\FileManageSystem2.0\\src\\main\\java\\com\\fileSystemManage\\model\\Test.java");
//        ClassLoader.getSystemClassLoader().loadClass("com.fileSystemManage.model.Test");
        process.destroy();
        System.out.println(process.exitValue());
        Process process1 = Runtime.getRuntime().exec(new String[]{"java","D:\\Projects\\FileManageSystem2.0\\target\\classes\\com\\fileSystemManage\\model\\Test"});
        process1.destroy();
        System.out.println(process1.exitValue());
    }
}

//class test{
//public test() throws ClassNotFoundException {
//    Class clazz = Class.forName("com.fileSystemManage.model.Test");
//    System.out.println(clazz.getPackageName());
//    try {
//        Test test = (Test)clazz.newInstance();
//        test.print();
//    } catch (InstantiationException e) {
//        e.printStackTrace();
//    } catch (IllegalAccessException e) {
//        e.printStackTrace();
//    }
//}
//}