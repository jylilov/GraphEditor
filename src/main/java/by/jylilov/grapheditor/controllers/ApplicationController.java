package by.jylilov.grapheditor.controllers;

import by.jylilov.grapheditor.models.GraphModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    @FXML
    private Pane workPane;

    @FXML
    private Node splashLabel;

    @FXML
    private Node root;

    private GraphController graphController;

    private FileChooser fileChooser;
    private File workingFile = null;

    public void initialize(URL location, ResourceBundle resources) {
        graphController = new GraphController(workPane);
        initializeGraphFileChooser();
    }

    public void newGraph() {
        splashLabel.setVisible(false);
        workPane.setVisible(true);
        graphController.createGraph();
    }

    public void openGraph() {
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        try {
            GraphModel model = readGraph(file);
            splashLabel.setVisible(false);
            workPane.setVisible(true);
            graphController.createGraph(model);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private GraphModel readGraph(File file) throws IOException, ClassNotFoundException {
        GraphModel model = null;
        if (file != null) {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            model = (GraphModel)inputStream.readObject();
            inputStream.close();
        }
        return model;
    }

    private FileChooser initializeGraphFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Graph files", "*.grf")
        );
        return fileChooser;
    }

    public void saveGraph() {
        if (workingFile != null) {
            try {
                writeGraph(workingFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            saveAsGraph();
        }
    }

    private void writeGraph(File file) throws IOException {
        if (file != null) {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(graphController.getModel());
            outputStream.close();
        }
    }

    public void saveAsGraph() {
        File file = fileChooser.showSaveDialog(root.getScene().getWindow());
        try {
            writeGraph(file);
            workingFile = file;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeGraph() {
        workPane.setVisible(false);
        splashLabel.setVisible(true);
        graphController.clearGraph();
        workingFile = null;
    }

    public void exitApplication() {
        Platform.exit();
    }
}
