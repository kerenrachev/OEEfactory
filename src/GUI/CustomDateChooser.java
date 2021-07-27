package GUI;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;

public class CustomDateChooser extends JDateChooser {

    public CustomDateChooser() {
        super();

        this.popup = new JPopupMenu() {
            @Override
            public void setVisible(final boolean b) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        handleVisibility(b);
                    }
                });
            }

            private void handleVisibility(boolean b) {
                if (!jcalendar.getMonthChooser().getComboBox().hasFocus()) {
                    super.setVisible(b);
                }
            }
        };

        this.popup.setLightWeightPopupEnabled(true);
        this.popup.add(this.jcalendar);
    }
}
