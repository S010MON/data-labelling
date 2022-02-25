package labelling;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class App extends Application
{
    @Override
    public void start(Stage stage)
    {
        MainFrame mainFrame = new MainFrame(getWidth(), getHeight());
        stage.setTitle("Satellite Image Labelling");
        Scene scene = new Scene(mainFrame,getWidth(), getHeight());
        stage.setScene(scene);

        KeyCombination ctrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        Runnable r = () -> mainFrame.getDisplay().undo();
        scene.getAccelerators().put(ctrlZ, r);

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