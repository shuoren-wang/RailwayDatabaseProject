import jdbc.JDBC;
import ui.passenger_ui.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static jdbc.JDBC.*;

public class PassengerMain {
    public static void main(String[] args) {

        JDBC.getInstance().openCon();

        PassengerMainFrame frame = PassengerMainFrame.getInstance();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JDBC.getInstance().closeCon();
                System.exit(0);
            }
        });
    }
}
