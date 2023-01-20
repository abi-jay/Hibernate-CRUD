package org.demo.hibernate.controller;

import org.demo.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.UUID;

public class CreateUser {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        User user1 = new User();
        user1.setEmail("user@test.com");
        user1.setFirstName("John");
        user1.setLastName("Smith");
        user1.setPhoneNumber("818-465-8051");
        user1.setUserId(UUID.randomUUID().toString());
        session.save(user1);
        t.commit();
        factory.close();
        session.close();
    }
}
