import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ResaPacket {

    private int idResa;
    private InetAddress ipDest;
    private InetAddress ipSource;
    public float debitRequest;
    private int portDest;
    private int portSource;
    private String protocol;
    private String classTrafic;

    public ResaPacket(int idR, InetAddress ipD, InetAddress ipS, float debitRequest, int portDest, int portsrouce, String protocol, String classTraffic){
        this.idResa = idR;
        this.ipDest=ipD;
        this.ipSource=ipS;
        this.debitRequest=debitRequest;
        this.portDest=portDest;
        this.portSource=portsrouce;
        this.protocol=protocol;
        this.classTrafic=classTraffic;
    }

    public int getIdResa(){
        return this.idResa;
    }

    public InetAddress getIpDest(){
        return this.ipDest;
    }

    public InetAddress getIpSource(){
        return this.ipSource;
    }

    public float getDebitRequest(){
        return this.debitRequest;
    }

    public int getPortDest(){
        return this.portDest;
    }

    public String getProtocol(){
        return this.protocol;
    }

    public String getClassTraffic(){
        return this.classTrafic;
    }

    public int getPortSource() {
        return this.portSource;
    }
}
