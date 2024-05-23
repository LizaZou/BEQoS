import java.io.IOException;
import java.net.*;

/**
 * test
 */

public class test {


    public static void main(String[] args) throws IOException {
        ResaPacket TestResaPacketTR = new ResaPacket(1, InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 500.0f, 0, 0, null, "TR");
        
        ResaPacket TestResaPacketTR2 = new ResaPacket(2, InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 700.0f, 0, 0, null, "TR");
        
        ResaPacket TestResaPacketTR3 = new ResaPacket(3, InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 7000.0f, 0,0, null, "BE");
                                                
        ResaPacket TestResaPacketTR4 = new ResaPacket(4, InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 900.0f, 0,0 , null, "BE");

        ResaPacket TestResaPacketTR5 = new ResaPacket(5, InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 2000.0f, 0,0, null, "TR");

        ResaPacket TestResaPacketTR6 = new ResaPacket(6, InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 5000.0f, 0, 0, null, "TR");

        ClosePacket ClosePacket3 = new ClosePacket(3, InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 0, null, null, null);
        BandWidthBroker BBTest = new BandWidthBroker(8000);
        PrintDebits(BBTest);

        BBTest.accept(TestResaPacketTR);
        
        PrintDebitsAttendus(500f, 0, 500f, 0, 0);
        PrintDebits(BBTest);

        BBTest.accept(TestResaPacketTR2);
        PrintDebitsAttendus(1200f, 0, 1200f, 0, 0);
        PrintDebits(BBTest);

        BBTest.accept(TestResaPacketTR3);
        PrintDebitsAttendus(8000f, 6800f, 1200f, 0, 0);
        PrintDebits(BBTest);

        BBTest.recep_close_packet(ClosePacket3);
        PrintDebitsAttendus(1200f, 0, 1200f, 0, 0);
        PrintDebits(BBTest);

        BBTest.accept(TestResaPacketTR4);
        PrintDebitsAttendus(3400f, 2100f, 1200f, 0, 0);
        PrintDebits(BBTest);

        BBTest.accept(TestResaPacketTR5);
        PrintDebitsAttendus(5300f, 2100f, 3200f, 0, 0);
        PrintDebits(BBTest);

        BBTest.accept(TestResaPacketTR6);
        PrintDebitsAttendus(5300, 2100f, 3200f, 0, 0);
        PrintDebits(BBTest);
    }

    static void PrintDebits(BandWidthBroker BB) {
        System.out.println("--------- Valeurs de debit actuelles: ----------- \n");
        System.out.println("Debit Tot actuel: " + BB.computeCurrentDebit() + "\n");
        System.out.println("Debit TR: " + BB.computeDebitTR() + "\n");
        System.out.println("\n");
    } 

    static void PrintDebitsAttendus(float Tot, float BE, float TR, float BK, float DT) {
        System.out.println("------- Valeurs de debit attendues au prochain print: --------- \n");
        System.out.println("Debit Tot attendu: " + Tot + "\n");
        System.out.println("Debit TR: " + TR + "\n");
    } 
 
}