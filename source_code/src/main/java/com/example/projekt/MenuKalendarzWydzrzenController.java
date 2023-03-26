package com.example.projekt;

import com.example.projekt.entity.Wydarzenia;
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MenuKalendarzWydzrzenController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private DatePicker data_date_picker;

    @FXML
    private TextField wydarzenie_text_field;

    @FXML
    private TextField wyszukaj;

    @FXML
    private TextField id_wydarzenie_text_field;

    @FXML
    private TableView<Wydarzenia> tabela;

    @FXML
    private TableColumn<Wydarzenia, String> wydzarzenie_tabela;

    @FXML
    private TableColumn<Wydarzenia, Date> data_tabela;

    @FXML
    private Button wyloguj;

    @FXML
    private Button cofnij;

    @FXML
    private Button zamknij;

    @FXML
    private Button zminimalizuj;
    @FXML
    private Button dodaj;

    @FXML
    private Button edytuj;

    @FXML
    private Button usun;

    @FXML
    private Button wyczysc;

    @FXML
    private Button zapisz_do_pdf;

    @FXML
    private Button zaktualizuj;

    @FXML
    private Label alert1;

    @FXML
    private Label alert2;

    @FXML
    private Pagination paginacja;

    private List<Wydarzenia> wydarzenia;

    private static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> data = session.createQuery(criteria).getResultList();
        return data;
    }

    public void setData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wydarzenia.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Wydarzenia x = new Wydarzenia();

        if (isWydarzenieOk(wydarzenie_text_field.getText()) && data_date_picker.getValue() != null) {

            x.setWydarzenie(wydarzenie_text_field.getText());
            x.setData(data_date_picker.getValue());

            session.persist(x);
            transaction.commit();
            session.close();
            LoadStagess();
        }
    }

    public void fetchData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wydarzenia.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        wydarzenia = loadAllData(Wydarzenia.class, session);

        transaction.commit();
        session.close();
    }

    public void deleteData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wydarzenia.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Wydarzenia x = new Wydarzenia();

        if (isWydarzenieOk(wydarzenie_text_field.getText()) && data_date_picker.getValue() != null) {

            x.setId(Integer.parseInt(id_wydarzenie_text_field.getText()));
            x.setWydarzenie(wydarzenie_text_field.getText());
            x.setData(data_date_picker.getValue());

            session.delete(x);
            transaction.commit();
            session.close();
            LoadStagess();
        }
    }

    public void editData() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Wydarzenia.class);

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        SessionFactory factory = config.buildSessionFactory(builder.build());

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        Wydarzenia x = new Wydarzenia();

        if (isWydarzenieOk(wydarzenie_text_field.getText()) && data_date_picker.getValue() != null) {

            x.setId(Integer.parseInt(id_wydarzenie_text_field.getText()));
            x.setWydarzenie(wydarzenie_text_field.getText());
            x.setData(data_date_picker.getValue());

            session.update(x);
            transaction.commit();
            session.close();
            LoadStagess();
        }
    }

    public void wyszukiwanie() {
        FilteredList<Wydarzenia> filteredList = new FilteredList<>(observableList, e -> true);

        wyszukaj.textProperty().addListener((Observable, oldValue, newValue) -> {
            filteredList.setPredicate(predicateEventData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (String.valueOf(predicateEventData.getWydarzenie()).toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateEventData.getData().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            SortedList<Wydarzenia> sortedList = new SortedList<>(filteredList);

            sortedList.comparatorProperty().bind(tabela.comparatorProperty());
            tabela.setItems(sortedList);
        });
    }

    ObservableList<Wydarzenia> observableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        wydzarzenie_tabela.setCellValueFactory(new PropertyValueFactory<Wydarzenia, String>("wydarzenie"));
        data_tabela.setCellValueFactory(new PropertyValueFactory<Wydarzenia, Date>("data"));

        fetchData();
        wyszukiwanie();

        for (Wydarzenia temp : wydarzenia) {
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
        return 15;
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
        for (Wydarzenia temp : wydarzenia) {
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


        LoadStagess();
    }

    public void wyczysc() {
        id_wydarzenie_text_field.setText("");
        wydarzenie_text_field.setText("");
        data_date_picker.setValue(null);
        LoadStagess();
    }

    public void wybrane() {

        Wydarzenia dane = tabela.getSelectionModel().getSelectedItem();

        id_wydarzenie_text_field.setText(String.valueOf(dane.getId()));
        wydarzenie_text_field.setText(String.valueOf(dane.getWydarzenie()));
        data_date_picker.setValue(dane.getData());

    }

    @FXML
    private void pdfs()
            throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projekt", "root", "karol0611");
            Statement stmt = con.createStatement();
            ResultSet query_set = stmt.executeQuery("SELECT * From wydarzenia");
            Document my_pdf_report = new Document();
            PdfWriter.getInstance(my_pdf_report, new FileOutputStream("wydarzenia.pdf"));
            my_pdf_report.open();
            PdfPTable my_report_table = new PdfPTable(2);
            PdfPCell table_cell;

            while (query_set.next()) {
                String wydarzenie = query_set.getString("wydarzenie");
                table_cell = new PdfPCell(new Phrase(wydarzenie));
                my_report_table.addCell(table_cell);
                String data = query_set.getString("data");
                table_cell = new PdfPCell(new Phrase(data));
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
        LoadStagess();
    }

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

    private void LoadStagess() {
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

    public void zamknij() {
        System.exit(0);
    }

    public void zminimalizuj() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    private boolean isWydarzenieOk(String s) {
        return s.matches("\\w{2,24}[a-z].*");
    }

    public void walidacja() {
        if (!isWydarzenieOk(wydarzenie_text_field.getText()) || wydarzenie_text_field.getText().equals("")) {
            alert1.setVisible(true);
        } else if (isWydarzenieOk(wydarzenie_text_field.getText())) {
            alert1.setVisible(false);
        }
        if (data_date_picker.getValue() == null) {
            alert2.setVisible(true);
        } else if (data_date_picker.getValue() != null) {
            alert2.setVisible(false);
        }
    }
}
