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
                                        float debitBE =res.getDebitRequest();
                                        debitBE -= debit / nbClientBE;
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

                            for (Client cl : listResaTotal) {
                                for (ResaPacket res : cl.getListResaClient()) {
                                    if (res.getClassTraffic().equals("BE")) {
                                        res.getDebitRequest() -= debit / nbClientBE;
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
            case "BK":
                // Handle BE and BK reservations as needed
                break;
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
            for (ResaPacket resa : client.getlistResaClient()) {
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
