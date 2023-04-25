package produccion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class GUI extends JFrame {
    private final int HEIGHT_GAME = 650, WIDTH_GAME = 950;
    private Board game;
    private GameContent gameContent;
    private PlayerConfig playerBlueConfig = new PlayerConfig("Blue player");
    private PlayerConfig playerRedConfig = new PlayerConfig("Red player");
    private JLabel turnPlayer;
    public GUI(Board game){
        this.game = game;
        setTitle("SOS game");
        this.pack();
        setSize(WIDTH_GAME, HEIGHT_GAME);
        setContentPane();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    class GameContent extends JPanel{
        private JRadioButton simpleGame = new JRadioButton("Simple game");
        private JRadioButton generalGame = new JRadioButton("General game");
        private JTextField sizeBoard = new JTextField();
        private JButton newGame = new JButton("New Game");

        public JButton getNewGame() {
            return newGame;
        }

        public JRadioButton getSimpleGame() {
            return simpleGame;
        }

        public JRadioButton getGeneralGame() {
            return generalGame;
        }

        private GameBoard gameBoard = new GameBoard();
        public GameContent(){
            setLayout(new BorderLayout());
            addContentTop();
            addContentPlayer(BorderLayout.LINE_START, playerBlueConfig);
            add(gameBoard, BorderLayout.CENTER);
            addContentPlayer(BorderLayout.LINE_END, playerRedConfig);
            playerRedConfig.enableTurnPlayer(false);
            addContentBottom();
        }

        private void addContentBottom(){
            JPanel panelContent = new JPanel();
            panelContent.setLayout(new BorderLayout());
            panelContent.setBorder(new EmptyBorder(20,20,20,20));

            newGame.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(sizeBoard.getText().equals("")){
                        game = new Board(3);
                        gameBoard.drawBoard(3,3);
                    }
                    else {
                        int size = Integer.parseInt(sizeBoard.getText());
                        game = new Board(size);
                        gameBoard.drawBoard(size,size);
                    }
                    simpleGame.setEnabled(true);
                    simpleGame.setSelected(false);
                    generalGame.setEnabled(true);
                    generalGame.setSelected(false);
                    if (playerRedConfig.getLetterS().isSelected() || playerRedConfig.getLetterO().isSelected()){
                        playerRedConfig.enableTurnPlayer(false);
                        playerBlueConfig.enableTurnPlayer(true);
                    }
                }
            });

            turnPlayer = new JLabel("Current turn: "+((game.getTurn()==1)? "Blue player": "Red player"));
            turnPlayer.setHorizontalAlignment(JLabel.CENTER);

            panelContent.add(turnPlayer, BorderLayout.CENTER);
            panelContent.add(newGame, BorderLayout.LINE_END);
            add(panelContent, BorderLayout.PAGE_END);
        }

        public void newGame(){

        }

        private void addContentPlayer(String location, PlayerConfig playerConfig){
            JPanel panelContent = new JPanel();
            panelContent.setBorder(new EmptyBorder(0,50,0,50));
            panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
            panelContent.add(Box.createVerticalGlue());
            panelContent.add(new JLabel(playerConfig.getName()));
            panelContent.add(playerConfig.getLetterS());
            panelContent.add(playerConfig.getLetterO());
            panelContent.add(Box.createVerticalGlue());
            add(panelContent, location);
        }

        private void addContentTop(){
            JPanel panelContent = new JPanel();
            panelContent.setLayout(new BorderLayout());

            JPanel panelContentLeft = new JPanel();
            panelContentLeft.add(new JLabel("SOS"));
            panelContentLeft.add(simpleGame);
            simpleGame.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(simpleGame.isSelected()){
                        simpleGame.setEnabled(false);
                        generalGame.setEnabled(false);
                    }
                }
            });
            panelContentLeft.add(generalGame);
            generalGame.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(generalGame.isSelected()){
                        simpleGame.setEnabled(false);
                        generalGame.setEnabled(false);
                    }
                }
            });

            JPanel panelContentRigth = new JPanel();
            panelContentRigth.add(new JLabel("Board size"));
            sizeBoard.setColumns(3);
            panelContentRigth.add(sizeBoard);

            panelContent.add(panelContentLeft, BorderLayout.WEST);
            panelContent.add(panelContentRigth, BorderLayout.EAST);
            add(panelContent, BorderLayout.PAGE_START);

            AbstractDocument doc = (AbstractDocument) sizeBoard.getDocument();
            doc.setDocumentFilter(new DocumentFilter() {
                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                    if (newText.matches("\\d*")) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });
            sizeBoard.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()== KeyEvent.VK_ENTER){
                        if (Integer.parseInt(sizeBoard.getText())>2){
                            gameBoard.drawBoard(Integer.parseInt(sizeBoard.getText()),Integer.parseInt(sizeBoard.getText()));
                            game = new Board(Integer.parseInt(sizeBoard.getText()));
                        }
                        else {
                            String message =  "El tamaño del tablero tiene que ser mayor que 2";
                            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        }
    }

    class PlayerConfig{
        private String name;
        private JRadioButton letterS;
        private JRadioButton letterO;

        public String getName() {
            return name;
        }

        public JRadioButton getLetterS() {
            return letterS;
        }

        public JRadioButton getLetterO() {
            return letterO;
        }

        public PlayerConfig(String name){
            this.name = name;
            letterS = new JRadioButton("S");
            letterO = new JRadioButton("O");
            letterS.setSelected(true);
            choseLetter();
        }

        public void enableTurnPlayer(boolean enable){
            letterS.setEnabled(enable);
            letterO.setEnabled(enable);
        }
        private void choseLetter(){
            letterS.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (letterS.isSelected()){
                        letterO.setSelected(false);
                    }
                    else {
                        letterO.setSelected(true);
                    }
                }
            });
            letterO.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (letterO.isSelected()){
                        letterS.setSelected(false);
                    }
                    else {
                        letterS.setSelected(true);
                    }
                }
            });
        }
    }

    class GameBoard extends JPanel{
        JPanel board = new JPanel();
        ArrayList <Casilla> casillas = new ArrayList<>();
        GridBagConstraints c = new GridBagConstraints();
        public GameBoard(){
            setLayout(new GridBagLayout());
            int dimBoard = WIDTH_GAME/2;
            board.setPreferredSize(new Dimension(dimBoard,dimBoard));
            drawBoard(3, 3);
            c.fill = GridBagConstraints.NONE;
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 1.0;
            c.weighty = 1.0;
            c.anchor = GridBagConstraints.CENTER;
            add(board, c);
        }
        public void drawBoard(int rows, int columns){
            int pos = 0;
            casillas.clear();
            board.removeAll();
            board.setLayout(new GridLayout(rows, columns));
            for(int i=0; i<rows; i++){
                for (int j=0; j<columns; j++){
                    JPanel panelCelda = new JPanel();
                    panelCelda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    casillas.add(new Casilla(panelCelda, i, j));
                    board.add(panelCelda);
                    addMouseListener(casillas.get(pos++));
                }
            }
            board.revalidate();
            board.repaint();
        }

        public void addMouseListener(Casilla casilla) {
            casilla.getContent().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    char letterChose;
                    if(!gameContent.getSimpleGame().isSelected() && !gameContent.getGeneralGame().isSelected()){
                        JOptionPane.showMessageDialog(null,"Seleccione el modo de juego", "Seleccione modo",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(game.getCell(casilla.posX, casilla.posY)=='-'){
                        if(game.getTurn()==1){
                            if(playerBlueConfig.letterO.isSelected()){
                                game.makePlay(casilla.posX,casilla.posY,playerBlueConfig.letterO.getText().toCharArray()[0]);
                                casilla.addContentCasilla(playerBlueConfig.letterO.getText());
                                letterChose = 'O';
                            }
                            else {
                                game.makePlay(casilla.posX,casilla.posY,playerBlueConfig.letterS.getText().toCharArray()[0]);
                                casilla.addContentCasilla(playerBlueConfig.letterS.getText());
                                letterChose = 'S';
                            }
                            playerBlueConfig.enableTurnPlayer(false);
                            playerRedConfig.enableTurnPlayer(true);
                        }
                        else {
                            if(playerRedConfig.letterO.isSelected()){
                                game.makePlay(casilla.posX,casilla.posY,playerRedConfig.letterO.getText().toCharArray()[0]);
                                casilla.addContentCasilla(playerRedConfig.letterO.getText());
                                letterChose = 'O';
                            }
                            else {
                                game.makePlay(casilla.posX,casilla.posY,playerRedConfig.letterS.getText().toCharArray()[0]);
                                casilla.addContentCasilla(playerRedConfig.letterS.getText());
                                letterChose = 'S';
                            }
                            playerRedConfig.enableTurnPlayer(false);
                            playerBlueConfig.enableTurnPlayer(true);
                        }
                        if ( gameContent.getSimpleGame().isSelected() && game.howManySOS(casilla.posX, casilla.posY, letterChose) > 0 ) {
                            String ganador = (game.getTurn()==1)? playerBlueConfig.getName(): playerRedConfig.getName();
                            JOptionPane.showMessageDialog(null,"El ganador es " + ganador + ". Felicidades!", "Ganador",JOptionPane.INFORMATION_MESSAGE);
                            drawBoard(game.getSquaresPerSide(), game.getSquaresPerSide());
                        }
                        game.increaseScore(game.getTurn(), game.howManySOS(casilla.posX, casilla.posY, letterChose));
                        turnPlayer.setText("Current turn: "+((game.getTurn()==1)? "Blue player": "Red player"));
                        if(game.isBoardFull() && gameContent.getSimpleGame().isSelected()){
                            JOptionPane.showMessageDialog(null,"Quedaron en empate", "Empate",JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if(game.isBoardFull() && gameContent.getGeneralGame().isSelected()){
                            if(game.getWinnerPlayer()==1){
                                JOptionPane.showMessageDialog(null,"El ganador es " + playerBlueConfig.getName() + ". con "+ game.getScore()[0] +" puntos", "Ganador",JOptionPane.INFORMATION_MESSAGE);
                            }
                            else if(game.getWinnerPlayer()==2){
                                JOptionPane.showMessageDialog(null,"El ganador es " + playerRedConfig.getName() + ". con "+ game.getScore()[1] + " puntos", "Ganador",JOptionPane.INFORMATION_MESSAGE);
                            }
                            else {
                                JOptionPane.showMessageDialog(null,"Quedaron en empate", "Empate",JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        game.changeTurn();
                    }
                }
            });
        }

        class Casilla{
            private JPanel content;
            private JLabel letterCasilla = new JLabel();
            private int posX,posY;
            public Casilla(JPanel content, int posX, int posY){
                this.content = content;
                this.content.setLayout(new BorderLayout());
                this.content.add(letterCasilla, BorderLayout.CENTER);
                this.posX = posX;
                this.posY = posY;
            }
            private void addContentCasilla(String letter){
                letterCasilla.setText(letter);
                letterCasilla.setHorizontalAlignment(JLabel.CENTER);
                letterCasilla.setFont(new Font("Arial",Font.BOLD,content.getHeight()));
            }
            public JPanel getContent() {
                return content;
            }

            public int getPosX() {
                return posX;
            }

            public int getPosY() {
                return posY;
            }
        }
    }

    private void setContentPane() {
        gameContent = new GameContent();
        this.setContentPane(gameContent);
    }

    public static void main(String[] args) {
        new GUI(new Board(3));
    }
}