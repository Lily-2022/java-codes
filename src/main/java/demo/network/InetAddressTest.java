package demo.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {

    public static void main(String[] args) throws UnknownHostException {
        if (args.length > 0) {
            String host = args[0];
            InetAddress[] inetAddresses = InetAddress.getAllByName(host);
            for (InetAddress inetAddress : inetAddresses) {
                System.out.println(inetAddress);
            }
        } else {
            InetAddress localAddress = InetAddress.getLocalHost();
            System.out.println(localAddress);
        }

    }
}
