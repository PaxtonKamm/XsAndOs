package sample;
//XsAndOs.java
// author: Paxton Kammermeier
// CS111B Assignment 6
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

public class XsAndOs extends Application {

    private boolean gameOver = false;  //flag
    private char whoseTurn = 'X'; // 'X' or 'O' but 'X' starts
    private Cell[][] board =  new Cell[3][3];  //the board for playing
    private Label statusLabel = new Label("X's turn to play");  //let user know status of game

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                pane.add(board[i][j] = new Cell(), j, i);
        //The nested loop creates cell objects for each location in order to be seen in javafx.
        // in this case, j is the x values and i is the y values.

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(statusLabel);

        Scene scene = new Scene(borderPane, 300, 300);
        primaryStage.setTitle("XsAndOs");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                if (board[i][j].getToken() == ' ')
                    return false;
        }
        return true;
        //here if any of the spots are blank (the token is neither 'x' or 'o') then the board is not considered full
        // and isFull() is false. Otherwise, it will return true, the game will be over.
    }

    public boolean hasWon(char tkn) {
        // horizontal
        for (int j = 0; j < 3; j++) {
            if (board[0][j].getToken() == tkn && board[1][j].getToken() == tkn && board[2][j].getToken() == tkn)
                return true;
        }

        // vertical
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getToken() == tkn && board[i][1].getToken() == tkn && board[i][2].getToken() == tkn)
                return true;
        }

        // diagonals
        if (board[0][0].getToken() == tkn && board[1][1].getToken() == tkn && board[2][2].getToken() == tkn)
            return true;
        if  (board[2][0].getToken() == tkn && board[1][1].getToken() == tkn && board[0][2].getToken() == tkn)
            return true;
        return false;
        //In the above statement there are horizontal, vertical and diagonal possibilities of winning. The horizontal
        // and vertical diagonals go through a nested for loop to represent each column or row the y or the x are
        //following. As a result if the board has the same characters in a row whether it is horizontal, vertical or
        //diagonal, hasWon is true and the game is over. Otherwise, it stays false.
    }

    //HERE IS INNER CLASS REPRESENTING ONE CELL IN BOARD
    //The inner class has access to all of the outer classes data/methods
    public class Cell extends Pane {

        private char token = ' ';   // one of blank, X, or O

        public Cell() {
            setStyle("-fx-border-color: black");
            setPrefSize(100, 100);
            setOnMouseClicked(e -> handleMouseClick());
        }

        public char getToken() {
            return token;
            //Returns token and will mark a spot on the tic-tac-toe board. It will be useful to determine the winner
        }

        public void drawX() {
            double w = getWidth();
            double h = getHeight();
            Line line1 = new Line(10.0, 10.0, w - 10.0, h - 10.0);
            Line line2 = new Line (10.0, h - 10.0, w - 10.0,10.0 );
            //Created two lines to create an "X" shape.
            getChildren().addAll(line1, line2);
        }

        public void drawO() {
            double w = getWidth();
            double h = getHeight();
            Ellipse ellipse = new Ellipse (w/2,h/2,w/2 - 10,h/2 - 10.0);
            ellipse.setStroke(Color.BLACK);
            ellipse.setFill(Color.WHITE);
            //Created an ellipse to create the "o"
            getChildren().add(ellipse);
        }

        public void setToken(char c) {
            // this will set token to the formal parameter c.
            token = c;
            if (c == 'X') {
                drawX();
            }
            else if (c == 'O'){
                drawO();
            }
            //if the c equals x, it will change the cell to X with the drawX method. Same for the O.
        }

        private void handleMouseClick() {
            String s = "";
            if (!gameOver)
            {
                setToken(whoseTurn);
                s = whoseTurn + "'s turn";
                if (hasWon(whoseTurn)) {
                    s = whoseTurn + " is the winner! The game is over";
                    gameOver = true;
                } else
                {
                    whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
                    s = whoseTurn + "'s turn";
                }
                if (isFull())
                {
                    gameOver = true;
                    s = "Draw! The game is over";
                }
            }
            statusLabel.setText(s);
            //If the game is not over, the token will be set on either X or O (depending on what whoseTurn is valued
            //to). However, if the hasWon function equals whoseTurn (X or Y), the game is over and the winner is
            // announced in the statusLabel. Otherwise, whoseTurn will switch to the next character (either X or O)
            // and the status label will announce whose turn it is. But if there is no winner determined and the board
            // is full, the status will announce the game as a draw.
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
