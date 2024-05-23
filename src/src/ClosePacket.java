import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ClosePacket {
    public int idResa;
    private InetAddress ipDest;
    private InetAddress ipSource;
    private InetSocketAddress portDest;
    private String protocol;
    private String classTrafic;

    public ClosePacket(int idR, InetAddress ipD, InetAddress ipS, float debitRequest, InetSocketAddress port, String protocol, String classTraffic){
        this.idResa =idR;
        this.ipDest=ipD;
        this.ipSource=ipS;
        this.portDest=port;
        this.protocol=protocol;
        this.classTrafic=classTraffic;
    }
}
