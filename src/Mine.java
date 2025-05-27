import java.sql.Date;

public class Mine {
    static Hasher hasher = new Hasher();
    static String lastHash = Blockchain.getLastBlock();
    static Date date = new Date();
    public static Block mineBlock() {
        int timestamp = Math.toIntExact(date.getTime());
        int nonce = 0;
        String hash;
        do {
            nonce++;
            String[] data = {};
            hash = hasher.Hasher(timestamp, lastHash, data);
        } while (!hash.startsWith("0000"));
        return new Block(timestamp, lastHash, );
    }

}
