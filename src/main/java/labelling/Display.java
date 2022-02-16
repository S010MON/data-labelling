package labelling;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import lombok.Getter;

public class Display extends Canvas
{
    @Getter private Image image;
    private ArrayList<BoundingBox> boxes;
    private ArrayList<GuideLine> guideLines;
    private BoundingBox template;
    private boolean dragImage = false;
    private double zoomX = 1d;
    private double zoomY = 1d;
    private double zoomRate = 0.01;
    private double offsetX = 0d;
    private double offsetY = 0d;
    private double shiftAmount = 20;

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
        setOnScroll(this::zoom);
        setOnKeyPressed(this::handleKey);
        draw();
    }

    public void draw()
    {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        if(image != null)
            gc.drawImage(image,
                    offsetX,
                    offsetY,
                    image.getWidth() * zoomX,
                    image.getHeight() * zoomY);

        boxes.forEach(e -> e.draw(gc, offsetX, offsetY, zoomX, zoomY));
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
        dragImage = e.isShiftDown();

        switch (e.getText())
        {
            case "w" -> shiftUp();
            case "a" -> shiftLeft();
            case "s" -> shiftDown();
            case "d" -> shiftRight();
        }
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
        BoundingBox scaled = new BoundingBox(template.getX()/zoomX,
                                             template.getY() /zoomY,
                                             template.getW() / zoomX,
                                             template.getH() / zoomY);
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
        {
            zoomX += zoomRate;
            zoomY += zoomRate;
        }
        else if(dy < 0)
        {
            zoomX -= zoomRate;
            zoomY -= zoomRate;
        }
        draw();
    }

    private void shiftUp()
    {
        offsetY = offsetY - shiftAmount;
        System.out.println("w");
        draw();
    }

    private void shiftDown()
    {
        offsetY = offsetY + shiftAmount;
        draw();
    }

    private void shiftLeft()
    {
        offsetX = offsetX - shiftAmount;
        draw();
    }

    private void shiftRight()
    {
        offsetX = offsetX + shiftAmount;
        draw();
    }
}
