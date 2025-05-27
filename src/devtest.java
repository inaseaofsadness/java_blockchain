import java.lang.System;

public class devtest {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        System.out.println(Blockchain.getLastBlock());
        System.out.println(Blockchain.chain.getLast().toString());

    }
}
