import ui.passenger_ui.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static jdbc.JDBC.*;

public class PassengerMain {
    public static void main(String[] args) {

        PassengerMainFrame frame = PassengerMainFrame.getInstance();

//        openCon();

        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                closeCon();

                System.exit(0);
            }
        });
    }
}
