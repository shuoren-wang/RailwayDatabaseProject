package jdbc;

import model.Ticket;
import model.Train;
import model.TrainByStops;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuorenwang on 2016-11-14.
 */
public class TicketDAO  extends Component {

    private static final TicketDAO instance = new TicketDAO();

    private List<Ticket> tickets;
    private Connection con;

    private TicketDAO() {

        System.out.println("Instantiating TicketDAO");
    }

    public static final TicketDAO getInstance() {
        return instance;
    }

    public void init() {
        try {
            System.out.println("Initializing TicketDAO");
            con = JDBC.getCon();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void loadData(User user) {
        System.out.println(String.format("Load data from trainByStopsList"));
        tickets = new ArrayList<Ticket>();
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("CALL spViewTickets("+user.getUserID()+")");
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();

                ticket.setUser(user);
                ticket.setId(rs.getInt("ticketId"));
                ticket.setFromStationId(rs.getInt("fromStationId"));
                ticket.setToStationId(rs.getInt("toStationId"));
                ticket.setDepartDate(rs.getDate("travelDate"));
                ticket.setSeatClass(rs.getString(6));
                ticket.setLineId(rs.getInt("lineID"));
                ticket.setTrainNo(rs.getInt("TrainNumber"));

                tickets.add(ticket);
            }
            System.out.println("Load data from trainByStopsDAO: success!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * fails if requesting UserId is not clerk and not owner of ticket or ticket doesn't exist
     * @param userId
     * @param ticketId
     * @return true if success, false if failure.
     */
    public boolean returnTicket(int userId, int ticketId){
        System.out.println("Return Tickets");
        boolean status=false;
        CallableStatement cs = null;
        try {
            cs = con.prepareCall("{CALL spReturnTicket("
                    + userId + ","
                    + ticketId  +")}");

            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                status = (rs.getInt(0)==1);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),"Warning",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        return status;
    }

    /**
     * @param user
     * @param trainByStops
     * @return TicketID if success,  -1 if failed
     */
    public int purchaseTickets(User user, TrainByStops trainByStops) {
        System.out.println("Purchase Tickets"+user);
        System.out.println("Purchase Tickets"+trainByStops);
        int ticketID=0;
        CallableStatement cs = null;

        try {
            System.out.println("sp= "+"{CALL spPurchase("
                    + user.getUserID() + ","
                    + trainByStops.getFromStationId() + ","
                    + trainByStops.getToStationId() + ","
                    + "'"+trainByStops.getDate() + "',"
                    + "'"+trainByStops.getSeatClass() + "',"
                    + trainByStops.getLineId() + ","
                    + trainByStops.getTrainNumber() +")}");

            cs = con.prepareCall("{CALL spPurchase("
                    + user.getUserID() + ","
                    + trainByStops.getFromStationId() + ","
                    + trainByStops.getToStationId() + ","
                    + "'"+trainByStops.getDate() + "',"
                    + "'"+trainByStops.getSeatClass() + "',"
                    + trainByStops.getLineId() + ","
                    + trainByStops.getTrainNumber() +")}");

            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                ticketID = rs.getInt(1);
            }

            return ticketID;

        } catch (Exception e) {
            return -1;
        }
    }


}
