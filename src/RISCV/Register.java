package RISCV;

public class Register {
    String name;
    public int byteNum;

    public Register(String name_, int num) {
        name = name_;
        byteNum = num;
    }

    public Register(String name_) {
        name = name_;
        byteNum = 0;
    }

    public String toString() {
        return name;
    }
}
