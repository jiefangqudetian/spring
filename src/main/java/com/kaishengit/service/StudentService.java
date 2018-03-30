package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import com.kaishengit.entiey.Student;

public class StudentService {

    private Student student;
   /* public StudentService(UserDao userDao1,UserDao userDao2){
        System.out.println(123);
    }*/
   public void setStudent(Student student){
       this.student = student;
   }

   public void run(){
       System.out.println(student);
   }
}
