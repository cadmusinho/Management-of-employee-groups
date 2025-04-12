package com.backend.models;

import com.backend.Hibernate;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "groups")
public class ClassEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private List<Employee> employees = new ArrayList<>();

    @Column(name = "max_employees")
    private int maxEmployees;

    public ClassEmployee() {}

    public ClassEmployee(String groupName, int maxEmployees) throws Exception{
        if(maxEmployees <= 0) throw new Exception("Rozmiar grupy musi być większy niż 0");

        this.groupName = groupName;
        this.maxEmployees = maxEmployees;
    }

    public Long getId(){
        return this.id;
    }

    public String getGroupName(){
        return this.groupName;
    }

    public int getCurrentSize(){
        return this.employees.size();
    }

    public int getMaxEmployees(){
        return this.maxEmployees;
    }

    public List<Employee> getEmployees(){
        return this.employees;
    }

    public Employee getEmployee(String fullName){
        return this.employees.stream().filter(e -> fullName.equals(e.getName() + " " + e.getSurname())).findFirst().orElse(null);
    }

    public void addEmployee(Employee employee) throws Exception{
        if(this.employees.size() == this.maxEmployees) throw new Exception("Grupa nie moze mieć więcej pracowników");

        if(this.employees.stream().anyMatch(employee1 -> Objects.equals(employee1.getName(), employee.getName()) &&
                Objects.equals(employee1.getSurname(), employee.getSurname()))) throw new Exception("Taki pracownik juz istnieje");

        employees.add(employee);
    }

    public void removeEmployee(Employee employee){
        this.employees = employees.stream().filter(employee1 ->
                !Objects.equals(employee1.getSurname(), employee.getSurname()) &&
                        !Objects.equals(employee1.getName(), employee.getName())).collect(Collectors.toCollection(ArrayList::new));
    }
}
