package app.display;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

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

        if (w < 0)
        {
            this.x = x + w;
            this.w = Math.abs(w);
        }

        if (h < 0)
        {
            this.y = y + h;
            this.h = Math.abs(h);
        }
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

    public double area()
    {
        return this.h * this.w;
    }

    @Override
    public String toString()
    {
        return x + " " + y + " " + h + " " + w;
    }

    public double IoU(BoundingBox other)
    {
        if(this.contains(other))
            return other.area() / this.area();

        if(other.contains(this))
            return this.area() / other.area();

        if(!overlap(other))
            return 0;

        /* Intersection */
        double x_inter_min = Math.max(this.getX(), other.getX());
        double y_inter_min = Math.max(this.getY(), other.getY());
        double x_inter_max = Math.min(this.getX() + this.getW(), other.getX()  + this.getW());
        double y_inter_max = Math.min(this.getY() + this.getH(), other.getY()  + this.getH());
        double width = Math.abs(x_inter_max - x_inter_min);
        double height = Math.abs(y_inter_max - y_inter_min);
        double inter_area = width * height;

        /* Union */
        double union_area = this.area() + other.area();

        if(inter_area > 0)
            union_area -= inter_area; // Inter area removed for double counting

        return inter_area / union_area;
    }

    public boolean overlap(BoundingBox other)
    {
        if(inside(other.x, other.y))
            return true;

        if(inside(other.x, other.y + other.w))
            return true;

        if(inside(other.x + other.w, other.y))
            return true;

        return inside(other.x + other.w, other.y + other.w);
    }

    private boolean contains(BoundingBox other)
    {
        return inside(other.x, other.y) &&
               inside(other.x + other.w, other.y) &&
               inside(other.x, other.y + other.h) &&
               inside(other.x + other.w, other.y + other.h);
    }

    private boolean inside(double pointX, double pointY)
    {
        return x <= pointX && pointX <= (x + w) && y <= pointY && pointY <= (y + w);
    }
}
