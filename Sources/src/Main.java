import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("BIAI - TicTacToe Game");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text welcomeText = new Text("Welcome in our TicTacToe Game!");
        welcomeText.setId("welcomeText");
        welcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(welcomeText, 0, 0, 2, 1);

        Label chooseMode = new Label("Choose game mode: ");
        grid.add(chooseMode, 0, 1);

        /*Button playerVsComBtn = new Button("Player VS. Computer");
        grid.add(playerVsComBtn, 0, 2);

        Button comVsComBtn = new Button("Computer VS. Computer");
        grid.add(comVsComBtn, 1,2);*/

        CheckBox playerVsComCheck = new CheckBox("Player VS. Computer");
        grid.add(playerVsComCheck, 0, 2);
        CheckBox comVsComCheck = new CheckBox("Computer VS. Computer");
        grid.add(comVsComCheck, 1,2);

        Label chooseSize = new Label("Choose size of the board: ");
        grid.add(chooseSize, 0, 3);
        TextField sizeField = new TextField();
        grid.add(sizeField, 1, 3);

        Label chooseVictroyCond = new Label("Choose victory condition: ");
        grid.add(chooseVictroyCond, 0, 4);
        TextField victoryCondField = new TextField();
        grid.add(victoryCondField, 1, 4);

        Button startBtn = new Button("Start Game");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(startBtn);
        grid.add(hbBtn, 0, 5, 2, 1);



        Scene scene = new Scene(grid, 500, 300);

        primaryStage.setScene(scene);
        scene.getStylesheets().add
                (Main.class.getResource("MainMenuStyles.css").toExternalForm());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
