import java.util.ArrayList;

public class Blockchain {

    static ArrayList<Block> chain = new ArrayList<Block>();

    public Blockchain(){
        chain.add(Block.genesisBlock());
    }

    public void addBlock(Block block){
        chain.add(block);
    }

    public static String getLastBlock(){
        Block lastBlock = chain.getLast();
        return lastBlock.hash;
    }

}
