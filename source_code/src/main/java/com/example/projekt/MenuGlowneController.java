package com.example.projekt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MenuGlowneController{

    @FXML
    private AnchorPane main_form;
    @FXML
    private Button uczniowie;

    @FXML
    private Button nauczyciele;

    @FXML
    private Button kalendarz_wydarzen;

    @FXML
    private Button wyloguj;

    @FXML
    private Button zamknij;

    @FXML
    private Button zminimalizuj;

    @FXML
    private void handleBottomClick(javafx.event.ActionEvent mouseEvent) throws IOException {

        if (mouseEvent.getSource() == uczniowie) {
            main_form.getScene().getWindow().hide();
            LoadStages("menuUczen.fxml");
        } else if (mouseEvent.getSource() == nauczyciele) {
            main_form.getScene().getWindow().hide();
            LoadStages("menuNauczyciel.fxml");
        } else if (mouseEvent.getSource() == kalendarz_wydarzen) {
            main_form.getScene().getWindow().hide();
            LoadStages("menuKalendarzWydzrzen.fxml");
        } else if (mouseEvent.getSource() == wyloguj) {
            main_form.getScene().getWindow().hide();
            LoadStages("ekranLogowania.fxml");
        }

    }

    private void LoadStages(String fxml) {
        try {
            FXMLLoader x = new FXMLLoader(getClass().getResource(fxml));
            Stage stage = new Stage();
            Parent root = x.load();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void zamknij() {
        System.exit(0);
    }

    public void zminimalizuj() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

}
