import java.net.*;
import java.util.ArrayList;
import java.util.List;


public class BandWidthBroker {
    private float debitDispoDT;
    private float debitDispoTR;
    private float debitDispoBE;
    private float debitDispoBK;
    private float debitTot;
    private float debitDispo;
    private int nb_max_be = 5 ; 
    public float min_debit_be = 2 ; // en ko
    //Liste des liens qui relient chaque client à son CE
    private List<Link> listLink;

    //Liste des réservations acceptées sur l'ensemble du réseau
    private List<Client> listResaTotal;


    //???
    private InetAddress ref;

    public BandWidthBroker(float debitDispo) throws UnknownHostException {
        this.ref = InetAddress.getByName("0.0.0.0");
        if(this.ref==InetAddress.getLoopbackAddress()){
            throw new UnknownHostException();
        }
        //débit dispo au total en bits/s
        this.debitDispo=debitDispo;
        this.debitTot = 0;
        this.listLink = new ArrayList<>();
        this.listResaTotal = new ArrayList<>();
    }

    void accept(ResaPacket resaPacket) {
        float debit = resaPacket.getDebitRequest();
        float debitTot = this.computeCurrentDebit();
        boolean pasRegle;

        switch (resaPacket.getClassTraffic()) {
            case "BK":
               if ((resaPacket.getDebitRequest() + this.debitTot - computeDebitBE()) >= this.debitDispo) {
                    System.out.println("Reservation impossible, debit demandé trop important");
                } else {
                    System.out.println("Reservation possible, reservation en cours");

                    pasRegle = true;
                    if (computeDebitBE() > 0) {
                        while (pasRegle) {
                            int nbClientBE = 0;
                            for (Client cl : this.listResaTotal) {
                                for (ResaPacket res : cl.getListResaClient()) {
                                    if (res.getClassTraffic().equals("BE")) {
                                        nbClientBE++;
                                    }
                                }
                            }

                            for (Client cl : listResaTotal) {
                                for (ResaPacket res : cl.getListResaClient()) {
                                    if (res.getClassTraffic().equals("BE")) {
                                        
                                        res.debitRequest -= debit/ nbClientBE;
                                    }
                                }
                            }
                            pasRegle = false;
                        }
                    }

                    List<ResaPacket> aux = new ArrayList<>();
                    aux.add(resaPacket);
                    this.listResaTotal.add(new Client(resaPacket.getIdResa(), new ArrayList<>(aux)));
                    actualiserBB();
                    System.out.println("Reservation Fini!");
                }
                break;
                
            case "TR":
                if ((resaPacket.getDebitRequest() + this.debitTot - computeDebitBE()) >= this.debitDispo) {
                    System.out.println("Reservation impossible, debit demandé trop important");
                } else {
                    System.out.println("Reservation possible, reservation en cours");

                    pasRegle = true;
                    if (computeDebitBE() > 0) {
                        while (pasRegle) {
                            int nbClientBE = 0;
                            for (Client cl : this.listResaTotal) {
                                for (ResaPacket res : cl.getListResaClient()) {
                                    if (res.getClassTraffic().equals("BE")) {
                                        nbClientBE++;
                                    }
                                }
                            }

                            for (Client cl : listResaTotal) {
                                for (ResaPacket res : cl.getListResaClient()) {
                                    if (res.getClassTraffic().equals("BE")) {
                                        
                                        res.debitRequest -= debit/ nbClientBE;
                                    }
                                }
                            }
                            pasRegle = false;
                        }
                    }

                    List<ResaPacket> aux = new ArrayList<>();
                    aux.add(resaPacket);
                    this.listResaTotal.add(new Client(resaPacket.getIdResa(), new ArrayList<>(aux)));
                    actualiserBB();
                    System.out.println("Reservation Fini!");
                }
                break;

            case "DT":
                if (resaPacket.getDebitRequest() + debitTot - computeDebitBE() >= debitDispo) {
                    System.out.println("Reservation impossible, debit important");
                } else {
                    System.out.println("Reservation possible, reservation en cours");

                    pasRegle = true;
                    if (computeDebitBE() > 0) {
                        while (pasRegle) {
                            int nbClientBE = 0;
                            for (Client cl : listResaTotal) {
                                for (ResaPacket res : cl.getListResaClient()) {
                                    if (res.getClassTraffic().equals("BE")) {
                                        nbClientBE++;
                                    }
                                }
                            }
                            for (Client cl : this.listResaTotal) {
                                for (ResaPacket res : cl.getListResaClient()) {
                                    if (res.getClassTraffic().equals("BE")) {
                                        res.debitRequest -= debit / nbClientBE;
                                    }
                                }
                            }
                            pasRegle = false;
                        }
                    }
                    actualiserBB();
                    List<ResaPacket> aux = new ArrayList<>();
                    aux.add(resaPacket);
                    listResaTotal.add(new Client(resaPacket.getIdResa(), new ArrayList<>(aux)));
                    System.out.println("Reservation Fini!");
                }
                break;

            case "BE":

                int nbClientBE=0 ; 
                for (Client cl : listResaTotal) {
                    for (ResaPacket res : cl.getListResaClient()) {
                        if (res.getClassTraffic().equals("BE")) {
                            nbClientBE++;
                        }
                    }
                }
             // Pour le best effort, avoir un nombre de co max ? Avoir un debit minimum ? 
                if((computeCurrentDebit()<=debitTot) && (nbClientBE+1<= nb_max_be)){
                    System.out.println("Reservation possible, en cours ...");
                    nbClientBE=0 ; 
                    float be = debitTot-computeDebitBK()+computeDebitDT()+computeDebitTR() ; 
                    List<ResaPacket> aux = new ArrayList<>();
                    aux.add(resaPacket);
                    listResaTotal.add(new Client(listResaTotal.size()+1, new ArrayList<>(aux)));
                    for (Client cl : listResaTotal) {
                        for (ResaPacket res : cl.getListResaClient()) {
                            if (res.getClassTraffic().equals("BE")) {
                                nbClientBE++;
                            }
                        }
                    }
                    for (Client cl : listResaTotal) {
                        for (ResaPacket res : cl.getListResaClient()) {
                            if (res.getClassTraffic().equals("BE")) {
                                res.debitRequest = be/nbClientBE;
                            }
                        }
                    }
                    actualiserBB();
                    System.out.println("Reservation Fini!");
                }else{
                    System.out.println("Reservation Impossible !");
                }
                break ;
            
        }
    }
            
    
    void delete_be(){
        for (Client cl : listResaTotal) {
            for (ResaPacket res : cl.getListResaClient()) {
                if (res.getClassTraffic().equals("BE")) {
                    if(res.debitRequest<min_debit_be){
                        close_connection(res.getIdResa()) ; 
                    }
                    
                }
            }
        }

    }

    void recep_close_packet(ClosePacket p){
        for (Client cl : listResaTotal) {
            for (ResaPacket res : cl.getListResaClient()) {
                if (res.getIdResa() == p.idResa) {
                    close_connection(p.idResa) ; 
                }
            }
        }


    }
    void close_connection(int id ){
        for (Client cl : listResaTotal) {
            for (ResaPacket res : cl.getListResaClient()) {
                if (res.getIdResa() == id) {
                    this.listResaTotal.remove((id)) ; 
                    actualiserBB();
                }
            }
        }

    }
    void actualiserBB() {
        this.debitDispoDT = computeDebitDT();
        this.debitDispoTR = computeDebitTR();
        this.debitDispoBE = computeDebitBE();
        this.debitDispoBK = computeDebitBK();
        this.debitTot = computeCurrentDebit();
    }

    float computeDebitDT() {
        float debit = 0;
        for (Client client : listResaTotal) {
            for (ResaPacket resa : client.getListResaClient()) {
                if (resa.getClassTraffic().equals("DT")) {
                    debit += resa.getDebitRequest();
                }
            }
        }
        return debit;
    }

    float computeDebitBE() {
        float debit = 0;
        for (Client client : listResaTotal) {
            for (ResaPacket resa : client.getListResaClient()) {
                if (resa.getClassTraffic().equals("BE")) {
                    debit += resa.getDebitRequest();
                }
            }
        }
        return debit;
    }

    float computeDebitBK() {
        float debit = 0;
        for (Client client : listResaTotal) {
            for (ResaPacket resa : client.getListResaClient()) {
                if (resa.getClassTraffic().equals("BK")) {
                    debit += resa.getDebitRequest();
                }
            }
        }
        return debit;
    }

    float computeDebitTR() {
        float debit = 0;
        for (Client client : listResaTotal) {
            for (ResaPacket resa : client.getListResaClient()) {
                if (resa.getClassTraffic().equals("TR")) {
                    debit += resa.getDebitRequest();
                }
            }
        }
        return debit;
    }

    float computeCurrentDebit() {
        float debitTot = 0;
        for (Client client : listResaTotal) {
            for (ResaPacket resa : client.getListResaClient()) {
                debitTot += resa.getDebitRequest();
            }
        }
        return debitTot;
    }
}
