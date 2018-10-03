package main;

/**
 *
 * @author Daniel
 */
public class Memory {

    private int quantity;
    private String status;
    private int usedMemory;
    private int processID;

    public Memory(int quantity) {
        this.quantity = quantity;
        usedMemory = 0;
        status = "LI";
        processID = 0;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(int usedMemory) {
        this.usedMemory = usedMemory;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    @Override
    public String toString() {
        return format("") + format(quantity) + format("|") + format(status) + format("|") + format(usedMemory) + format("|") + format(processID);
    }

    private String format(Object value) {
        return String.format("%1$-" + 10 + "s", value.toString());//String.format("%-10s", value.toString());
    }
}
