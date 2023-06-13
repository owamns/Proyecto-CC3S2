import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SOSFiles implements Serializable{
    private List<SOSGameBoard> gameObjects = new ArrayList<>();
    private List<SOSGameGUI> sosGameGUIS = new ArrayList<>();
    private File gameData;
    private File gameInfoObject;
    private File gameInfo;
    public SOSFiles(){
        gameInfoObject = new File("GameRecord/gameInfoObject.dat");
        gameData = new File("GameRecord/gameData.dat");
        gameInfo = new File("GameRecord/gameInfo.txt");
    }

    public void saveGame(SOSGameBoard game, SOSGameGUI sosGameGUI){
        try (ObjectOutputStream oosInfo = new ObjectOutputStream(new FileOutputStream(gameInfoObject));
             ObjectOutputStream oosData = new ObjectOutputStream(new FileOutputStream(gameData))
        ){

            gameObjects.add(game);
            SOSGameGUI objeto = sosGameGUI.clone();
            objeto.getGameContent().getGameBoard().setBoard(sosGameGUI.getGameContent().getGameBoard().clone());
            sosGameGUIS.add(objeto);
            oosData.writeObject(sosGameGUIS);
            oosInfo.writeObject(gameObjects);

            saveGameInfo();
            JOptionPane.showMessageDialog(null,"Juego grabado!", "Record",JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Error de grabado!", "Record",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveGameInfo() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(gameInfoObject));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(gameInfo)));

        List<SOSGameBoard> games = (ArrayList<SOSGameBoard>) ois.readObject();
        for (SOSGameBoard game : games){
            String gameType = (game.getGameType()=='S')?"Simple":"General";
            out.println("Type Game: "+gameType+"\tBoard Size: "+game.getSquaresPerSide());
            for (SOSGameBoard.Player player: game.getPlayers()){
                String score = (game.getGameType()=='S')?"":("\tScore: "+String.valueOf(player.getScore()));
                String controlPlayer = (player.getControl()=='H')?"Human":"Computer";
                out.println("Name Player: "+player.getName()+"\tMode Player: "+controlPlayer+score);
            }
        }
        out.close();
    }

    public void ReplayGameData(){

        try (ObjectInputStream oisInfo = new ObjectInputStream(new FileInputStream("GameRecord/gameInfoObject.dat"))) {
            String [] partidas;
            int i=0;
            List<SOSGameBoard> list = (ArrayList<SOSGameBoard>) oisInfo.readObject();
            partidas = new String[list.size()];
            for (SOSGameBoard game : list){
                String gameType = (game.getGameType()=='S')?"Simple":"General";
                partidas[i] = "Opcion "+(i+1)+": \n";
                partidas[i] += "Type Game: "+gameType+"\tBoard Size: "+game.getSquaresPerSide()+"\n";
                for (SOSGameBoard.Player player: game.getPlayers()){
                    String score = (game.getGameType()=='S')?"":("\tScore: "+ player.getScore());
                    String controlPlayer = (player.getControl()=='H')?"Human":"Computer";
                    partidas[i]+="Name Player: "+player.getName()+"\tMode Player: "+controlPlayer+score+"\n";
                }
                partidas[i]+="\n";
                i++;
            }

            JFrame datosOpciones = new JFrame();
            datosOpciones.setTitle("Opciones");
            datosOpciones.setUndecorated(true);
            datosOpciones.pack();
            datosOpciones.setSize(600, 200);
            datosOpciones.setLocationRelativeTo(null);
            datosOpciones.setResizable(false);
            datosOpciones.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panelDatos = new JPanel(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.weightx = 0.8;
            constraints.weighty = 1;
            constraints.fill = GridBagConstraints.BOTH;


            JTextArea datos = new JTextArea();
            for (String partida: partidas){
                datos.append(partida);
            }
            datos.setEditable(false);
            JScrollPane listaPartidas = new JScrollPane();
            listaPartidas.setViewportView(datos);

            panelDatos.add(listaPartidas, constraints);
            constraints.weightx = 0.2;

            JPanel comenzarJuego = new JPanel();
            comenzarJuego.setLayout(new BoxLayout(comenzarJuego, BoxLayout.Y_AXIS));
            String [] opciones = new String[partidas.length];
            i = 0;
            JComboBox opcBox = new JComboBox<>();
            for (String opcion : opciones){
                opcion = "opcion "+(++i);
                opcBox.addItem(opcion);
            }
            JButton startbutton = new JButton("Start Game");
            JPanel opcPanel = new JPanel();
            opcPanel.add(opcBox);
            comenzarJuego.add(Box.createVerticalGlue());
            comenzarJuego.add(opcPanel);
            comenzarJuego.add(Box.createVerticalStrut(10));
            comenzarJuego.add(startbutton);
            comenzarJuego.add(Box.createVerticalGlue());

            panelDatos.add(comenzarJuego, constraints);
            datosOpciones.add(panelDatos);

            startbutton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String [] numOpc = opcBox.getSelectedItem().toString().split(" ");
                    int opc = Integer.valueOf(numOpc[1]);
                    int i=1;
                    datosOpciones.setVisible(true);

                    try (ObjectInputStream oisData = new ObjectInputStream(new FileInputStream("GameRecord/gameData.dat"))) {
                        List<SOSGameGUI> datosGrabados = (ArrayList<SOSGameGUI>) oisData.readObject();
                        for (SOSGameGUI sosGameGUI: datosGrabados){
                            if(opc==i){
                                restartGame(sosGameGUI);
                                break;
                            }
                            i++;
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    datosOpciones.dispose();
                }
            });

            datosOpciones.setVisible(true);


        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void restartGame(SOSGameGUI sosGameGUI){
        SOSGameGUI datoGrabado = sosGameGUI;
        SOSGameBoard juegoGrabado = datoGrabado.getGame();
        int size = juegoGrabado.getSquaresPerSide();
        char typeGame = juegoGrabado.getGameType();

        SOSGameGUI juegoNuevoGUI = new SOSGameGUI(new SOSGameBoard(size, typeGame));
        SOSGameBoard juegoNuevo = juegoNuevoGUI.getGame();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                juegoNuevo.setLetter(i, j, juegoGrabado.getBox(i, j));
            }
        }

        SOSGameGUI.GameContent gameContent = juegoNuevoGUI.getGameContent();
        gameContent.getSizeBoard().setText(String.valueOf(size));
        if(typeGame=='S'){
            gameContent.getSimpleGame().setSelected(true);
        }
        else{
            gameContent.getGeneralGame().setSelected(true);
        }

        if(juegoGrabado.getPlayers()[0].getControl()=='C'){
            juegoNuevo.getPlayers()[0].setControl('C');
            juegoNuevoGUI.getPlayerBlueConfig().getComputerPlayer().setSelected(true);
            juegoNuevoGUI.getPlayerBlueConfig().getHumanPlayer().setEnabled(false);
        }
        else {
            juegoNuevo.getPlayers()[0].setControl('H');
            juegoNuevoGUI.getPlayerBlueConfig().getComputerPlayer().setEnabled(false);
        }

        if(juegoGrabado.getPlayers()[1].getControl()=='C'){
            juegoNuevo.getPlayers()[1].setControl('C');
            juegoNuevoGUI.getPlayerRedConfig().getHumanPlayer().setEnabled(false);
            juegoNuevoGUI.getPlayerRedConfig().getComputerPlayer().setSelected(true);
        }
        else {
            juegoNuevo.getPlayers()[1].setControl('H');
            juegoNuevoGUI.getPlayerRedConfig().getComputerPlayer().setEnabled(false);
        }

        String turnPlayer = juegoGrabado.getActivePlayer().getName();
        if (!turnPlayer.equals(juegoNuevo.getActivePlayer().getName())){
            gameContent.getGameBoard().changeEnableTurnPLayer();
            juegoNuevo.changeActivePlayer();
        }

        int cas=0;
        ArrayList<SOSGameGUI.GameBoard.Casilla> casillaJuegoNuevo = juegoNuevoGUI.getGameContent().getGameBoard().getCasillas();
        for (SOSGameGUI.GameBoard.Casilla casilla: datoGrabado.getGameContent().getGameBoard().getCasillas()){
            for (Component component : casilla.getContent().getComponents()){
                casillaJuegoNuevo.get(cas).getContent().add(component);
            }
            cas++;
        }
    }
}