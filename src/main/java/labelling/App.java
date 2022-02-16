package labelling;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        MainFrame mainFrame = new MainFrame(getWidth(), getHeight());
        stage.setTitle("Satellite Image Labelling");
        Scene scene = new Scene(mainFrame,getWidth(), getHeight());
        scene.setOnKeyPressed(mainFrame::handleKey);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }

    private static double getWidth()
    {
        return javafx.stage.Screen.getPrimary().getBounds().getWidth();
    }

    private static double getHeight()
    {
        return javafx.stage.Screen.getPrimary().getBounds().getHeight();
    }
}