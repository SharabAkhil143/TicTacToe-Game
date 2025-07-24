
import java.util.Random;
import java.util.Scanner;

class Game
{
    static char[][] board = new char[3][3];
    public Game()
    {
        board = new char[3][3];
    }
    static void initBoard()
    {
        for (char[] row : board)
        {
            for (int j = 0; j < row.length; j++)
            {
                row[j] = ' ';
            }
        }
    }
    static void dispBoard()
    {
        System.out.println("-------------");
        for (char[] row : board)
        {
            System.out.print("| ");
            for (char cell : row)
            {
                System.out.print(cell + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
    static void placeMark(int row, int column, char mark)
    {
        if(row>=0 && row <=2 && column>=0 && column<=2)
        {
            board[row][column]=mark;
        }
    }
    static boolean checkRowWin()
    {
        for(int i=0;i<=2;i++)
        {
            if(board[i][0]!=' ' && board[i][0] ==board[i][1] && board[i][0] == board[i][2])
            {
                return true;
            }
        }
        return false;
    }
    static boolean checkColWin()
    {
        for(int j=0;j<=2;j++)
        {
            if(board[0][j]!=' ' && board[0][j]==board[1][j] && board[1][j]==board[2][j])
            {
                return true;
            }
        }
        return false;
    }
    static boolean checkDiagWin()
    {
        return (board[0][0]!=' ' && board[0][0]==board[1][1] && board[1][1] == board[2][2]) ||
               (board[0][2]!= ' ' && board[0][2]==board[1][1] && board[1][1]==board[2][0]);
    }
    static boolean checkDraw()
    {
        for(int i=0;i<=2;i++)
        {
            for(int j=0;j<=2;j++)
            {
                if(board[i][j]==' ')
                {
                    return false;
                }
            }
        }
        return true;
    }
}
abstract class Player
{
    String name;
    char mark;
    abstract void makeMove();
    boolean isValidMove(int row, int column)
    {
        if(row>=0 && row<=2 && column>=0 && column<=2)
        {
            if(Game.board[row][column]==' ')
            {
                return true;
            }
        }
        return false;
    }
}
class HumanPlayer extends Player
{
    HumanPlayer(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }
    @Override
    void makeMove()
    {
        int row = -1;
        int column = -1;
        Scanner sc = new Scanner(System.in);
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Enter the Row & Column (0-2):");
                row = sc.nextInt();
                column = sc.nextInt();
                if (!isValidMove(row, column)) {
                    System.out.println("Invalid move. Try again.");
                } else {
                    break; // Exit loop when valid
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter numbers only.");
            sc.nextLine(); // Clear invalid input
            }
}
        Game.placeMark(row, column, mark);
    }
}
class AIPlayer extends Player
{
    AIPlayer(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }
    @Override
    void makeMove()
    {
        Random r = new Random();
        int row;
        int column;
        do { 
            row = r.nextInt(3);
            column = r.nextInt(3);
        } 
        while (!isValidMove(row, column));
        Game.placeMark(row, column, mark);
    }
}





public class App {
    public static void main(String[] args) {
        Game.initBoard();
        Player P1 = new AIPlayer("AI", 'X');
        Player P2 = new HumanPlayer("You", 'O');
        Player cp = P2;
        while (true) {
            cp.makeMove();
            Game.dispBoard();
            if(Game.checkColWin() || Game.checkRowWin() || Game.checkDiagWin())
            {
                System.out.println(cp.name + " Has Won");
                break;
            }
            else if(Game.checkDraw())
            {
                System.out.println("Game is Draw");
                break;
            }
            else
            {
                // Switch player for next turn
                cp = (cp == P1) ? P2 : P1;
            }
        }
        
    }
}
