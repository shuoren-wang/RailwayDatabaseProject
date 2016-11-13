import jdbc.JDBC;
import model.User;
import ui.clerk_ui.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClerkMain {
    public static void main(String[] args) {

        JDBC.getInstance().openCon();
        System.out.println("ClerkMain opened");

        ClerkMainFrame frame = ClerkMainFrame.getInstance();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //TODO shutdown database
                System.exit(0);
            }
        });
    }

}
