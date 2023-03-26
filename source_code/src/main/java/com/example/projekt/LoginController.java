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
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField login;

    @FXML
    private PasswordField haslo;

    @FXML
    private Button zaloguj;

    @FXML
    private Button zamknij;

    @FXML
    private Button zminimalizuj;

    @FXML
    private Button zarejestruj;

    @FXML
    private Label haslo_alert;

    @FXML
    private Label nazwa_alert;

    @FXML
    private void handleBottomClick(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == zarejestruj) {
            main_form.getScene().getWindow().hide();
            LoadStages("rejestracja.fxml");
        }
        else if (mouseEvent.getSource() == zaloguj){
            main_form.getScene().getWindow().hide();
            LoadStages("menuGlowne.fxml");
        }
    }

    public void logowanie(javafx.event.ActionEvent mouseEvent) throws IOException{

        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Rejestracja.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Rejestracja where login=:login and haslo=:haslo");
        query.setParameter("login",login.getText());
        query.setParameter("haslo",haslo.getText());

        List list = query.list();

        if (list.size()==1){
            main_form.getScene().getWindow().hide();
            LoadStages("menuGlowne.fxml");
        }
        else {
            nazwa_alert.setVisible(true);
            haslo_alert.setVisible(true);
        }

        transaction.commit();
        session.close();
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

    public void zamknij(){
        System.exit(0);
    }

    public void zminimalizuj(){
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

}
