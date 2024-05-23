import java.util.ArrayList;
import java.util.List;

public class Client {

    private int idClient;

    private ArrayList<ResaPacket> listResaClient;

    public Client(int idClient, ArrayList<ResaPacket> listResaClient) {
        this.idClient = idClient;
        //differente resa d'un seul client
        this.listResaClient = listResaClient;
    }

    public int getIdClient(){
        return this.idClient;
    }

    public ArrayList<ResaPacket> getListResaClient(){
        return this.listResaClient;
    }

}
