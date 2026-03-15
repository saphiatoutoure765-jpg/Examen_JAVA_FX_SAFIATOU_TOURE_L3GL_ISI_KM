module com.gl.gestionclinique {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;
    requires java.desktop;
    requires org.controlsfx.controls;

    opens com.gl.gestionclinique to javafx.fxml;
    opens com.gl.gestionclinique.Controller to javafx.fxml;
    opens com.gl.gestionclinique.Model to org.hibernate.orm.core, java.persistence;

    exports com.gl.gestionclinique;
    exports com.gl.gestionclinique.Controller;
    exports com.gl.gestionclinique.Model;
    exports com.gl.gestionclinique.Service;
    exports com.gl.gestionclinique.Repository;
}