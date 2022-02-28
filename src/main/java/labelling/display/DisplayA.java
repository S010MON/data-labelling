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
import java.util.Stack;

import labelling.BackGround;
import lombok.Getter;
import lombok.Setter;

public class DisplayA extends Canvas
{
    @Getter private Image image;
    @Setter private BoundingBox template;
    private boolean drawingTemplate = false;
    private boolean usingTemplate = false;
    private Stack<BoundingBox> stack;
    private ArrayList<GuideLine> guideLines;
    private double zoom = 1d;
    private double zoomRate = 0.02;
    private Point2D click;
    private Point2D lastMousePos = new Point2D(0,0);
    private Point2D offset = new Point2D(0,0);

    public DisplayA(double width, double height)
    {
        super(width, height);
        image = null;
        template = null;
        stack = new Stack<>();
        guideLines = new ArrayList<>();
        setCursor(Cursor.CROSSHAIR);

        setOnMouseMoved(this::mouseMoved);
        setOnMousePressed(this::mousePressed);
        setOnMouseDragged(this::mouseDragged);
        setOnMouseReleased(this::mouseReleased);
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

        stack.forEach(e -> e.draw(gc, offset.getX(), offset.getY(), zoom));
        guideLines.forEach(e -> e.draw(gc));

        if(template != null)
            template.draw(gc, 0, 0);
    }

    public void setImage(Image image)
    {
        stack.clear();
        this.image = image;
        draw();
    }

    public void cancelTemplate()
    {
        usingTemplate = false;
        template = null;
        draw();
    }

    public void undo()
    {
        if(!stack.isEmpty())
            stack.pop();
        draw();
    }

    public void drawTemplate()
    {
        drawingTemplate = true;
        usingTemplate = false;
    }

    private void mouseMoved(MouseEvent e)
    {
        System.out.println("Mouse Moved");
        if (e.isShiftDown())
        {
            double dx = lastMousePos.getX() - e.getX();
            double dy = lastMousePos.getY() - e.getY();
            Point2D delta = new Point2D(dx, dy);
            shift(delta);
        }
        lastMousePos = new Point2D(e.getX(), e.getY());

        if (usingTemplate)
            updateTemplate(e.getX(), e.getY());
        updateGuidelines(e);
        draw();
    }

    private void mousePressed(MouseEvent e)
    {
        System.out.println("Mouse pressed at: " + e.getX() + " " + e.getY());
        click = new Point2D(e.getX(), e.getY());
    }

    private void mouseDragged(MouseEvent e)
    {
        System.out.println("Mouse dragged");
        template = new BoundingBox(click.getX(), click.getY(), e.getX() - click.getX(), e.getY() - click.getY());
        template.setColor(Color.BLUE);

        updateGuidelines(e);
        draw();
    }

    private void mouseReleased(MouseEvent e)
    {
        System.out.println("Mouse released");
        double x = click.getX();
        double y = click.getY();
        double w = e.getX() - click.getX();
        double h = e.getY() - click.getY();

        BoundingBox scaled = new BoundingBox((x - offset.getX()) / zoom,
                                             (y  - offset.getY()) / zoom,
                                              w / zoom,
                                              h / zoom);
        stack.push(scaled);
        click = new Point2D(0,0);
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

    public void updateTemplate(double x, double y)
    {
        template = new BoundingBox(x, y, template.getW(), template.getH());
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
