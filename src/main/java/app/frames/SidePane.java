package app.frames;

import app.display.BackGround;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class SidePane extends VBox
{
    private final boolean admin = false;
    private MainFrame mainFrame;
    private Button setTemplate;
    private Button cancelTemplate;

    public SidePane(MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;

        setPadding(new Insets(10));
        setSpacing(8);
        setBackground(BackGround.GREY_80);
        setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Templates");
        title.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        getChildren().add(title);

        setTemplate = new Button("Set");
        setTemplate.setOnAction(e -> {mainFrame.getDisplay().drawTemplate();
                                      setActive();});
        setTemplate.setMinWidth(50);
        getChildren().add(setTemplate);

        cancelTemplate = new Button("Clear");
        cancelTemplate.setOnAction(e -> {mainFrame.getDisplay().cancelTemplate();
                                         setDisabled();});
        cancelTemplate.setMinWidth(50);
        getChildren().add(cancelTemplate);

        Button undo = new Button("Undo");
        undo.setOnAction(e -> mainFrame.getDisplay().undo());
        undo.setMinWidth(50);

        Label ctrlTitle = new Label("Controls");
        ctrlTitle.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        Text ctrls = new Text("Click SET to draw\na new template\n" +
                "Press SHIFT and\n move mouse\n" +
                              "to move image\n");
        ctrls.setStyle("-fx-text-fill: white;");

        Button end = new Button("FINISH");
        end.setOnAction(e -> mainFrame.finish());
        end.setMinWidth(50);
        end.setStyle("-fx-background-color: red;");

        getChildren().addAll(undo, ctrlTitle, ctrls, end);

        if(admin)
        {
            Button print = new Button("print");
            print.setOnAction(e -> mainFrame.getDisplay().printBoundingBoxes());
            print.setMinWidth(50);
            getChildren().add(print);
        }
    }

    public void enableTemplates()
    {
        setTemplate.setOnAction(e -> mainFrame.getDisplay().drawTemplate());
        setTemplate.setStyle("-fx-background-color: white;");
        cancelTemplate.setOnAction(e -> mainFrame.getDisplay().cancelTemplate());
        cancelTemplate.setStyle("-fx-background-color: white;");
    }

    public void disableTemplates()
    {
        setTemplate.setOnAction(e -> e.consume());
        setTemplate.setStyle("-fx-background-color: grey;");
        cancelTemplate.setOnAction((e -> e.consume()));
        cancelTemplate.setStyle("-fx-background-color: grey;");
    }

    public void setActive()
    {
        if(setTemplate != null)
            setTemplate.setStyle("-fx-background-color: blue;");
    }

    public void setDisabled()
    {
        if(setTemplate != null)
            setTemplate.setStyle("-fx-background-color: white;");
    }
}
