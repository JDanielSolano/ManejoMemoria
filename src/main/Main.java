package main;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel
 */
public class Main {

    private Program program = new Program();

    public void option() {
        // To keep it open
        while (true) {
            try {
                int value = Integer.valueOf(JOptionPane.showInputDialog(null, "Which option want to use? \n 1. Load information\n 2. Allocate memory\n 3. Print memory\n 4. Exit", "Memory Management", JOptionPane.QUESTION_MESSAGE));
                switch (value) {
                    case (1):
                        loadFiles();
                        break;
                    case (2):
                        // Allocate memory
                        allocateMemory();
                        // Validate if the memory is full
                        if (program.isMemoryFull()) {
                            JOptionPane.showMessageDialog(null, "The memory is full, the system is going to close:\n" + program.toString(), "Assignment", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        }
                        break;
                    case (3):
                        // Print
                        print();
                        break;
                    case (4):
                        // Exit
                        System.exit(0);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid selection", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Invalid selection", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void allocateMemory() {
        int processID = -1;
        while (true) {
            try {
                processID = Integer.valueOf(JOptionPane.showInputDialog(null, "Enter the process code, or -1 to cancel", "Memory Management", JOptionPane.QUESTION_MESSAGE));
                if (processID <= 0 && processID != -1) {
                    JOptionPane.showMessageDialog(null, "The process must be positive numeric", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "The process must be positive numeric", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (processID == -1) {
            return;
        }
        int memoryToUse = 0;
        while (true) {
            try {
                memoryToUse = Integer.valueOf(JOptionPane.showInputDialog(null, "Enter the amount of memory", "Memory Management", JOptionPane.QUESTION_MESSAGE));
                if (memoryToUse <= 0 && memoryToUse != -1) {
                    JOptionPane.showMessageDialog(null, "Invalid value, must be positive numeric", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Invalid value", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (memoryToUse == -1) {
            return;
        }

        int programType = 0;
        while (true) {
            try {
                programType = Integer.valueOf(JOptionPane.showInputDialog(null, "Enter the algorithm\n 1. First Adjustment\n 2. Worst Adjustment\n 3. Best Adjustment\n 4. Cancel", "Memory Management", JOptionPane.QUESTION_MESSAGE));
                if (programType < 1 && programType > 4) {
                    JOptionPane.showMessageDialog(null, "Invalid selection", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Invalid selection", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (programType == 4) {
            return;
        }
        boolean result = program.asignateMemory(programType, processID, memoryToUse);
        if (!result) {
            JOptionPane.showMessageDialog(null, "The assignment could not be made", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Selection made", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void print() {
        JOptionPane.showMessageDialog(null, program.toString(), "Assignment", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String args[]) {
        new Main().option();
    }

    private void loadFiles() {
        while (true) {
            try {
                int value = Integer.valueOf(JOptionPane.showInputDialog(null, "Which file do you want to load? \n 1. Memory file\n 2. State file\n 3. Cancel\n", "Memory Management", JOptionPane.QUESTION_MESSAGE));
                JFileChooser fileChooser = new JFileChooser();
                switch (value) {
                    case (1):
                        // Load the memory file                      
                        fileChooser.showOpenDialog(null);
                        File memoryFile = fileChooser.getSelectedFile();
                        program.loadFile(memoryFile, FILE_TYPE.MEMORY);
                        break;
                    case (2):
                        // Load the state file
                        fileChooser.showOpenDialog(null);
                        File statusFile = fileChooser.getSelectedFile();
                        program.loadFile(statusFile, FILE_TYPE.STATUS);
                        break;
                    default:
                        break;
                }
                break;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Invalid selection", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(null, "Invalid file", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}