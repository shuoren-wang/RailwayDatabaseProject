package ui;

import jdbc.TicketDAO;
import model.Ticket;
import model.User;
import net.miginfocom.swing.MigLayout;

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
public class PurchasedTicketsDialog extends JDialog{
    private User user;
    private Ticket ticket;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JComboBox ticketComboBox;
    private DefaultComboBoxModel ticketComboBoxModel;
    private JLabel ticketIdLabel;
    private JLabel dateLabel;
    private JLabel fromLineIdLabel;
    private JLabel toLineIdLabel;
    private JLabel trainNoLabel;
    private JLabel seatClassLabel;
    private JLabel seatNoLabel;
    private TicketDAO ticketDAO;


    public PurchasedTicketsDialog(User user){
        this.user=user;
        ticketDAO=TicketDAO.getInstance();
        ticketDAO.init();

        contentPanel=new JPanel();
        buttonPanel=new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(500,330);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("","[][grow]","[][][][][][][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

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
        contentPanel.add(label, "cell 0 0, alignx trailing");
    }

    private void addTicketInfoDataComboBox(){
        ticketComboBoxModel=new DefaultComboBoxModel();
        ticketComboBox=new JComboBox(ticketComboBoxModel);
        ticketComboBox.setMaximumSize(new Dimension(300,600));
        contentPanel.add(ticketComboBox, "cell 1 0,growx");

        ArrayList<Ticket> tickets=new ArrayList<Ticket>();
        //TODO: get data from database
        if(tickets.size()>0) {
            Object[] ticketsArr = tickets.toArray();
            synchronized (tickets.toArray()) {
                for (Object next : ticketsArr) {
                    ticketComboBoxModel.addElement(next);
                }
            }

            ticketComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        Ticket ticket = (Ticket) ticketComboBox.getSelectedItem();
                        update(ticket);
                    }
                }
            });
        }
    }

    private void addTicketIdLabel(){
        JLabel label=new JLabel("Ticket ID :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addTicketIdDataLabel(){
        String ticketIdData="";
        if(ticket!=null) {
            ticketIdData= Integer.toString(ticket.getId());
        }
        ticketIdLabel=new JLabel(ticketIdData);
        contentPanel.add(ticketIdLabel, "cell 1 1, growx");
    }

    private void addTrainNoLabel(){
        JLabel label=new JLabel("TrainByStops No. :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }

    private void addTrainNoDataLabel(){
        String ticketIdData="";
        if(ticket!=null) {
            ticketIdData = Integer.toString(ticket.getTrainNo());
        }
        trainNoLabel=new JLabel(ticketIdData);
        contentPanel.add(trainNoLabel, "cell 1 2,growx");
    }

    private void addDateLabel(){
        JLabel label=new JLabel("Date :");
        contentPanel.add(label, "cell 0 3, alignx trailing");
    }

    private void addDateDataLabel(){
        String date="";
        if(ticket!=null) {
            date = ticket.getDepartDate().toString();
        }
        dateLabel=new JLabel(date);
        contentPanel.add(dateLabel, "cell 1 3, growx");
    }

    private void addFromLineIdLabel(){
        JLabel label=new JLabel("From Line id :");
        contentPanel.add(label, "cell 0 4, alignx trailing");
    }

    private void addFromLineIdDataLabel(){
        String fromLineData="";
        if(ticket!=null) {
            fromLineData = Integer.toString(ticket.getFromStationId());
        }
        fromLineIdLabel=new JLabel(fromLineData);
        contentPanel.add(fromLineIdLabel, "cell 1 4, growx");
    }

    private void addToLineIdLabel(){
        JLabel label=new JLabel("To Line id :");
        contentPanel.add(label, "cell 0 5, alignx trailing");
    }

    private void addToLineIdDataLabel(){
        String toLineData="";
        if(ticket!=null) {
            toLineData=Integer.toString(ticket.getToStationId());
        }
        toLineIdLabel=new JLabel(toLineData);
        contentPanel.add(toLineIdLabel, "cell 1 5, growx");
    }

    private void addSeatClassLabel(){
        JLabel label=new JLabel("Seat Class :");
        contentPanel.add(label, "cell 0 6, alignx trailing");
    }

    private void addSeatClassDataLabel(){
        String seat="";
        if(ticket!=null) {
            seat = ticket.getSeatClass();
        }
        seatClassLabel=new JLabel(seat);
        contentPanel.add(seatClassLabel, "cell 1 6, growx");
    }

    private void addSeatNoLabel(){
        JLabel label=new JLabel("Seat No :");
        contentPanel.add(label, "cell 0 7, alignx trailing");
    }

    private void addSeatNoDataLabel(){
        String seat="";
        if(ticket!=null) {
            seat = Integer.toString(ticket.getSeatNo());
        }
        seatNoLabel=new JLabel(seat);
        contentPanel.add(seatNoLabel, "cell 1 7, growx");
    }

    private void addReturnTicketButton(){
        JButton submitButton = new JButton("Return Ticket");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO database
            }
        });
        submitButton.setActionCommand("OK");
        buttonPanel.add(submitButton);
    }

    private void addCancelButton(){
        JButton cancelButton=new JButton("Go Back");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PurchasedTicketsDialog.this.setVisible(false);
                PurchasedTicketsDialog.this.dispose();
                System.out.println("LoginDialog: GoBack button pressed");
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPanel.add(cancelButton);
    }

    private void update(Ticket ticket){
        this.ticket =ticket;

        ticketIdLabel.setText(Integer.toString(this.ticket.getId()));
        trainNoLabel.setText(Integer.toString(this.ticket.getTrainNo()));
        fromLineIdLabel.setText(Integer.toString(this.ticket.getFromStationId()));
        toLineIdLabel.setText(Integer.toString(this.ticket.getToStationId()));
        dateLabel.setText(this.ticket.getDepartDate().toString());
        seatNoLabel.setText(Integer.toString(this.ticket.getSeatNo()));
        seatClassLabel.setText(this.ticket.getSeatClass());

    }
}
