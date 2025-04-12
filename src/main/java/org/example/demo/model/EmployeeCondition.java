package main.java.org.example.demo.model;

public enum EmployeeCondition {
    obecny,
    delegacja,
    chory,
    nieobecny;

    public String toString(){
        return name().toLowerCase();
    }
}
