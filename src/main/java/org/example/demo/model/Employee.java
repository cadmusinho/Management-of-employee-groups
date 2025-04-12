package main.java.org.example.demo.model;

public class Employee implements Comparable<Employee>{

    public String imie;
    public String nazwisko;
    public int rok;
    public EmployeeCondition stan;
    public double wynagrodzenie; // Właściwość wynagrodzenie

    public Employee(String imie, EmployeeCondition stan, String nazwisko, int rok, double wynagrodzenie) {
        this.imie = imie;
        this.stan = stan;
        this.nazwisko = nazwisko;
        this.rok = rok;
        this.wynagrodzenie = wynagrodzenie;
    }

    public void printing(){
        System.out.println("Imie: " + imie + "\nnazwisko: " + nazwisko + "\nStan: " + stan +"\nRok: " + rok + "\nWynagrodzenie: " + wynagrodzenie);
    }

    public int compareTo(Employee o) {
        return nazwisko.compareTo(o.nazwisko);
    }

    // Getter i setter dla wynagrodzenia
    public double getWynagrodzenie() {
        return wynagrodzenie;
    }

    public void setWynagrodzenie(double wynagrodzenie) {
        this.wynagrodzenie = wynagrodzenie;
    }

    // Pozostałe gettery i settery...
    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public EmployeeCondition getStan() {
        return stan;
    }

    public void setStan(EmployeeCondition stan) {
        this.stan = stan;
    }

    public String toString() {
        return String.format("%s / %s / %s / %.2f zł", imie, stan, rok + " r", wynagrodzenie);
    }


}

