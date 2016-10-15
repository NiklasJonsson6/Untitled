package com.company;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Daniel.
 */
public class LogProcessor extends Thread
{
    InputStream out,err;
    ServerLogHandler handler;
    AtomicBoolean pause;

    public LogProcessor(ServerLogHandler logHandler)
    {
        pause = new AtomicBoolean(false);
        handler = logHandler;
        this.setDaemon(true);
    }

    public void setIO(InputStream out, InputStream err)
    {
        synchronized (this)
        {
            this.out = out;
            this.err = err;
        }
    }

    @Override
    public void run()
    {
        while(true)
        {
            if(pause.get())continue;
            try
            {
                synchronized (this)
                {
                    byte buffer[]= new byte[1024];
                    if(out!= null && out.available()!=0)
                    {
                        System.out.println("processor: got out");
                        int read = out.read(buffer);
                        System.out.write(buffer,0,read);
                        handler.write_to_all_sockets(buffer,read);
                    }

                    if(err !=null && err.available()!=0)
                    {
                        System.out.println("processor: got err");
                        int read = err.read(buffer);
                        System.err.write(buffer, 0, read);
                        handler.write_to_all_sockets(buffer,read);
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
