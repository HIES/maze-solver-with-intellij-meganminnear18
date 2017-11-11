import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;

public class Maze
{
    private Cell[][] board;
    private final int DELAY = 200;

    public Maze(int rows, int cols, int[][] map){
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows);
        board = new Cell[rows][cols];
        //grab number of rows to invert grid system with StdDraw (lower-left, instead of top-left)
        int height = board.length - 1;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                board[r][c] = map[r][c] == 1 ? new Cell(c , height - r, 0.5, false) : new Cell(c, height - r, 0.5, true);
            }
    }

    public void draw()
    {
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[r].length; c++){
                Cell cell = board[r][c];
                StdDraw.setPenColor(cell.getColor());
                StdDraw.filledSquare(cell.getX(), cell.getY(), cell.getRadius());
            }
            StdDraw.show();
    }

    public boolean findPath(int row, int col)
    {

        boolean complete = false;
        if(this.isValid(row, col)){
            board[row][col].setColor(Color.BLUE);
            this.draw();
            StdDraw.pause(DELAY);

            if(this.isExit(row, col)){
                complete = true;
            }
            else{
                board[row][col].visitCell();
                complete = findPath(row, col+1);
            }

            if(!complete){
                board[row][col].visitCell();
                complete = findPath(row+1, col);
            }

            if(!complete){
                board[row][col].visitCell();
                complete = findPath(row, col-1);
            }

            if(!complete) {
                board[row][col].visitCell();
                complete = findPath(row-1, col);
            }
            }

            if(complete) {

                board[row][col].becomePath();
                this.draw();
                StdDraw.pause(DELAY);

            }
            return complete;
        }




    private boolean isValid(int row, int col) {
        if (row >= 0 && row < board.length && col >= 0 && col < board[0].length) {
            if (!board[row][col].isVisited() && !board[row][col].isWall()) {
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    private boolean isExit(int row, int col)
    {
        if(row == board.length-1 && col == board[0].length -1)
                return true;
        else
            return false;
    }

    public static void main(String[] args) throws Exception {
        StdDraw.enableDoubleBuffering();
//        int[][] maze = {{1,1,0,0,0,0,0,0,0,0},
//                        {0,1,1,1,1,0,1,1,1,0},
//                        {0,1,1,1,1,0,1,1,0,0},
//                        {0,1,0,1,1,1,1,1,1,0},
//                        {0,1,0,0,0,0,0,1,1,0},
//                        {0,1,1,0,1,1,1,1,1,0},
//                        {0,0,1,0,0,1,0,1,0,0},
//                        {0,1,1,0,1,1,0,1,1,0},
//                        {0,1,1,0,1,1,0,1,1,0},
//                        {0,0,0,0,0,0,0,0,1,1}};
//        Maze geerid = new Maze(maze.length, maze[0].length, maze);
//        geerid.draw();
//        geerid.findPath(0, 0);
//        geerid.draw();
        int[][] maze = readFile("Maze2", ".csv");
        Maze geerid = new Maze(maze.length, maze[0].length, maze);
        geerid.draw();
        geerid.findPath(0,0);
        geerid.draw();
    }

    private static int[][] readFile(String name, String extension) throws FileNotFoundException {
        int countRow = 0;
        int countCol = 0;
        File countFileName = new File(name + extension);
        Scanner countInputObject = new Scanner(countFileName);
        String[] row1 = countInputObject.nextLine().split(",");

        for(int x = 0; x < row1.length; x++){
            countCol++;
        }
        countRow++;

        while(countInputObject.hasNextLine()){
            countRow++;
            countInputObject.nextLine();
        }
        countInputObject.close();

        File fileName = new File(name + extension);
        Scanner inputObject = new Scanner(fileName);
        String[] arraySizes = (inputObject.nextLine().split(","));

//      int[][] maze = new int[Integer.parseInt(arraySizes[0])][Integer.parseInt(arraySizes[1])];
        int[][] maze = new int[countRow][countCol];

        for(int row = 0; inputObject.hasNextLine(); row++){
            String aRow = inputObject.nextLine();
            String[] lineHolder = aRow.split(",");
            for(int col = 0; col < lineHolder.length; col++)
                maze[row][col] = Integer.parseInt(lineHolder[row]);


        }
        inputObject.close();
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    System.out.print(maze[i][j]);
                }
                System.out.println();
            }

        return maze;

    }
}
