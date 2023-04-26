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
public class SOSGameGUI extends JFrame {
    private final int HEIGHT_GAME = 650, WIDTH_GAME = 950;
    private SOSGameBoard game;
    private GameContent gameContent;
    private PlayerConfig playerBlueConfig;
    private PlayerConfig playerRedConfig;
    private JLabel turnPlayer;
    public SOSGameGUI(SOSGameBoard game){
        this.game = game;
        playerBlueConfig = new PlayerConfig(game.getPlayers()[0]);
        playerRedConfig = new PlayerConfig(game.getPlayers()[1]);
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
        private final JRadioButton simpleGame = new JRadioButton("Simple game");
        private final JRadioButton generalGame = new JRadioButton("General game");
        private JTextField sizeBoard = new JTextField();
        private final JButton newGame = new JButton("New Game");
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
                        game = new SOSGameBoard(3);
                        gameBoard.drawBoard(3,3);
                    }
                    else {
                        int size = Integer.parseInt(sizeBoard.getText());
                        game = new SOSGameBoard(size);
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
                    game.setTurn('B');
                    turnPlayer.setText("Current turn: "+((game.getTurn()=='B')? "Blue player": "Red player"));
                    playerRedConfig.player.setScore(0);
                    playerBlueConfig.player.setScore(0);
                }
            });

            turnPlayer = new JLabel("Current turn: "+((game.getTurn()=='B')? "Blue player": "Red player"));
            turnPlayer.setHorizontalAlignment(JLabel.CENTER);

            panelContent.add(turnPlayer, BorderLayout.CENTER);
            panelContent.add(newGame, BorderLayout.LINE_END);
            add(panelContent, BorderLayout.PAGE_END);
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
                            game = new SOSGameBoard(Integer.parseInt(sizeBoard.getText()));
                            playerBlueConfig.player.setScore(0);
                            playerRedConfig.player.setScore(0);
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
        private SOSGameBoard.Player player;
        public PlayerConfig(SOSGameBoard.Player player){
            this.player = player;
            this.name = player.getName();
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
                        player.setLetter('S');
                        letterO.setSelected(false);
                    }
                    else {
                        player.setLetter('O');
                        letterO.setSelected(true);
                    }
                }
            });
            letterO.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (letterO.isSelected()){
                        player.setLetter('O');
                        letterS.setSelected(false);
                    }
                    else {
                        player.setLetter('S');
                        letterS.setSelected(true);
                    }
                }
            });
        }
    }

    class GameBoard extends JPanel{
        private JPanel board = new JPanel();
        private ArrayList <Casilla> casillas = new ArrayList<>();
        private GridBagConstraints c = new GridBagConstraints();
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
                    SOSGameBoard.contentBoxes cb;
                    if(!gameContent.getSimpleGame().isSelected() && !gameContent.getGeneralGame().isSelected()){
                        JOptionPane.showMessageDialog(null,"Seleccione el modo de juego", "Seleccione modo",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(game.getCell(casilla.getPosX(), casilla.getPosY()) == SOSGameBoard.contentBoxes.EMPTY){
                        if(game.getTurn()=='B'){
                            letterChose = playerBlueConfig.player.getLetter();
                            cb = ((letterChose=='S')?SOSGameBoard.contentBoxes.LETTER_S:SOSGameBoard.contentBoxes.LETTER_O);
                            playerBlueConfig.enableTurnPlayer(false);
                            playerRedConfig.enableTurnPlayer(true);
                            playerBlueConfig.player.increaseScore(game.howManySOS(casilla.posX, casilla.posY, cb));
                        }
                        else {
                            letterChose = playerRedConfig.player.getLetter();
                            cb = ((letterChose=='S')?SOSGameBoard.contentBoxes.LETTER_S:SOSGameBoard.contentBoxes.LETTER_O);
                            playerRedConfig.enableTurnPlayer(false);
                            playerBlueConfig.enableTurnPlayer(true);
                            playerRedConfig.player.increaseScore(game.howManySOS(casilla.posX, casilla.posY, cb));
                        }
                        game.makePlay(casilla.getPosX(), casilla.getPosY(), cb);
                        casilla.addContentCasilla(String.valueOf(letterChose));
                        if ( gameContent.getSimpleGame().isSelected() && game.howManySOS(casilla.posX, casilla.posY, cb) > 0 ) {
                            drawLines(game.positionSOS(casilla.getPosX(),casilla.getPosY(),cb),casillas);
                            String ganador = (game.getTurn()=='R')? playerBlueConfig.player.getName(): playerRedConfig.player.getName();
                            JOptionPane.showMessageDialog(null,"El ganador es " + ganador + ". Felicidades!", "Ganador",JOptionPane.INFORMATION_MESSAGE);
                            drawBoard(game.getSquaresPerSide(), game.getSquaresPerSide());
                            if (game.getTurn() == 'R') {
                                playerBlueConfig.enableTurnPlayer(true);
                                playerRedConfig.enableTurnPlayer(false);
                            }
                            game = new SOSGameBoard(game.getSquaresPerSide());
                        }
                        turnPlayer.setText("Current turn: "+((game.getTurn()=='B')? playerBlueConfig.player.getName() : playerRedConfig.player.getName()));
                        if(game.howManySOS(casilla.getPosX(),casilla.getPosY(),cb)!=0){
                            drawLines(game.positionSOS(casilla.getPosX(),casilla.getPosY(),cb), casillas);
                        }
                        if(game.isBoardFull() && gameContent.getSimpleGame().isSelected()){
                            JOptionPane.showMessageDialog(null,"Quedaron en empate", "Empate",JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if(game.isBoardFull() && gameContent.getGeneralGame().isSelected()){
                            if(playerBlueConfig.player.getScore()>playerRedConfig.player.getScore()){
                                JOptionPane.showMessageDialog(null,"El ganador es " + playerBlueConfig.player.getName() + ". con "+ playerBlueConfig.player.getScore() +" puntos", "Ganador",JOptionPane.INFORMATION_MESSAGE);
                            } else if (playerBlueConfig.player.getScore() < playerRedConfig.player.getScore()) {
                                JOptionPane.showMessageDialog(null,"El ganador es " + playerRedConfig.player.getName() + ". con "+ playerRedConfig.player.getScore() +" puntos", "Ganador",JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null,"Quedaron en empate", "Empate",JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
            });
        }

        public void drawLines(int []positions, ArrayList<Casilla> casillas){
            int i=positions[0],j=positions[1],cont=0;
            if((positions[0]-positions[2])==(positions[1]-positions[3])){
                if(positions[0]-positions[2]>0){
                    i=positions[2];
                    j=positions[3];
                }
                for (Casilla casilla: casillas){
                    if(casilla.getPosX()==i && casilla.getPosY()==j){
                        if (cont==3) break;
                        casilla.drawLine(game.getTurn(),positions);
                        i++;
                        j++;
                        cont++;
                    }
                }
                cont=0;
            } else if ((positions[0]-positions[2])==-(positions[1]-positions[3])) {
                if(positions[0]-positions[2]>0){
                    i=positions[2];
                    j=positions[3];
                }
                for (Casilla casilla: casillas){
                    if(casilla.getPosX()==i && casilla.getPosY()==j){
                        if (cont==3) break;
                        casilla.drawLine(game.getTurn(),positions);
                        i++;
                        j--;
                        cont++;
                    }
                }
                cont=0;
            } else if (positions[0]-positions[2]==0) {
                if (positions[1]-positions[3]>0){
                    i=positions[2];
                    j=positions[3];
                }
                for (Casilla casilla: casillas){
                    if(casilla.getPosX()==i && casilla.getPosY()==j){
                        if (cont==3) break;
                        casilla.drawLine(game.getTurn(),positions);
                        j++;
                        cont++;
                    }
                }
                cont=0;
            } else if (positions[1]-positions[3] == 0) {
                if (positions[0]-positions[2]>0){
                    i=positions[2];
                    j=positions[3];
                }
                for (Casilla casilla: casillas){
                    if(casilla.getPosX()==i && casilla.getPosY()==j){
                        if (cont==3) break;
                        casilla.drawLine(game.getTurn(),positions);
                        i++;
                        cont++;
                    }
                }
                cont=0;
            }
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
            public void drawLine(char turn, int [] positions){
                int x1,y1,x2,y2;
                Color color = ((turn=='B')?Color.RED:Color.BLUE);
                if((positions[0]-positions[2])==(positions[1]-positions[3])){
                    x1=0;
                    y1=0;
                    x2=content.getWidth();
                    y2=content.getHeight();
                } else if ((positions[0]-positions[2])==-(positions[1]-positions[3])) {
                    x1=content.getWidth();
                    y1=0;
                    x2=0;
                    y2=content.getHeight();
                } else if (positions[0]-positions[2]==0) {
                    x1=0;
                    y1=content.getHeight()/2;
                    x2=content.getWidth();
                    y2=content.getHeight()/2;
                } else if (positions[1]-positions[3] == 0) {
                    x1=content.getWidth()/2;
                    y1=0;
                    x2=content.getWidth()/2;
                    y2=content.getHeight();
                }
                else {
                    x1=0;
                    y1=0;
                    x2=0;
                    y2=0;
                }
                JPanel line = new JPanel(){
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setColor(color);
                        g2d.setStroke(new BasicStroke(3));
                        g2d.drawLine(x1,y1,x2,y2);
                    }
                };
                line.setOpaque(false);
                content.add(line);
                content.setComponentZOrder(letterCasilla,1);
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
        new SOSGameGUI(new SOSGameBoard(3));
    }
}