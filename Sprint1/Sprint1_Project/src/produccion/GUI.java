package produccion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.http.HttpResponse;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.util.ArrayList;
public class GUI extends JFrame {
    private final int HEIGHT_GAME = 650, WIDTH_GAME = 950;

    private GameContent gameContent;
    public GUI(){
        setTitle("Game SOS");
        //setUndecorated(true);
        this.pack();
        setSize(WIDTH_GAME, HEIGHT_GAME);
        setContentPane();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    class GameContent extends JPanel{
        JRadioButton simpleGame = new JRadioButton("Simple game");
        JRadioButton generalGame = new JRadioButton("General game");
        JTextField sizeBoard = new JTextField();
        public GameContent(){
            setLayout(new BorderLayout());
            addContentTop();
            addContentPlayer("Blue player", BorderLayout.LINE_START);
            add(new GameBoard(), BorderLayout.CENTER);
            addContentPlayer("Red player", BorderLayout.LINE_END);
            addContentBottom();
        }

        private void addContentBottom(){
            JPanel panelContent = new JPanel();
            panelContent.setLayout(new BorderLayout());
            panelContent.setBorder(new EmptyBorder(20,20,20,20));

            JButton newGame = new JButton("New Game");

            JLabel turnPlayer = new JLabel("Current turn: blue");
            turnPlayer.setHorizontalAlignment(JLabel.CENTER);

            panelContent.add(turnPlayer, BorderLayout.CENTER);
            panelContent.add(newGame, BorderLayout.LINE_END);
            add(panelContent, BorderLayout.PAGE_END);
        }

        private void addContentPlayer(String name, String location){
            JPanel panelContent = new JPanel();
            panelContent.setBorder(new EmptyBorder(0,50,0,50));
            panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
            panelContent.add(Box.createVerticalGlue());
            panelContent.add(new JLabel(name));
            panelContent.add(new JRadioButton("S"));
            panelContent.add(new JRadioButton("O"));
            panelContent.add(Box.createVerticalGlue());
            add(panelContent, location);
        }

        private void addContentTop(){
            JPanel panelContent = new JPanel();
            panelContent.setLayout(new BorderLayout());

            JPanel panelContentLeft = new JPanel();
            panelContentLeft.add(new JLabel("SOS"));
            panelContentLeft.add(simpleGame);
            panelContentLeft.add(generalGame);

            JPanel panelContentRigth = new JPanel();
            panelContentRigth.add(new JLabel("Board size"));
            sizeBoard.setColumns(3);
            panelContentRigth.add(sizeBoard);

            panelContent.add(panelContentLeft, BorderLayout.WEST);
            panelContent.add(panelContentRigth, BorderLayout.EAST);
            add(panelContent, BorderLayout.PAGE_START);
        }
    }

    class GameBoard extends JPanel{
        JPanel board = new JPanel();
        ArrayList <JPanel> casillas = new ArrayList<>();
        GridBagConstraints c = new GridBagConstraints();
        public GameBoard(){
            setLayout(new GridBagLayout());
            int casillas = 8, dimBoard = WIDTH_GAME/2;
            board.setPreferredSize(new Dimension(dimBoard,dimBoard));
            drawBoard(casillas,casillas);
            c.fill = GridBagConstraints.NONE;
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 1.0;
            c.weighty = 1.0;
            c.anchor = GridBagConstraints.CENTER;
            add(board, c);
        }

        public void drawBoard(int rows, int columns){
            casillas.clear();
            board.removeAll();
            board.setLayout(new GridLayout(rows, columns));
            for(int i=0; i<rows*columns; i++){
                JPanel panelCelda = new JPanel();
                panelCelda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                casillas.add(panelCelda);
                board.add(panelCelda);
            }
            board.revalidate();
            board.repaint();
        }
    }

    private void setContentPane() {
        gameContent = new GameContent();
        this.setContentPane(gameContent);
    }

    public static void main(String[] args) {
        new GUI();
    }
}