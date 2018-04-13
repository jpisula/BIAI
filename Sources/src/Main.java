import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Collection;

public class Main extends Application {

    Stage window;
    Scene mainScene, gameScene, scoreScene;

    private char currentPlayer = 'X';
    private Cell[][] cell = new Cell[1000][1000];
    private Label statusMsg = new Label("X must play");

    @Override
    public void start(Stage primaryStage) throws Exception{

        window = primaryStage;

        //--- MAIN SCENE GRID PANE ---//
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

        CheckBox playerVsComCheck = new CheckBox("Player VS. Computer");
        grid.add(playerVsComCheck, 0, 2);
        CheckBox comVsComCheck = new CheckBox("Computer VS. Computer");
        grid.add(comVsComCheck, 1,2);

        Label chooseSize = new Label("Choose size of the board: ");
        grid.add(chooseSize, 0, 3);
        TextField sizeField = new TextField();
        grid.add(sizeField, 1, 3);

        Label chooseVictoryCond = new Label("Choose victory condition: ");
        grid.add(chooseVictoryCond, 0, 4);
        TextField victoryCondField = new TextField();
        grid.add(victoryCondField, 1, 4);

        Button startBtn = new Button("Start Game");
        startBtn.setOnAction(e -> window.setScene(gameScene));
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(startBtn);
        grid.add(hbBtn, 0, 5, 2, 1);

        //--- GAME SCENE GRID PANE ---//
        GridPane mainGameGrid = new GridPane();
        GridPane gameGrid = new GridPane();
        int size = 3;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                cell[i][j] = new Cell(size);
                gameGrid.add(cell[i][j], j, i);
            }
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gameGrid);
        borderPane.setTop(statusMsg);

        mainGameGrid.add(borderPane, 0, 0);

        Button endGameBtn = new Button("End Game");
        endGameBtn.setOnAction(e -> window.setScene(scoreScene));
        mainGameGrid.add(endGameBtn, 0, 1);

        //--- SCORE SCENE GRID PANE ---//
        GridPane scoreGrid = new GridPane();
        scoreGrid.setAlignment(Pos.CENTER);
        scoreGrid.setHgap(10);
        scoreGrid.setVgap(10);
        scoreGrid.setPadding(new Insets(25, 25, 25, 25));

        Button backToMainMenuBtn = new Button("Back to main menu");
        backToMainMenuBtn.setOnAction(e -> window.setScene(mainScene));
        scoreGrid.add(backToMainMenuBtn, 0, 0);




        //--- SETTING SCENES ---//

        mainScene = new Scene(grid, 500, 300);
        mainScene.getStylesheets().add
                (Main.class.getResource("MainMenuStyles.css").toExternalForm());

        gameScene = new Scene(mainGameGrid, 600, 600);

        scoreScene = new Scene(scoreGrid, 500, 300);

        window.setScene(mainScene);
        window.setTitle("BIAI - TicTacToe Game");
        window.show();
    }

    public boolean isBoardFull(int size) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(cell[i][j].getPlayer() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }



    public class Cell extends Pane {
        private char player = ' ';

        public Cell(int size) {
            setStyle("-fx-border-color: black");
            this.setPrefSize(700/size, 700/size);
            this.setOnMouseClicked(e -> handleClick());
        }

        private void handleClick(){
            if(player == ' '  && currentPlayer != ' '){
                setPlayer(currentPlayer);

                //tutaj sprawdzanie wygranej


                currentPlayer = (currentPlayer == 'x') ? '0' : 'X';
                statusMsg.setText(currentPlayer + " must play");
            }
        }

        public char getPlayer(){
            return player;
        }

        public void setPlayer(char c) {
            player = c;
            if(player == 'X') {
                Line line1 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, this.getHeight() - 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));

                Line line2 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                line2.endXProperty().bind(this.widthProperty().subtract(10));
                line2.startYProperty().bind(this.heightProperty().subtract(10));

                getChildren().addAll(line1, line2);
            } else if(player == 'Y') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight()/2, this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);

                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.BLUE);

                getChildren().add(ellipse);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
