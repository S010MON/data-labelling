module labelling
{
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    opens labelling to javafx.fxml;
    exports labelling;
    exports labelling.display;
    opens labelling.display to javafx.fxml;
}