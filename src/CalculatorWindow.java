// Import the necessary classes for event handling, GUI displays and String handling
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.String;

// Window properties
public class CalculatorWindow extends JFrame implements ActionListener {
    private static final int width = 600;                       // Window's width
    private static final int height = 350;                      // Window's height
    private static final int xOrigin = 450;                     // Window display point in X
    private static final int yOrigin = 250;                     // Window display point in Y

    // Inner properties, inner vertical separation, column size, line thickness, etc
    private static final int VerticalSpInB = 6;                 // Vertical space in between value for the GUI
    private static final int VerticalSpInBII = 8;               // Second vertical space in between value for the GUI
    private static final int HorizontalSpInB = 6;               // Horizontal space in between value for the GUI
    private static final int colSize = 30;                      // Column size
    private static final int fontSize = 20;                     // Font size
    private static final int thick = 2;                         // Line thickness

    // Inner variables
    private int op = -1;                                        // -1 = no operation

    private String val1 = "0.0";
    private double result;
    private double memory;
    private double value1;
    private double value2;

    /*
     * There are two different kinds of buttons in this program:
     *      1. First, the number buttons: including pointB and makeNegB, these buttons 'append' a value to the display, can be a digit or symbol
     *      2. Second, the function buttons: all the buttons that can perform a mathematical action, such as +, -, *, /, %, etc.
     */
    // Center buttons - numbers
    private final JButton sevenB = new JButton("7");
    private final JButton eightB = new JButton("8");
    private final JButton nineB = new JButton("9");
    private final JButton fourB = new JButton("4");
    private final JButton fiveB = new JButton("5");
    private final JButton sixB = new JButton("6");
    private final JButton oneB = new JButton("1");
    private final JButton twoB = new JButton("2");
    private final JButton threeB = new JButton("3");

    // Center buttons - functions
    private final JButton memoryB = new JButton("M");
    private final JButton clearB = new JButton("C");
    private final JButton makeNegB = new JButton("(-)");
    private final JButton pointB = new JButton(".");
    private final JButton ceroB = new JButton("0");
    private final JButton equalsB = new JButton("=");

    // Center buttons array
    private final JButton[] centButtons = {memoryB, clearB, makeNegB, sevenB, eightB, nineB, fourB, fiveB, sixB, oneB, twoB, threeB, pointB, ceroB, equalsB};


    // West panel buttons
    private final JButton powerYB = new JButton("x^y");
    private final JButton sinB = new JButton("sin");
    private final JButton cosB = new JButton("cos");
    private final JButton tanB = new JButton("tan");
    private final JButton logB = new JButton("log");
    private final JButton lnB = new JButton("ln");
    private final JButton factB = new JButton("x!");

    // West panel buttons arrays
    private final JButton[] westFunctButtons = {powerYB, sinB, cosB, tanB, logB, lnB, factB};

    // East panel buttons
    private final JButton plusB = new JButton("+");
    private final JButton minusB = new JButton("-");
    private final JButton multB = new JButton("x");
    private final JButton divisionB = new JButton("/");
    private final JButton modularB = new JButton("%");
    private final JButton squareRootB = new JButton("√");
    private final JButton powerTwoB = new JButton("x^2");
    private final JButton powerThreeB = new JButton("x^3");

    //East panel buttons arrays
    private final JButton[] eastFunctButtons = {plusB, minusB, multB, divisionB, modularB, squareRootB, powerTwoB, powerThreeB};

    // Display
    private final JTextField calcDisplay;

    MathProcess doubleValProcess;
    MathProcess singleValProcess;

    // Calculator properties
    CalculatorWindow() {
        // Main container
        Container contentPane;

        // Function panels
        JPanel functPanelWest;
        JPanel functPanelCenter;
        JPanel functPanelEast;

        // Display panel
        JPanel displayPanel;

        // Main window properties
        setSize(width, height);
        setResizable(false);
        setTitle("Calculator App");
        setLocation(xOrigin, yOrigin);

        // Set calculator inner layout
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(HorizontalSpInB, VerticalSpInB));
        contentPane.setBackground(Color.LIGHT_GRAY);

        // Display panel properties
        displayPanel = new JPanel();
        displayPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, thick));
        displayPanel.setBackground(Color.LIGHT_GRAY);
        displayPanel.setLayout(new FlowLayout());

        // Center panel properties
        functPanelCenter = new JPanel();
        functPanelCenter = setPanelCharacteristics(functPanelCenter, 5, 3);

        // West panel properties
        functPanelWest = new JPanel();
        functPanelWest = setPanelCharacteristics(functPanelWest, 4, 2);

        // East panel properties
        functPanelEast = new JPanel();
        functPanelEast = setPanelCharacteristics(functPanelEast, 4, 2);

        // Window organization for inner layout
        contentPane.add(displayPanel, BorderLayout.NORTH);
        contentPane.add(functPanelWest, BorderLayout.WEST);
        contentPane.add(functPanelCenter, BorderLayout.CENTER);
        contentPane.add(functPanelEast, BorderLayout.EAST);

        // Display definition
        calcDisplay = new JTextField();
        calcDisplay.setColumns(colSize);
        calcDisplay.setFont(new Font("Verdana", Font.PLAIN, fontSize));
        calcDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        calcDisplay.setEditable(false);
        displayPanel.add(calcDisplay);

        // Button definitions
        setButtonSettings(centButtons, functPanelCenter);
        setButtonSettings(westFunctButtons, functPanelWest);
        setButtonSettings(eastFunctButtons, functPanelEast);

        // Closes when 'X' is pressed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Method that sets the characteristics for the panels
    public JPanel setPanelCharacteristics(JPanel toEdit, int setRow, int setCol){
        JPanel newPanel = toEdit;

        toEdit.setBorder(BorderFactory.createLineBorder(Color.GRAY, thick));
        toEdit.setBackground(Color.LIGHT_GRAY);
        toEdit.setLayout(new GridLayout(setRow, setCol, HorizontalSpInB, VerticalSpInBII));

        return newPanel;
    }

    // Method that sets the characteristics for the buttons and adds it to its panel
    public void setButtonSettings(JButton[] buttonArray, JPanel panel){
        for(int i = 0; i < buttonArray.length; i++){
            buttonArray[i].addActionListener(this);
            buttonArray[i].setBackground(Color.WHITE);
            buttonArray[i].setForeground(Color.BLACK);

            panel.add(buttonArray[i]);
        }
    }

    // Method that handles the events for the buttons
    public void actionPerformed(ActionEvent event){
        JButton clickedButton = (JButton) event.getSource();
            
        // Number Buttons
        if (clickedButton == oneB){                                 // If pressed '1'
            if(result != 0){                                        // If the user writes in a new value after the previous calculation, this resets the display for the user in any # press
                result = 0;
                calcDisplay.setText("");
            }
            calcDisplay.setText(calcDisplay.getText() + "1");       // Store the char in the display string

        } else if (clickedButton == twoB) {                         // If pressed '2'
            if(result != 0){
                result = 0;
                calcDisplay.setText("");
            }
            calcDisplay.setText(calcDisplay.getText() + "2");

        } else if (clickedButton == threeB) {                       // If pressed '3'
            if(result != 0){
                result = 0;
                calcDisplay.setText("");
            }
            calcDisplay.setText(calcDisplay.getText() + "3");

        } else if (clickedButton == fourB) {                        // If pressed '4'
            if(result != 0){
                result = 0;
                calcDisplay.setText("");
            }
            calcDisplay.setText(calcDisplay.getText() + "4");

        } else if (clickedButton == fiveB) {                        // If pressed '5'
            if(result != 0){
                result = 0;
                calcDisplay.setText("");
            }
            calcDisplay.setText(calcDisplay.getText() + "5");

        } else if (clickedButton == sixB) {                         // If pressed '6'
            if(result != 0){
                result = 0;
                calcDisplay.setText("");
            }
            calcDisplay.setText(calcDisplay.getText() + "6");

        } else if (clickedButton == sevenB) {                       // If pressed '7'
            if(result != 0){
                result = 0;
                calcDisplay.setText("");
            }
            calcDisplay.setText(calcDisplay.getText() + "7");

        } else if (clickedButton == eightB) {                       // If pressed '8'
            if(result != 0){
                result = 0;
                calcDisplay.setText("");
            }
            calcDisplay.setText(calcDisplay.getText() + "8");

        } else if (clickedButton == nineB) {                        // If pressed '9'
            if(result != 0){
                result = 0;
                calcDisplay.setText("");
            }
            calcDisplay.setText(calcDisplay.getText() + "9");

        } else if (clickedButton == ceroB) {                        // If pressed '0'
            if(result != 0){
                result = 0;
                calcDisplay.setText("");
            }
            calcDisplay.setText(calcDisplay.getText() + "0");

        } else if (clickedButton == pointB) {                       // If pressed '.'
            if(result != 0){
                result = 0;
                calcDisplay.setText("");
            }
            calcDisplay.setText(calcDisplay.getText() + ".");


        // Function buttons
        } else if (clickedButton == makeNegB) {                     // If pressed make negative button - turns the value on display into a negative value
            if (calcDisplay.getText().charAt(0) == '-'){             // Checks if the value was already converted to negative to turn it into positive
                calcDisplay.setText(calcDisplay.getText().replace('-', ' '));
            } else {
                calcDisplay.setText("-" + calcDisplay.getText());
            }

        } else if (clickedButton == memoryB) {                      // If pressed memory button, display the value stored in memory
            calcDisplay.setText("" + memory);

        } else if (clickedButton == clearB) {                       // If pressed clear button
            val1 = "";
            calcDisplay.setText("0");

        } else if (clickedButton == plusB) {                        // If pressed plus button - +
            val1 = calcDisplay.getText();                           // Store the first value for later use
            op = 0;                                                 // Sets 'op' to its corresponding function across the operations[] array
            calcDisplay.setText("");                                // Clears the display for the second value
    
        } else if (clickedButton == minusB) {                       // If pressed minus button - -
            val1 = calcDisplay.getText();
            op = 1;
            calcDisplay.setText("");
    
        } else if (clickedButton == multB) {                        // If pressed multiplication button - * 
            val1 = calcDisplay.getText();
            op = 2;
            calcDisplay.setText("");
    
        } else if (clickedButton == divisionB) {                    // If pressed division button - /
            val1 = calcDisplay.getText();
            op = 3;
            calcDisplay.setText("");
    
        } else if (clickedButton == modularB) {                     // If pressed modular button - %
            val1 = calcDisplay.getText();
            op = 4;
            calcDisplay.setText("");

        } else if (clickedButton == powerYB) {                      // If pressed power of Y button - x^y
            val1 = calcDisplay.getText();
            op = 8;
            calcDisplay.setText("");

        // Equals button
        } else if (clickedButton == equalsB) {
            // Convert both value strings into doubles to do the math
            value1 = Double.parseDouble(val1);
            value2 = Double.parseDouble(calcDisplay.getText());

            // Create a MathProcess object for a double value process

            try {
                doubleValProcess = new MathProcess(value1, value2);   // Create a MathProcess object for a double value process

                switch (op) {                                       // Depending on which value was given to 'op', choose the operation
                    case 0:                                        // If op 0 = plus
                        result = doubleValProcess.plus();
                        break;

                    // If operations[1] = minus
                    case 1:                                       // If op 1 = minus
                        result = doubleValProcess.minus();
                        break;

                    case 2:                                        // If op 2 = multiplication
                        result = doubleValProcess.mult();
                        break;

                    case 3:                                    // If op 3 = division
                        if(doubleValProcess.getVal2() == 0){ throw new Exception();};

                        result = doubleValProcess.division();
                        break;
                    
                    case 4:                                     // If op 4 = modular
                        result = doubleValProcess.modular();
                        break;
                    
                    case 8:                                      // If op 8 = powerY
                        if(doubleValProcess.getVal1() < 0 && doubleValProcess.getVal2() < 0 ){ throw new Exception();};
                        result = doubleValProcess.powerY();
                        break;
                }     

                val1 = "" + result;                                     // Store the result as the first value if the player wants to keep using it
                memory = result;                                        // Store the result in memory, also if the player wants to keep using it
                calcDisplay.setText(val1);                              // Display the result

            } catch (Exception e){
                System.out.println("Exception caught!!");
                calcDisplay.setText("ERROR");

                val1 = null;                                            // Store the result as the first value if the player wants to keep using it
            }
            

        // If the user clicks any single value process (√, x^2, x^3, sin, cos, tan, log, ln, x!)
        } else if (clickedButton == squareRootB || clickedButton == powerTwoB || clickedButton == powerThreeB
        || clickedButton == sinB || clickedButton == cosB || clickedButton == tanB
        || clickedButton == logB || clickedButton == lnB || clickedButton == factB){
            value1 = Double.parseDouble(calcDisplay.getText());     // Get the value for the function, set the second one to 0
            value2 = 0;

            if(clickedButton == squareRootB){ op = 5;}              // Set the op position within the 'operations' array
            else if(clickedButton == powerTwoB){ op = 6;}
            else if(clickedButton == powerThreeB){ op = 7;}
            else if(clickedButton == sinB){ op = 9;}
            else if(clickedButton == cosB){ op = 10;}
            else if(clickedButton == tanB){ op = 11;}
            else if(clickedButton == logB){ op = 12;}
            else if(clickedButton == lnB){ op = 13;}
            else if(clickedButton == factB){ op = 14;}

            try {
                // Create a MathProcess object for a single value process
                singleValProcess = new MathProcess(value1);

                switch (op){
                    case 5:                                             // If op 5 = squareRoot
                        if(singleValProcess.getVal1() < 0){throw new Exception();};     // If the user tries to get the square root of a negative number, it returns an error
                        result = singleValProcess.squareRoot();
                        break;

                    case 6:                                    // If operations[6] = powerTwo
                        result = singleValProcess.powerTwo();
                        break;

                    case 7:                                  // If operations[7] = powerThree
                        result = singleValProcess.powerThree();
                        break;
                    
                    case 9:                                         // If operations[9] = sin
                        result = singleValProcess.sin();
                        break;
                    
                    case 10:                                         // If operations[10] = cos
                        result = singleValProcess.cos();
                        break;

                    case 11:                                         // If operations[11] = tan
                        result = singleValProcess.tan();
                        break;

                    case 12:                                         // If operations[12] = log
                        if(singleValProcess.getVal1() < 0){throw new Exception();};
                        result = singleValProcess.log();
                        break;

                    case 13:                                          // If operations[13] = ln
                        if(singleValProcess.getVal1() < 0){throw new Exception();};
                        result = singleValProcess.ln();
                        break;

                    case 14:                                        // If operations[14] = x!
                        if(singleValProcess.getVal1() < 0){throw new Exception();};
                        result = singleValProcess.factorial();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Exception caught!!");
                calcDisplay.setText("ERROR");

                val1 = null;    
            }

            

            if(calcDisplay.getText().contains("ERROR")){            // If the squareRoot method sets the result as "ERROR", this just keeps displaying it
                // do nothing and continue displaying the error message
            } else {
                val1 = "" + result;                                 // Same procedure as equalsButton
                memory = result;  
                calcDisplay.setText(val1);
            }
        }
    }
}