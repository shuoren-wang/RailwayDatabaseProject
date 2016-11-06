package ui;

import model.Ticket;
import model.Train;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by shuorenwang on 2016-10-21.
 */
public class PurchaseDialog extends JDialog{
    static Logger LOG = Logger.getLogger(LoginDialog.class);
    private final JPanel contentPanel;
    private final JPanel buttonPanel;
    private Train currentTrain;

    private Ticket currentTicket;

    PurchaseDialog(JFrame frame, Train train){

        this.currentTrain = train;
        contentPanel=new JPanel();
        buttonPanel=new JPanel();

        setResizable(false);
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(500,500);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new MigLayout("","[][grow]","[][][][][][][]"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        addTicketIdLabel();
        addTicketIdDataLabel();
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

        addConfirmButton();
        addCancelButton();
    }

    private void addTicketIdLabel(){
        JLabel label=new JLabel("Ticket ID :");
        contentPanel.add(label, "cell 0 1, alignx trailing");
    }

    private void addTicketIdDataLabel(){
        String ticketIdData=Integer.toString(currentTicket.getId());
        JLabel label=new JLabel(ticketIdData);
        contentPanel.add(label, "cell 1 1, alignx trailing");
    }

    private void addTrainNoLabel(){
        JLabel label=new JLabel("Train No. :");
        contentPanel.add(label, "cell 0 2, alignx trailing");
    }

    private void addTrainNoDataLabel(){
        String ticketIdData=Integer.toString(currentTicket.getTrainNo());
        JLabel label=new JLabel(ticketIdData);
        contentPanel.add(label, "cell 1 2, alignx trailing");
    }

    private void addDateLabel(){
        JLabel label=new JLabel("Date :");
        contentPanel.add(label, "cell 0 3, alignx trailing");
    }

    private void addDateDataLabel(){
        String date= currentTicket.getDepartDate().toString();
        JLabel label=new JLabel(date);
        contentPanel.add(label, "cell 1 3, alignx trailing");
    }

    private void addFromLineIdLabel(){
        JLabel label=new JLabel("From Line id :");
        contentPanel.add(label, "cell 0 4, alignx trailing");
    }

    private void addFromLineIdDataLabel(){
        String fromLineData=Integer.toString(currentTicket.getFromLineId());
        JLabel label=new JLabel(fromLineData);
        contentPanel.add(label, "cell 1 4, alignx trailing");
    }

    private void addToLineIdLabel(){
        JLabel label=new JLabel("To Line id :");
        contentPanel.add(label, "cell 0 5, alignx trailing");
    }

    private void addToLineIdDataLabel(){
        String toLineData=Integer.toString(currentTicket.getToLineId());
        JLabel label=new JLabel(toLineData);
        contentPanel.add(label, "cell 1 5, alignx trailing");
    }

    private void addSeatClassLabel(){
        JLabel label=new JLabel("Seat Class :");
        contentPanel.add(label, "cell 0 6, alignx trailing");
    }

    private void addSeatClassDataLabel(){
        String seat=currentTicket.getSeatClass();
        JLabel label=new JLabel(seat);
        contentPanel.add(label, "cell 1 6, alignx trailing");
    }

    private void addSeatNoLabel(){
        JLabel label=new JLabel("Seat No :");
        contentPanel.add(label, "cell 0 7, alignx trailing");
    }

    private void addSeatNoDataLabel(){
        String seat=Integer.toString(currentTicket.getSeatNo());
        JLabel label=new JLabel(seat);
        contentPanel.add(label, "cell 1 7, alignx trailing");
    }


    private void addConfirmButton(){
        JButton submitButton = new JButton("Confirm");
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
                PurchaseDialog.this.setVisible(false);
                PurchaseDialog.this.dispose();
                LOG.info("LoginDialog: GoBack button pressed");
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPanel.add(cancelButton);
    }
}
