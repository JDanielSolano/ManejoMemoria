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
        //Para que se mantenga abierto
        while (true) {
            try {
                int value = Integer.valueOf(JOptionPane.showInputDialog(null, "Cual opción desea Levantar \n 1. Cargar Información\n 2. Asignar Memoria\n 3. Imprimir Memoria\n 4.Salir", "Selección Aplicación", JOptionPane.QUESTION_MESSAGE));
                switch (value) {
                    case (1):
                        loadFiles();
                        break;
                    case (2):
                        //Asignar Memoria
                        asignarMemoria();
                        //Validar si ya se lleno la memoria
                        if (program.isMemoryFull()) {
                            JOptionPane.showMessageDialog(null, "La memoria ya se lleno el programa se va a cerrar:\n" + program.toString(), "Asignación", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        }
                        break;
                    case (3):
                        //Imprimir
                        imprimir();
                        break;
                    case (4):
                        //Salir
                        System.exit(0);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Selección inválida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Selección inválida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void asignarMemoria() {
        int processID = -1;
        while (true) {
            try {
                processID = Integer.valueOf(JOptionPane.showInputDialog(null, "Digite el código de proceso, o -1 para cancelar", "Selección Aplicación", JOptionPane.QUESTION_MESSAGE));
                if (processID <= 0 && processID != -1) {
                    JOptionPane.showMessageDialog(null, "El proceso debe ser numerico positivo", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "El proceso debe ser numerico positivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (processID == -1) {
            return;
        }
        int memoryToUse = 0;
        while (true) {
            try {
                memoryToUse = Integer.valueOf(JOptionPane.showInputDialog(null, "Digite la cantidad de memoria", "Selección Aplicación", JOptionPane.QUESTION_MESSAGE));
                if (memoryToUse <= 0 && memoryToUse != -1) {
                    JOptionPane.showMessageDialog(null, "Valor inválido debe ser numerico positivo", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Valor inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (memoryToUse == -1) {
            return;
        }

        int programType = 0;
        while (true) {
            try {
                programType = Integer.valueOf(JOptionPane.showInputDialog(null, "Digite el algoritmo\n 1. Primer Ajuste\n 2. Peor Ajuste\n 3. Mejor Ajuste\n 4. Cancelar", "Selección Aplicación", JOptionPane.QUESTION_MESSAGE));
                if (programType < 1 && programType > 4) {
                    JOptionPane.showMessageDialog(null, "Selección inválida", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Selección inválida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (programType == 4) {
            return;
        }
        boolean result = program.asignateMemory(programType, processID, memoryToUse);
        if (!result) {
            JOptionPane.showMessageDialog(null, "No se logró realizar la asignación", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccion realizada", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void imprimir() {
        JOptionPane.showMessageDialog(null, program.toString(), "Asignación", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String args[]) {
        new Main().option();
    }

    private void loadFiles() {
        while (true) {
            try {
                int value = Integer.valueOf(JOptionPane.showInputDialog(null, "Cual archivo desea cargar \n 1. Archivo de Memoria\n 2. Archivo de Estados\n 3. Cancelar\n", "Selección Aplicación", JOptionPane.QUESTION_MESSAGE));
                JFileChooser fileChooser = new JFileChooser();
                switch (value) {
                    case (1):
                        //Cargar el archivo de memoria                        
                        fileChooser.showOpenDialog(null);
                        File memoryFile = fileChooser.getSelectedFile();
                        program.loadFile(memoryFile, FILE_TYPE.MEMORY);
                        break;
                    case (2):
                        //Cargar el archivo de estados
                        fileChooser.showOpenDialog(null);
                        File statusFile = fileChooser.getSelectedFile();
                        program.loadFile(statusFile, FILE_TYPE.STATUS);
                        break;
                    default:
                        break;
                }
                break;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Selección inválida", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(null, "Archivo no válido", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Archivo no válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
