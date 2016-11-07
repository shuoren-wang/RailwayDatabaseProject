package ui.passenger_ui;

import ui.MainFrame;

/**
 * Created by shuorenwang on 2016-11-06.
 */
public class PassengerMainFrame extends MainFrame {

    protected static final PassengerMainFrame instance = new PassengerMainFrame();

    private PassengerMainFrame() {
        super();
    }


    public static PassengerMainFrame getInstance() {
        return instance;
    }
}
