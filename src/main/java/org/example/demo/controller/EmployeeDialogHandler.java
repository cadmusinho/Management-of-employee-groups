package main.java.org.example.demo.controller;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import org.example.demo.model.Employee;
import org.example.demo.model.EmployeeCondition;
import org.example.demo.model.ClassEmployee;

import java.util.Optional;

public class EmployeeDialogHandler {

    public static void promptToAddEmployee(ClassEmployee group, ListView<Employee> employeesList) {
        // Sprawdzenie, czy grupa osiągnęła maksymalną liczbę pracowników
        if (group.pracownicy.size() >= group.max) {
            ErrorHandler.showWarning("Limit Pracowników", "Nie można dodać pracownika, limit przekroczony.");
            return; // Przerwij działanie metody
        }

        // Dialog dla imienia
        TextInputDialog dialogImie = new TextInputDialog();
        dialogImie.setTitle("Dodaj Pracownika");
        dialogImie.setHeaderText("Dodaj nowego pracownika");
        dialogImie.setContentText("Imię:");
        Optional<String> resultImie = dialogImie.showAndWait();

        resultImie.ifPresent(imie -> {
            TextInputDialog dialogNazwisko = new TextInputDialog();
            dialogNazwisko.setTitle("Dodaj Pracownika");
            dialogNazwisko.setContentText("Nazwisko:");
            dialogNazwisko.showAndWait().ifPresent(nazwisko -> {
                TextInputDialog dialogRok = new TextInputDialog();
                dialogRok.setTitle("Dodaj Pracownika");
                dialogRok.setContentText("Rok:");
                dialogRok.showAndWait().ifPresent(rokStr -> {
                    try {
                        int rok = Integer.parseInt(rokStr.trim());
                        TextInputDialog dialogWynagrodzenie = new TextInputDialog();
                        dialogWynagrodzenie.setTitle("Dodaj Pracownika");
                        dialogWynagrodzenie.setContentText("Wynagrodzenie:");
                        dialogWynagrodzenie.showAndWait().ifPresent(wynStr -> {
                            try {
                                double wynagrodzenie = Double.parseDouble(wynStr.trim());
                                promptForEmployeeCondition(group, employeesList, imie, nazwisko, rok, wynagrodzenie);
                            } catch (NumberFormatException e) {
                                ErrorHandler.showError("Błąd", "Niepoprawny format wynagrodzenia.");
                            }
                        });
                    } catch (NumberFormatException e) {
                        ErrorHandler.showError("Błąd", "Niepoprawny format roku.");
                    }
                });
            });
        });
    }


    private static void promptForEmployeeCondition(ClassEmployee group, ListView<Employee> employeesList, String imie, String nazwisko, int rok, double wynagrodzenie) {
        ComboBox<EmployeeCondition> stanComboBox = new ComboBox<>();
        stanComboBox.getItems().addAll(EmployeeCondition.values());
        stanComboBox.setValue(EmployeeCondition.obecny);

        Stage stateDialog = new Stage();
        stateDialog.setTitle("Wybierz stan");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Label stanLabel = new Label("Wybierz stan pracownika:");
        Button confirmButton = new Button("Potwierdź");

        confirmButton.setOnAction(event -> {
            EmployeeCondition stan = stanComboBox.getValue();
            Employee newEmployee = new Employee(imie, stan, nazwisko, rok, wynagrodzenie);
            group.addEmployee(newEmployee);
            UIUpdater.updateEmployeeList(employeesList, group);
            stateDialog.close();
        });

        vbox.getChildren().addAll(stanLabel, stanComboBox, confirmButton);

        Scene scene = new Scene(vbox, 300, 200);
        stateDialog.setScene(scene);
        stateDialog.show();
    }
    public static void promptToEditEmployee(ClassEmployee group, Employee employee, ListView<Employee> employeesList) {
        // Dialog setup
        Stage editDialog = new Stage();
        editDialog.setTitle("Edytuj Dane Pracownika");

        VBox content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_LEFT);

        // Fields for editing
        Label nameLabel = new Label("Imię:");
        javafx.scene.control.TextField nameField = new javafx.scene.control.TextField(employee.imie);

        Label surnameLabel = new Label("Nazwisko:");
        javafx.scene.control.TextField surnameField = new javafx.scene.control.TextField(employee.nazwisko);

        Label yearLabel = new Label("Rok urodzenia:");
        javafx.scene.control.TextField yearField = new javafx.scene.control.TextField(String.valueOf(employee.rok));

        Label salaryLabel = new Label("Pensja:");
        javafx.scene.control.TextField salaryField = new javafx.scene.control.TextField(String.valueOf(employee.wynagrodzenie));

        Label conditionLabel = new Label("Stan:");
        ComboBox<EmployeeCondition> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll(EmployeeCondition.values());
        conditionComboBox.setValue(employee.stan);

        // Save button
        Button saveButton = new Button("Zapisz");
        saveButton.setOnAction(e -> {
            // Update employee fields
            employee.imie = nameField.getText();
            employee.nazwisko = surnameField.getText();
            employee.rok = Integer.parseInt(yearField.getText());
            employee.wynagrodzenie = Double.parseDouble(salaryField.getText());
            employee.stan = conditionComboBox.getValue();

            // Refresh the employee list in the UI
            employeesList.refresh();
            editDialog.close();
        });

        // Layout
        content.getChildren().addAll(
                nameLabel, nameField,
                surnameLabel, surnameField,
                yearLabel, yearField,
                salaryLabel, salaryField,
                conditionLabel, conditionComboBox,
                saveButton
        );

        Scene scene = new Scene(content, 300, 400);
        editDialog.setScene(scene);
        editDialog.show();
    }

}


