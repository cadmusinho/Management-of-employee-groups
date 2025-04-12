package main.java.test;
import main.java.models.ClassEmployee;
import main.java.models.Employee;
import main.java.models.Rating;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Hibernate {
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration config = new Configuration();
            config.addAnnotatedClass(Employee.class);
            config.addAnnotatedClass(ClassEmployee.class);
            config.addAnnotatedClass(Rating.class);
            sessionFactory = config.buildSessionFactory();
        } catch (Throwable ex){
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static void shutdown(){
        sessionFactory.close();
    }
}
