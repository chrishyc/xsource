package network.host;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String hostname = InetAddress.getLocalHost().getHostName();
        System.out.println("1:" + hostname);
        InetAddress[] addresses = InetAddress.getAllByName(hostname);
        if (addresses == null) return;
        for (InetAddress address : addresses) {
            if (address.isSiteLocalAddress()) {
                System.out.println("2:" + address.getHostName());
            }
        }
        System.out.println("3:" + InetAddress.getLocalHost().getHostName());
        Process hostname1 = Runtime.getRuntime().exec("hostname");
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(hostname1.getInputStream()));
        String s;
        while ((s = stdInput.readLine()) != null) {
            System.out.println("4:" + s);
        }
        
    }
    
}
