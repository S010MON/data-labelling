package labelling;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.Getter;

public class Display extends Canvas
{
    @Getter private Image image;
    private ArrayList<BoundingBox> boxes;
    private ArrayList<GuideLine> guideLines;
    private BoundingBox template;

    public Display(double width, double height)
    {
        super(width, height);
        image = null;
        template = null;
        boxes = new ArrayList<>();
        guideLines = new ArrayList<>();
        setCursor(Cursor.CROSSHAIR);
        setOnMouseMoved(this::updateGuidelines);
        setOnMouseDragged(this::updateTemplate);
        setOnMouseReleased(this::addBoundingBox);
        draw();
    }

    public void draw()
    {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        if(image != null)
            gc.drawImage(image, 0, 0);

        boxes.forEach(e -> e.draw(gc));
        guideLines.forEach(e -> e.draw(gc));

        if(template != null)
            template.draw(gc);
    }

    public void setImage(Image image)
    {
        this.image = image;
        draw();
    }

    private void updateTemplate(MouseEvent e)
    {
        double x;
        double y;
        double w;
        double h;
        if(template == null)
        {
            x = e.getX();
            y = e.getY();
            w = 1;
            h = 1;
        }
        else
        {
            x = template.getX();
            y = template.getY();
            w = e.getX() - x;
            h = e.getY() - y;
        }
        template = new BoundingBox(x, y, w, h);
        updateGuidelines(e);
        draw();
    }

    private void addBoundingBox(MouseEvent e)
    {
        if(template == null)
            return;
        boxes.add(template);
        template = null;
        draw();
    }

    private void updateGuidelines(MouseEvent e)
    {
        guideLines.clear();

        GuideLine yLine = new GuideLine(0, e.getY(), getWidth(), e.getY());
        guideLines.add(yLine);

        GuideLine xLine = new GuideLine(e.getX(), 0, e.getX(), getHeight());
        guideLines.add(xLine);

        draw();
    }
}
