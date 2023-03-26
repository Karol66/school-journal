package com.example.projekt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class MenuOcenyController {

    @FXML
    private BorderPane border_pane;

    @FXML
    private AnchorPane oceny;

    @FXML
    private Button oceny_matematyka_button;

    @FXML
    private Button oceny_polski_button;

    @FXML
    private Button oceny_geografia_button;

    @FXML
    private Button oceny_informatyka_button;

    @FXML
    private Button oceny_wf_button;

    @FXML
    private Button oceny_biologia_button;

    private int idLekcji = 0;

    @FXML
    void switchForm(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == oceny_matematyka_button) {
            LoadForms("Uczniowie/stopnie.fxml");
            setIdLekcji(1);
        } else if (mouseEvent.getSource() == oceny_polski_button) {
            LoadForms("Uczniowie/stopnie.fxml");
            setIdLekcji(2);
        } else if (mouseEvent.getSource() == oceny_geografia_button) {
            LoadForms("Uczniowie/stopnie.fxml");
            setIdLekcji(3);
        } else if (mouseEvent.getSource() == oceny_informatyka_button) {
            LoadForms("Uczniowie/stopnie.fxml");
            setIdLekcji(4);
        } else if (mouseEvent.getSource() == oceny_wf_button) {
            LoadForms("Uczniowie/stopnie.fxml");
            setIdLekcji(5);
        } else if (mouseEvent.getSource() == oceny_biologia_button) {
            LoadForms("Uczniowie/stopnie.fxml");
            setIdLekcji(6);
        }
    }

    public void setIdLekcji(int idLek){
        idLekcji = idLek;
        Stage stage1 = (Stage) border_pane.getScene().getWindow();
        stage1.setUserData(idLek);
    }

    private void LoadForms(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            border_pane.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

