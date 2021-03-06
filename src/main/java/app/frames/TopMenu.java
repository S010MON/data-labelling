package app.frames;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import lombok.Getter;

@Getter
public class TopMenu extends MenuBar
{
    private MainFrame mainFrame;
    private MenuItem loadItem;
    private MenuItem saveItem;
    private MenuItem exitItem;

    public TopMenu(MainFrame mainFrame)
    {
        super();
        this.mainFrame = mainFrame;

        Menu file = new Menu("File");
        file.setStyle("-fx-text-fill: green;");

        Menu View = new Menu("View");
        file.setStyle("-fx-text-fill: green;");

        getMenus().addAll(file, View);

        loadItem = new MenuItem("Load");
        loadItem.setOnAction(e -> mainFrame.loadImage());
        saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> mainFrame.getDisplay().printBoundingBoxes());
        exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));

        file.getItems().addAll(loadItem, saveItem, exitItem);
    }
}
