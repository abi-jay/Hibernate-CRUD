package org.demo.hibernate;

import org.demo.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.UUID;


public class App 
{
    public static void main( String[] args )
    {

        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        //Query query = session.getNamedQuery("getAllBooks");
        /*User user1 = new User();
        user1.setEmail("user@test.com");
        user1.setFirstName("John");
        user1.setLastName("Smith");
        user1.setPhoneNumber("818-789-1234");
        user1.setUserId(UUID.randomUUID().toString());
        session.save(user1);
        t.commit();
        System.out.println("successfully created user table");
        factory.close();
        session.close();
        */
        User u = new User();
        u.setId(3);
        session.delete(u);
        t.commit();
        factory.close();
        session.close();

    }
}
