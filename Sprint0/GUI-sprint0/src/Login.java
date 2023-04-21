import javax.swing.*;
import java.awt.event.*;

public class Login extends JFrame{

    private JPanel mainPanel;
    private JLabel loginTitle;
    private JButton iniciarButton;
    private JButton salirButton;
    private JTextField txtName;
    private JCheckBox admiCheckBox;
    private JCheckBox userCheckBox;

    public Login(){
        setTitle("Login");
        setContentPane(mainPanel);
        this.pack();
        setSize(500,600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userCheckBox.setSelected(true);
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        userCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (userCheckBox.isSelected()){
                    admiCheckBox.setSelected(false);
                }
                else {
                    admiCheckBox.setSelected(true);
                }
            }
        });


        admiCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(admiCheckBox.isSelected()){
                    userCheckBox.setSelected(false);
                }
                else {
                    userCheckBox.setSelected(true);
                }
            }
        });
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean confirm = false;
                String user = txtName.getText();
                if(user.equals(userCheckBox.getText()) && userCheckBox.isSelected()){
                    confirm = true;
                }
                else if(user.equals(admiCheckBox.getText()) && admiCheckBox.isSelected()){
                    confirm = true;
                }
                showConfirmation(confirm, user);
            }
        });
    }

    void showConfirmation(boolean confirm,String user){
        if (confirm){
            JOptionPane.showMessageDialog(null,"Iniciando sesion como "+user+" ...","confirmacion",JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }
        else if(userCheckBox.isSelected()){
            JOptionPane.showMessageDialog(null,"No registra como "+userCheckBox.getText(),"confirmacion",JOptionPane.ERROR_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(null,"No registra como "+admiCheckBox.getText(),"confirmacion",JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        new Login().setVisible(true);
    }

}
