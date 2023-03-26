package com.example.projekt;

import com.example.projekt.entity.Nauczyciel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
import java.time.LocalDate;
import java.util.*;

public class ListaNauczycieliController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label data_alert;

    @FXML
    private Label imie_alert;

    @FXML
    private Label nazwisko_alert;

    @FXML
    private Label index_alert;

    @FXML
    private Label plec_alert;

    @FXML
    private Label przedmiot_alert;

    @FXML
    private Label wyplata_alert;

    @FXML
    private TextField wyszukaj;

    @FXML
    private TableView<Nauczyciel> tabela;

    @FXML
    private TableColumn<Nauczyciel, Integer> index_tabela;

    @FXML
    private TableColumn<Nauczyciel, String> imie_tabela;

    @FXML
    private TableColumn<Nauczyciel, String> nazwisko_tabela;

    @FXML
    private TableColumn<Nauczyciel, String> plec_tabela;

    @FXML
    private TableColumn<Nauczyciel, Date> data_urodzenia_tabela;

    @FXML
    private TableColumn<Nauczyciel, String> prowadzony_przedmiot_tabela;

    @FXML
    private TableColumn<Nauczyciel, Double> wyplata_tabela;

    @FXML
    private TextField index_text_field;

    @FXML
    private ComboBox<?> prowadzony_przedmiot_combobox;

    @FXML
    private DatePicker data_urodzenia_date_picker;

    @FXML
    private Button wyczysc;

    @FXML
    private Button usun;

    @FXML
    private Button zaktualizuj;

    @FXML
    private Button dodaj;

    @FXML
    private Button zapis_do_pdf;

    @FXML
    private Button edytuj;

    @FXML
    private TextField imie_text_field;

    @FXML
    private TextField wyplata_text_field;

    @FXML
    private TextField nazwisko_text_field;

    @FXML
    private ComboBox<?> plec_combobox;

    private List<Nauczyciel> nauczyciel;

    @FXML
    private Pagination paginacja;

    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }

    private String[] plec = {"Kobieta", "Mężczyzna"};

    public void listaPlec() {
        List<String> genderList = new ArrayList<>();

        for (String dane : plec) {
            genderList.add(dane);
        }

        ObservableList oList = FXCollections.observableArrayList(plec);
        plec_combobox.setItems(oList);
    }

    private String[] prowadzonyPrzedmiot = {"Matematyka", "Język Polski", "Geografia", "Informatyka", "Wychowanie Fizyczne", "Biologia"};

    public void listaPrzedmiotow() {
        List<String> genderList = new ArrayList<>();

        for (String dane : prowadzonyPrzedmiot) {
            genderList.add(dane);
        }

        ObservableList oList1 = FXCollections.observableArrayList(prowadzonyPrzedmiot);
        prowadzony_przedmiot_combobox.setItems(oList1);
    }

    public void setData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Nauczyciel.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Nauczyciel x = new Nauczyciel();


        if (isWyplataOk(wyplata_text_field.getText()) && isIndexOk(index_text_field.getText()) && isImieOk(imie_text_field.getText()) &&
                isNazwiskoOk(nazwisko_text_field.getText()) && data_urodzenia_date_picker.getValue() != null &&
                prowadzony_przedmiot_combobox.getSelectionModel().getSelectedIndex() != -1 &&
                plec_combobox.getSelectionModel().getSelectedIndex() != -1) {

            x.setId(Integer.parseInt(index_text_field.getText()));
            x.setImie(imie_text_field.getText());
            x.setNazwisko(nazwisko_text_field.getText());
            x.setPlec(plec_combobox.getValue().toString());
            x.setData_urodzenia(data_urodzenia_date_picker.getValue());
            x.setProwadzony_przedmiot(prowadzony_przedmiot_combobox.getValue().toString());
            x.setWyplata(Double.parseDouble(wyplata_text_field.getText()));


            session.persist(x);
            transaction.commit();
            session.close();
            LoadStages();
        }
    }

    public void fetchData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Nauczyciel.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        nauczyciel = loadAllData(Nauczyciel.class, session);

        transaction.commit();
        session.close();
    }

    public void deleteData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Nauczyciel.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Nauczyciel x = new Nauczyciel();

        if (isWyplataOk(wyplata_text_field.getText()) && isIndexOk(index_text_field.getText()) && isImieOk(imie_text_field.getText()) &&
                isNazwiskoOk(nazwisko_text_field.getText()) && data_urodzenia_date_picker.getValue() != null &&
                prowadzony_przedmiot_combobox.getSelectionModel().getSelectedIndex() != -1 &&
                plec_combobox.getSelectionModel().getSelectedIndex() != -1) {

            x.setId(Integer.parseInt(index_text_field.getText()));
            x.setImie(imie_text_field.getText());
            x.setNazwisko(nazwisko_text_field.getText());
            x.setPlec(plec_combobox.getValue().toString());
            x.setData_urodzenia(data_urodzenia_date_picker.getValue());
            x.setProwadzony_przedmiot(prowadzony_przedmiot_combobox.getValue().toString());
            x.setWyplata(Double.parseDouble(wyplata_text_field.getText()));


            session.delete(x);

            transaction.commit();
            session.close();
            LoadStages();
        }
    }

    public void editData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Nauczyciel.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Nauczyciel x = new Nauczyciel();

        if (isWyplataOk(wyplata_text_field.getText()) && isIndexOk(index_text_field.getText()) && isImieOk(imie_text_field.getText()) &&
                isNazwiskoOk(nazwisko_text_field.getText()) && data_urodzenia_date_picker.getValue() != null &&
                prowadzony_przedmiot_combobox.getSelectionModel().getSelectedIndex() != -1 &&
                plec_combobox.getSelectionModel().getSelectedIndex() != -1) {

            x.setId(Integer.parseInt(index_text_field.getText()));
            x.setImie(imie_text_field.getText());
            x.setNazwisko(nazwisko_text_field.getText());
            x.setPlec(plec_combobox.getValue().toString());
            x.setData_urodzenia(data_urodzenia_date_picker.getValue());
            x.setProwadzony_przedmiot(prowadzony_przedmiot_combobox.getValue().toString());
            x.setWyplata(Double.parseDouble(wyplata_text_field.getText()));


            session.update(x);
            transaction.commit();
            session.close();
            LoadStages();
        }
    }

    public void wyszukiwanie() {
        FilteredList<Nauczyciel> filteredList = new FilteredList<>(observableList, e -> true);

        wyszukaj.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredList.setPredicate(predicateTeacherData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (String.valueOf(predicateTeacherData.getId()).toString().contains(searchKey)) {
                    return true;
                } else if (predicateTeacherData.getImie().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacherData.getNazwisko().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacherData.getPlec().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacherData.getData_urodzenia().toString().contains(searchKey)) {
                    return true;
                } else if (predicateTeacherData.getProwadzony_przedmiot().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (String.valueOf(predicateTeacherData.getWyplata()).toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            SortedList<Nauczyciel> sortedList = new SortedList<>(filteredList);

            sortedList.comparatorProperty().bind(tabela.comparatorProperty());
            tabela.setItems(sortedList);
        });
    }

    ObservableList<Nauczyciel> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        index_tabela.setCellValueFactory(new PropertyValueFactory<Nauczyciel, Integer>("id"));
        imie_tabela.setCellValueFactory(new PropertyValueFactory<Nauczyciel, String>("imie"));
        nazwisko_tabela.setCellValueFactory(new PropertyValueFactory<Nauczyciel, String>("nazwisko"));
        plec_tabela.setCellValueFactory(new PropertyValueFactory<Nauczyciel, String>("plec"));
        data_urodzenia_tabela.setCellValueFactory(new PropertyValueFactory<Nauczyciel, Date>("data_urodzenia"));
        prowadzony_przedmiot_tabela.setCellValueFactory(new PropertyValueFactory<Nauczyciel, String>("prowadzony_przedmiot"));
        wyplata_tabela.setCellValueFactory(new PropertyValueFactory<Nauczyciel, Double>("wyplata"));

        fetchData();
        listaPlec();
        listaPrzedmiotow();
        wyszukiwanie();

        for (Nauczyciel temp : nauczyciel) {
            observableList.add(temp);
        }
        tabela.setItems(observableList);

        int pagination = 1;
        if (observableList.size() % rowsPerPage() == 0) {
            pagination = observableList.size() / rowsPerPage();
        } else if (observableList.size() > rowsPerPage()) {
            pagination = observableList.size() / rowsPerPage() + 1;
        }
        paginacja.setPageCount(pagination);
        paginacja.setCurrentPageIndex(0);
        paginacja.setPageFactory(this::createPage);
    }

    public int rowsPerPage() {
        return 10;
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage();
        int toIndex = Math.min(fromIndex + rowsPerPage(), observableList.size());
        tabela.setItems(FXCollections.observableArrayList(observableList.subList(fromIndex, toIndex)));
        return new BorderPane(tabela);
    }

    public void zaktualizuj() {
        fetchData();
        observableList.removeAll(observableList);
        for (Nauczyciel temp : nauczyciel) {
            observableList.add(temp);
        }

        int pagination = 1;
        if (observableList.size() % rowsPerPage() == 0) {
            pagination = observableList.size() / rowsPerPage();
        } else if (observableList.size() > rowsPerPage()) {
            pagination = observableList.size() / rowsPerPage() + 1;
        }
        paginacja.setPageCount(pagination);
        paginacja.setPageFactory(this::createPage);

        LoadStages();
    }

    public void wyczysc() {
        index_text_field.setText("");
        imie_text_field.setText("");
        nazwisko_text_field.setText("");
        plec_combobox.getSelectionModel().clearSelection();
        data_urodzenia_date_picker.setValue(null);
        prowadzony_przedmiot_combobox.getSelectionModel().clearSelection();
        wyplata_text_field.setText("");

        LoadStages();
    }

    public void wybrane() {

            Nauczyciel nauczycielDane = tabela.getSelectionModel().getSelectedItem();
            int num = tabela.getSelectionModel().getSelectedIndex();

            index_text_field.setText(String.valueOf(nauczycielDane.getId()));
            imie_text_field.setText(String.valueOf(nauczycielDane.getImie()));
            nazwisko_text_field.setText(String.valueOf(nauczycielDane.getNazwisko()));
//        plec_combobox.setText(String.valueOf(nauczycielDane.getId()));
            data_urodzenia_date_picker.setValue(nauczycielDane.getData_urodzenia());
//        prowadzony_przedmiot_combobox.setText(String.valueOf(nauczycielDane.getId()));
            wyplata_text_field.setText(String.valueOf(nauczycielDane.getWyplata()));

    }

    @FXML
    private void pdfs()
            throws Exception {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "karol0611");
            Statement stmt = con.createStatement();
            ResultSet query_set = stmt.executeQuery("SELECT * From nauczyciel");
            Document my_pdf_report = new Document();
            PdfWriter.getInstance(my_pdf_report, new FileOutputStream("nauczyciele.pdf"));
            my_pdf_report.open();
            PdfPTable my_report_table = new PdfPTable(7);
            PdfPCell table_cell;

            while (query_set.next()) {
                String index = query_set.getString("index_nauczyciela");
                table_cell = new PdfPCell(new Phrase(index));
                my_report_table.addCell(table_cell);
                String imie = query_set.getString("imie");
                table_cell = new PdfPCell(new Phrase(imie));
                my_report_table.addCell(table_cell);
                String nazwisko = query_set.getString("nazwisko");
                table_cell = new PdfPCell(new Phrase(nazwisko));
                my_report_table.addCell(table_cell);
                String plec = query_set.getString("plec");
                table_cell = new PdfPCell(new Phrase(plec));
                my_report_table.addCell(table_cell);
                String data = query_set.getString("data_urodzenia");
                table_cell = new PdfPCell(new Phrase(data));
                my_report_table.addCell(table_cell);
                String przedmiot = query_set.getString("prowadzony_przedmiot");
                table_cell = new PdfPCell(new Phrase(przedmiot));
                my_report_table.addCell(table_cell);
                String wyplata = query_set.getString("wyplata");
                table_cell = new PdfPCell(new Phrase(wyplata));
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

    private boolean isWyplataOk(String s) {
        return s.matches("[0-9][0-9.]*[0-9]+[0-9]*");
    }

    private boolean isIndexOk(String s) {
        return s.matches("[0-9]*[0-9]");
    }

    private boolean isImieOk(String s) {
        return s.matches("^[a-zA-Z\\s]+");
    }

    private boolean isNazwiskoOk(String s) {
        return s.matches("^[a-zA-Z\\s]+");
    }

    public void walidacja() {
        if (!isWyplataOk(wyplata_text_field.getText()) || wyplata_text_field.getText().equals("")) {
            wyplata_alert.setVisible(true);
        } else if (isWyplataOk(wyplata_text_field.getText())) {
            wyplata_alert.setVisible(false);
        }
        if (!isImieOk(imie_text_field.getText()) || imie_text_field.getText().equals("")) {
            imie_alert.setVisible(true);
        } else if (isImieOk(imie_text_field.getText())) {
            imie_alert.setVisible(false);
        }
        if (!isNazwiskoOk(nazwisko_text_field.getText()) || nazwisko_text_field.getText().equals("")) {
            nazwisko_alert.setVisible(true);
        } else if (isNazwiskoOk(nazwisko_text_field.getText())) {
            nazwisko_alert.setVisible(false);
        }
        if (!isIndexOk(index_text_field.getText()) || index_text_field.getText().equals("")) {
            index_alert.setVisible(true);
        } else if (isIndexOk(index_text_field.getText())) {
            index_alert.setVisible(false);
        }
        if (data_urodzenia_date_picker.getValue() == null) {
            data_alert.setVisible(true);
        } else if (data_urodzenia_date_picker.getValue() != null) {
            data_alert.setVisible(false);
        }
        if (prowadzony_przedmiot_combobox.getSelectionModel().getSelectedIndex() == -1) {
            przedmiot_alert.setVisible(true);
        } else if (prowadzony_przedmiot_combobox.getSelectionModel().getSelectedIndex() != -1) {
            przedmiot_alert.setVisible(false);
        }
        if (prowadzony_przedmiot_combobox.getSelectionModel().getSelectedIndex() == -1) {
            plec_alert.setVisible(true);
        } else if (plec_combobox.getSelectionModel().getSelectedIndex() != -1) {
            plec_alert.setVisible(false);
        }
    }
}
