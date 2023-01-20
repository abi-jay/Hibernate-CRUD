package org.demo.hibernate.controller;

import org.demo.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import java.util.List;

public class FindingUser {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        // Example of HQL to get all records of user class - case-sensitive
        String hql = "FROM User";
        TypedQuery query = session.createQuery(hql);
        List<User> results = query.getResultList();
        for(User u : results){
            System.out.println("First Name: " + u.getFirstName()+ "\nLast Name: " + u.getLastName()+ "\nEmail: " + u.getEmail());
        }
        factory.close();
        session.close();
    }
}
