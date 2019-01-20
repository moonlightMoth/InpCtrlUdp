import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server extends Thread
{
    private DatagramSocket socket;
    private byte[] buf = new byte[1];
    private Point curPos;

    private final byte MV_UP = 1;
    private final byte MV_DW = 2;
    private final byte MV_RT = 3;
    private final byte MV_LT = 4;
    private final byte SC_UP = 5;
    private final byte SC_DW = 6;
    private final byte CL_LB = 7;
    private final byte DR_ON = 8;
    private final byte DR_OF = 9;
    private final byte CL_RB = 10;
    private final byte CL_MB = 11;
    private final byte EXIT_SIGNAL = 127;

    private Robot robot;
    {
        try
        {
            robot = new Robot();
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }
    }


    Server() throws SocketException
    {
        socket = new DatagramSocket(1337);
    }

    public void run()
    {
        try
        {
            DatagramPacket packet;

            while (true) {

                packet = new DatagramPacket(buf, 1);

                socket.receive(packet);

                System.out.println(packet.getData()[0]);

                makeDesByte(packet.getData()[0]);

            }
        }
        catch (IOException e)
        {
            //do nothing
        }
    }

    private void makeDesByte(byte b)
    {
        if (b == MV_UP)
        {
            curPos = MouseInfo.getPointerInfo().getLocation();
            robot.mouseMove(curPos.x, curPos.y - 1);
        }

        if (b == MV_DW)
        {
            curPos = MouseInfo.getPointerInfo().getLocation();
            robot.mouseMove(curPos.x, curPos.y + 1);
        }

        if (b == MV_RT)
        {
            curPos = MouseInfo.getPointerInfo().getLocation();
            robot.mouseMove(curPos.x + 1, curPos.y);
        }

        if (b == MV_LT)
        {
            curPos = MouseInfo.getPointerInfo().getLocation();
            robot.mouseMove(curPos.x - 1, curPos.y);
        }

        if (b == SC_UP)
            robot.mouseWheel(1);

        if (b == SC_DW)
            robot.mouseWheel(-1);

        if (b == CL_LB)
        {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }

        if (b == DR_ON)
            robot.mousePress(InputEvent.BUTTON1_MASK);

        if (b == DR_OF)
            robot.mouseRelease(InputEvent.BUTTON1_MASK);

        if (b == CL_RB)
        {
            robot.mousePress(InputEvent.BUTTON3_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_MASK);
        }

        if (b == CL_MB)
        {
            robot.mousePress(InputEvent.BUTTON2_MASK);
            robot.mouseRelease(InputEvent.BUTTON2_MASK);
        }
    }

    void shutdown() throws ShutdownSignal
    {
        socket.close();
        throw new ShutdownSignal();
    }
}
