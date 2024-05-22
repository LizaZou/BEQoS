/**
 * bandwidthbroker
 */
import java.net.*;
import java.util.ArrayList;

import javax.management.openmbean.ArrayType;

public class Link {


    InetAddress source;
    InetAddress dest;
    float InitialCapacity = 8000;
    float CurrentBE = 0;
    float CurrentVoix = 0;
    float CurrentDT = 0;
    float CurrentTR = 0;


}

public class ResaPacket {

    int id;
    InetAddress IpDest;
    InetAddress IpSource;
    float Debit;
    InetSocketAddress PortDest;
    String Protocol;
    String Class;
}

public class Client {

    int idClient;
    List listResaClient = new ArrayList<ResaPacket>();  //differente resa d'un seul client
} 

/*RESA(IPDEST, IPSOURCE, DEBIT, PORTDEST, PROTOCOL, Class)*/
private static List listLink = new ArrayList<Link>();
private static List listResaTotal = new ArrayList<ResaPacket>();
//public link[] LinkArray;

public class bandwidthbroker {

    private float DebitDispoDT;
    private float DebitDispoTR;
    private float DebitDispoBE;
    private float DebitDispoBK;
    private float DebitTot = ResearchCurrentDebit() ;


    private InetAddress Ref = InetAddress.getByName("0.0.0.0");

    
    void Accept(ResaPacket ResaPacket) {
        //if (ResaPacket.IpDest.equals(LinkArray[i].dest) && ResaPacket.IpSource.equals(LinkArray[i].source)) {
            //Ref = ResaPacket.IpDest;
            float debit = ResaPacket.debit;
            float debitTot = ResearchCurrentDebit();
            if (debit+)
        //}
        
    }

    float ComputeDebitDT(){
        float Debit =0 ; 
        for (ResaPacket Resa:listResaTotal){
            if(Resa.Class.equals("DT")){
                Debit+=Resa.Debit ; 
            }
        }
        return Debit; 
    }

    float ComputeDebitBE(){
        float Debit =0 ; 
        for (ResaPacket Resa:listResaTotal){
            if(Resa.Class.equals("BE")){
                Debit+=Resa.Debit ; 
            }
        }
        return Debit ; 
    }
    float ComputeDebitBK(){
        float Debit =0 ; 
        for (ResaPacket Resa:listResaTotal){
            if(Resa.Class.equals("BK")){
                Debit+=Resa.Debit ; 
            }
        }
        return Debit ; 
    }
    float ComputeDebitTR(){
        float Debit =0 ; 
        for (ResaPacket Resa:listResaTotal){
            if(Resa.Class.equals("TR")){
                Debit+=Resa.Debit ; 
            }
        }
        return Debit ; 
    }

    float ResearchCurrentDebit() {
        float DebitTot = 0;
        for (ResaPacket Resa:listResaTotal) {
            DebitTot+=Resa.Debit;
        }

        return DebitTot;
    }

}

