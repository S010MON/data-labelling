package labelling;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SidePane extends VBox
{
    public SidePane(MainFrame mainFrame)
    {
        setPadding(new Insets(10));
        setSpacing(8);

        Label title = new Label("Templates");
        getChildren().add(title);

        Button newTemplate = new Button("Set");
        newTemplate.setOnAction(e -> System.out.println("set pressed"));
        getChildren().add(newTemplate);
    }


}
