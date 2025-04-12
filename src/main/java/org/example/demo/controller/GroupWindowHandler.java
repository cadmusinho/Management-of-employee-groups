
package main.java.org.example.demo.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo.model.ClassEmployee;
import org.example.demo.model.ClassContainer;
import org.example.demo.model.Employee;

import java.util.Comparator;
import java.util.stream.Collectors;

public class GroupWindowHandler {
    public static void openGroupWindow(String groupName, ClassContainer classContainer) {
        ClassEmployee group = classContainer.grupyPracownikow.get(groupName);

        Stage groupWindow = new Stage();
        groupWindow.setTitle(groupName);

        VBox content = new VBox();
        content.setSpacing(10);
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(20));

        ListView<Employee> employeesList = new ListView<>();
        employeesList.setPrefSize(400, 300);
        employeesList.getItems().addAll(group.pracownicy);

        // Customizing how employees are displayed in the list
        employeesList.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Employee employee, boolean empty) {
                super.updateItem(employee, empty);
                if (empty || employee == null) {
                    setText(null);
                } else {
                    setText(employee.imie + " " + employee.nazwisko + " / " + employee.stan + " / "
                            + employee.rok + " r / " + employee.wynagrodzenie + " zł");
                }
            }
        });

        // TextField for filtering employees by surname
        TextField textFilter = new TextField();
        textFilter.setPromptText("Wpisz nazwisko pracownika...");
        textFilter.setOnAction(event -> {
            String filterText = textFilter.getText().trim().toLowerCase();
            if (!filterText.isEmpty()) {
                employeesList.getItems().setAll(group.pracownicy.stream()
                        .filter(employee -> employee.nazwisko.toLowerCase().contains(filterText))
                        .collect(Collectors.toList()));
                System.out.println("Filtered employees by surname: " + filterText);
            } else {
                employeesList.getItems().setAll(group.pracownicy); // Reset to all employees if filter is empty
                System.out.println("Cleared filter, showing all employees.");
            }
        });

        // Button for adding employees
        Button addEmployeeButton = new Button("+");
        addEmployeeButton.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        addEmployeeButton.setPrefWidth(40);
        addEmployeeButton.setOnAction(event -> {
            EmployeeDialogHandler.promptToAddEmployee(group, employeesList);
        });

        // Button for removing employees
        Button removeEmployeeButton = new Button("-");
        removeEmployeeButton.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        removeEmployeeButton.setPrefWidth(40);
        removeEmployeeButton.setOnAction(event -> {
            Employee selectedEmployee = employeesList.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                group.removeEmployee(selectedEmployee);
                employeesList.getItems().remove(selectedEmployee);
                System.out.println("Removed employee: " + selectedEmployee + " from group: " + groupName);
            } else {
                System.out.println("No employee selected for removal.");
            }
        });

        // Button for editing employees
        Button editEmployeeButton = new Button("...");
        editEmployeeButton.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        editEmployeeButton.setPrefWidth(40);
        editEmployeeButton.setOnAction(event -> {
            Employee selectedEmployee = employeesList.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                EmployeeDialogHandler.promptToEditEmployee(group, selectedEmployee, employeesList);
            } else {
                System.out.println("No employee selected for editing.");
            }
        });

        // Button for sorting employees by salary
        Button sortButton = new Button("Sort");
        sortButton.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        sortButton.setPrefWidth(60);
        sortButton.setOnAction(event -> {
            employeesList.getItems().sort(Comparator.comparingDouble(Employee::getWynagrodzenie));
            System.out.println("Sorted employees by salary.");
        });

        // Layout of buttons
        HBox buttons = new HBox(addEmployeeButton, removeEmployeeButton, editEmployeeButton, sortButton);
        buttons.setSpacing(10); // Space between buttons
        buttons.setAlignment(Pos.CENTER_LEFT);

        content.getChildren().addAll(textFilter, buttons, employeesList);

        Scene scene = new Scene(content, 500, 450);
        groupWindow.setScene(scene);
        groupWindow.show();
    }
}
