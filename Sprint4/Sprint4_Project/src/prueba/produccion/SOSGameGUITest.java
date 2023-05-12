package produccion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SOSGameGUITest{

    @Test
    public void TestsimulateClick(){
        SOSGameGUI sosGameGUI = new SOSGameGUI(new SOSGameBoard(3,'S'));
        sosGameGUI.getGameContent().getSimpleGame().setSelected(true);
        sosGameGUI.getPlayerBlueConfig().getHumanPlayer().setEnabled(false);
        sosGameGUI.getGameContent().simulateClick(sosGameGUI.getGameContent().getGameBoard().getCasilla(0,0).getContent(),0);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(sosGameGUI.getGame().getBox(0, 0) != SOSGameBoard.Box.EMPTY);
    }

}