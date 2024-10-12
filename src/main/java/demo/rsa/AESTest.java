package demo.rsa;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESTest {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        if (args[0].equals("-genKey")) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            var random = new SecureRandom();
            keyGenerator.init(random);
            SecretKey key = keyGenerator.generateKey();
            try (var out = new ObjectOutputStream(new FileOutputStream(args[1]))) {
                out.writeObject(key);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            int mode;
            if (args[0].equals("-encrypt")) mode = Cipher.ENCRYPT_MODE;
            else mode = Cipher.DECRYPT_MODE;

            try (var keyIn = new ObjectInputStream(new FileInputStream(args[3]));
                var in = new FileInputStream(args[1]);
                var out = new FileOutputStream(args[2])) {
                var key = (Key)keyIn.readObject();
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(mode, key);
                Util.crypt(in, out, cipher);

            } catch (Exception e) {


            }
        }
    }
}
