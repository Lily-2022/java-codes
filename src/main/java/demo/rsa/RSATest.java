package demo.rsa;

import javax.crypto.*;
import java.io.*;
import java.security.*;

public class RSATest {

    public static final int KEYSIZE = 512;

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, ShortBufferException {
        if (args[0].equals("-genKey")) {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            var random = new SecureRandom();
            keyPairGenerator.initialize(KEYSIZE, random);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            try (var out = new ObjectOutputStream(new FileOutputStream(args[1]))) {
                out.writeObject(keyPair.getPublic());
            }
            try (var out = new ObjectOutputStream(new FileOutputStream(args[2]))) {
                out.writeObject(keyPair.getPrivate());
            }
        } else if (args[0].equals("-encrypt")) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            var random = new SecureRandom();
            keyGenerator.init(random);
            SecretKey key = keyGenerator.generateKey();
            try (var keyIn = new ObjectInputStream(new FileInputStream(args[3]));
            var out = new DataOutputStream(new FileOutputStream(args[2]));
            var in = new FileInputStream(args[1])) {

                var publicKey = (Key)keyIn.readObject();
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.WRAP_MODE, publicKey);
                byte[] wrappedKey = cipher.wrap(key);
                out.writeInt(wrappedKey.length);
                out.write(wrappedKey);

                cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                Util.crypt(in, out, cipher);
            }
        } else {
            try (var in = new DataInputStream(new FileInputStream(args[1]));
            var keyIn = new ObjectInputStream(new FileInputStream(args[3]));
            var out = new FileOutputStream(args[2])) {
                int length = in.readInt();
                var wrappedKey = new byte[length];
                in.read(wrappedKey, 0, length);

                var privateKey = (Key)keyIn.readObject();
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.UNWRAP_MODE, privateKey);
                Key key = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);

                cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, key);

                Util. crypt(in, out, cipher);
            }
        }
    }


}
