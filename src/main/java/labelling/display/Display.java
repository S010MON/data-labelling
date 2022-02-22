package labelling.display;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;

import labelling.BackGround;
import lombok.Getter;
import lombok.Setter;

public class Display extends Canvas
{
    @Getter private Image image;
    @Setter private BoundingBox template;
    private boolean usingTemplate = false;
    private ArrayList<BoundingBox> boxes;
    private ArrayList<GuideLine> guideLines;
    private double zoom = 1d;
    private double zoomRate = 0.02;
    private Point2D lastMousePos = new Point2D(0,0);
    private Point2D offset = new Point2D(0,0);

    public Display(double width, double height)
    {
        super(width, height);
        image = null;
        template = null;
        boxes = new ArrayList<>();
        guideLines = new ArrayList<>();
        setCursor(Cursor.CROSSHAIR);

        setOnMouseMoved(this::mouseMoved);
        setOnMouseDragged(this::updateTemplate);
        setOnMouseReleased(this::addBoundingBox);
        setOnScroll(this::zoom);
        draw();
    }

    public void draw()
    {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(BackGround.grey50);
        gc.fillRect(0, 0, getWidth(), getHeight());

        if(image != null)
        {
            gc.drawImage(image,
                    offset.getX(),
                    offset.getY(),
                    image.getWidth() * zoom,
                    image.getHeight() * zoom);
        }

        boxes.forEach(e -> e.draw(gc, offset.getX(), offset.getY(), zoom));
        guideLines.forEach(e -> e.draw(gc));

        if(template != null)
            template.draw(gc, 0, 0);
    }

    public void setImage(Image image)
    {
        boxes.clear();
        this.image = image;
        draw();
    }

    public void setTemplate()
    {
        usingTemplate = true;
        template = new BoundingBox(0, 0, 50, 50);
        template.setColor(Color.BLUE);
        draw();
    }

    public void cancelTemplate()
    {
        usingTemplate = false;
        template = null;
        draw();
    }

    private void mouseMoved(MouseEvent e)
    {
        if (e.isShiftDown()) {
            double dx = lastMousePos.getX() - e.getX();
            double dy = lastMousePos.getY() - e.getY();
            Point2D delta = new Point2D(dx, dy);
            shift(delta);
        }
        lastMousePos = new Point2D(e.getX(), e.getY());
        if (usingTemplate)
            updateTemplate(e);
        updateGuidelines(e);
    }

    private void updateTemplate(MouseEvent e)
    {
        double x, y, h, w;
        if(usingTemplate)
        {
            x = e.getX();
            y = e.getY();
            w = template.getW();
            h = template.getH();
        }
        else if(template == null)
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
        template.setColor(Color.BLUE);
        updateGuidelines(e);
        draw();
    }

    private void addBoundingBox(MouseEvent e)
    {
        if(template == null)
            return;
        BoundingBox scaled = new BoundingBox((template.getX() - offset.getX()) / zoom,
                                             (template.getY()  - offset.getY()) / zoom,
                                              template.getW() / zoom,
                                              template.getH() / zoom);
        boxes.add(scaled);
        if(!usingTemplate)
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

    private void zoom(ScrollEvent e)
    {
        double dy = e.getDeltaY();
        if(dy > 0)
            zoom += zoomRate;
        else if(dy < 0)
            zoom -= zoomRate;
        draw();
    }

    private void shift(Point2D delta)
    {
        offset = offset.subtract(delta);
        draw();
    }
}
