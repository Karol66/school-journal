package com.example.projekt;

import com.example.projekt.entity.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
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
import java.util.*;

public class ListaUczniowController implements Initializable {

    @FXML
    private AnchorPane lista_uczniow;

    @FXML
    private TextField lista_uczniow_wyszukaj_text_field;

    @FXML
    private TableView<Uczen> lista_uczniow_tabela;

    @FXML
    private TableColumn<Uczen, Integer> lista_uczniow_index_tabe;

    @FXML
    private TableColumn<Uczen, Integer> lista_uczniow_rok_tabe;

    @FXML
    private TableColumn<Uczen, String> lista_uczniow_profil_tabe;

    @FXML
    private TableColumn<Uczen, String> lista_uczniow_imie_tabe;

    @FXML
    private TableColumn<Uczen, String> lista_uczniow_nazwisko_tabe;

    @FXML
    private TableColumn<Uczen, String> lista_uczniow_plec_tabe;

    @FXML
    private TableColumn<Uczen, Date> lista_uczniow_data_urodzenia_tabe;

    @FXML
    private TextField lista_uczniow_index_text_field;

    @FXML
    private ComboBox<?> lista_uczniow_rok_combobox;

    @FXML
    private ComboBox<?> lista_uczniow_profil_combobox;

    @FXML
    private TextField lista_uczniow_imie_text_field;

    @FXML
    private TextField lista_uczniow_nazwisko_text_field;

    @FXML
    private ComboBox<?> lista_uczniow_plec_combobox;

    @FXML
    private DatePicker lista_uczniow_data_urodzenia_date_picker;

    @FXML
    private Button lista_uczniow_wyczysc_button;

    @FXML
    private Button lista_uczniow_usun_button;

    @FXML
    private Button lista_uczniow_zaktualizuj_button;

    @FXML
    private Button lista_uczniow_dodaj_button;

    @FXML
    private Button lista_uczniow_zapis_do_pdf_button;

    @FXML
    private Button lista_uczniow_edytuj_button;

    @FXML
    private Label data_alert;

    @FXML
    private Label imie_alert;

    @FXML
    private Label index_alert;

    @FXML
    private Label nazwisko_alert;

    @FXML
    private Label plec_alert;

    @FXML
    private Label profil_alert;

    @FXML
    private Label rok_alert;

    @FXML
    private Pagination paginacja;

    private List<Uczen> uczen;

    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }

    private String[] rokNauki = {"I", "II", "III", "IV", "V"};

    public void listaLat() {

        List<String> yearList = new ArrayList<>();

        for (String dane : rokNauki) {
            yearList.add(dane);
        }

        ObservableList oList = FXCollections.observableArrayList(rokNauki);
        lista_uczniow_rok_combobox.setItems(oList);
    }

    private String[] plec = {"Kobieta", "Mężczyzna"};

    public void listaPlec() {
        List<String> genderList = new ArrayList<>();

        for (String dane : plec) {
            genderList.add(dane);
        }

        ObservableList oList1 = FXCollections.observableArrayList(plec);
        lista_uczniow_plec_combobox.setItems(oList1);
    }

    private String[] profil = {"biol-chem", "mat-inf", "humanistyczny", "geograficzny"};

    public void listaProfili() {
        List<String> courseList = new ArrayList<>();

        for (String dane : profil) {
            courseList.add(dane);
        }

        ObservableList oList2 = FXCollections.observableArrayList(profil);
        lista_uczniow_profil_combobox.setItems(oList2);
    }

    public void setData() {
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

        Uczen x = new Uczen();

        if (isImieOk(lista_uczniow_imie_text_field.getText()) && isNazwiskoOk(lista_uczniow_nazwisko_text_field.getText())
                && isIndexOk(lista_uczniow_index_text_field.getText()) && lista_uczniow_data_urodzenia_date_picker.getValue() != null
                && lista_uczniow_rok_combobox.getSelectionModel().getSelectedIndex() != -1
                && lista_uczniow_profil_combobox.getSelectionModel().getSelectedIndex() != -1
                && lista_uczniow_plec_combobox.getSelectionModel().getSelectedIndex() != -1) {

//            if (!lista_uczniow_index_text_field.getText().equals(session.find(Uczen.class, session))) {
                x.setId(Integer.parseInt(lista_uczniow_index_text_field.getText()));
                x.setRok(lista_uczniow_rok_combobox.getValue().toString());
                x.setProfil(lista_uczniow_profil_combobox.getValue().toString());
                x.setImie(lista_uczniow_imie_text_field.getText());
                x.setNazwisko(lista_uczniow_nazwisko_text_field.getText());
                x.setPlec(lista_uczniow_plec_combobox.getValue().toString());
                x.setData_urodzenia(lista_uczniow_data_urodzenia_date_picker.getValue());

                session.persist(x);

                transaction.commit();
                session.close();
                LoadStages();
//            }else {
//                index_alert.setText("Podany index już istnieje !");
//                index_alert.setVisible(true);
//            }
        }
    }

    public void fetchData() {
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

        uczen = loadAllData(Uczen.class, session);

        transaction.commit();
        session.close();

    }

    public void deleteData() {
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

        Uczen x = new Uczen();

        x.setId(Integer.parseInt(lista_uczniow_index_text_field.getText()));
        x.setRok(lista_uczniow_rok_combobox.getValue().toString());
        x.setProfil(lista_uczniow_profil_combobox.getValue().toString());
        x.setImie(lista_uczniow_imie_text_field.getText());
        x.setNazwisko(lista_uczniow_nazwisko_text_field.getText());
        x.setPlec(lista_uczniow_plec_combobox.getValue().toString());
        x.setData_urodzenia(lista_uczniow_data_urodzenia_date_picker.getValue());


        if (isImieOk(lista_uczniow_imie_text_field.getText()) && isNazwiskoOk(lista_uczniow_nazwisko_text_field.getText())
                && isIndexOk(lista_uczniow_index_text_field.getText()) && lista_uczniow_data_urodzenia_date_picker.getValue() != null
                && lista_uczniow_rok_combobox.getSelectionModel().getSelectedIndex() != -1
                && lista_uczniow_profil_combobox.getSelectionModel().getSelectedIndex() != -1
                && lista_uczniow_plec_combobox.getSelectionModel().getSelectedIndex() != -1) {

            session.delete(x);

            transaction.commit();
            session.close();
            LoadStages();
        }
    }

    public void editData() {
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

        Uczen x = new Uczen();

        if (isImieOk(lista_uczniow_imie_text_field.getText()) && isNazwiskoOk(lista_uczniow_nazwisko_text_field.getText())
                && isIndexOk(lista_uczniow_index_text_field.getText()) && lista_uczniow_data_urodzenia_date_picker.getValue() != null
                && lista_uczniow_rok_combobox.getSelectionModel().getSelectedIndex() != -1
                && lista_uczniow_profil_combobox.getSelectionModel().getSelectedIndex() != -1
                && lista_uczniow_plec_combobox.getSelectionModel().getSelectedIndex() != -1) {

            x.setId(Integer.parseInt(lista_uczniow_index_text_field.getText()));
            x.setRok(lista_uczniow_rok_combobox.getValue().toString());
            x.setProfil(lista_uczniow_profil_combobox.getValue().toString());
            x.setImie(lista_uczniow_imie_text_field.getText());
            x.setNazwisko(lista_uczniow_nazwisko_text_field.getText());
            x.setPlec(lista_uczniow_plec_combobox.getValue().toString());
            x.setData_urodzenia(lista_uczniow_data_urodzenia_date_picker.getValue());

            session.update(x);

            transaction.commit();
            session.close();
            LoadStages();
        }
    }

    public void wyszukiwanie() {
        FilteredList<Uczen> filteredList = new FilteredList<>(observableList, e -> true);

        lista_uczniow_wyszukaj_text_field.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredList.setPredicate(predicateStudentData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateStudentData.getRok().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getProfil().toString().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getImie().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getNazwisko().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateStudentData.getPlec().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (String.valueOf(predicateStudentData.getId()).toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            SortedList<Uczen> sortedList = new SortedList<>(filteredList);

            sortedList.comparatorProperty().bind(lista_uczniow_tabela.comparatorProperty());
            lista_uczniow_tabela.setItems(sortedList);
        });
    }

    ObservableList<Uczen> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lista_uczniow_index_tabe.setCellValueFactory(new PropertyValueFactory<Uczen, Integer>("id"));
        lista_uczniow_rok_tabe.setCellValueFactory(new PropertyValueFactory<Uczen, Integer>("rok"));
        lista_uczniow_profil_tabe.setCellValueFactory(new PropertyValueFactory<Uczen, String>("profil"));
        lista_uczniow_imie_tabe.setCellValueFactory(new PropertyValueFactory<Uczen, String>("imie"));
        lista_uczniow_nazwisko_tabe.setCellValueFactory(new PropertyValueFactory<Uczen, String>("nazwisko"));
        lista_uczniow_plec_tabe.setCellValueFactory(new PropertyValueFactory<Uczen, String>("plec"));
        lista_uczniow_data_urodzenia_tabe.setCellValueFactory(new PropertyValueFactory<Uczen, Date>("data_urodzenia"));

        fetchData();
        listaLat();
        listaPlec();
        listaProfili();
        wyszukiwanie();

        for (Uczen temp : uczen) {
            observableList.add(temp);
        }
        lista_uczniow_tabela.setItems(observableList);


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
        lista_uczniow_tabela.setItems(FXCollections.observableArrayList(observableList.subList(fromIndex, toIndex)));
        return new BorderPane(lista_uczniow_tabela);
    }

    public void zaktualizuj() {
        fetchData();
        observableList.removeAll(observableList);
        for (Uczen temp : uczen) {
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
        lista_uczniow_index_text_field.setText("");
        lista_uczniow_rok_combobox.getSelectionModel().clearSelection();
        lista_uczniow_profil_combobox.getSelectionModel().clearSelection();
        lista_uczniow_imie_text_field.setText("");
        lista_uczniow_nazwisko_text_field.setText("");
        lista_uczniow_plec_combobox.getSelectionModel().clearSelection();
        lista_uczniow_data_urodzenia_date_picker.setValue(null);

        LoadStages();

    }

    public void wybrane() {


        Uczen uczenDane = lista_uczniow_tabela.getSelectionModel().getSelectedItem();
        int num = lista_uczniow_tabela.getSelectionModel().getSelectedIndex();


        lista_uczniow_index_text_field.setText(String.valueOf(uczenDane.getId()));
//        lista_uczniow_rok_combobox.setValue();
//        lista_uczniow_profil_combobox.;
        lista_uczniow_imie_text_field.setText(uczenDane.getImie());
        lista_uczniow_nazwisko_text_field.setText(uczenDane.getNazwisko());
//        lista_uczniow_plec_combobox.getSelectionModel().clearSelection();
        lista_uczniow_data_urodzenia_date_picker.setValue(uczenDane.getData_urodzenia());


    }

    @FXML
    private void pdfs()
            throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "karol0611");
            Statement stmt = con.createStatement();
            ResultSet query_set = stmt.executeQuery("SELECT * From uczen");
            Document my_pdf_report = new Document();
            PdfWriter.getInstance(my_pdf_report, new FileOutputStream("uczniowie.pdf"));
            my_pdf_report.open();
            PdfPTable my_report_table = new PdfPTable(7);
            PdfPCell table_cell;
            while (query_set.next()) {
                String index = query_set.getString("index_ucznia");
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
                String profil = query_set.getString("profil");
                table_cell = new PdfPCell(new Phrase(profil));
                my_report_table.addCell(table_cell);
                String rok = query_set.getString("rok");
                table_cell = new PdfPCell(new Phrase(rok));
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

    private boolean isIndexOk(String s) {
        return s.matches("[0-9]*[0-9]");
    }

    private boolean isImieOk(String s) {
        return s.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z])$");
    }

    private boolean isNazwiskoOk(String s) {
        return s.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z])$");
    }

    public void walidacja() {

        if (!isImieOk(lista_uczniow_imie_text_field.getText()) || lista_uczniow_imie_text_field.getText().equals("")) {
            imie_alert.setVisible(true);
        } else if (isImieOk(lista_uczniow_imie_text_field.getText())) {
            imie_alert.setVisible(false);
        }
        if (!isNazwiskoOk(lista_uczniow_nazwisko_text_field.getText()) || lista_uczniow_nazwisko_text_field.getText().equals("")) {
            nazwisko_alert.setVisible(true);
        } else if (isNazwiskoOk(lista_uczniow_nazwisko_text_field.getText())) {
            nazwisko_alert.setVisible(false);
        }
        if (!isIndexOk(lista_uczniow_index_text_field.getText()) || lista_uczniow_index_text_field.getText().equals("")) {
            index_alert.setVisible(true);
        } else if (isIndexOk(lista_uczniow_index_text_field.getText())) {
            index_alert.setVisible(false);
        }
        if (lista_uczniow_data_urodzenia_date_picker.getValue() == null) {
            data_alert.setVisible(true);
        } else if (lista_uczniow_data_urodzenia_date_picker.getValue() != null) {
            data_alert.setVisible(false);
        }
        if (lista_uczniow_rok_combobox.getSelectionModel().getSelectedIndex() == -1) {
            rok_alert.setVisible(true);
        } else if (lista_uczniow_rok_combobox.getSelectionModel().getSelectedIndex() != -1) {
            rok_alert.setVisible(false);
        }
        if (lista_uczniow_profil_combobox.getSelectionModel().getSelectedIndex() == -1) {
            profil_alert.setVisible(true);
        } else if (lista_uczniow_profil_combobox.getSelectionModel().getSelectedIndex() != -1) {
            profil_alert.setVisible(false);
        }
        if (lista_uczniow_plec_combobox.getSelectionModel().getSelectedIndex() == -1) {
            plec_alert.setVisible(true);
        } else if (lista_uczniow_plec_combobox.getSelectionModel().getSelectedIndex() != -1) {
            plec_alert.setVisible(false);
        }
    }
}

