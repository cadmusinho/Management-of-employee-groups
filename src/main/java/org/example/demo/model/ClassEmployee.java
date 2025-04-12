package main.java.org.example.demo.model;

import java.util.*;

public class ClassEmployee {
    public String nazwa_grupy;
    public List<Employee> pracownicy;
    public int max;

    public ClassEmployee(String n, ArrayList<Employee> p, int m) {
        nazwa_grupy = n;
        pracownicy = p;
        max = m;
    }

    public void addEmployee(Employee e) {
        long liczba = pracownicy.stream().filter(employee -> e.imie == employee.imie)
                .filter(employee -> e.nazwisko == employee.nazwisko).count();
        if(pracownicy.size() == max){
            System.out.println("Nie mozna dodac pracownika, limit przekroczony\n");
        } else if (liczba > 0) {
            System.out.println("Nie mozna dodac pracownika, juz taki istnieje\n");
        }
        else{
            pracownicy.add(e);
        }
    }

    public void addSalary(Employee e, double s){
        e.wynagrodzenie += s;
    }

    public void removeEmployee(Employee employee) {
        if (pracownicy.remove(employee)) {
            System.out.println("Employee " + employee + " removed.");
        } else {
            System.out.println("Employee " + employee + " not found.");
        }
    }

    public void changeCondition(Employee e, EmployeeCondition c) {
        e.stan = c;
    }

    Employee search(String a){
        return pracownicy.stream().filter(employee -> employee.nazwisko == a).findFirst().orElse(null);
    }

    List<Employee> searchPartial(String a){
        return pracownicy.stream().filter(employee -> employee.nazwisko == a && employee.imie == a).toList();
    }

    long countByCondition(EmployeeCondition c){
        return pracownicy.stream().filter(employee -> employee.stan == c).count();
    }

    void summary(){
        for(Employee e : pracownicy){
            e.printing();
        }
    }

    List<Employee> sortByName(){
        return pracownicy.stream().sorted().toList();
    }

    List<Employee> sortBySalary() {
        return pracownicy.stream().sorted((e1, e2) -> Double.compare(e2.wynagrodzenie, e1.wynagrodzenie)).toList();
    }

    Employee max() {
        return Collections.max(pracownicy, Comparator.comparingDouble(e -> e.wynagrodzenie));
    }
}
