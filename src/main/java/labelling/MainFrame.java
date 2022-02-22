package labelling;

import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;

public class MainFrame extends BorderPane
{
    @Getter private Display display;
    @Getter private SidePane menuPane;
    @Getter private Introduction intro;

    public MainFrame(double width, double height)
    {
        setTop(new TopMenu(this));

        intro = new Introduction(width, height);

        display = new Display(width, height);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("img_2.png"));
        display.setImage(image);

        menuPane = new SidePane(this);
        setLeft(menuPane);

        gotoDisplay();
    }


    private void gotoDisplay()
    {
        setCenter(display);
    }

    private void gotoIntro()
    {
        setCenter(intro);
    }

    public void loadImage()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        File file = fileChooser.showOpenDialog(new Stage());
        Image img = new Image("file:"+file.getAbsolutePath());
        display.setImage(img);
    }

    public void save() {}
}
