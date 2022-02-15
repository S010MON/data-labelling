module labelling.datalabelling {
    requires javafx.controls;
    requires javafx.fxml;


    opens labelling to javafx.fxml;
    exports labelling;
}