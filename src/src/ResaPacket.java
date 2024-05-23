import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ResaPacket {

    private int idResa;
    private InetAddress ipDest;
    private InetAddress ipSource;
    public float debitRequest;
    private InetSocketAddress portDest;
    private String protocol;
    private String classTrafic;

    public ResaPacket(int idR, InetAddress ipD, InetAddress ipS, float debitRequest, InetSocketAddress port, String protocol, String classTraffic){
        this.idResa = idR;
        this.ipDest=ipD;
        this.ipSource=ipS;
        this.debitRequest=debitRequest;
        this.portDest=port;
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

    public InetSocketAddress getPortDest(){
        return this.portDest;
    }

    public String getProtocol(){
        return this.protocol;
    }

    public String getClassTraffic(){
        return this.classTrafic;
    }
}
