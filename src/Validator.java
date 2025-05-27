import java.util.ArrayList;
import java.util.Objects;

public class Validator {
    Hasher hasher = new Hasher();

    public boolean Validator(ArrayList<Block> receivedBlocks) {
        if (!Objects.equals(receivedBlocks.getFirst(), Block.genesisBlock())) {
            return false;
        }

        for (int i = 0; i < Blockchain.chain.size(); i++) {
            boolean correctHash = Objects.equals(Blockchain.chain.get(i).hash, hasher.Hasher(Blockchain.chain.get(i).timestamp, lastHash, Blockchain.chain.get(i).data));
            boolean receivedBlock = (!Objects.equals(receivedBlocks.get(i), Blockchain.chain.get(i)));
            if (!correctHash || !receivedBlock) {
                return false;
            }
        }
        return true;
    }
}
