import javax.swing.*;
import java.awt.*;
import java.util.*;
/**
 * The Maze.java class contains the main method
 * To generate the maze, and to display the path
 * @author Ethan Hartfield
 * 11/29/2022
 */
public class Maze{
    public int[][] maze;
    public int penalty;
    private final static Object[][] DIRS = {{1, 0, "S"}, {-1, 0, "N"}, {0, 1, "E"}, {0, -1, "W"}};
    private boolean printItOnce=false;
    /**
     * Constructor for the maze object
     * @param n the number of rows in the maze
     * @param m the number of columns in the maze
     * @param breakWall the number of maximum walls to break
     */
    public Maze(int n, int m, int breakWall){
        maze = generateMaze(n, m, breakWall);
        penalty = breakWall;
    }
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int n=1, m=1, breakWall = 0;
        boolean flag = false;
        while(!flag){
            try{
                System.out.println("Please specify number of rows for the maze.");
                n = scan.nextInt();
                System.out.println("Please specify number of columns for the maze.");
                m = scan.nextInt();
                System.out.println("What is the maximun amount of walls you want to break down?");
                breakWall = scan.nextInt();
                flag = true;
            } catch(Exception e){
                System.out.println("Please enter integer numbers only. Try again.");
                scan.nextLine();
            }
        }
        Maze M = new Maze(n,m,breakWall);
        Runnable r = new Runnable(){
            @Override
            public void run(){
                DisplayMaze display = new DisplayMaze(M);
                JFrame f = new JFrame("GenerateMaze");
                f.add(display.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
    /**
     * The generateMaze method generates a random maze
     * There is a 60% chance of a wall being in every spot
     * @param n the number of rows in the maze
     * @param m the number of columns in the maze
     * @param breakWall the maximum number of allowed walls to break
     * @return int[][] The maze with walls
     */
    public int[][] generateMaze(int n, int m, int breakWall){
        int[][] maze = new int[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if((i == 0 && j == 0) || (i == n-1 && j == m-1))
                    continue;
                else{
                    double random = Math.random();
                    if(random < .6)
                        maze[i][j] = 1;
                }
            }
        }
        if(isValid(maze, 0, 0, breakWall)) {
            return maze;
        }
        return generateMaze(n, m, breakWall);
    }
    /**
     * The showPath method finds and shows the path to the maze
     * @param maze the maze with walls
     * @param r the starting row
     * @param c the starting column
     * @param wallBreaks the number of wallBreaks available
     * @param path will print out the cardinal directions of the path
     * @return int[][] the maze with the correct path highlighted
     */
    public int[][] showPath(int[][] maze, int r, int c, int wallBreaks, StringBuilder path) {
        if(r < maze.length && r >= 0 && c < maze[0].length && c >= 0 && maze[r][c] != 2) {
            if(!(maze[r][c] == 1 && wallBreaks == 0)) {
                if(maze[r][c] == 1) {
                    wallBreaks--;
                }
                int temp = maze[r][c];
                maze[r][c] = 2;
                if(r == maze.length - 1 && c == maze[0].length - 1) {
                    int[][] copy = new int[maze.length][maze[0].length];
                    for(int i = 0; i < copy.length; i++) {
                        for(int j = 0; j < copy[0].length; j++) {
                            copy[i][j] = maze[i][j];
                        }
                    }
                    return copy;
                }
                for(Object[] dir : DIRS) {
                    int[][] newMaze = showPath(maze, r + (int)dir[0], c + (int)dir[1], wallBreaks, path.append(dir[2]));
                    if(newMaze[maze.length - 1][maze[0].length - 1] == 2) {
                        if(!printItOnce){
                            System.out.println(path);
                            printItOnce=true;
                        }
                        return newMaze;
                    }
                    path.deleteCharAt(path.length() - 1);
                }
                maze[r][c] = temp;
            }
        }
        return maze;
    }
    /**
     * The isValid method checks if the current maze is solvable
     * @param maze the maze with walls
     * @param r the starting row
     * @param c the starting column
     * @param wallBreaks the number of allowed wallbreaks
     * @return true if valid, false if invalid
     */
    public boolean isValid(int[][] maze, int r, int c, int wallBreaks) {
        if(r < maze.length && r >= 0 && c < maze[0].length && c >= 0 && maze[r][c] != 2) {
            if(!(maze[r][c] == 1 && wallBreaks == 0)) {
                if(maze[r][c] == 1) {
                    wallBreaks--;
                }
                int temp = maze[r][c];
                if(r == maze.length - 1 && c == maze[0].length - 1) {
                    return true;
                }
                for(Object[] dir : DIRS) {
                    maze[r][c] = 2;
                    boolean isValid = isValid(maze, r + (int)dir[0], c + (int)dir[1], wallBreaks);
                    maze[r][c] = temp;
                    if(isValid) {

                        return true;
                    }
                }
            }
        }
        return false;
    }
}
