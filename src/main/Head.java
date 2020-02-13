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
                    && // That is less than or equal
                    (quantity - memoryToUse < diference || diference == -1)
                    && // That the difference is minus than the previous difference
                    // If it is minus one, it is because none has been assigned
                    memory[i].getStatus().equals("LI")) { //que este libre el bloque
                diference = quantity - memoryToUse;
                pos = i;
                if (diference == 0) { // If it is zero, it's perfect
                    break;
                }
            }
        }
        return pos;
    }

    @Override
    public String toString() {
        String result = "Memory/Dis" + "\t | \t" + "Status" + "\t | \t" + "Memory used" + "\t | \t" + "ID/process\n";
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
