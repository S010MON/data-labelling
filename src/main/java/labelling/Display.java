package labelling;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class Display extends Canvas
{
    @Getter private Image image;
    @Setter private BoundingBox template;
    private ArrayList<BoundingBox> boxes;
    private ArrayList<GuideLine> guideLines;
    private double zoom = 1d;
    private double zoomRate = 0.02;
    private double shiftAmount = 20;
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
        setOnMouseMoved(this::handleMove);
        setOnMouseDragged(this::updateTemplate);
        setOnMouseReleased(this::addBoundingBox);
        setOnScroll(this::zoom);
        draw();
    }

    public void draw()
    {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
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

    public void handleKey(KeyEvent e)
    {
        switch (e.getText())
        {
            case "w" -> shiftUp();
            case "a" -> shiftLeft();
            case "s" -> shiftDown();
            case "d" -> shiftRight();
        }
    }

    public void handleMove(MouseEvent e)
    {
        if(e.isShiftDown())
        {
            double dx = lastMousePos.getX() - e.getX();
            double dy = lastMousePos.getY() - e.getY();
            Point2D delta = new Point2D(dx, dy);
            shift(delta);
        }
        lastMousePos = new Point2D(e.getX(), e.getY());
        updateGuidelines(e);
    }

    private void updateTemplate(MouseEvent e)
    {
        double x, y, h, w;
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
        BoundingBox scaled = new BoundingBox((template.getX() - offset.getX()) / zoom,
                                             (template.getY()  - offset.getY()) / zoom,
                                              template.getW() / zoom,
                                              template.getH() / zoom);
        boxes.add(scaled);
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

    private void shiftUp()
    {
        offset = offset.subtract(new Point2D(0, shiftAmount));
        draw();
    }

    private void shiftDown()
    {
        offset = offset.add(new Point2D(0, shiftAmount));
        draw();
    }

    private void shiftLeft()
    {
        offset = offset.subtract(new Point2D(shiftAmount, 0));
        draw();
    }

    private void shiftRight()
    {
        offset = offset.add(new Point2D(shiftAmount, 0));
        draw();
    }

    private void shift(Point2D delta)
    {
        offset = offset.subtract(delta);
        draw();
    }
}
