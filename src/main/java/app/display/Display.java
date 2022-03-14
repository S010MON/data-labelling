package app.display;

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
import app.logging.Logger;
import lombok.Getter;
import lombok.Setter;

public class Display extends Canvas
{
    @Getter private Image image;
    @Setter private BoundingBox template;
    @Getter private int fileID;
    private Stack<BoundingBox> boxes;
    private boolean drawingTemplate = false;
    private boolean usingTemplate = false;
    private boolean featuresEnabled;
    private ArrayList<GuideLine> guideLines;
    private double zoom = 1d;
    private double zoomRate = 0.02;
    private Point2D click;
    private Point2D lastMousePos = new Point2D(0,0);
    private Point2D offset = new Point2D(0,0);

    public Display(double width, double height, boolean featuresEnabled, int fileID)
    {
        super(width, height);
        image = null;
        template = null;
        boxes = new Stack<>();
        guideLines = new ArrayList<>();
        this.fileID = fileID;
        this.featuresEnabled = featuresEnabled;
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

    public void cancelTemplate()
    {
        usingTemplate = false;
        template = null;
        draw();
    }

    public void undo()
    {
        if(!boxes.isEmpty())
            boxes.pop();
        draw();
    }

    public void drawTemplate()
    {
        drawingTemplate = true;
        usingTemplate = false;
    }

    public void printBoundingBoxes()
    {
        Logger logger = new Logger("ans_4.txt");
        boxes.forEach(e -> logger.log(e.toString()));
    }

    public ArrayList<BoundingBox> getBoxes()
    {
        ArrayList<BoundingBox> out = new ArrayList<>();
        boxes.forEach(e -> out.add(e));
        return out;
    }

    private void mouseMoved(MouseEvent e)
    {
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
        click = new Point2D(e.getX(), e.getY());
    }

    private void mouseDragged(MouseEvent e)
    {
        if(!usingTemplate)
        {
            template = new BoundingBox(click.getX(), click.getY(), e.getX() - click.getX(), e.getY() - click.getY());
            template.setColor(Color.BLUE);
        }
        updateGuidelines(e);
        draw();
    }

    private void mouseReleased(MouseEvent e)
    {
        double x = (click.getX() - offset.getX()) / zoom;
        double y = (click.getY()  - offset.getY()) / zoom;
        double w = (e.getX() - click.getX())/ zoom;
        double h = (e.getY() - click.getY()) / zoom;
        BoundingBox template = new BoundingBox(x, y, w, h);

        if(drawingTemplate)
        {
            boxes.push(template);
            this.template = template;
            usingTemplate = true;
            drawingTemplate = false;
        }
        else if(usingTemplate)
        {
            BoundingBox temp = new BoundingBox(e.getX() - offset.getX(),
                                               e.getY() - offset.getY(),
                                               this.template.getW(),
                                               this.template.getH());
            boxes.push(temp);
        }
        else
        {
            boxes.push(template);
            this.template = null;
            click = new Point2D(0,0);
        }
        draw();
    }

    private void updateGuidelines(MouseEvent e)
    {
        if(featuresEnabled)
        {
            guideLines.clear();

            GuideLine yLine = new GuideLine(0, e.getY(), getWidth(), e.getY());
            guideLines.add(yLine);

            GuideLine xLine = new GuideLine(e.getX(), 0, e.getX(), getHeight());
            guideLines.add(xLine);

            draw();
        }
    }

    private void updateTemplate(double x, double y)
    {
        template = new BoundingBox(x, y, template.getW(), template.getH());
    }

    private void zoom(ScrollEvent e)
    {
//        double dy = e.getDeltaY();
//        if(dy > 0)
//            zoom += zoomRate;
//        else if(dy < 0)
//            zoom -= zoomRate;
//        draw();
    }

    private void shift(Point2D delta)
    {
        offset = offset.subtract(delta);
        draw();
    }
}
