package app.display;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class BackGround
{
    public static Color grey110 = Color.rgb(110, 110,110);
    public static Color grey100 = Color.rgb(100, 100,100);
    public static Color grey90 = Color.rgb(90, 90,90);
    public static Color grey80 = Color.rgb(80, 80,80);
    public static Color grey70 = Color.rgb(70, 70,70);
    public static Color grey60 = Color.rgb(60, 60,60);
    public static Color grey50 = Color.rgb(50, 50,50);
    public static Color grey40 = Color.rgb(40, 40,40);
    public static Color grey30 = Color.rgb(30, 30,30);
    public static Color grey20 = Color.rgb(20, 20,20);
    public static Color grey10 = Color.rgb(10, 10,10);

    public static Background BLACK = new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY));
    public static Background GREY_80 = new Background(new BackgroundFill(grey80, new CornerRadii(0), Insets.EMPTY));
    public static Background GREY_70 = new Background(new BackgroundFill(grey70, new CornerRadii(0), Insets.EMPTY));
    public static Background GREY_60 = new Background(new BackgroundFill(grey60, new CornerRadii(0), Insets.EMPTY));
    public static Background GREY_50 = new Background(new BackgroundFill(grey50, new CornerRadii(0), Insets.EMPTY));
    public static Background GREY_40 = new Background(new BackgroundFill(grey40, new CornerRadii(0), Insets.EMPTY));
    public static Background GREY_30 = new Background(new BackgroundFill(grey30, new CornerRadii(0), Insets.EMPTY));
    public static Background GREY_20 = new Background(new BackgroundFill(grey20, new CornerRadii(0), Insets.EMPTY));
    public static Background GREY_10 = new Background(new BackgroundFill(grey10, new CornerRadii(0), Insets.EMPTY));

}
