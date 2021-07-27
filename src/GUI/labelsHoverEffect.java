 
package GUI;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; 
import java.util.ArrayList;
import javax.swing.JLabel;

 
public class labelsHoverEffect  extends MouseAdapter{ 
        private Font              f; 
        private ArrayList<JLabel> labels;
        
        //Class that is used for making the hovering effects on jlabels 
        public labelsHoverEffect(ArrayList<JLabel> labels){
        this.labels = labels;     
        }
        public void start(){ 
        for(int i = 0 ; i < labels.size(); i++)
            labels.get(i).addMouseListener(this); 
        }
        public  void labelBold(JLabel label){
         f = label.getFont(); 
         label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        }
        public  void labelUnBold(JLabel label){
         f = label.getFont(); 
         label.setFont(f.deriveFont(f.getStyle() & ~ Font.BOLD));
        }  
        @Override
        public void mouseEntered(MouseEvent evt) { 
            JLabel source = (JLabel) evt.getSource();
            for (JLabel label : labels) {
                if (label == source) {
                    labelBold(label);
                }  
        
            }
        }  
        @Override
        public void mouseExited(MouseEvent evt) {
           JLabel source = (JLabel) evt.getSource();
            for (JLabel label : labels) {
                if (label == source) {
                    labelUnBold(label);
                }  
            }
        }  
}
