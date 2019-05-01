package sample;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimerServiceApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        TimerService service = new TimerService();
        AtomicInteger count = new AtomicInteger(0);
        Rectangle rectangle = new Rectangle(50, 10, 20,20);
        Pane pane = new Pane();
        pane.getChildren().add(rectangle);
        Scene scene = new Scene(pane, 300, 300);
        stage.setScene(scene);
        stage.show();

        service.setCount(count.get());
        service.setPeriod(Duration.seconds(1));
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                if (rectangle.getX() == 100){
                    service.cancel();
                }
                rectangle.setX(rectangle.getX() + 10);
            }
        });
        service.start();
    }

    public static void main(String[] args) {
        launch();
    }

    private class TimerService extends ScheduledService<Integer> {
        private IntegerProperty count = new SimpleIntegerProperty();

        public final void setCount(Integer value) {
            count.set(value);
        }

        public final Integer getCount() {
            return count.get();
        }

        public final IntegerProperty countProperty() {
            return count;
        }

        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                protected Integer call() {
                    //Adds 1 to the count
                    count.set(getCount() + 1);
                    return getCount();
                }
            };
        }
    }
}
