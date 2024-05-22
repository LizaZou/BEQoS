import java.net.InetAddress;

public class Link {

    private InetAddress source;
    private InetAddress dest;
    private final float INITIALCAPACITY = 8000;
    private float currentBE;
    private float currentVoix;
    private float currentDT;
    private float currentBK;

    public Link(InetAddress s, InetAddress d){
        this.source=s;
        this.dest=d;
        this.currentBE=0;
        this.currentDT=0;
        this.currentVoix=0;
        this.currentBK=0;
    }

    public float getCurrentBE(){
        return this.currentBE;
    }

    public float getCurrentVoix(){
        return this.currentVoix;
    }

    public float getCurrentDT(){
        return this.currentDT;
    }

    public float getCurrentBK(){
        return this.currentBK;
    }

}
