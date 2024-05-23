import java.io.Serializable;
import java.net.InetAddress;

public class Message implements Serializable{
    private InetAddress Ipsource;
    private InetAddress IpDest;
    private int PortDest;
    private int PortSource;
    private float debit;

    public Message(InetAddress ipsource, InetAddress ipdest, int portdest, int portsource, float d){
        this.Ipsource = ipsource;
        this.IpDest = ipdest;
        this.PortDest = portdest;
        this.PortSource = portsource;
        this.debit = d;
    }

    public InetAddress getIpsource() {
        return Ipsource;
    }

    public void setIpsource(InetAddress ipsource) {
        Ipsource = ipsource;
    }

    public InetAddress getIpDest() {
        return IpDest;
    }

    public void setIpDest(InetAddress ipDest) {
        IpDest = ipDest;
    }

    public int getPortDest() {
        return PortDest;
    }

    public void setPortDest(int portDest) {
        PortDest = portDest;
    }

    public int getPortSource() {
        return PortSource;
    }

    public void setPortSource(int portSource) {
        PortSource = portSource;
    }

    public float getDebit() {
        return debit;
    }

    public void setDebit(float debit) {
        this.debit = debit;
    }

    
}
