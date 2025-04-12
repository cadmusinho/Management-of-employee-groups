package main.java.org.example.demo.controller;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.demo.model.ClassContainer;
import javafx.scene.control.ListView;
import org.example.demo.model.ClassEmployee;
import org.example.demo.model.Employee;

public class UIUpdater {

    public static void addGroupToUI(VBox groupsContainer, String groupName, ClassContainer classContainer) {
        HBox groupBox = new HBox();
        groupBox.setSpacing(10);
        groupBox.setStyle("-fx-border-color: gray; -fx-border-width: 2; -fx-padding: 10; -fx-background-color: #f9f9f9;");
        groupBox.setPrefSize(300, 100);
        groupBox.setAlignment(Pos.CENTER_LEFT);

        Label groupLabel = new Label(groupName);
        groupLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Label percentageLabel = new Label();
        percentageLabel.setStyle("-fx-font-size: 14; -fx-text-fill: green;");

        // Calculate initial percentage and update the label
        ClassEmployee group = classContainer.grupyPracownikow.get(groupName);
        updatePercentageLabel(percentageLabel, group);

        Button optionsButton = new Button("Details");
        optionsButton.setOnAction(event -> {
            GroupWindowHandler.openGroupWindow(groupName, classContainer);
            // Update percentage after the details window is closed
            updatePercentageLabel(percentageLabel, group);
        });

        Button deleteButton = new Button("-");
        deleteButton.setOnAction(event -> {
            groupsContainer.getChildren().remove(groupBox);
            classContainer.removeClass(groupName);
        });

        groupBox.getChildren().addAll(groupLabel, percentageLabel, optionsButton, deleteButton);
        groupsContainer.getChildren().add(groupBox);
    }

    public static void updateEmployeeList(ListView<Employee> listView, ClassEmployee group) {
        listView.getItems().clear();
        listView.getItems().addAll(group.pracownicy);
    }

    private static void updatePercentageLabel(Label label, ClassEmployee group) {
        int filled = group.pracownicy.size();
        int max = group.max;
        double percentage = max > 0 ? ((double) filled / max) * 100 : 0;
        label.setText(String.format("%.2f%%", percentage));
    }
}
