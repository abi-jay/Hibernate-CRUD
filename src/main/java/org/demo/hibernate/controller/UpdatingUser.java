package org.demo.hibernate.controller;

import org.demo.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UpdatingUser {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        User u = new User();
        u.setId(3);
        u.setEmail("perscholas@test.com");
        u.setFirstName("Jake");
        u.setLastName("Larson");
        u.setPhoneNumber("209-874-6352");
        // empty userId
        session.merge(u);
        session.getTransaction().commit();
        session.close();
    }
}
