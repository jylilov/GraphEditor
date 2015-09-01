package by.jylilov.grapheditor.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class DialogUtils {

    private DialogUtils() {}

    public static void chooseEdgeWeight(IntegerProperty weightProperty) {
        Dialog<Integer> dialog = new Dialog<>();

        dialog.setTitle("Choose Dialog");
        dialog.setHeaderText("Choose new edge weight");

        Label weightLabel = new Label("Edge weight:");
        Spinner<Integer> weightSpinner = new Spinner<>(Integer.MIN_VALUE, Integer.MAX_VALUE, weightProperty.get());

        GridPane contentPane = new GridPane();
        contentPane.add(weightLabel, 1, 1);
        contentPane.add(weightSpinner, 2, 1);
        dialog.getDialogPane().setContent(contentPane);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(param -> {
            Integer answer = null;
            if (param == ButtonType.OK) {
                answer = weightSpinner.getValue();
            }
            return answer;
        });

        dialog.showAndWait().ifPresent(weightProperty::set);
    }

    public static void chooseVertexName(StringProperty nameProperty) {
        TextInputDialog dialog = new TextInputDialog(nameProperty.get());
        dialog.setTitle("Choose Dialog");
        dialog.setHeaderText("Choose new vertex name");
        dialog.setContentText("Vertex name: ");
        dialog.showAndWait().ifPresent(nameProperty::set);
    }
}
