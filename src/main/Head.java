package main;

/**
 *
 * @author Daniel
 */
public class Head {

    private Memory[] memory;

    public Head(int blocks) {
        memory = new Memory[blocks];
    }

    public Memory[] getMemory() {
        return memory;
    }

    public void setMemory(Memory[] memory) {
        this.memory = memory;
    }

    public boolean putMemory(int pos, int quantiy) {
        if (pos < memory.length) {
            Memory currentMemory = memory[pos];
            if (currentMemory == null || currentMemory.getStatus().equals("EU")) {
                memory[pos] = new Memory(quantiy);
                return true;
            }
        }
        return false;
    }

    public boolean setStatus(int pos, String status) {
        if (pos >= 0 && pos < memory.length) {
            memory[pos].setStatus(status);
            return true;
        }
        return false;
    }

    public Memory getMemory(int pos) {
        if (memory.length < pos) {
            return memory[pos];
        }
        return null;
    }

    public boolean firstAjust(int process, int memoryToUse) {
        boolean asignate = false;
        for (int i = 0; i < memory.length; i++) {
            if (memory[i].getQuantity() >= memoryToUse && memory[i].getStatus().equals("LI")) {
                memory[i].setUsedMemory(memoryToUse);
                memory[i].setStatus("EU");
                memory[i].setProcessID(process);
                asignate = true;
                break;
            }
        }
        return asignate;
    }

    public boolean worseAjust(int process, int memoryToUse) {
        int worse = getMostSize(memoryToUse);
        if (worse != -1) {
            memory[worse].setUsedMemory(memoryToUse);
            memory[worse].setProcessID(process);
            memory[worse].setStatus("EU");
            return true;
        }
        return false;
    }

    public boolean bestAjust(int process, int memoryToUse) {
        int best = getBestSize(memoryToUse);
        if (best != -1) {
            memory[best].setUsedMemory(memoryToUse);
            memory[best].setProcessID(process);
            memory[best].setStatus("EU");
            return true;
        }
        return false;
    }

    private int getMostSize(int memoryToUse) {
        int mostSize = -1;
        int pos = -1;
        for (int i = 0; i < memory.length; i++) {
            int quantity = memory[i].getQuantity();
            if ((mostSize < quantity || mostSize == -1)
                    && memoryToUse <= quantity
                    && memory[i].getStatus().equals("LI")) {
                mostSize = quantity;
                pos = i;
            }
        }
        return pos;
    }

    private int getBestSize(int memoryToUse) {
        int diference = -1;
        int pos = -1;
        for (int i = 0; i < memory.length; i++) {
            int quantity = memory[i].getQuantity();
            if (memoryToUse <= quantity
                    && //Que sea menor o igual
                    (quantity - memoryToUse < diference || diference == -1)
                    && //Que la diferencia sea menor a la diferencia anterior
                    //Si es menos uno es por que no se ha asignado ninguno
                    memory[i].getStatus().equals("LI")) { //que este libre el bloque
                diference = quantity - memoryToUse;
                pos = i;
                if (diference == 0) {//Si es cero ya ese esta perfecto
                    break;
                }
            }
        }
        return pos;
    }

    @Override
    public String toString() {
        String result = "Memoria/Dis" + "\t | \t" + "Estado" + "\t | \t" + "Memory Usada" + "\t | \t" + "ID/process\n";
        for (int i = 0; i < memory.length; i++) {
            result += memory[i].toString() + "\n";
        }
        return result;
    }

    public boolean isMemoryFull() {
        boolean full = true;
        int i = 0;
        while (i < memory.length && full) {
            full = memory[i].getStatus().equals("EU");
            i++;
        }
        return full;
    }

}
