package main.java.org.example.demo.controller;

import javafx.scene.control.TextInputDialog;
import org.example.demo.model.ClassContainer;
import java.util.function.BiConsumer;
import java.util.Optional;
import java.util.function.Consumer;

public class DialogHandler {

    public static void promptForGroupDetails(ClassContainer classContainer, BiConsumer<String, Integer> onDetailsProvided) {
        // Dialog dla nazwy grupy
        TextInputDialog nameDialog = new TextInputDialog("Nowa Grupa");
        nameDialog.setTitle("Dodaj Grupę");
        nameDialog.setHeaderText("Wprowadź nazwę grupy pracowników:");
        nameDialog.setContentText("Nazwa grupy:");

        Optional<String> nameResult = nameDialog.showAndWait();
        nameResult.ifPresent(groupName -> {
            // Dialog dla maksymalnej liczby miejsc
            TextInputDialog maxCapacityDialog = new TextInputDialog("10");
            maxCapacityDialog.setTitle("Dodaj Grupę");
            maxCapacityDialog.setHeaderText("Ustaw maksymalną liczbę miejsc:");
            maxCapacityDialog.setContentText("Maksymalna liczba miejsc:");

            Optional<String> maxCapacityResult = maxCapacityDialog.showAndWait();
            maxCapacityResult.ifPresent(maxCapacityStr -> {
                try {
                    int maxCapacity = Integer.parseInt(maxCapacityStr.trim());
                    if (maxCapacity > 0) {
                        onDetailsProvided.accept(groupName, maxCapacity);
                    } else {
                        ErrorHandler.showError("Błąd", "Maksymalna liczba miejsc musi być większa od 0.");
                    }
                } catch (NumberFormatException e) {
                    ErrorHandler.showError("Błąd", "Niepoprawny format liczby.");
                }
            });
        });
    }

}
