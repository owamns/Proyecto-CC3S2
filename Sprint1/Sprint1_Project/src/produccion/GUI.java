package produccion;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private final int HEIGHT_GAME = 650, WIDTH_GAME = 950;

    public int getHEIGHT_GAME() {
        return HEIGHT_GAME;
    }

    public int getWIDTH_GAME() {
        return WIDTH_GAME;
    }

    private GameContent gameContent;
    public GUI(){
        setTitle("Game SOS");
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
            addContentConfig();
            add(new GameBoard());
        }

        private void addContentConfig(){
            JPanel panelTopContent = new JPanel();
            panelTopContent.setLayout(new BorderLayout());
            panelTopContent.setBounds(0,0,WIDTH_GAME,WIDTH_GAME/16);

            JPanel panelTopContentLeft = new JPanel();
            panelTopContentLeft.add(new JLabel("SOS"));
            panelTopContentLeft.add(simpleGame);
            panelTopContentLeft.add(generalGame);

            JPanel panelTopContentRigth = new JPanel();
            panelTopContentRigth.add(new JLabel("Board size"));
            sizeBoard.setColumns(3);
            panelTopContentRigth.add(sizeBoard);

            panelTopContent.add(panelTopContentLeft, BorderLayout.WEST);
            panelTopContent.add(panelTopContentRigth, BorderLayout.EAST);
            add(panelTopContent);
        }
    }

    class GameBoard extends JPanel{
        public GameBoard(){
            int casillas = 3, dimBoard = WIDTH_GAME/2;
            setSize(dimBoard,dimBoard);
            setLocation((getWIDTH_GAME()-dimBoard)/2,(getHEIGHT_GAME()-dimBoard)/2);
            drawBoard(casillas,casillas);
        }

        public void drawBoard(int rows, int columns){
            removeAll();
            setLayout(new GridLayout(rows, columns));
            for(int i=0; i<rows*columns; i++){
                JPanel panelCelda = new JPanel();
                panelCelda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                add(panelCelda);
            }
            revalidate();
            repaint();
        }
    }

    private void setContentPane() {
        gameContent = new GameContent();
        gameContent.setLayout(null);
        this.setContentPane(gameContent);
    }

    public static void main(String[] args) {
        new GUI();
    }
}

