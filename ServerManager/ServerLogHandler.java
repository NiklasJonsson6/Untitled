package com.company;

import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Daniel.
 */
public class ServerLogHandler extends Thread
{

    ConcurrentLinkedDeque<Socket> sockets = new ConcurrentLinkedDeque<>();

    public ServerLogHandler()
    {
        this.setDaemon(true);
    }


    void addSocket(Socket socket)
    {
        sockets.add(socket);
    }

    byte[] buffer = new byte[1024];
    volatile int write_pos = 0;

    void write_to_all_sockets(byte[] bytes, int length)
    {
        synchronized (this)
        {
            int write_len = Math.min(buffer.length - write_pos, length);
            System.arraycopy(bytes, 0, buffer, write_pos, write_len);
            write_pos += write_len;
            System.out.println("writing to all msg_len ="+write_len);
            System.out.println("total unread: "+ write_pos);
        }
    }

    @Override
    public void run()
    {
        // below is a perfect example of java's stupidity.
        // ugly af foreach loop because we can't remove otherwise.
        // exceptions as control flow because why the fuck not.
        // syncronized.

        while (true)
        {
            try
            {
                if (write_pos != 0)
                {
                    synchronized (this)
                    {
                        for (Iterator<Socket> iterator = sockets.iterator(); iterator.hasNext();)
                        {
                            Socket socket = iterator.next();
                            try
                            {
                                socket.getOutputStream().write(buffer, 0, write_pos);
                            }
                            catch (SocketException ex)
                            {
                                iterator.remove();
                            }
                        }
                        write_pos = 0;
                    }
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

}
