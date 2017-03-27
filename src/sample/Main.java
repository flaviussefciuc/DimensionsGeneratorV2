package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.EventHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Dimensions Generator");
        Scene mainScene=new Scene(root, 800, 650);
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(mainScene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        //primaryStage.initStyle(StageStyle.TRANSPARENT);

        // allow the clock background to be used to drag the clock around.
        final Delta dragDelta = new Delta();
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = primaryStage.getX() - mouseEvent.getScreenX();
                dragDelta.y = primaryStage.getY() - mouseEvent.getScreenY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                primaryStage.setX(mouseEvent.getScreenX() + dragDelta.x);
                primaryStage.setY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });

        primaryStage.show();
    }

    // records relative x and y co-ordinates.
    class Delta { double x, y; }

    public static void main(String[] args) {
        launch(args);
    }
}
