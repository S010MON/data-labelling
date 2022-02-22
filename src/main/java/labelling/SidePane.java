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
        setBackground(BackGround.GREY);

        Label title = new Label("Templates");
        getChildren().add(title);

        Button setTemplate = new Button("Set");
        setTemplate.setOnAction(e -> mainFrame.getDisplay().setTemplate());
        getChildren().add(setTemplate);

        Button cancelTemplate = new Button("Cancel");
        cancelTemplate.setOnAction(e -> mainFrame.getDisplay().cancelTemplate());
        getChildren().add(cancelTemplate);

    }
}
