package labelling;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;

public class MainFrame extends BorderPane
{
    private MenuBar menuBar;
    @Getter private Display display;
    @Getter private SidePane menuPane;

    public MainFrame(double width, double height)
    {
        menuBar = generateMenuBar();
        setTop(menuBar);

        display = new Display(width, height);
        setCenter(display);

        menuPane = new SidePane(this);
        setLeft(menuPane);

        Image image = new Image(getClass().getClassLoader().getResourceAsStream("img_2.png"));
        display.setImage(image);
    }

    private MenuBar generateMenuBar()
    {
        MenuBar menuBar = new MenuBar();

        Menu file = new Menu("File");
        menuBar.getMenus().add(file);

        MenuItem loadItem = new MenuItem("Load");
        loadItem.setOnAction(e -> display.setImage(loadImage()));

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> save());

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));

        file.getItems().addAll(loadItem, saveItem, exitItem);
        return menuBar;
    }

    private Image loadImage()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        File file = fileChooser.showOpenDialog(new Stage());
        return new Image("file:"+file.getAbsolutePath());
    }

    private void save() {}

    public void handleKey(KeyEvent e)
    {
        display.handleKey(e);
    }
}
