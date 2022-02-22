package labelling;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SidePane extends VBox
{
    public SidePane(MainFrame mainFrame)
    {
        setPadding(new Insets(10));
        setSpacing(8);
        setBackground(BackGround.GREY_80);
        setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Templates");
        title.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");

        Button setTemplate = new Button("Set");
        setTemplate.setOnAction(e -> mainFrame.getDisplay().setTemplate());
        setTemplate.setMinWidth(50);

        Button cancelTemplate = new Button("Clear");
        cancelTemplate.setOnAction(e -> mainFrame.getDisplay().cancelTemplate());
        cancelTemplate.setMinWidth(50);

        Button undo = new Button("Undo");
        undo.setOnAction(e -> mainFrame.getDisplay().undo());
        undo.setMinWidth(50);


        Label ctrlTitle = new Label("Controls");
        ctrlTitle.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");


        getChildren().addAll(title, setTemplate, cancelTemplate, undo, ctrlTitle);
    }
}
