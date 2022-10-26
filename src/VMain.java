import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VMain extends JFrame implements ActionListener {
    private VPanel panel;

    public VMain() {
        super();

        // frame 속성 설정
        this.setTitle("CheckSum 구하기");
        this.setSize(new Dimension(500,900));
        this.setLocation(new Point(200,100));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        this.panel = new VPanel();
        this.panel.setActionListener(this);
        this.getContentPane().add(this.panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) { this.panel.actionPerformed(e); }



    public static void main(String[] args) {
        VMain vMain = new VMain();
        vMain.setVisible(true);
    }


}
