package org.demo.hibernate.controller;
import org.demo.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;

public class FindUserBy {
    public static void main(String[] args) {
        String email = "user@test.com";
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        String hql = "FROM User u where u.email =:email";
        TypedQuery query = session.createQuery(hql,User.class);
        query.setParameter("email",email);
        User u = (User) query.getSingleResult();
        t.commit();
        System.out.println("First Name: " + u.getFirstName()+ "\nLast Name: " + u.getLastName()+ "\nEmail: " + u.getEmail());
        factory.close();
        session.close();
    }
}