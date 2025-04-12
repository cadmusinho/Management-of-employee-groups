package main.java.test;

import main.java.controllers.ExportToCSV;
import models.*;
import main.java.models.EmployeeCondition;
import main.java.controllers.DBOperations;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
/*
        Employee employee = new Employee("Zbyszek", "Kazek", EmployeeCondition.DELEGATED, 1244, 123);
        try {
            DBOperations.EmployeeTable.addEmployee(employee);
            System.out.println("Employee saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save employee to the database.");
        }

        ClassEmployee classEmployee = new ClassEmployee();
        classEmployee.setGroupName("czesc");
        classEmployee.setMaxEmployees(5);
        try {
            DBOperations.GroupTable.addGroup(classEmployee);
            System.out.println("Grup saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save group to the database.");
        }


        try {
            DBOperations.GroupTable.addEmployee(9L, 1L);
            System.out.println("Employee saved to group successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save Employee to group.");
        }
*/
        /*
        try {
            DBOperations.GroupTable.rateGroup(1L, 5, "dobry przekaz leci");
            System.out.println("git");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("zle.");
        }
        */

/*
        try {
            DBOperations.GroupTable.deleteGroup(3L);
            System.out.println("Group deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to delete group from the database.");
        }

        try {
            DBOperations.EmployeeTable.deleteEmployee(2L);
            System.out.println("Group deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to delete group from the database.");
        }
 */
/*
        try {
            DBOperations.EmployeeTable.removeEmployeeFromGroup(6L);
            System.out.println("Employee deleted from group successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to delete employee from group.");
        }
*/
/*
        try {
            List<Employee> list = DBOperations.GroupTable.sortBy(1L, "salary");
            for (Employee employee : list) {
                employee.print();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to sort.");
        }
*/
        /*
        try {
            ExportToCSV.exportEmployees("employees.csv");
            ExportToCSV.exportGroups("groups.csv");
            ExportToCSV.exportRating("ratings.csv");
            System.out.println("ez.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("zle.");
        }
        */
    }
}

