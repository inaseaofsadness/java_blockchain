import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    public String Hasher(long timestamp, String lastHash, String[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            String blockData = String.join("", data);
            String blockTimestamp = String.valueOf(timestamp);

            String input = blockData + " " + blockTimestamp;
            byte[] hash = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}