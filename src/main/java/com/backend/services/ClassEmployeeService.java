package com.backend.services;

import com.backend.models.ClassEmployee;
import com.backend.models.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClassEmployeeService {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public List<ClassEmployee> getAllGroups(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return entityManager.createQuery("SELECT c FROM ClassEmployee c", ClassEmployee.class).getResultList();
        } catch (Exception e){
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public void addGroup(ClassEmployee classEmployee){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(classEmployee);
            transaction.commit();
        } catch (Exception e){
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public boolean deleteGroup(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            var classEmployee = entityManager.find(ClassEmployee.class, id);

            if (classEmployee != null) {
                entityManager.remove(classEmployee);
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

    public Double getGroupCurrentSize(Long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            ClassEmployee classEmployee = entityManager.find(ClassEmployee.class, id);

            if(classEmployee == null) return null;

            int currentSize = classEmployee.getCurrentSize();
            int maxEmployees = classEmployee.getMaxEmployees();

            if(maxEmployees == 0) return 0.0;

            return ((double) currentSize / maxEmployees) * 100;
        } catch (Exception e){
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public List<Employee> getAllEmployeesInGroup(Long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            ClassEmployee classEmployee = entityManager.find(ClassEmployee.class, id);

            if (classEmployee == null){
                return null;
            }

            return entityManager.createQuery("SELECT e FROM Employee e WHERE e.groupId = :groupId").setParameter("groupId", id).getResultList();
        } catch (Exception e){
            throw e;
        }
    }
}
