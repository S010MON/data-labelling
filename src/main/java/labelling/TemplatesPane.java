package labelling;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TemplatesPane extends VBox
{
    public TemplatesPane(MainFrame mainFrame)
    {
        setPadding(new Insets(10));
        setSpacing(8);

        Label title = new Label("Templates");
        getChildren().add(title);

        Button newTemplate = new Button("Set");
        getChildren().add(newTemplate);
    }


}
