package labelling;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Introduction extends VBox
{
    public Introduction(MainFrame mainFrame)
    {
        setPadding(new Insets(20));
        setSpacing(10);
        setAlignment(Pos.TOP_CENTER);

        Node para1 = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("para1.png")));
        Node example = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("example.png")));
        Node para2 = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("para2.png")));
        Button start = new Button("Start");
        start.setStyle("-fx-background-color: green;" +
                " -fx-text-fill: #003308;" +
                " -fx-font-weight: bold;" +
                " -fx-font-size:  24 px;");
        start.setOnAction(e -> mainFrame.gotoDisplay());
        getChildren().addAll(para1, example, para2, start);

    }
}
