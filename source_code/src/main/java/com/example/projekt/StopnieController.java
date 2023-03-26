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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.Set;

public class StopnieController implements Initializable {
    @FXML
    private AnchorPane stopnie_anchor_pane;

    @FXML
    private TextField wyszukiwanie;

    @FXML
    private TableView<FakeOceny> tabela;

    @FXML
    private TableColumn<FakeOceny, String> index_tabela;

    @FXML
    private TableColumn<FakeOceny, String> imie;

    @FXML
    private TableColumn<FakeOceny, String> nazwisko;

    @FXML
    private TableColumn<FakeOceny, String> ocena_1;

    @FXML
    private TableColumn<FakeOceny, String> ocena_2;

    @FXML
    private TableColumn<FakeOceny, String> ocena_3;

    @FXML
    private TableColumn<FakeOceny, String> ocena_4;

    @FXML
    private TableColumn<FakeOceny, String> ocena_5;

    @FXML
    private TableColumn<FakeOceny, String> ocena_6;

    @FXML
    private TableColumn<FakeOceny, String> srednia;

    @FXML
    private Button zapis_do_pdf;

    @FXML
    private Button zapisz;

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
    private TextField ocena_text_field1;

    @FXML
    private TextField ocena_text_field2;

    @FXML
    private TextField ocena_text_field3;

    @FXML
    private TextField ocena_text_field4;

    @FXML
    private TextField ocena_text_field5;

    @FXML
    private TextField ocena_text_field6;

    @FXML
    private Label ocena_alert1;

    @FXML
    private Label ocena_alert2;

    @FXML
    private Label ocena_alert3;

    @FXML
    private Label ocena_alert4;

    @FXML
    private Label ocena_alert5;

    @FXML
    private Label ocena_alert6;

    @FXML
    private Pagination paginacja;

    private int idLekcji = 0;

    private List<Oceny> ocenaList;

    private List<FakeOceny> fakeocenies = new ArrayList<>();

    private List<Integer> idUczniow = new ArrayList<>();

    private List<Uczen> uczniowieList = new ArrayList<>();

    ObservableList<FakeOceny> fakeOcenyObservableList = FXCollections.observableArrayList();

    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }

    public void fetchData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Oceny.class);
        config.addAnnotatedClass(Lekcje.class);
        config.addAnnotatedClass(Uczen.class);
        config.addAnnotatedClass(Frekwencja.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        uczniowieList = loadAllData(Uczen.class, session);
        ocenaList = loadAllData(Oceny.class, session);

        transaction.commit();
        session.close();
    }

    public void showData() {
        wyczysc();
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

            FakeOceny wybranyUczen = tabela.getSelectionModel().getSelectedItem();
            if (wybranyUczen != null) {
                Uczen wybraniec = new Uczen();

                for (int i = 0; i < uczniowieList.size(); i++) {
                    if (wybranyUczen.getIdUcznia() == uczniowieList.get(i).getId()) {
                        wybraniec = uczniowieList.get(i);
                    }
                }

                Uczen filnalnyUczen = session.find(Uczen.class, wybraniec.getId());
                List<Oceny> x = filnalnyUczen.getOceny();

                List<Oceny> ocenyWybranegoUcznia = new ArrayList<>();
                for (int i = 0; i < x.size(); i++) {
                    if (x.get(i).getId_lekcje().getId() == idLekcji) {
                        ocenyWybranegoUcznia.add(x.get(i));
                    }
                }

                index_text_field.setText(String.valueOf(wybranyUczen.getIdUcznia()));
                imie_text_field.setText(wybranyUczen.getImie());
                nazwisko_text_field.setText(wybranyUczen.getNazwisko());

                int limit = ocenyWybranegoUcznia.size() + 1;

                if (limit > 1) ocena_text_field1.setText(String.valueOf(ocenyWybranegoUcznia.get(0).getOcena()));
                if (limit > 2) ocena_text_field2.setText(String.valueOf(ocenyWybranegoUcznia.get(1).getOcena()));
                if (limit > 3) ocena_text_field3.setText(String.valueOf(ocenyWybranegoUcznia.get(2).getOcena()));
                if (limit > 4) ocena_text_field4.setText(String.valueOf(ocenyWybranegoUcznia.get(3).getOcena()));
                if (limit > 5) ocena_text_field5.setText(String.valueOf(ocenyWybranegoUcznia.get(4).getOcena()));
                if (limit > 6) ocena_text_field6.setText(String.valueOf(ocenyWybranegoUcznia.get(5).getOcena()));

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

            //Pobieram wybranego ucznia z tabelki
            FakeOceny fakeOcenaUczen = tabela.getSelectionModel().getSelectedItem();
            if (fakeOcenaUczen != null) {
                Uczen wybranyUczen = session.find(Uczen.class, fakeOcenaUczen.getIdUcznia());
                Lekcje lekcja = session.find(Lekcje.class, idLekcji);

                //Wszystkie oceny ucznia
                List<Oceny> x = wybranyUczen.getOceny();

                //Oczeny ucznia z WYBRANEGO przedmiotu
                List<Oceny> ocenyWybranegoUcznia = new ArrayList<>();
                for (int i = 0; i < x.size(); i++) {
                    if (x.get(i).getId_lekcje().getId() == idLekcji) {
                        ocenyWybranegoUcznia.add(x.get(i));
                    }
                }

                //Pobieram wszystkie Oceny z TextFieldow i zapisuje do tablicy
                String[] zaktualizowaneOceny = new String[6];

                zaktualizowaneOceny[0] = ocena_text_field1.getText();
                zaktualizowaneOceny[1] = ocena_text_field2.getText();
                zaktualizowaneOceny[2] = ocena_text_field3.getText();
                zaktualizowaneOceny[3] = ocena_text_field4.getText();
                zaktualizowaneOceny[4] = ocena_text_field5.getText();
                zaktualizowaneOceny[5] = ocena_text_field6.getText();

                boolean sprawdz = true;

                if (!ocena_text_field1.getText().equals("")) {
                    if (Double.parseDouble(ocena_text_field1.getText()) > 6.0) {
                        ocena_alert1.setVisible(true);
                        sprawdz = false;
                    }
                }
                if (!ocena_text_field2.getText().equals("")) {
                    if (Double.parseDouble(ocena_text_field2.getText()) > 6.0) {
                        ocena_alert2.setVisible(true);
                        sprawdz = false;
                    }
                }
                if (!ocena_text_field3.getText().equals("")) {
                    if (Double.parseDouble(ocena_text_field3.getText()) > 6.0) {
                        ocena_alert3.setVisible(true);
                        sprawdz = false;
                    }
                }
                if (!ocena_text_field4.getText().equals("")) {
                    if (Double.parseDouble(ocena_text_field4.getText()) > 6.0) {
                        ocena_alert4.setVisible(true);
                        sprawdz = false;
                    }
                }
                if (!ocena_text_field5.getText().equals("")) {
                    if (Double.parseDouble(ocena_text_field5.getText()) > 6.0) {
                        ocena_alert5.setVisible(true);
                        sprawdz = false;
                    }
                }
                if (!ocena_text_field6.getText().equals("")) {
                    if (Double.parseDouble(ocena_text_field6.getText()) > 6.0) {
                        ocena_alert6.setVisible(true);
                        sprawdz = false;
                    }
                }

                if (sprawdz) {
                    //Dodawanie i aktualizowane ocen
                    for (int i = 0; i < zaktualizowaneOceny.length; i++) {
                        //Jesli ocena nie istnieje i w pole fieldText cos wpisano to tworzy nowa
                        if (i >= ocenyWybranegoUcznia.size() && !zaktualizowaneOceny[i].equals("")) {
                            Oceny temp = new Oceny(zaktualizowaneOceny[i], wybranyUczen, lekcja);
                            System.out.println("nowa Ocena: " + temp);
                            session.persist(temp);
                        }
                        //Jesli ocena istnieje i zostala zmieniona w fieldtext to ja aktualizuje
                        if (i < ocenyWybranegoUcznia.size() && !zaktualizowaneOceny[i].equals("") && !ocenyWybranegoUcznia.get(i).getOcena().equals(zaktualizowaneOceny[i])) {
                            Oceny temp = ocenyWybranegoUcznia.get(i);
                            temp.setOcena(zaktualizowaneOceny[i]);
                            System.out.println("Zaktualizowana ocena: " + temp);
                            session.update(temp);
                        }
                    }

                    transaction.commit();
                    session.close();
                    zaktualizuj();
                    wyczysc();

                    ocena_alert1.setVisible(false);
                    ocena_alert2.setVisible(false);
                    ocena_alert3.setVisible(false);
                    ocena_alert4.setVisible(false);
                    ocena_alert5.setVisible(false);
                    ocena_alert6.setVisible(false);

                    LoadStages();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    ObservableList<Oceny> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        index_tabela.setCellValueFactory(new PropertyValueFactory<>("idUcznia"));
        imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        ocena_1.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getOcena(0))));
        ocena_2.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getOcena(1))));
        ocena_3.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getOcena(2))));
        ocena_4.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getOcena(3))));
        ocena_5.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getOcena(4))));
        ocena_6.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getOcena(5))));
        srednia.setCellValueFactory(c -> new SimpleStringProperty((c.getValue().getSrednia())));

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
        int toIndex = Math.min(fromIndex + rowsPerPage(), fakeOcenyObservableList.size());
        tabela.setItems(FXCollections.observableArrayList(fakeOcenyObservableList.subList(fromIndex, toIndex)));
        return new BorderPane(tabela);
    }


    public void getData() {
        Stage stage = (Stage) tabela.getScene().getWindow();
        idLekcji = (Integer) stage.getUserData();

        fetchData();
        for (Oceny temp : ocenaList) {
            observableList.add(temp);
        }
        zrupFakeOceny();
        for (FakeOceny temp : fakeocenies) {
            fakeOcenyObservableList.add(temp);
        }
        tabela.setItems(fakeOcenyObservableList);

        int pagination = 1;
        if (fakeOcenyObservableList.size() % rowsPerPage() == 0) {
            pagination = fakeOcenyObservableList.size() / rowsPerPage();
        } else if (fakeOcenyObservableList.size() > rowsPerPage()) {
            pagination = fakeOcenyObservableList.size() / rowsPerPage() + 1;
        }
        paginacja.setPageCount(pagination);
        paginacja.setCurrentPageIndex(0);
        paginacja.setPageFactory(this::createPage);
    }

    public void zrupFakeOceny() {
        for (int i = 0; i < uczniowieList.size(); i++) {
            Integer z = uczniowieList.get(i).getId();
            if (!idUczniow.contains(z)) {
                idUczniow.add(z);
                FakeOceny temp = new FakeOceny();
                temp.setIdUcznia(uczniowieList.get(i).getId());
                temp.setImie(uczniowieList.get(i).getImie());
                temp.setNazwisko(uczniowieList.get(i).getNazwisko());
                fakeocenies.add(temp);
            }
        }

        for (int i = 0; i < observableList.size(); i++) {
            if (observableList.get(i).getId_lekcje().getId() != idLekcji) continue;
            double ocena = Double.parseDouble(observableList.get(i).getOcena());
            int indexUcznia = observableList.get(i).getIndex_ucznia().getId();
            for (int j = 0; j < fakeocenies.size(); j++) {
                if (fakeocenies.get(j).getIdUcznia() == indexUcznia) {
                    fakeocenies.get(j).dodajOcene(ocena);
                    break;
                }
            }
        }
    }

    public void zaktualizuj() {
        observableList.removeAll(observableList);
        fakeOcenyObservableList.removeAll(fakeOcenyObservableList);
        fakeocenies.removeAll(fakeocenies);
        ocenaList.removeAll(ocenaList);
        idUczniow.removeAll(idUczniow);

        getData();

        int pagination = 1;
        if (observableList.size() % rowsPerPage() == 0) {
            pagination = observableList.size() / rowsPerPage();
        } else if (observableList.size() > rowsPerPage()) {
            pagination = observableList.size() / rowsPerPage() + 1;
        }
        paginacja.setPageCount(pagination);
        paginacja.setPageFactory(this::createPage);
    }

    public void wyczysc() {
        index_text_field.setText("");
        imie_text_field.setText("");
        nazwisko_text_field.setText("");
        ocena_text_field1.setText("");
        ocena_text_field2.setText("");
        ocena_text_field3.setText("");
        ocena_text_field4.setText("");
        ocena_text_field5.setText("");
        ocena_text_field6.setText("");

        LoadStages();
    }

    @FXML
    private void pdfs()
            throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "karol0611");
            Statement stmt = con.createStatement();
            ResultSet query_set = stmt.executeQuery("SELECT * From oceny");
            Document my_pdf_report = new Document();
            PdfWriter.getInstance(my_pdf_report, new FileOutputStream("oceny.pdf"));
            my_pdf_report.open();
            PdfPTable my_report_table = new PdfPTable(3);
            PdfPCell table_cell;

            while (query_set.next()) {
                String index = query_set.getString("index_ucznia");
                table_cell = new PdfPCell(new Phrase(index));
                my_report_table.addCell(table_cell);
                String ocena = query_set.getString("ocena");
                table_cell = new PdfPCell(new Phrase(ocena));
                my_report_table.addCell(table_cell);
                String id_lekcje = query_set.getString("id_lekcje");
                table_cell = new PdfPCell(new Phrase(id_lekcje));
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