import Model.GeneticAlgorithm;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    Stage window;
    Scene mainScene, gameScene, scoreScene;

    private char currentPlayer  = '2';
    //MAX SIZE OF THE BOARD IS 30X30!!!!!!
    private Cell[][] cell       = new Cell[30][30];
    private Label statusMsg     = new Label("O must play");
    private int size            = 3;
    private String wejscie      = "";
    private int noMove          = 0;
    private boolean wygrana     = false;

    private boolean mode = false;
    private boolean mode2 = true;
    private RadioButton comVsComCheck;
    private RadioButton playerVsComCheck;
    private GridPane gameGrid;
    GridPane summaryGrid;
    private Vector<String> moves;

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        moves = new Vector<>();
        summaryGrid = new GridPane();


        //--- MAIN SCENE GRID PANE ---//
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text welcomeText = new Text("Welcome in TicTacToe Game!");
        welcomeText.setId("welcomeText");
        welcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(welcomeText, 0, 0, 2, 1);

        Label chooseMode = new Label("Choose game mode: ");
        grid.add(chooseMode, 0, 1);

        final ToggleGroup group = new ToggleGroup();

        playerVsComCheck = new RadioButton();
        playerVsComCheck.setToggleGroup(group);
        playerVsComCheck.setText("Player vs Computer");
        playerVsComCheck.setSelected(true);

        comVsComCheck = new RadioButton();
        comVsComCheck.setToggleGroup(group);
        comVsComCheck.setText("Computer vs Computer");
        grid.add(playerVsComCheck, 0, 2);
        grid.add(comVsComCheck, 1, 2);

        Button startBtn = new Button("Start Game");
        startBtn.setOnAction(e -> {
            window.setScene(gameScene);
            });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(startBtn);
        grid.add(hbBtn, 0, 5, 2, 1);

        //--- GAME SCENE GRID PANE ---//
        GridPane mainGameGrid = new GridPane();
        gameGrid = new GridPane();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cell[i][j] = new Cell(size);
                gameGrid.add(cell[i][j], j, i);
            }
        }


        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gameGrid);
        borderPane.setTop(statusMsg);



        Button endGameBtn = new Button("End Game");
        endGameBtn.setOnAction(e -> {
            if(playerVsComCheck.isSelected()) {
                window.setScene(mainScene);
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        cell[i][j] = new Cell(size);
                        gameGrid.getChildren().clear();
                    }
                }
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        cell[i][j] = new Cell(size);
                        gameGrid.add(cell[i][j], j, i);
                        cell[i][j].setNullPlayer();
                    }
                }
                statusMsg.setText("O must play");
                borderPane.setCenter(gameGrid);
                borderPane.setTop(statusMsg);
                currentPlayer = '2';
                wygrana = false;
                noMove = 0;
                wejscie = "";
                mode = false;
                mode2 = true;
            } else {
                //dodanie okna podsumowania --------------------------------------------------------------------------
                summaryGrid.getChildren().clear();
                summaryGrid.setPadding(new Insets(5, 5, 0, 10));
                Text summaryText = new Text("Summary of Computer vs. Computer mode:");
                summaryText.setId("summaryText");
                summaryText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
                summaryGrid.add(summaryText, 0, 0, 2, 1);

                String text = "\n";
                System.out.println(moves.size());
                String temp = "";
                for(int r = 0; r < moves.size(); r++) {
                    text += "Move number " + (r+1) + ":\n";
                    temp = moves.get(r);
                    for (int i = 0; i < size*size; i++) {
                        if((i%3 == 0) && (i > 1)) text += "\n";
                        //System.out.println(moves.get(i));
                        if(temp.charAt(i) == '1')
                            text += "X" + "\t";
                        else if(temp.charAt(i) == '2')
                            text += "O" + "\t";
                        else
                            text += "-" + "\t";
                    }
                    text += "\n";
                }
                Text movesText = new Text(text);
                movesText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
                summaryGrid.add(movesText, 0, 1, 1, 1);

                Button mainMenuBtn = new Button("Back To Main Menu");
                mainMenuBtn.setOnMouseClicked(f ->{
                    window.setScene(mainScene);

                });
                summaryGrid.add(mainMenuBtn, 3, 1, 1, 1);

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        cell[i][j] = new Cell(size);
                        gameGrid.getChildren().clear();
                    }
                }
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        cell[i][j] = new Cell(size);
                        gameGrid.add(cell[i][j], j, i);
                        cell[i][j].setNullPlayer();
                    }
                }
                statusMsg.setText("O must play");
                borderPane.setCenter(gameGrid);
                borderPane.setTop(statusMsg);
                currentPlayer = '2';
                wygrana = false;
                noMove = 0;
                wejscie = "";
                mode = false;
                mode2 = false;
                moves.clear();
                window.setScene(scoreScene);
            }
        });
        mainGameGrid.add(borderPane, 0, 0);
        mainGameGrid.add(endGameBtn, 0, 1);

        //--- SETTING SCENES ---//

        mainScene = new Scene(grid, 500, 300);
        mainScene.getStylesheets().add
                (Main.class.getResource("MainMenuStyles.css").toExternalForm());

        gameScene = new Scene(mainGameGrid, 600, 600);

        scoreScene = new Scene(summaryGrid, 600, 600);


        window.setScene(mainScene);
        window.setTitle("BIAI - TicTacToe Game");
        window.show();
    }

    public boolean isBoardFull(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cell[i][j].getPlayer() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isBoardEmpty(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cell[i][j].getPlayer() != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasWon(char player, int size) {
        for (int i = 0; i < size; i++) {
            if (cell[i][0].getPlayer() == player && cell[i][1].getPlayer() == player && cell[i][2].getPlayer() == player)
                return true;
        }
        for (int i = 0; i < size; i++) {
            if (cell[0][i].getPlayer() == player && cell[1][i].getPlayer() == player && cell[2][i].getPlayer() == player)
                return true;
        }
        if (cell[0][0].getPlayer() == player && cell[1][1].getPlayer() == player && cell[2][2].getPlayer() == player)
            return true;
        if (cell[0][2].getPlayer() == player && cell[1][1].getPlayer() == player && cell[2][0].getPlayer() == player)
            return true;

        return false;
    }

    public void generujWejscie() {
        int iloscRuchow = 0;
        wejscie = "";
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {

                if (cell[i][j].getPlayer() == ' ')
                    wejscie += '0';
                else {
                    wejscie += cell[i][j].getPlayer();
                    iloscRuchow++;
                }
            }
        wejscie += iloscRuchow;
    }

    public boolean GenerujRuch(int comMove, char numb) {

        int ktory = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ktory++;
                if (ktory == comMove) {
                    if (cell[i][j].getPlayer() != ' ') {
                        return false;
                    } else {
                        cell[i][j].setPlayer(numb);
                        generujWejscie();
                        return true;
                    }
                }
            }
        }
        return true;
    }

    public void dodajRuchNaPlansze(String board) {
        for (int i = 0; i < size * size; i++) {
            if (board.charAt(i) != wejscie.charAt(i))
                GenerujRuch(i + 1, board.charAt(i));
        }
    }

    public boolean sprawdzWygrana(int size) {
        if (hasWon(currentPlayer, size)) {
            gameGrid = new GridPane();
            if (currentPlayer == '1')
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
            currentPlayer = (currentPlayer == '1') ? 'O' : 'X';
            statusMsg.setText(currentPlayer + " must play");
            currentPlayer = (currentPlayer == 'X') ? '1' : '2';
            return false;
        }
    }


    public class Cell extends Pane {
        public char player = ' ';


        public Cell(int size) {
            setStyle("-fx-border-color: black");
            this.setPrefSize(700 / size, 700 / size);
            this.setOnMouseClicked(e -> {
                if (!wygrana) {

                    if (comVsComCheck.isSelected()) {
                        mode2 = false;

                        while (!sprawdzWygrana(size)) {
                            handleClick(size);
                        }
                    } else {
                        handleClick(size);
                    }
                }
            });
        }


            private void handleClick ( int size){
                if (playerVsComCheck.isSelected()) {
                    noMove++;
                    if (player == ' ' && currentPlayer != ' ') {
                        setPlayer(currentPlayer);
                        generujWejscie();
                    }

                    //tutaj sprawdzanie wygranej
                    if (sprawdzWygrana(size))
                        return;



                    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(wejscie);
                    String temp = wejscie;
                    try {

                        mode = true;
                        mode2 = true;
                        String board = geneticAlgorithm.start(mode, mode2);
                        dodajRuchNaPlansze(board);
                        if (sprawdzWygrana(size)) return;
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                        alert.showAndWait();
                    }

                }
                //trzeba cos zmienic bo nie dziala rysowanie kolejnych elementow na biezaco w while w handleClick. Jak bylo tak jak zrobiles to narysowal wszystko na samym koncu.
                if (comVsComCheck.isSelected()) {  //------------ ROZGRYWKA KOMPUTER VS KOMPUTER, trzeba kliknąć aby rozpocząć rozgrywke comp vs comp

                    generujWejscie();
                    GeneticAlgorithm alg1 = new GeneticAlgorithm(wejscie);
                    String temp = wejscie;
                    try {
                        String board = alg1.start(mode, mode2);
                        System.out.println(board); //tempF
                        moves.addElement(board);
                        mode = !mode;
                        //mode2 = !mode2;
                        dodajRuchNaPlansze(board);
                        if (sprawdzWygrana(size))
                            return;
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        System.out.println(e);
                        //Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                        //alert.showAndWait();
                    }

                }
            }


            public char getPlayer () {
                return player;
            }

            public void setNullPlayer () {
                player = ' ';
            }

            public void setPlayer ( char c){
                player = c;
                if (player == '1') {
                    Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                    line1.endXProperty().bind(this.widthProperty().subtract(10));
                    line1.endYProperty().bind(this.heightProperty().subtract(10));

                    Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
                    line2.endXProperty().bind(this.widthProperty().subtract(10));
                    line2.startYProperty().bind(this.heightProperty().subtract(10));

                    getChildren().addAll(line1, line2);
                } else if (player == '2') {
                    Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);

                    ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                    ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                    ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                    ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                    ellipse.setStroke(Color.BLACK);
                    ellipse.setFill(Color.color(1, 1, 1, 1));

                    getChildren().add(ellipse);
                }
            }
        }


    public static void main(String[] args) {
        launch(args);
    }
}
