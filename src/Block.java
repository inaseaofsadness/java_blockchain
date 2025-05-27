import java.util.Arrays;
import java.util.Date;

public class Block {
    long timestamp;
    String[] data;
    String lastHash;
    String hash;
    Hasher hasher = new Hasher();
    String previousBlock;

    public Block(){
        this.timestamp = new Date().getTime();
        this.data = new String[]{};
        this.hash = hasher.Hasher(this.timestamp, lastHash, this.data);
        if (Blockchain.chain.isEmpty()){
            this.previousBlock = "0";
        }
        else{
            this.previousBlock = Blockchain.getLastBlock();
        }
        this.previousBlock = "";
    }

    public static Block genesisBlock(){
        return new Block();
    }


    @Override
    public String toString() {
        return "Block{" +
                "index=" + this.hash +
                ", timestamp=" + timestamp +
                ", data=" + Arrays.toString(data) +
                ", previousHash='" + this.lastHash + "'" +
                ", hash='" + hash + "'" +
                '}';
    }


}
