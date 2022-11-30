import javax.swing.*;
import java.awt.*;
import java.util.*;
public class maze{
    private int[][] maze;
    
    private final static int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public maze(int n, int m){
        maze = generateMaze(n, m);
    }
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int n=1, m=1;
        boolean flag = false;
        while(!flag){
            try{
                System.out.println("Please specify number of rows for the maze.");
                n = scan.nextInt();
                System.out.println("Please specify number of columns for the maze.");
                m = scan.nextInt();
                flag = true;
            } catch(Exception e){
                System.out.println("Please enter integer numbers only. Try again.");
                scan.nextLine();
            }
        }
        maze M = new maze(n,m);
        Runnable r = new Runnable(){
            @Override
            public void run(){
                DisplayMaze display = new DisplayMaze(M.maze);
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
    public int[][] generateMaze(int n, int m){
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
        return maze;
    }

    public int[][] dfs(int[][] maze, int r, int c, int wallBreaks) {
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
                for(int[] dir : DIRS) {
                    int[][] newMaze = dfs(maze, r + dir[0], c + dir[1], wallBreaks);
                    if(newMaze[maze.length - 1][maze[0].length - 1] == 2) {
                        return newMaze;
                    }
                }
                maze[r][c] = temp;
            }
        }
        return maze;
    }
}
