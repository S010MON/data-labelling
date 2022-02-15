package labelling;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class GuideLine
{
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public void draw(GraphicsContext gc)
    {
        gc.setStroke(Color.LIGHTGRAY);
        gc.strokeLine(x1, y1, x2, y2);
    }
}
