package com.example.projekt;

import com.example.projekt.entity.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FrekfencjaController implements Initializable {

    @FXML
    private AnchorPane frekfencja_anchor_pane;

    @FXML
    private TableView<FakeFrekwencja> tabela;

    @FXML
    private TableColumn<FakeFrekwencja, Integer> index_tabela;

    @FXML
    private TableColumn<FakeFrekwencja, String> imie_tabela;

    @FXML
    private TableColumn<FakeFrekwencja, String> nazwisko_tabela;

    @FXML
    private TableColumn<FakeFrekwencja, String> matematyka_tabela;

    @FXML
    private TableColumn<FakeFrekwencja, String> polski_tabela;

    @FXML
    private TableColumn<FakeFrekwencja, String> geografia_tabela;

    @FXML
    private TableColumn<FakeFrekwencja, String> informatyka_tabela;

    @FXML
    private TableColumn<FakeFrekwencja, String> wf_tabela;

    @FXML
    private TableColumn<FakeFrekwencja, String> biologia_tabela;

    @FXML
    private TextField wyszukaj;

    @FXML
    private Button zapisz;

    @FXML
    private Button frekfencja_zapisz_do_pdf_bzapisz_do_pdfutton;


    @FXML
    private Button wyczysc;

    @FXML
    private Button zaktualizuj;

    @FXML
    private TextField index_text_field;

    @FXML
    private TextField imie_text_field;

    @FXML
    private TextField nazwisko_text_field;

    @FXML
    private TextField matematyka_text_field;

    @FXML
    private TextField polski_text_field;

    @FXML
    private TextField geografia_text_field;

    @FXML
    private TextField informatyka_text_field;

    @FXML
    private TextField wf_text_field;

    @FXML
    private TextField biologia_text_field;

    @FXML
    private Label obecnosc_alert1;

    @FXML
    private Label obecnosc_alert2;

    @FXML
    private Label obecnosc_alert3;

    @FXML
    private Label obecnosc_alert4;

    @FXML
    private Label obecnosc_alert5;

    @FXML
    private Label obecnosc_alert6;

    @FXML
    private Pagination paginacja;

    private List<Frekwencja> frekwencjaList;

    private List<FakeFrekwencja> fakeFrekfencjas = new ArrayList<>();

    private List<Integer> idUczniow = new ArrayList<>();

    private List<Uczen> uczniowieList = new ArrayList<>();

    ObservableList<FakeFrekwencja> fakeFrekwencjaObservableList = FXCollections.observableArrayList();
    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }

    public void fetchData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Frekwencja.class);
        config.addAnnotatedClass(Uczen.class);
        config.addAnnotatedClass(Oceny.class);
        config.addAnnotatedClass(Lekcje.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        uczniowieList = loadAllData(Uczen.class, session);
        frekwencjaList = loadAllData(Frekwencja.class, session);

        transaction.commit();
        session.close();
    }

    public void showData() {
        wyczysc();
        try {
            Configuration config = new Configuration().configure();
            config.addAnnotatedClass(Uczen.class);
            config.addAnnotatedClass(Frekwencja.class);
            config.addAnnotatedClass(Oceny.class);
            config.addAnnotatedClass(Lekcje.class);

            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            SessionFactory factory = config.buildSessionFactory(builder.build());

            Session session = factory.openSession();
            Transaction transaction = session.beginTransaction();

            FakeFrekwencja wybranyUczen = tabela.getSelectionModel().getSelectedItem();
            if (wybranyUczen != null) {
                Uczen wybraniec = new Uczen();

                for (int i = 0; i < uczniowieList.size(); i++) {
                    if (wybranyUczen.getIdUcznia() == uczniowieList.get(i).getId()) {
                        wybraniec = uczniowieList.get(i);
                    }
                }

                Uczen filnalnyUczen = session.find(Uczen.class, wybraniec.getId());
                List<Frekwencja> x = filnalnyUczen.getFrekwencja();

                List<Frekwencja> frekwencjaWybranegoUcznia = new ArrayList<>();
                for (int i = 0; i < x.size(); i++) {
                    frekwencjaWybranegoUcznia.add(x.get(i));
                }

                index_text_field.setText(String.valueOf(wybranyUczen.getIdUcznia()));
                imie_text_field.setText(wybranyUczen.getImie());
                nazwisko_text_field.setText(wybranyUczen.getNazwisko());

                int limit = frekwencjaWybranegoUcznia.size() + 1;

                if (limit > 1) matematyka_text_field.setText(String.valueOf(frekwencjaWybranegoUcznia.get(0).getFrekwencja()));
                if (limit > 2) polski_text_field.setText(String.valueOf(frekwencjaWybranegoUcznia.get(1).getFrekwencja()));
                if (limit > 3) geografia_text_field.setText(String.valueOf(frekwencjaWybranegoUcznia.get(2).getFrekwencja()));
                if (limit > 4) informatyka_text_field.setText(String.valueOf(frekwencjaWybranegoUcznia.get(3).getFrekwencja()));
                if (limit > 5) wf_text_field.setText(String.valueOf(frekwencjaWybranegoUcznia.get(4).getFrekwencja()));
                if (limit > 6) biologia_text_field.setText(String.valueOf(frekwencjaWybranegoUcznia.get(5).getFrekwencja()));

                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void editData() {
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
            Transaction transaction = session.beginTransaction();

            FakeFrekwencja fakeFrekwencjaUczen = tabela.getSelectionModel().getSelectedItem();
            if (fakeFrekwencjaUczen != null) {
                Uczen wybranyUczen = session.find(Uczen.class, fakeFrekwencjaUczen.getIdUcznia());

                List<Frekwencja> x = wybranyUczen.getFrekwencja();

                List<Frekwencja> frekwencjaWybranegoUcznia = new ArrayList<>();
                for (int i = 0; i < x.size(); i++) {

                    frekwencjaWybranegoUcznia.add(x.get(i));

                }

                String[] zaktualizowanaFrekwencja = new String[6];

                zaktualizowanaFrekwencja[0] = matematyka_text_field.getText();
                zaktualizowanaFrekwencja[1] = polski_text_field.getText();
                zaktualizowanaFrekwencja[2] = geografia_text_field.getText();
                zaktualizowanaFrekwencja[3] = informatyka_text_field.getText();
                zaktualizowanaFrekwencja[4] = wf_text_field.getText();
                zaktualizowanaFrekwencja[5] = biologia_text_field.getText();

                boolean sprawdz = true;

                if (!matematyka_text_field.getText().equals("")) {
                    if (Double.parseDouble(matematyka_text_field.getText()) > 100.0) {
                        obecnosc_alert1.setVisible(true);
                        sprawdz = false;
                    }
                }
                if (!polski_text_field.getText().equals("")) {
                    if (Double.parseDouble(polski_text_field.getText()) > 100.0) {
                        obecnosc_alert2.setVisible(true);
                        sprawdz = false;
                    }
                }
                if (!geografia_text_field.getText().equals("")) {
                    if (Double.parseDouble(geografia_text_field.getText()) > 100.0) {
                        obecnosc_alert3.setVisible(true);
                        sprawdz = false;
                    }
                }
                if (!informatyka_text_field.getText().equals("")) {
                    if (Double.parseDouble(informatyka_text_field.getText()) > 100.0) {
                        obecnosc_alert4.setVisible(true);
                        sprawdz = false;
                    }
                }
                if (!wf_text_field.getText().equals("")) {
                    if (Double.parseDouble(wf_text_field.getText()) > 100.0) {
                        obecnosc_alert5.setVisible(true);
                        sprawdz = false;
                    }
                }
                if (!biologia_text_field.getText().equals("")) {
                    if (Double.parseDouble(biologia_text_field.getText()) > 100.0) {
                        obecnosc_alert6.setVisible(true);
                        sprawdz = false;
                    }
                }

                if (sprawdz) {
                    for (int i = 0; i < zaktualizowanaFrekwencja.length; i++) {
                        if (i >= frekwencjaWybranegoUcznia.size() && !zaktualizowanaFrekwencja[i].equals("")) {
                            Frekwencja temp = new Frekwencja(zaktualizowanaFrekwencja[i], wybranyUczen);
                            System.out.println("nowa Ocena: " + temp);
                            session.persist(temp);
                        }
                        if (i < frekwencjaWybranegoUcznia.size() && !zaktualizowanaFrekwencja[i].equals("") && !frekwencjaWybranegoUcznia.get(i).getFrekwencja().equals(zaktualizowanaFrekwencja[i])) {
                            Frekwencja temp = frekwencjaWybranegoUcznia.get(i);
                            temp.setFrekwencja(zaktualizowanaFrekwencja[i]);
                            System.out.println("Zaktualizowana ocena: " + temp);
                            session.update(temp);
                        }
                    }

                    transaction.commit();
                    session.close();
                    zaktualizuj();
                    wyczysc();

                    obecnosc_alert1.setVisible(false);
                    obecnosc_alert2.setVisible(false);
                    obecnosc_alert3.setVisible(false);
                    obecnosc_alert4.setVisible(false);
                    obecnosc_alert5.setVisible(false);
                    obecnosc_alert6.setVisible(false);

                    LoadStages();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ObservableList<Frekwencja> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        index_tabela.setCellValueFactory(new PropertyValueFactory<>("idUcznia"));
        imie_tabela.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko_tabela.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        matematyka_tabela.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getFrekwencje(0))));
        polski_tabela.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getFrekwencje(1))));
        geografia_tabela.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getFrekwencje(2))));
        informatyka_tabela.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getFrekwencje(3))));
        wf_tabela.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getFrekwencje(4))));
        biologia_tabela.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getFrekwencje(5))));

        Platform.runLater(new Runnable() {
            public void run() {
                getData();
            }
        });

    }

    public int rowsPerPage() {
        return 10;
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage();
        int toIndex = Math.min(fromIndex + rowsPerPage(), fakeFrekwencjaObservableList.size());
        tabela.setItems(FXCollections.observableArrayList(fakeFrekwencjaObservableList.subList(fromIndex, toIndex)));
        return new BorderPane(tabela);
    }

    public void getData() {
        Stage stage = (Stage) tabela.getScene().getWindow();


        fetchData();
        for (Frekwencja temp : frekwencjaList) {
            observableList.add(temp);
        }
        zrupFakeFrekwencja();
        for (FakeFrekwencja temp : fakeFrekfencjas) {
            fakeFrekwencjaObservableList.add(temp);
        }
        tabela.setItems(fakeFrekwencjaObservableList);


        int pagination = 1;
        if (fakeFrekwencjaObservableList.size() % rowsPerPage() == 0) {
            pagination = fakeFrekwencjaObservableList.size() / rowsPerPage();
        } else if (fakeFrekwencjaObservableList.size() > rowsPerPage()) {
            pagination = fakeFrekwencjaObservableList.size() / rowsPerPage() + 1;
        }
        paginacja.setPageCount(pagination);
        paginacja.setCurrentPageIndex(0);
        paginacja.setPageFactory(this::createPage);
    }

    public void zrupFakeFrekwencja() {
        for (int i = 0; i < uczniowieList.size(); i++) {
            Integer z = uczniowieList.get(i).getId();
            if (!idUczniow.contains(z)) {
                idUczniow.add(z);
                FakeFrekwencja temp = new FakeFrekwencja();
                temp.setIdUcznia(uczniowieList.get(i).getId());
                temp.setImie(uczniowieList.get(i).getImie());
                temp.setNazwisko(uczniowieList.get(i).getNazwisko());
                fakeFrekfencjas.add(temp);
            }
        }

        for (int i = 0; i < observableList.size(); i++) {
            double frekwencja = Double.parseDouble(observableList.get(i).getFrekwencja());
            int indexUcznia = observableList.get(i).getIndex_ucznia().getId();
            for (int j = 0; j < fakeFrekfencjas.size(); j++) {
                if (fakeFrekfencjas.get(j).getIdUcznia() == indexUcznia) {
                    fakeFrekfencjas.get(j).dodajFrekwencje(frekwencja);
                    break;
                }
            }
        }
    }

    public void zaktualizuj() {
        observableList.removeAll(observableList);
        fakeFrekwencjaObservableList.removeAll(fakeFrekwencjaObservableList);
        fakeFrekfencjas.removeAll(fakeFrekfencjas);
        frekwencjaList.removeAll(frekwencjaList);
        idUczniow.removeAll(idUczniow);

        getData();
    }

    public void wyczysc() {
        index_text_field.setText("");
        imie_text_field.setText("");
        nazwisko_text_field.setText("");
        matematyka_text_field.setText("");
        polski_text_field.setText("");
        geografia_text_field.setText("");
        informatyka_text_field.setText("");
        wf_text_field.setText("");
        biologia_text_field.setText("");

        LoadStages();
    }

    @FXML
    private void pdfs()
            throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "karol0611");
            Statement stmt = con.createStatement();
            ResultSet query_set = stmt.executeQuery("SELECT * From frekwencja");
            Document my_pdf_report = new Document();
            PdfWriter.getInstance(my_pdf_report, new FileOutputStream("frekwencja.pdf"));
            my_pdf_report.open();
            PdfPTable my_report_table = new PdfPTable(2);
            PdfPCell table_cell;

            while (query_set.next()) {
                String index = query_set.getString("index_ucznia");
                table_cell = new PdfPCell(new Phrase(index));
                my_report_table.addCell(table_cell);

                String frekwencja = query_set.getString("frekwencja");
                table_cell = new PdfPCell(new Phrase(frekwencja));
                my_report_table.addCell(table_cell);

            }
            my_pdf_report.add(my_report_table);
            my_pdf_report.close();

            query_set.close();
            stmt.close();
            con.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        LoadStages();
    }

    private void LoadStages() {
        try {
            FXMLLoader x = new FXMLLoader(getClass().getResource("informacja.fxml"));
            Stage stage = new Stage();
            Parent root = x.load();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
