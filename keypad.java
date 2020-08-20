import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class keypad extends JFrame {
    private int digit_count = 15;
    private String code_one,code_two,code_three,mastercode;
    JLabel lab_person  = new JLabel("Person 1:");   
    JLabel lab_digits   = new JLabel(" ");
    JLabel lab_status  = new JLabel("Initialisation");

    public static void main(String[] args) {
        System.out.println("WARNING: Make sure the CMOS battery is at a good level. Power loss will permantly prevent access.");

        keypad start_keypad = new keypad();
    }

    private keypad() {
        setTitle("Keypad");
        setSize(300, 400);
        setLayout(new GridLayout(5, 3));     

        add(lab_person); 
        add(lab_digits); 
        add(lab_status); 

        JButton btn_1 = new JButton("1");   add(btn_1); btn_1.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { handleKeypad(btn_1.getText()); } });
        JButton btn_2 = new JButton("2");   add(btn_2); btn_2.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { handleKeypad(btn_2.getText()); } });
        JButton btn_3 = new JButton("3");   add(btn_3); btn_3.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { handleKeypad(btn_3.getText()); } });
        JButton btn_4 = new JButton("4");   add(btn_4); btn_4.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { handleKeypad(btn_4.getText()); } });
        JButton btn_5 = new JButton("5");   add(btn_5); btn_5.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { handleKeypad(btn_5.getText()); } });
        JButton btn_6 = new JButton("6");   add(btn_6); btn_6.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { handleKeypad(btn_6.getText()); } });
        JButton btn_7 = new JButton("7");   add(btn_7); btn_7.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { handleKeypad(btn_7.getText()); } });
        JButton btn_8 = new JButton("8");   add(btn_8); btn_8.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { handleKeypad(btn_8.getText()); } });
        JButton btn_9 = new JButton("9");   add(btn_9); btn_9.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { handleKeypad(btn_9.getText()); } });
        JButton btn_0 = new JButton("0");   add(btn_0); btn_0.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { handleKeypad(btn_0.getText()); } });
        JLabel lab_blank_3  = new JLabel("");   add(lab_blank_3); 

        setVisible(true);
    }

    private String handleKeypad(String x ) {      
        if ( digit_count > 10 ){
            if ( code_one == null ) code_one = x;
            else code_one = code_one+x; 
        }
        else if ( digit_count > 5 ){ 
            if ( code_two == null ) code_two = x;
            else code_two = code_two+x;             
        }
        else if ( digit_count > 0 ){ 
            if ( code_three == null ) code_three = x;
            else code_three = code_three+x; 
            
        }
        System.out.println(digit_count + " " + code_one + " " + code_two + " " + code_three);
        digit_count--;
        switch (digit_count) {
            case 10:lab_person.setText("Person 2:");  
                    break;
            case 5: lab_person.setText("Person 3:");  
                    break;
            case 0: lab_person.setText("Person 1:");  
                    break;
        }

        if ( mastercode != null  && digit_count <= 0 ){
            if ( mastercode.equals(doubleTransposition(code_one, code_two, code_three))) { lab_status.setText("Unlocked"); }
            else lab_status.setText("Bad Code");
            System.out.println("\nCode Entered:" + doubleTransposition(code_one, code_two, code_three) + "\nMaster Code:" + mastercode);
            code_one = "";
            code_two = "";
            code_three = "";
            digit_count = 15;
        }

        if ( mastercode == null && digit_count <= 0 ){      
            System.out.println("Mastercode is now set to " + doubleTransposition(code_one, code_two, code_three));
            mastercode = doubleTransposition(code_one, code_two, code_three);
            code_one = "";
            code_two = "";
            code_three = "";
            lab_status.setText("Locked");
            digit_count = 15;
        }     
        return " ";
    }

    private String doubleTransposition(String one, String two, String three){
        // one is the first persons code
        // two is the second persons code
        // three is the third persons code
        char[] one_char = one.toCharArray(); 
        char[] two_char = two.toCharArray(); 
        char[] three_char = three.toCharArray(); 

        System.out.println("\n=== Original Codes ===\nCode 1:"+one+"\nCode 2:"+two+"\nCode 3:"+three);

        System.out.println( "\n=== Columns Transposed Codes ===\n" +
                            "\nColumns  "+"12345"+
                            "\nCode 1:  "+one_char[2]+one_char[3]+one_char[0]+one_char[1]+one_char[4]+ 
                            "\nCode 2:  "+two_char[2]+two_char[3]+two_char[0]+two_char[1]+two_char[4]+
                            "\nCode 3:  "+three_char[2]+three_char[3]+three_char[0]+three_char[1]+three_char[4]);

        System.out.println( "\n=== Rows and Columns Transposed Codes ===\n" +
                            "\nColumns  "+"34125"+
                            "\nCode 2:  "+two_char[2]+two_char[3]+two_char[0]+two_char[1]+two_char[4]+
                            "\nCode 1:  "+one_char[2]+one_char[3]+one_char[0]+one_char[1]+one_char[4]+
                            "\nCode 3:  "+three_char[2]+three_char[3]+three_char[0]+three_char[1]+three_char[4]);
        
        // Value returned will shift the columns by shifting the indexes 
        // of each code character arrays around.
        // The will return a string of all the codes around shifting the order around.
        // This will will do a transposition of rows (2,1,3) and columns (3,4,1,2,5)

        // An addition to this each set of code will be prefixed with the order it was entered
        // This will ensure that the code is entered in the correct order.
        return ""+  two_char[2]+two_char[3]+two_char[0]+two_char[1]+two_char[4]+
                    one_char[2]+one_char[3]+one_char[0]+one_char[1]+one_char[4]+
                    three_char[2]+three_char[3]+three_char[0]+three_char[1]+three_char[4];
        //This code ensures the codes are entered in the correct order
        //return  "2"+ two_char[2]+two_char[3]+two_char[0]+two_char[1]+two_char[4]+
        //        "1"+ one_char[2]+one_char[3]+one_char[0]+one_char[1]+one_char[4]+
        //        "3"+ three_char[2]+three_char[3]+three_char[0]+three_char[1]+three_char[4];
    }
}