module app
{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires lombok;
    exports app;
    exports app.logging;
    exports app.frames;
    exports app.display;
}