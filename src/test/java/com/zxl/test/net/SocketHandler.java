package com.zxl.test.net;

import java.net.Socket;

public interface SocketHandler extends Runnable
{
    public void handle(Socket socket);
}
