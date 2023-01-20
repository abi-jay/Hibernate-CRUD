
# Hibernate Project Demonstration

####  Develop CRUD Operations


## Project Structure
```
.
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── org
    │   │       └── demo
    │   │           └── hibernate
    │   │               ├── App.java
    │   │               ├── controller
    │   │               │   ├── CreateUser.java
    │   │               │   ├── CreateUserTable.java
    │   │               │   ├── DeletingUser.java
    │   │               │   ├── FindUserBy.java
    │   │               │   ├── FindingUser.java
    │   │               │   └── UpdatingUser.java
    │   │               ├── model
    │   │               │   └── User.java
    │   │               └── service
    │   └── resources
    │       └── hibernate.cfg.xml
    └── test
        └── java
            └── org
                └── demo
                    └── hibernate
                        └── AppTest.java




```
## POM.XML
Add Jar dependencies,and configuration in pom.xml file\
Next, we need to add a couple of jar dependencies for Hibernate, JPA and MySQL Connector Java in the pom.xml file of our Maven’s Project. \
Open the pom.xml file and insert the following XML code under <dependencies> </dependencies> tag,  just  before the </project> tag\
Here we added two dependencies for the project: hibernate-core and MySQL-connection. Maven automatically downloads the required JAR files, which are shown under the Maven Dependencies node in the project

```bash
  <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>org.demo.hibernate</groupId>
  <artifactId>HibernateDemo2</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>HibernateDemo2</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <java.version>17</java.version>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
  </properties>

  <dependencies>
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.31</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-core -->
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-core</artifactId>
      <version>5.5.7.Final</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-annotations -->
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-annotations</artifactId>
      <version>3.5.6-Final</version>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>

```

## Create the Persistence Class (Model Class or Pojo).
- Create a package “com.test.hib.model” under the main->src->Java
- Create an entity class named “User.java” under the above package.
- Then we will use annotations to map this table to the corresponding table in the database.
This is a POJO (Plain Old Java Object) class with some class variables, its getter, setter methods, and constructors.\
Use some annotations provided by JPA to map this model class to the user table in the database.


```bash
package org.demo.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//change table name, column name as follows
//@Table(name = "library_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    //Default constructor
    public User() {
    }
    //Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

```


    

## Create the Configuration File

- For Eclipse IDE: To create the configuration file, To do so, right click on src/main/java →   New → Other - search files from search panel → click on File → specify the file name “hibernate.cfg.xml” → Finish. Write the following code in it.
- For intellij IDE: Create a configuration file named hibernate.cfg.xml under the resources folder, and write the following code in it.\
hibernate.cfg.xml

```bash
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <!-- Drop and re-create the database on startup -->
        <property name="hibernate.hbm2ddl.auto">update</property>

        <!-- Database connection settings -->
        <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://localhost:3306/library</property>
        <property name="connection.username">{username}</property>
        <property name="connection.password">{password}</property>

        <!-- MySQL DB dialect -->
        <property name="dialect">org.hibernate.dialect.MySQL5Dialect</property>
        <!-- print all executed SQL on console -->
        <property name="hibernate.show_sql" >true</property>
        <property name="hibernate.format_sql" >true</property>

        <!--   Mapping entity file -->
        <mapping class="org.demo.hibernate.model.User"/>
        <mapping class="org.demo.hibernate.model.Book"/>
     </session-factory>
 </hibernate-configuration>
```


## Create the Configuration File

Add the following code to the “CreateUserTable” class.

```bash
  package org.demo.hibernate.controller;
  import org.demo.hibernate.model.User;
  import org.hibernate.Session;
  import org.hibernate.SessionFactory;
  import org.hibernate.Transaction;
  import org.hibernate.cfg.Configuration;
  public class CreateUserTable {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        User user = new User();
        t.commit();
        System.out.println("successfully created user table");
        factory.close();
        session.close();
    }
  }
```
Hibernate has many configuration properties. Apart from the standard connection properties, it is worth mentioning the dialect property, which allows us to specify the name of the SQL dialect for the database.

## Develop CRUD Operations

Create a package called com.test.hib.controller. Then create the below classes in that package:
- CreateUserTable.java
- CreatingUser.java
- FindingUser.java
- UpdatingUser.java
- DeletingUser.java

## Create a User Table using Hibernate.

Add the following code to the “CreateUserTable” class. 
```bash
package org.demo.hibernate.controller;
import org.demo.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
public class CreateUserTable {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        User user = new User();
        t.commit();
        System.out.println("successfully created user table");
        factory.close();
        session.close();
    }
}
```
Run CreateUserTable.java

```bash
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=59051:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/abibala/Documents/perscholas/IdeaProjects/Hibernate/HibernateLab2/target/classes:/Users/abibala/.m2/repository/com/mysql/mysql-connector-j/8.0.31/mysql-connector-j-8.0.31.jar:/Users/abibala/.m2/repository/com/google/protobuf/protobuf-java/3.19.4/protobuf-java-3.19.4.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-core/5.5.7.Final/hibernate-core-5.5.7.Final.jar:/Users/abibala/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/Users/abibala/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar:/Users/abibala/.m2/repository/org/javassist/javassist/3.27.0-GA/javassist-3.27.0-GA.jar:/Users/abibala/.m2/repository/net/bytebuddy/byte-buddy/1.11.12/byte-buddy-1.11.12.jar:/Users/abibala/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/abibala/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.1.1.Final/jboss-transaction-api_1.2_spec-1.1.1.Final.jar:/Users/abibala/.m2/repository/org/jboss/jandex/2.2.3.Final/jandex-2.2.3.Final.jar:/Users/abibala/.m2/repository/com/fasterxml/classmate/1.5.1/classmate-1.5.1.jar:/Users/abibala/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/Users/abibala/.m2/repository/org/hibernate/common/hibernate-commons-annotations/5.1.2.Final/hibernate-commons-annotations-5.1.2.Final.jar:/Users/abibala/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.1/jaxb-runtime-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/txw2/2.3.1/txw2-2.3.1.jar:/Users/abibala/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:/Users/abibala/.m2/repository/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:/Users/abibala/.m2/repository/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-annotations/3.5.6-Final/hibernate-annotations-3.5.6-Final.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-commons-annotations/3.2.0.Final/hibernate-commons-annotations-3.2.0.Final.jar:/Users/abibala/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.0-api/1.0.0.Final/hibernate-jpa-2.0-api-1.0.0.Final.jar:/Users/abibala/.m2/repository/org/slf4j/slf4j-api/1.5.8/slf4j-api-1.5.8.jar org.demo.hibernate.controller.CreateUserTable
successfully created user table
Process finished with exit code 0
```
MySQL\
The user table should be generated/created in the library Database:
```bash
SELECT * FROM USER;
```
| id | email     | firstName                |lastName | phoneNumber     | userId                |
| :-------- | :------- | :------------------------- |:-------- | :------- | :------------------------- |
| NULL | NULL  |NULL  |NULL  |NULL  |NULL  |


## Adding User Records in the Database
Now, let’s write some code to create user entity instances using JPA.\
To do so, 
Open the CreateUser.java class, which contains the main method(). Add the following Java code:


```bash
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

```
Run CreateUser.java

```bash
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=59714:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/abibala/Documents/perscholas/IdeaProjects/Hibernate/HibernateLab2/target/classes:/Users/abibala/.m2/repository/com/mysql/mysql-connector-j/8.0.31/mysql-connector-j-8.0.31.jar:/Users/abibala/.m2/repository/com/google/protobuf/protobuf-java/3.19.4/protobuf-java-3.19.4.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-core/5.5.7.Final/hibernate-core-5.5.7.Final.jar:/Users/abibala/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/Users/abibala/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar:/Users/abibala/.m2/repository/org/javassist/javassist/3.27.0-GA/javassist-3.27.0-GA.jar:/Users/abibala/.m2/repository/net/bytebuddy/byte-buddy/1.11.12/byte-buddy-1.11.12.jar:/Users/abibala/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/abibala/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.1.1.Final/jboss-transaction-api_1.2_spec-1.1.1.Final.jar:/Users/abibala/.m2/repository/org/jboss/jandex/2.2.3.Final/jandex-2.2.3.Final.jar:/Users/abibala/.m2/repository/com/fasterxml/classmate/1.5.1/classmate-1.5.1.jar:/Users/abibala/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/Users/abibala/.m2/repository/org/hibernate/common/hibernate-commons-annotations/5.1.2.Final/hibernate-commons-annotations-5.1.2.Final.jar:/Users/abibala/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.1/jaxb-runtime-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/txw2/2.3.1/txw2-2.3.1.jar:/Users/abibala/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:/Users/abibala/.m2/repository/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:/Users/abibala/.m2/repository/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-annotations/3.5.6-Final/hibernate-annotations-3.5.6-Final.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-commons-annotations/3.2.0.Final/hibernate-commons-annotations-3.2.0.Final.jar:/Users/abibala/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.0-api/1.0.0.Final/hibernate-jpa-2.0-api-1.0.0.Final.jar:/Users/abibala/.m2/repository/org/slf4j/slf4j-api/1.5.8/slf4j-api-1.5.8.jar org.demo.hibernate.controller.CreateUser\
Hibernate: 
    insert 
    into
        User
        (email, firstName, lastName, phoneNumber, userId) 
    values
        (?, ?, ?, ?, ?)
Process finished with exit code 0
```
MySQL\
The user table should be generated/created in the library Database:
```bash
SELECT * FROM USER;
```
| id | email     | firstName                |lastName | phoneNumber     | userId                |
| :-------- | :------- | :------------------------- |:-------- | :------- | :------------------------- |
| 4 | user@test.com  |John  |Smith  |818-465-8051  |bf01a4ea-8450-4d0a-a427-354dc6432fc7  |
| NULL | NULL  |NULL  |NULL  |NULL  |NULL  |


## READ all the Users

The session.get(Class, id) returns an object of the specified class that maps a row in the database table. If no row is found , it returns null.\
Open FindingUser.java class under the controller package and add the code below: 

```bash
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

```
Run FindingUser.java

```bash
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=60067:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/abibala/Documents/perscholas/IdeaProjects/Hibernate/HibernateLab2/target/classes:/Users/abibala/.m2/repository/com/mysql/mysql-connector-j/8.0.31/mysql-connector-j-8.0.31.jar:/Users/abibala/.m2/repository/com/google/protobuf/protobuf-java/3.19.4/protobuf-java-3.19.4.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-core/5.5.7.Final/hibernate-core-5.5.7.Final.jar:/Users/abibala/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/Users/abibala/.m2/repository/javax/persistence/javax.persistence-api/2.2/javax.persistence-api-2.2.jar:/Users/abibala/.m2/repository/org/javassist/javassist/3.27.0-GA/javassist-3.27.0-GA.jar:/Users/abibala/.m2/repository/net/bytebuddy/byte-buddy/1.11.12/byte-buddy-1.11.12.jar:/Users/abibala/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:/Users/abibala/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.2_spec/1.1.1.Final/jboss-transaction-api_1.2_spec-1.1.1.Final.jar:/Users/abibala/.m2/repository/org/jboss/jandex/2.2.3.Final/jandex-2.2.3.Final.jar:/Users/abibala/.m2/repository/com/fasterxml/classmate/1.5.1/classmate-1.5.1.jar:/Users/abibala/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/Users/abibala/.m2/repository/org/hibernate/common/hibernate-commons-annotations/5.1.2.Final/hibernate-commons-annotations-5.1.2.Final.jar:/Users/abibala/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.1/jaxb-runtime-2.3.1.jar:/Users/abibala/.m2/repository/org/glassfish/jaxb/txw2/2.3.1/txw2-2.3.1.jar:/Users/abibala/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.7/istack-commons-runtime-3.0.7.jar:/Users/abibala/.m2/repository/org/jvnet/staxex/stax-ex/1.8/stax-ex-1.8.jar:/Users/abibala/.m2/repository/com/sun/xml/fastinfoset/FastInfoset/1.2.15/FastInfoset-1.2.15.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-annotations/3.5.6-Final/hibernate-annotations-3.5.6-Final.jar:/Users/abibala/.m2/repository/org/hibernate/hibernate-commons-annotations/3.2.0.Final/hibernate-commons-annotations-3.2.0.Final.jar:/Users/abibala/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.0-api/1.0.0.Final/hibernate-jpa-2.0-api-1.0.0.Final.jar:/Users/abibala/.m2/repository/org/slf4j/slf4j-api/1.5.8/slf4j-api-1.5.8.jar org.demo.hibernate.controller.FindingUser
Hibernate: 
    select
        user0_.id as id1_0_,
        user0_.email as email2_0_,
        user0_.firstName as firstnam3_0_,
        user0_.lastName as lastname4_0_,
        user0_.phoneNumber as phonenum5_0_,
        user0_.userId as userid6_0_ 
    from
        User user0_
First Name: John
Last Name: Smith
Email: user@test.com
Process finished with exit code 0
```


## Updating a Row/User

- Session.update() and session.merge() are both used for updating the rows of data in the database.

- Session.update(): If you are sure that the session does not contain an already persistent instance with the same identifier, use update to save the data in hibernate.
- Session.merge():  If you want to save your modifications at any time without knowing about the state of a session, use merge() in hibernate.

Open the UpdatingUser.java class under the controller package and add the code below: 

```bash
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

```

Run UpdatingUser.java

```bash
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=60535:/Applications/IntelliJ IDEA CE.app/Contents/bin -D
Hibernate: 
    select
        user0_.id as id1_0_0_,
        user0_.email as email2_0_0_,
        user0_.firstName as firstnam3_0_0_,
        user0_.lastName as lastname4_0_0_,
        user0_.phoneNumber as phonenum5_0_0_,
        user0_.userId as userid6_0_0_ 
    from
        User user0_ 
    where
        user0_.id=?
Hibernate: 
    insert 
    into
        User
        (email, firstName, lastName, phoneNumber, userId) 
    values
        (?, ?, ?, ?, ?)

Process finished with exit code 0
```
MySQL\
The user table should be updated in the library Database:
```bash
SELECT * FROM USER;
```
| id | email     | firstName                |lastName | phoneNumber     | userId                |
| :-------- | :------- | :------------------------- |:-------- | :------- | :------------------------- |
| 5 | perscholas@test.com  |Jake  |Larson  |209-874-6352 |NULL|
| 4 | user@test.com  |John  |Smith  |818-465-8051  |bf01a4ea-8450-4d0a-a427-354dc6432fc7  |
| NULL | NULL  |NULL  |NULL  |NULL  |NULL  |

## Performing DELETE operation

We will call the session.delete(Object)method to remove a mapped object from the database.

Open the DeletingUser.java class under the controller package and add the code below

```bash
package org.demo.hibernate.controller;

import org.demo.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DeletingUser {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        User u = new User();
        u.setId(5);
        session.delete(u);
        tx.commit();
        session.close();
        factory.close();
    }
}

```
Run DeletingUser.java

```bash
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=60535:/Applications/IntelliJ IDEA CE.app/Contents/bin -D
Hibernate: 
    delete 
    from
        User 
    where
        id=?
Process finished with exit code 0
```
MySQL\
The record with id 5 will be deleted from the user table in the library Database:
```bash
SELECT * FROM USER;
```
| id | email     | firstName                |lastName | phoneNumber     | userId                |
| :-------- | :------- | :------------------------- |:-------- | :------- | :------------------------- |
| 4 | user@test.com  |John  |Smith  |818-465-8051  |bf01a4ea-8450-4d0a-a427-354dc6432fc7  |
| NULL | NULL  |NULL  |NULL  |NULL  |NULL  |

## Find User By

Add the following code to the “FindUserBy” class.

```bash
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
```
Run FindUserBy.java

```bash
/Library/Java/JavaVirtualMachines/jdk-17.0.5.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=60535:/Applications/IntelliJ IDEA CE.app/Contents/bin -D
Hibernate: 
    select
        user0_.id as id1_0_,
        user0_.email as email2_0_,
        user0_.firstName as firstnam3_0_,
        user0_.lastName as lastname4_0_,
        user0_.phoneNumber as phonenum5_0_,
        user0_.userId as userid6_0_ 
    from
        User user0_ 
    where
        user0_.email=?
First Name: John
Last Name: Smith
Email: user@test.com
Process finished with exit code 0
```
MySQL\
The record with email - user@test.com is retrieved from the user table in the library Database:
```bash
SELECT * FROM USER;
```
| id | email     | firstName                |lastName | phoneNumber     | userId                |
| :-------- | :------- | :------------------------- |:-------- | :------- | :------------------------- |
| 4 | user@test.com  |John  |Smith  |818-465-8051  |bf01a4ea-8450-4d0a-a427-354dc6432fc7  |
| NULL | NULL  |NULL  |NULL  |NULL  |NULL  |

## Author

- [@abijay](https://github.com/abi-jay)

