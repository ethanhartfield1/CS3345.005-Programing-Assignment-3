import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class DisplayMaze{
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel[][] mazeSquares = new JPanel[9][9];
    private JPanel maze;

    DisplayMaze(){
        initializeGui();
    }

    public final void initializeGui(){
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        /*Action newGameAction = new AbstractAction("New"){
            @Override
            public void actionPerformed(ActionEvent e){
                setupNewMaze();
            }
        }; */
        //tools.add(newGameAction);
        tools.add(new JButton("Draw path")); //TODO - add functionality!

        maze = new JPanel(new GridLayout(0, 9)){
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
                JPanel p = new JPanel();
                p.setMargin(panelMargin);
                p.setBackground(Color.WHITE);
                mazeSquares[j][i] = p;
            }
        }
    }
    public final JComponent getGui(){
        return gui;
    }

} 
