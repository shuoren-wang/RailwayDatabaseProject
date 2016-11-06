package ui;

import model.Ticket;
import model.User;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class PassengerTicketsDialog extends JDialog{
    static Logger LOG = Logger.getLogger(PassengerTicketsDialog.class);
    private User user;
    private Ticket ticket;
    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JComboBox ticketComboBox;
    private JLabel ticketIdLabel;
    private JLabel dateLabel;
    private JLabel fromLineIdLabel;
    private JLabel toLineIdLabel;
    private JLabel trainNoLabel;
    private JLabel seatClassLabel;
    private JLabel seatNoLabel;


    PassengerTicketsDialog(JFrame frame, User user){
        contentPanel=new JPanel();
        buttonPanel=new JPanel();


        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(500,500);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("","[][grow]","[]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        addTicketInfoLabel();
        addTicketInfoDataComboBox();

        addTicketIdLabel();
        addTicketIdDataLabel();
        addTrainNoLabel();
        addTrainNoDataLabel();
        addDateLabel();
        addDateDataLabel();
        addFromLineIdLabel();
        addFromLineIdDataLabel();
        addToLineIdLabel();
        addToLineIdDataLabel();
        addSeatClassLabel();
        addSeatClassDataLabel();
        addSeatNoLabel();
        addSeatNoDataLabel();

        addReturnTicketButton();
        addCancelButton();
    }

   private void addTicketInfoLabel(){
        JLabel label=new JLabel("Ticket Info:");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addTicketInfoDataComboBox(){
        ArrayList<Ticket> tickets=new ArrayList<Ticket>();
        //TODO: get data from database

        ticketComboBox=new JComboBox(tickets.toArray());
        ticketComboBox.setMaximumSize(new Dimension(300,600));
        ticketComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    Ticket ticket= (Ticket) ticketComboBox.getSelectedItem();
                    update(ticket);
                }
            }
        });
        contentPanel.add(ticketComboBox, "cell 1 1, alignx trailing");
    }

    private void addTicketIdLabel(){
        JLabel label=new JLabel("Ticket ID :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addTicketIdDataLabel(){
        String ticketIdData=Integer.toString(ticket.getId());
        ticketIdLabel=new JLabel(ticketIdData);
        contentPanel.add(ticketIdLabel, "cell 1 1, alignx trailing");
    }

    private void addTrainNoLabel(){
        JLabel label=new JLabel("Train No. :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }

    private void addTrainNoDataLabel(){
        String ticketIdData=Integer.toString(ticket.getTrainNo());
        trainNoLabel=new JLabel(ticketIdData);
        contentPanel.add(trainNoLabel, "cell 1 2, alignx trailing");
    }

    private void addDateLabel(){
        JLabel label=new JLabel("Date :");
        contentPanel.add(label, "cell 0 3, alignx trailing");
    }

    private void addDateDataLabel(){
        String date= ticket.getDepartDate().toString();
        dateLabel=new JLabel(date);
        contentPanel.add(dateLabel, "cell 1 3, alignx trailing");
    }

    private void addFromLineIdLabel(){
        JLabel label=new JLabel("From Line id :");
        contentPanel.add(label, "cell 0 4, alignx trailing");
    }

    private void addFromLineIdDataLabel(){
        String fromLineData=Integer.toString(ticket.getFromLineId());
        fromLineIdLabel=new JLabel(fromLineData);
        contentPanel.add(fromLineIdLabel, "cell 1 4, alignx trailing");
    }

    private void addToLineIdLabel(){
        JLabel label=new JLabel("To Line id :");
        contentPanel.add(label, "cell 0 5, alignx trailing");
    }

    private void addToLineIdDataLabel(){
        String toLineData=Integer.toString(ticket.getToLineId());
        toLineIdLabel=new JLabel(toLineData);
        contentPanel.add(toLineIdLabel, "cell 1 5, alignx trailing");
    }

    private void addSeatClassLabel(){
        JLabel label=new JLabel("Seat Class :");
        contentPanel.add(label, "cell 0 6, alignx trailing");
    }

    private void addSeatClassDataLabel(){
        String seat= ticket.getSeatClass();
        seatClassLabel=new JLabel(seat);
        contentPanel.add(seatClassLabel, "cell 1 6, alignx trailing");
    }

    private void addSeatNoLabel(){
        JLabel label=new JLabel("Seat No :");
        contentPanel.add(label, "cell 0 7, alignx trailing");
    }

    private void addSeatNoDataLabel(){
        String seat=Integer.toString(ticket.getSeatNo());
        seatNoLabel=new JLabel(seat);
        contentPanel.add(seatNoLabel, "cell 1 7, alignx trailing");
    }

    private void addReturnTicketButton(){
        JButton submitButton = new JButton("Return Ticket");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
        submitButton.setActionCommand("OK");
        contentPanel.add(submitButton);
    }

    private void addCancelButton(){
        JButton cancelButton=new JButton("Go Back");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PassengerTicketsDialog.this.setVisible(false);
                PassengerTicketsDialog.this.dispose();
                LOG.info("LoginDialog: GoBack button pressed");
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPanel.add(cancelButton);
    }

    private void update(Ticket ticket){
        this.ticket =ticket;

        ticketIdLabel.setText(Integer.toString(this.ticket.getId()));
        trainNoLabel.setText(Integer.toString(this.ticket.getTrainNo()));
        fromLineIdLabel.setText(Integer.toString(this.ticket.getFromLineId()));
        toLineIdLabel.setText(Integer.toString(this.ticket.getToLineId()));
        dateLabel.setText(this.ticket.getDepartDate().toString());
        seatNoLabel.setText(Integer.toString(this.ticket.getSeatNo()));
        seatClassLabel.setText(this.ticket.getSeatClass());

    }
}
