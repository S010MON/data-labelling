package labelling;

import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import labelling.display.DisplayA;
import lombok.Getter;

import java.io.File;

public class MainFrame extends BorderPane
{
    @Getter private DisplayA display;
    @Getter private SidePane menuPane;
    @Getter private Introduction intro;

    public MainFrame(double width, double height)
    {
        setTop(new TopMenu(this));
        setBackground(BackGround.GREY_50);

        intro = new Introduction(this);

        display = new DisplayA(width, height);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("img_9.png"));
        display.setImage(image);

        menuPane = new SidePane(this);
        setLeft(menuPane);

        gotoIntro();
    }

    public void gotoDisplay()
    {
        setCenter(display);
    }

    public void gotoIntro()
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
