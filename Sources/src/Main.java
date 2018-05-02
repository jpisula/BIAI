import Model.GeneticAlgorithm;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class Main extends Application {

    Stage window;
    Scene mainScene, gameScene, scoreScene;

    private char currentPlayer = '2';
    //MAX SIZE OF THE BOARD IS 30X30!!!!!!
    private Cell[][] cell = new Cell[30][30];
    private Label statusMsg = new Label("O must play");
    private int size = 3;
    private String wejscie = "";
    private int noMove = 0;
    private boolean wygrana = false;

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
        startBtn.setOnAction(e -> {
            window.setScene(gameScene);


//            int ktory = 0;
//            for(int i = 0; i < size; i++) {
//                for(int j = 0; j < size; j++) {
//                    cell[i][j] = new Cell(size);
//                    ktory++;
//                    if(wejscie.charAt(ktory) == '1');
//                    cell[i][j].player = '1';
//
//
//                }
//            }
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(startBtn);
        grid.add(hbBtn, 0, 5, 2, 1);

        //--- GAME SCENE GRID PANE ---//
        GridPane mainGameGrid = new GridPane();
        GridPane gameGrid = new GridPane();

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
        endGameBtn.setOnAction(e -> {
            window.setScene(mainScene);
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    cell[i][j] = new Cell(size);
                    gameGrid.getChildren().clear();
                }
            }
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    cell[i][j] = new Cell(size);
                    gameGrid.add(cell[i][j], j, i);
                    cell[i][j].setNullPlayer();

                }
            }
        });
        mainGameGrid.add(endGameBtn, 0, 1);





        //--- SETTING SCENES ---//

        mainScene = new Scene(grid, 500, 300);
        mainScene.getStylesheets().add
                (Main.class.getResource("MainMenuStyles.css").toExternalForm());

        gameScene = new Scene(mainGameGrid, 600, 600);


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

    public boolean isBoardEmpty(int size) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(cell[i][j].getPlayer() != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasWon(char player, int size){
        for (int i = 0; i < size; i++){
            if(cell[i][0].getPlayer() == player && cell[i][1].getPlayer() == player && cell[i][2].getPlayer() == player)
                return true;
        }
        for (int i = 0; i < size; i++){
            if(cell[0][i].getPlayer() == player && cell[1][i].getPlayer() == player && cell[2][i].getPlayer() == player)
                return true;
        }
        if(cell[0][0].getPlayer() == player && cell[1][1].getPlayer() == player && cell[2][2].getPlayer() == player)
            return true;
        if(cell[0][2].getPlayer() == player && cell[1][1].getPlayer() == player && cell[2][0].getPlayer() == player)
            return true;

        return false;
    }

    public void generujWejscie(){
        int iloscRuchow = 0;
        wejscie = "";
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
            {

                if(cell[i][j].getPlayer() == ' ')
                    wejscie += '0';
                else {
                    wejscie += cell[i][j].getPlayer();
                    iloscRuchow++;
                }
            }

            wejscie += iloscRuchow;
    }

    public boolean GenerujRuch(int comMove){

        int ktory = 0;
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ktory++;
                if(ktory == comMove) {
                    if (cell[i][j].getPlayer() != ' ') {
                        return false;
                    } else {
                        cell[i][j].setPlayer('1');
                        generujWejscie();
                        return true;
                    }
                }
            }
        }
            return true;
    }

    public void dodajRuchNaPlansze(String board){
        //int pos = 0;
        for(int i = 0; i < size*size; i++) {
            if(board.charAt(i) != wejscie.charAt(i))
                GenerujRuch(i+1);
        }
        //System.out.println(pos);
    }



    public class Cell extends Pane {
        public char player = ' ';


        public Cell(int size) {
            setStyle("-fx-border-color: black");
            this.setPrefSize(700/size, 700/size);
            this.setOnMouseClicked(e -> {
                if(!wygrana)
                    handleClick(size);
            });
        }

        private void handleClick(int size) {
            noMove++;


                if (player == ' ' && currentPlayer != ' ') {
                    setPlayer(currentPlayer);
                    generujWejscie();
                }

                //tutaj sprawdzanie wygranej
            if(sprawdzWygrana(size)) return;
                    //ruch kompa
                    System.out.println(wejscie);
                    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(wejscie);
                    String board = geneticAlgorithm.start();
                    System.out.println(board);
                    dodajRuchNaPlansze(board);


                if(sprawdzWygrana(size)) return;
//            }
            //System.out.println(wejscie);
        }

        private boolean sprawdzWygrana(int size) {
            if (hasWon(currentPlayer, size)) {
                if(currentPlayer == '1')
                    statusMsg.setText("X won!");
                else
                    statusMsg.setText("O won!");
                currentPlayer = ' ';
                wygrana = true;
                return true;
            } else if (isBoardFull(size)) {
                statusMsg.setText("Draw!");
                currentPlayer = ' ';
                wygrana = true;
                return true;
            } else {
                currentPlayer = (currentPlayer == '1') ? '2' : '1';
                statusMsg.setText(currentPlayer + " must play");
                return false;
            }
        }


        public char getPlayer(){
            return player;
        }

        public void setNullPlayer() {
            player = ' ';
        }

        public void setPlayer(char c) {
            player = c;
            if(player == '1') {
                Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));

                Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
                line2.endXProperty().bind(this.widthProperty().subtract(10));
                line2.startYProperty().bind(this.heightProperty().subtract(10));

                getChildren().addAll(line1, line2);
            } else if(player == '2') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight()/2, this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);

                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.color(1,1,1,1));

                getChildren().add(ellipse);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
