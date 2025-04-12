package main.java.controllers;

import Hibernate;
import models.ClassEmployee;
import models.Employee;
import models.EmployeeCondition;
import models.Rating;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DBOperations {
    public static class EmployeeTable{
        public static void addEmployee(Employee e){
            Session session = Hibernate.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(e);
            transaction.commit();
            session.close();
        }

        public static Employee editEmployee(Long id, String name, String surname, EmployeeCondition employeeCondition, int birthYear, double salary){
            Session session = Hibernate.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            Employee editedEmp = session.get(Employee.class, id);
            editedEmp.setName(name);
            editedEmp.setSurname(surname);
            editedEmp.setCondition(employeeCondition);
            editedEmp.setBirthYear(birthYear);
            editedEmp.setSalary(salary);
            transaction.commit();
            session.close();
            return editedEmp;
        }

        public static void deleteEmployee(Long id){
            Session session = Hibernate.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Employee employee = session.get(Employee.class, id);
            session.remove(employee);

            transaction.commit();
            session.close();
        }

        public static void removeEmployeeFromGroup(Long id){
            Session session = Hibernate.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Employee employee = session.get(Employee.class, id);
            employee.setGroupId(null);
            session.merge(employee);

            transaction.commit();
            session.close();
        }
    }
    public static class GroupTable{
        public static void addGroup(ClassEmployee ce){
            Session session = Hibernate.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.persist(ce);
            transaction.commit();
            session.close();
        }

        public static void addEmployee(Long employeeId, Long groupId) throws Exception{
            Session session = Hibernate.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Employee employee = session.get(Employee.class, employeeId);
            if(employee == null)
                throw new Exception("Nie ma takiego pracownika");
            if(groupId.equals(employee.getGroupId()))
                throw new Exception("Ten pracownik nalezy juz do tej grupy");

            ClassEmployee classEmployee = session.get(ClassEmployee.class, groupId);
            if(classEmployee == null)
                throw new Exception("Nie ma takiej grupy");

            if(classEmployee.getCurrentSize() >= classEmployee.getMaxEmployees())
                throw new Exception("Grupa osiagnela limit pracownikow");

            employee.setGroupId(groupId);
            session.merge(employee);

            transaction.commit();
        }

        public static List<Employee> sortBy(Long id, String criteria){
            Session session = Hibernate.getSessionFactory().openSession();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("groupId"), id));
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(criteria)));
            List<Employee> employees = session.createQuery(criteriaQuery).getResultList();
            session.close();

            return employees;
        }



        public static void deleteGroup(Long id){
            Session session = Hibernate.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            ClassEmployee group = session.get(ClassEmployee.class, id);
            if(group != null)
                session.remove(group);

            transaction.commit();
            session.close();
        }

        public static Employee search(Long groupId, String surname) throws Exception{
            Session session = Hibernate.getSessionFactory().openSession();

            Query query = session.createQuery("FROM Employee e where e.surname = :surname AND e.groupId = :groupId", Employee.class);
            query.setParameter("surname", surname);
            query.setParameter("groupId", groupId);
            Employee employee;
            try {
                employee = (Employee) query.getSingleResult();
            } catch (NoResultException e){
                throw new Exception("Nie ma takiego pracownika w grupie");
            }

            session.close();

            return employee;
        }

        public static List<Employee> searchPartial(Long groupId, String surname) throws Exception{
            Session session = Hibernate.getSessionFactory().openSession();

            Query query = session.createQuery("from Employee e where e.surname LIKE :surname AND e.groupId = :groupId", Employee.class);
            query.setParameter("surname", "%" + surname + "%");
            query.setParameter("groupId", groupId);
            List<Employee> employee = query.getResultList();

            if(employee.isEmpty()) throw new Exception("Nie ma pracownikow o podanym patternie w grupie");

            session.close();

            return employee;
        }

        public static void changeCondition(Long id, EmployeeCondition employeeCondition){
            Session session = Hibernate.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Employee employee = session.get(Employee.class, id);
            employee.setCondition(employeeCondition);
            session.merge(employee);

            transaction.commit();
        }

        public static void addSalary(Long id, double amount){
            Session session = Hibernate.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Employee employee = session.get(Employee.class, id);
            employee.setSalary(employee.getSalary() + amount);
            session.merge(employee);

            transaction.commit();
        }

        public static Long countByCondition(Long groupId, EmployeeCondition employeeCondition){
            Session session = Hibernate.getSessionFactory().openSession();

            Query query = session.createQuery("SELECT COUNT(e) FROM Employee e WHERE e.employeeCondition = :condition AND e.groupId = :groupId", Long.class);
            query.setParameter("condition", employeeCondition);
            query.setParameter("groupId", groupId);

            Long count = (Long) query.getSingleResult();
            session.close();

            return count;
        }

        public static void rateGroup(Long id, int rate, String comment){
            Session session = Hibernate.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Rating rating = new Rating(rate, id, comment);
            session.persist(rating);

            transaction.commit();
            session.close();
        }

        public static Object getAverageRating(Long id){
            Session session = Hibernate.getSessionFactory().openSession();

            Query query = session.createQuery("SELECT AVG(r.value) FROM Rating r WHERE r.groupId = :groupId", Double.class);
            query.setParameter("groupId", id);

            Object average = query.getSingleResult();

            if(average == null) average = "N/A";

            session.close();

            return average;
        }

        public static Long getRatingsCount(Long id){
            Session session = Hibernate.getSessionFactory().openSession();

            Query query = session.createQuery("SELECT COUNT(r) FROM Rating r WHERE r.groupId = :groupId", Long.class);
            query.setParameter("groupId", id);

            Long count = (Long) query.getSingleResult();

            session.close();

            return count;
        }
    }
}
