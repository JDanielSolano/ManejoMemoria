package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Daniel
 */
public class Program {

    private Head head;

    public Program() {
        head = new Head(10); 
        fillMemory();
    }

    public void fillMemory() {
        Random rnd = new Random();
        final int VALOR_MAXIMO = 512;
        for (int i = 0; i < head.getMemory().length; i++) {
            head.putMemory(i, rnd.nextInt(VALOR_MAXIMO) + 1);
            head.setStatus(i, "LI");
        }
    }

    public boolean asignateMemory(int programType, int processID, int memoryTouse) {
        if (programType == 1) {
            return head.firstAjust(processID, memoryTouse);
        } else if (programType == 2) {
            return head.worseAjust(processID, memoryTouse);
        } else if (programType == 3) {
            return head.bestAjust(processID, memoryTouse);
        }
        return false;
    }

    @Override
    public String toString() {
        return head.toString();
    }

    public boolean isMemoryFull() {
        return head.isMemoryFull();
    }

    public void loadFile(File file, FILE_TYPE fileType) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        List<Memory> memories = new ArrayList();
        do {
            line = br.readLine();
            if (line != null) {
                String[] values = line.split(",");
                for (String currenValue : values) {
                    if (currenValue != null && currenValue.length() > 0) {
                        if (fileType.equals(FILE_TYPE.MEMORY)) {
                            Memory temp = new Memory(Integer.parseInt(currenValue));
                            temp.setStatus("LI");
                            memories.add(temp);
                        } else {
                            Memory temp = new Memory(0);
                            temp.setStatus(currenValue);
                            memories.add(temp);
                        }
                    }
                }
            }
        } while (line != null);
        Memory[] arrayMemories = memories.toArray(new Memory[0]);
        // If it is a memory file, it is set with those memories
        if (fileType.equals(FILE_TYPE.MEMORY)) {
            head.setMemory(arrayMemories);
        } else {
            // The memory used was not defined in the file, so what contained space was written
            int i = 0;
            while (i < head.getMemory().length && i < arrayMemories.length) {
                head.setStatus(i, arrayMemories[i].getStatus());
                if (arrayMemories[i].getStatus().equals("EU")) {
                    head.getMemory()[i].setUsedMemory(head.getMemory()[i].getQuantity());
                    // As a process, we add the process ID
                    head.getMemory()[i].setProcessID(i + 1);
                } else {
                    // On the contrary, when freeing memory it is set to 0 and the process to 0
                    head.getMemory()[i].setProcessID(0);
                    head.getMemory()[i].setUsedMemory(0);
                }
                i++;
            }
        }
        br.close();
    }
}

enum FILE_TYPE {
    STATUS, MEMORY
}
