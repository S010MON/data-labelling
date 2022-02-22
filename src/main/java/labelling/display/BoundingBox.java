package labelling.display;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class BoundingBox
{
    @Setter @Getter private Color color;
    @Getter private double x;
    @Getter private double y;
    @Getter private double w;
    @Getter private double h;

    /**
     * Creates a new instance of {@code Rectangle2D}.
     *
     * @param x   The x coordinate of the upper-left corner of the {@code BoundingBox}
     * @param y   The y coordinate of the upper-left corner of the {@code BoundingBox}
     * @param w  The width of the {@code BoundingBox}
     * @param h The height of the {@code BoundingBox}
     */
    public BoundingBox(double x, double y, double w, double h)
    {
        this.color = Color.RED;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(GraphicsContext gc, double offsetX, double offsetY)
    {
        gc.setStroke(color);
        gc.strokeRect(x+offsetX, y+offsetY, w, h);
    }

    public void draw(GraphicsContext gc, double offsetX, double offsetY, double zoom)
    {
        gc.setStroke(color);
        double x = (this.x * zoom) + offsetX;
        double y = (this.y * zoom) + offsetY;
        gc.strokeRect(x, y, w * zoom, h * zoom);
    }
}
