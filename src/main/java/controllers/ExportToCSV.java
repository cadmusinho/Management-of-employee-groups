package main.java.controllers;

import test.Hibernate;
import models.Employee;
import org.hibernate.Session;

import java.io.FileWriter;
import java.util.List;
import java.util.Objects;

public class ExportToCSV {
    public static void exportEmployees(String fileName){
        Session session = Hibernate.getSessionFactory().openSession();

        try {
            String hql = "SELECT e.id, e.name, e.surname, e.employeeCondition, e.birthYear, e.salary, e.groupId FROM Employee e";
            List<Object[]> employees = session.createQuery(hql).getResultList();

            try (FileWriter writer = new FileWriter(fileName)){
                writer.append("ID,Name,Surname,Condition,Birth Year,Salary,Group ID\n");
                for(Object[] employee: employees){
                    writer.append(employee[0].toString())
                            .append(",")
                            .append(employee[1].toString())
                            .append(",")
                            .append(employee[2].toString())
                            .append(",")
                            .append(employee[3].toString())
                            .append(",")
                            .append(employee[4].toString())
                            .append(",")
                            .append(employee[5].toString())
                            .append(",")
                            .append(employee[6] == null ? "NULL" : employee[6].toString())
                            .append("\n");
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void exportGroups(String fileName){
        Session session = Hibernate.getSessionFactory().openSession();

        try {
            String hql = "SELECT ce.id, ce.groupName, ce.maxEmployees FROM ClassEmployee ce";
            List<Object[]> employees = session.createQuery(hql).getResultList();

            try (FileWriter writer = new FileWriter(fileName)){
                writer.append("ID,Group Name,Max Employees\n");
                for(Object[] employee: employees){
                    writer.append(employee[0].toString())
                            .append(",")
                            .append(employee[1].toString())
                            .append(",")
                            .append(employee[2].toString())
                            .append("\n");
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    public static void exportRating(String fileName){
        Session session = Hibernate.getSessionFactory().openSession();

        try {
            String hql = "SELECT r.id, r.comment, r.groupId, r.value FROM Rating r";
            List<Object[]> employees = session.createQuery(hql).getResultList();

            try (FileWriter writer = new FileWriter(fileName)){
                writer.append("ID,Comment,Group ID,Value\n");
                for(Object[] employee: employees){
                    writer.append(employee[0].toString())
                            .append(",")
                            .append(employee[1].toString())
                            .append(",")
                            .append(employee[2].toString())
                            .append(",")
                            .append(employee[3].toString())
                            .append("\n");
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}

