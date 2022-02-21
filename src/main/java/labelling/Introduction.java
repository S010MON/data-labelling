package labelling;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Introduction extends Canvas
{
    public Introduction(double width, double height)
    {
        super(width, height);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream("img_2.png"));
        GraphicsContext gc = this.getGraphicsContext2D();

        gc.strokeText("Here is the introduction", 20, 20);
    }
}
