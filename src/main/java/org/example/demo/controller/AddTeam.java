package main.java.org.example.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.example.demo.model.ClassContainer;
import org.example.demo.model.ClassEmployee;
import org.example.demo.model.Employee;

public class AddTeam {

    @FXML
    private VBox groupsContainer;

    private final ClassContainer classContainer = new ClassContainer();

    // Zmienna do przechowywania nazwy aktualnie wybranej grupy
    private String selectedGroupName;

    @FXML
    protected void onAddGroupButtonClick() {
        DialogHandler.promptForGroupDetails(classContainer, (groupName, maxCapacity) -> {
            if (!classContainer.grupyPracownikow.containsKey(groupName)) {
                classContainer.addClass(groupName, maxCapacity);
                UIUpdater.addGroupToUI(groupsContainer, groupName, classContainer);

                // Ustawienie aktualnie wybranej grupy na nowo dodaną grupę
                selectedGroupName = groupName;
            } else {
                ErrorHandler.showWarning("Błąd", "Grupa o tej nazwie już istnieje!");
            }
        });
    }

    @FXML
    private void onRemoveEmployeeButtonClick() {
        // Walidacja, czy jest wybrana grupa
        if (selectedGroupName == null) {
            System.out.println("No group selected.");
            return;
        }

        // Logika usuwania pracownika z grupy
        String employeeName = getSelectedEmployeeName(); // Placeholder for selected employee logic
        if (employeeName != null) {
            ClassEmployee group = classContainer.grupyPracownikow.get(selectedGroupName);
            if (group != null) {
                // Znajdź obiekt Employee na podstawie nazwy
                Employee employeeToRemove = group.pracownicy.stream()
                        .filter(emp -> emp.toString().equals(employeeName))
                        .findFirst()
                        .orElse(null);

                if (employeeToRemove != null) {
                    classContainer.removeEmployeeFromGroup(selectedGroupName, employeeToRemove);
                    System.out.println("Removed employee: " + employeeName + " from group: " + selectedGroupName);
                } else {
                    System.out.println("Employee not found in the group.");
                }
            } else {
                System.out.println("Group not found.");
            }
        } else {
            System.out.println("No employee selected for removal.");
        }
    }

    // Placeholder method to retrieve selected employee's name
    private String getSelectedEmployeeName() {
        // TODO: Implement logic to fetch the selected employee from UI
        return "John Doe"; // Example
    }

    // Ustawianie aktualnie wybranej grupy
    public void setSelectedGroupName(String groupName) {
        this.selectedGroupName = groupName;
        System.out.println("Selected group: " + groupName);
    }
}
