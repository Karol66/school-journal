package com.example.projekt;

import com.example.projekt.entity.Frekwencja;
import com.example.projekt.entity.Lekcje;
import com.example.projekt.entity.Oceny;
import com.example.projekt.entity.Uczen;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuUczenController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private BorderPane border_pane;

    @FXML
    private Button zamknij;

    @FXML
    private Button zminimalizuj;

    @FXML
    private Button menu_button;

    @FXML
    private Button frekfencja_button;

    @FXML
    private Button oceny_button;

    @FXML
    private Button lista_uczniow_button;

    @FXML
    private Button wyloguj;

    @FXML
    private Button cofnij;

    @FXML
    private AnchorPane menu_glowne;

    @FXML
    private Label liczba_wszystkich;

    @FXML
    private Label liczba_mezcyzn;

    @FXML
    private Label liczba_kobiet;

    @FXML
    private void handleBottomClick(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == cofnij) {
            main_form.getScene().getWindow().hide();
            LoadStages("menuGlowne.fxml");
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

    @FXML
    void switchForm(javafx.event.ActionEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() == menu_button) {
            LoadForms("Uczniowie/menu.fxml");
        } else if (mouseEvent.getSource() == lista_uczniow_button) {
            LoadForms("Uczniowie/listaUczniow.fxml");
        } else if (mouseEvent.getSource() == frekfencja_button) {
            LoadForms("Uczniowie/frekfencja.fxml");
        } else if (mouseEvent.getSource() == oceny_button) {
            LoadForms("Uczniowie/oceny.fxml");
        }
    }

    private void LoadForms(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            border_pane.setCenter(root);
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

    public void wyswietlWszystkichDodanych() {
        try {
            Configuration config = new Configuration().configure();
            config.addAnnotatedClass(Uczen.class);
            config.addAnnotatedClass(Oceny.class);
            config.addAnnotatedClass(Lekcje.class);
            config.addAnnotatedClass(Frekwencja.class);

            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            SessionFactory factory = config.buildSessionFactory(builder.build());

            Session session = factory.openSession();

            Query query = session.createQuery("select count(id) from Uczen");

            Long count = (Long) query.uniqueResult();

            liczba_wszystkich.setText(String.valueOf(count));

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void wyswietlDodanychKobiet() {
        try {
            Configuration config = new Configuration().configure();
            config.addAnnotatedClass(Uczen.class);
            config.addAnnotatedClass(Oceny.class);
            config.addAnnotatedClass(Lekcje.class);
            config.addAnnotatedClass(Frekwencja.class);

            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            SessionFactory factory = config.buildSessionFactory(builder.build());

            Session session = factory.openSession();


            Query query = session.createQuery("select count(id) from Uczen where plec=:plec");
            query.setParameter("plec", "Kobieta");
            Long count = (Long) query.uniqueResult();

            liczba_kobiet.setText(String.valueOf(count));
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void wyswietlDodanychMezczyzn() {
        try {
            Configuration config = new Configuration().configure();
            config.addAnnotatedClass(Uczen.class);
            config.addAnnotatedClass(Oceny.class);
            config.addAnnotatedClass(Lekcje.class);
            config.addAnnotatedClass(Frekwencja.class);


            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            SessionFactory factory = config.buildSessionFactory(builder.build());

            Session session = factory.openSession();


            Query query = session.createQuery("select count(id) from Uczen where plec=:plec");
            query.setParameter("plec", "Mężczyzna");
            Long count = (Long) query.uniqueResult();

            liczba_mezcyzn.setText(String.valueOf(count));
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Uczen> uczen;

    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }

    public void fetchData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Uczen.class);
        config.addAnnotatedClass(Oceny.class);
        config.addAnnotatedClass(Lekcje.class);
        config.addAnnotatedClass(Frekwencja.class);


        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        uczen = loadAllData(Uczen.class, session);

        transaction.commit();
        session.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fetchData();
        wyswietlWszystkichDodanych();
        wyswietlDodanychKobiet();
        wyswietlDodanychMezczyzn();
    }
}
