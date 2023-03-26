package com.example.projekt;

import com.example.projekt.entity.Rejestracja;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

public class RejestracjaController {

    @FXML
    private Label email_alert;

    @FXML
    private Label nazwa_alert;

    @FXML
    private Label haslo_alert;

    @FXML
    private Label zarejestrowano;

    @FXML
    private TextField email;

    @FXML
    private TextField login;

    @FXML
    private PasswordField haslo;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button zaloguj;

    @FXML
    private Button zamknij;

    @FXML
    private Button zarejestruj;

    @FXML
    private Button zminimalizuj;

    @FXML
    private void handleBottomClick(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == zaloguj) {
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

    public void rejestr() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Rejestracja.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Rejestracja x = new Rejestracja();

        x.setEmail(email.getText());
        x.setLogin(login.getText());
        x.setHaslo(haslo.getText());

        if (isEmailOk(email.getText()) && isNazwaOk(login.getText()) && isHasloOk(haslo.getText())) {
            session.persist(x);
            transaction.commit();
            session.close();
            zarejestrowano.setVisible(true);
        }

    }

    public void zamknij() {
        System.exit(0);
    }

    public void zminimalizuj() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    private boolean isEmailOk(String s) {
        return s.matches("\\w{2,24}[a-z]\\d*@{1}\\w{0,24}[a-z].*");
    }

    private boolean isNazwaOk(String s) {
        return s.matches("\\w{2,24}[a-z].*");
    }

    private boolean isHasloOk(String s) {
        return s.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public void walidacja() {
        if (!isEmailOk(email.getText()) || email.getText().equals("")) {
            email_alert.setVisible(true);
        } else if (isEmailOk(email.getText())) {
            email_alert.setVisible(false);
        }

        if (!isNazwaOk(login.getText()) || login.getText().equals("")) {
            nazwa_alert.setVisible(true);
        } else if (isNazwaOk(login.getText())) {
            nazwa_alert.setVisible(false);
        }

        if (!isHasloOk(haslo.getText()) || haslo.getText().equals("")) {
            haslo_alert.setVisible(true);
        } else if (isHasloOk(haslo.getText())) {
            haslo_alert.setVisible(false);
        }
    }



}

