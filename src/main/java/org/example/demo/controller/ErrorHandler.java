package main.java.org.example.demo.controller;

import javafx.scene.control.Alert;

public class ErrorHandler {

    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null); // Usuń nagłówek dla czytelniejszego wyglądu
        alert.setContentText(message);
        alert.showAndWait();
    }

}
