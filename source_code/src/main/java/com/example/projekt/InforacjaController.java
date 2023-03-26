package com.example.projekt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InforacjaController {

    @FXML
    private AnchorPane main_form;
    @FXML
    private Label label;

    @FXML
    private Button zamknij;

    @FXML
    private Button zminimalizuj;

    public void zamknij() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.close();
    }

    public void zminimalizuj() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }
}
