import java.net.*;

/**
 * test
 */

public class test {


    public static void main(String[] args) throws UnknownHostException {
        ResaPacket TestResaPacketTR = new ResaPacket(InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 500.0f, 
                                                     InetSocketAddress.createUnresolved("193.168.3.3", 9500), null, "TR");
        
        ResaPacket TestResaPacketTR2 = new ResaPacket(InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 700.0f, 
                                                     InetSocketAddress.createUnresolved("193.168.3.3", 9500), null, "TR");
        
        ResaPacket TestResaPacketTR3 = new ResaPacket(InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 7000.0f, 
                                                     InetSocketAddress.createUnresolved("193.168.3.3", 9500), null, "BE");
                                                
        ResaPacket TestResaPacketTR4 = new ResaPacket(InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 900.0f, 
                                                     InetSocketAddress.createUnresolved("193.168.3.3", 9500), null, "BE");

        ResaPacket TestResaPacketTR5 = new ResaPacket(InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 2000.0f, 
                                                     InetSocketAddress.createUnresolved("193.168.3.3", 9500), null, "TR");

        ResaPacket TestResaPacketTR6 = new ResaPacket(InetAddress.getByName("193.168.3.3"), InetAddress.getByName("193.168.1.1"), 5000.0f, 
                                                     InetSocketAddress.createUnresolved("193.168.3.3", 9500), null, "TR");

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

        BBTest.accept(TestResaPacketTR4);
        PrintDebitsAttendus(8000f, 6800f, 1200f, 0, 0);
        PrintDebits(BBTest);

        BBTest.accept(TestResaPacketTR5);
        PrintDebitsAttendus(8000f, 4800f, 3200f, 0, 0);
        PrintDebits(BBTest);

        BBTest.accept(TestResaPacketTR6);
        PrintDebitsAttendus(8000f, 4800f, 3200f, 0, 0);
        PrintDebits(BBTest);
    }

    static void PrintDebits(BandWidthBroker BB) {
        System.out.println("--------- Valeurs de debit actuelles: ----------- \n");
        System.out.println("Debit Tot actuel: " + BB.computeCurrentDebit() + "\n");
        System.out.println("Debit BE: " + BB.computeDebitBE() + "\n");
        System.out.println("Debit TR: " + BB.computeDebitTR() + "\n");
        System.out.println("Debit BK: " + BB.computeDebitBK() + "\n");
        System.out.println("Dbeit DT: " + BB.computeDebitDT() + "\n");
        System.out.println("\n");
    } 

    static void PrintDebitsAttendus(float Tot, float BE, float TR, float BK, float DT) {
        System.out.println("------- Valeurs de debit attendues au prochain print: --------- \n");
        System.out.println("Debit Tot attendu: " + Tot + "\n");
        System.out.println("Debit BE: " + BE + "\n");
        System.out.println("Debit TR: " + TR + "\n");
        System.out.println("Debit BK: " + BK + "\n");
        System.out.println("Dbeit DT: " + DT + "\n");
    } 
 
}