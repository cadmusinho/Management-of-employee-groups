package com.backend.services;

import com.backend.models.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

@Service
public class EmployeeService {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public void addEmployee(Employee employee){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(employee);
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public boolean deleteEmployee(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            var employee = entityManager.find(Employee.class, id);

            if (employee != null) {
                entityManager.remove(employee);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            entityManager.close();
        }
    }

    public ByteArrayInputStream exportEmployees2CSV(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
            PrintWriter writer = new PrintWriter(outputStream);
            writer.println("id,name,surname,condition,birth_year,salary,group_id");
            for(Employee e : employees){
                writer.printf("%d,%s,%s,%s,%d,%.2f,%s\n",
                        e.getId(), e.getName(), e.getSurname(),
                        e.getEmployeeCondition(), e.getBirthYear(), e.getSalary(), e.getGroupId());
            }

            writer.flush();
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e){
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
