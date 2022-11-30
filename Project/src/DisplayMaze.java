import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * The DisplayMaze class uses java swing to make a gui to display the maze
 * @author Ethan Hartfield
 * 11/29/2022
 */
public class DisplayMaze{
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] mazeSquares;
    private JPanel maze;

    /**
     * Constructor for DisplayMaze object
     * @param m the Maze object
     */
    DisplayMaze(Maze m){
        mazeSquares = new JButton[m.maze.length][m.maze[0].length];
        initializeGui(m);
    }
    /**
     * The initializeGui method initializes the gui for java swing
     * @param m the Maze object
     */
    public final void initializeGui(Maze m){
        int[][] truth = m.maze;
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton drawPath = new JButton("Draw path");
        drawPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                m.maze = m.showPath(m.maze, 0, 0, m.penalty, new StringBuilder());
                for(int i = 0; i < mazeSquares.length; i++){
                    for(int j = 0; j < mazeSquares[i].length; j++){
                        if(truth[i][j] == 2)
                            mazeSquares[i][j].setBackground(Color.GREEN);
                    }
                }
            }
        });
        tools.add(drawPath); //TODO - add functionality!

        maze = new JPanel(new GridLayout(truth.length, truth[0].length)){
            @Override
            public final Dimension getPreferredSize(){
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null){
                    prefSize = new Dimension((int)d.getWidth(), (int)d.getHeight());
                } else if(c!=null && c.getWidth()>d.getHeight() && c.getHeight()>d.getHeight()){
                    prefSize = c.getSize();
                } else{
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                int s = (w>h ? h : w);
                return new Dimension(s,s);
            }
        };
        maze.setBorder(new CompoundBorder(new EmptyBorder(8,8,8,8), new LineBorder(Color.BLACK)));
        JPanel mazeConstrain = new JPanel(new GridBagLayout());
        mazeConstrain.setBackground(Color.WHITE);
        mazeConstrain.add(maze);
        gui.add(mazeConstrain);

        Insets panelMargin = new Insets(0, 0, 0, 0);
        for(int i = 0; i < mazeSquares.length; i++){
            for(int j = 0; j < mazeSquares[i].length; j++){
                JButton b = new JButton();
                b.setMargin(panelMargin);
                if(truth[i][j] == 0)
                    b.setBackground(Color.WHITE);
                else if(truth[i][j] == 1)
                    b.setBackground(Color.BLACK);
                else
                    b.setBackground(Color.GREEN);
                mazeSquares[i][j] = b;
                maze.add(b);
            }
        }
        
    }

    public final JComponent getGui(){
        return gui;
    }

} 
