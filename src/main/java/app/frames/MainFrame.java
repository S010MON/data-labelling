package app.frames;

import app.Metrics;
import app.display.BackGround;
import app.display.BoundingBox;
import app.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import app.display.Display;
import lombok.Getter;
import java.io.File;
import java.util.ArrayList;

public class MainFrame extends BorderPane
{
    @Getter private Display display;
    @Getter private SidePane sidePane;
    @Getter private Introduction intro;
    private boolean gridEnabled;
    private boolean tempEnabled;
    private double width;
    private double height;
    private long startTime = 0;
    private int fileID = 1;
    private Logger logger;

    public MainFrame(double width, double height)
    {
        setTop(new TopMenu(this));
        setBackground(BackGround.GREY_50);
        this.height = height;
        this.width = width;
        intro = new Introduction(this);
        sidePane = new SidePane(this);
        setLeft(sidePane);
        setCenter(intro);

        logger = new Logger("data.csv");
        logger.setOutputCsv();
        logger.setPrependDateTime(true);

        gridEnabled = Math.random() > 0;
        tempEnabled = Math.random() > 0;
        if(tempEnabled)
            sidePane.enableTemplates();
        else
            sidePane.disableTemplates();
    }

    public void start()
    {
        display = new Display(width, height, gridEnabled, fileID);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("img_" + fileID +".png"));
        display.setImage(image);
        startTime = System.currentTimeMillis();
        setCenter(display);
    }

    public void finish()
    {
        long time = (System.currentTimeMillis() - startTime) / 1000;
        ArrayList<BoundingBox> userBoxes = display.getBoxes();
        ArrayList<BoundingBox> savedBoxes = Metrics.loadBoxes(fileID);
        double[] values = Metrics.compare(userBoxes, savedBoxes);
        logger.log(time, "img_" + fileID, gridEnabled, tempEnabled, values);

        if(fileID < 4)
        {
            fileID++;
            start();
        }
        else
        {
            fileID = 1;
            setCenter(intro);
        }
    }

    public void loadImage()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        File file = fileChooser.showOpenDialog(new Stage());
        Image img = new Image("file:" + file.getAbsolutePath());
        display.setImage(img);
    }
}
