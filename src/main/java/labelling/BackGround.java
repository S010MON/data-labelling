package labelling;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class BackGround
{
    public static Background BLACK = new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY));
    public static Background GREY = new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), Insets.EMPTY));
    public static Background DARK_GREY = new Background(new BackgroundFill(Color.DARKGRAY, new CornerRadii(0), Insets.EMPTY));
}
