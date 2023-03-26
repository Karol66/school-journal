open module com.example.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires jakarta.persistence;
    requires com.calendarfx.view;
    requires itextpdf;
    requires javafx.base;

    exports com.example.projekt;
    exports com.example.projekt.entity;
}