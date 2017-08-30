package com.zxl.util;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IPUtil {
    private static List<String> localIps = new ArrayList<String>();

    static {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
                    for (InterfaceAddress address : interfaceAddresses) {
                        String ip = address.getAddress().getHostAddress();
                        if (ip != null && !"".equals(ip)) {
                            localIps.add(ip);
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();

        }

        if (!localIps.contains("127.0.0.1")) {
            localIps.add("127.0.0.1");
        }
    }

    /**
     * 
     * @return
     */
    public static List<String> getLocalIps() {
        return localIps;
    }

    public static boolean isLocalIp(String ip) {
        return localIps.contains(ip);
    }

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("shutdown hook thread....");
            }
        });

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                List<String> list = getLocalIps();
                System.out.println("list's size:" + list.size());
            }
        }.start();

        new Thread() {
            public void run() {
                System.out.println("check:" + isLocalIp("192.168.66.8"));
                ;
            }
        }.start();
    }
}
