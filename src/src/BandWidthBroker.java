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
    private static List<Link> listLink = new ArrayList<>();

    //Liste des réservations acceptées sur l'ensemble du réseau
    private static List<Client> listResaTotal = new ArrayList<>();


    //???
    private InetAddress ref;

    public BandwidthBroker() throws UnknownHostException {
        this.ref = InetAddress.getByName("0.0.0.0");
        //débit dispo au total en bits/s
        this.debitDispo=8000;
        this.debitTot = computeCurrentDebit();
    }

    void accept(ResaPacket resaPacket) {
        float debit = resaPacket.debit;
        float debitTot = this.computeCurrentDebit();
        boolean pasRegle;

        switch (resaPacket.trafficClass) {
            case "TR":
                if (resaPacket.debit + debitTot - computeDebitBE() >= debitDispo) {
                    System.out.println("Reservation impossible, debit important");
                } else {
                    System.out.println("Reservation possible, reservation en cours");

                    pasRegle = true;
                    if (computeDebitBE() > 0) {
                        while (pasRegle) {
                            int nbClientBE = 0;
                            for (Client cl : listResaTotal) {
                                for (ResaPacket res : cl.listResaClient) {
                                    if (res.trafficClass.equals("BE")) {
                                        nbClientBE++;
                                    }
                                }
                            }

                            for (Client cl : listResaTotal) {
                                for (ResaPacket res : cl.listResaClient) {
                                    if (res.trafficClass.equals("BE")) {
                                        res.debit -= debit / nbClientBE;
                                    }
                                }
                            }
                            pasRegle = false;
                        }
                    }

                    List<ResaPacket> aux = new ArrayList<>();
                    aux.add(resaPacket);
                    listResaTotal.add(new Client(resaPacket.id, new ArrayList<>(aux)));
                    actualiserBB();
                    System.out.println("Reservation Fini!");
                }
                break;

            case "DT":
                if (resaPacket.debit + debitTot - computeDebitBE() >= debitDispo) {
                    System.out.println("Reservation impossible, debit important");
                } else {
                    System.out.println("Reservation possible, reservation en cours");


                    pasRegle = true;
                    if (computeDebitBE() > 0) {
                        while (pasRegle) {
                            int nbClientBE = 0;
                            for (Client cl : listResaTotal) {
                                for (ResaPacket res : cl.listResaClient) {
                                    if (res.trafficClass.equals("BE")) {
                                        nbClientBE++;
                                    }
                                }
                            }

                            for (Client cl : listResaTotal) {
                                for (ResaPacket res : cl.listResaClient) {
                                    if (res.trafficClass.equals("BE")) {
                                        res.debit -= debit / nbClientBE;
                                    }
                                }
                            }
                            pasRegle = false;
                        }
                    }
                    actualiserBB();
                    List<ResaPacket> aux = new ArrayList<>();
                    aux.add(resaPacket);
                    listResaTotal.add(new Client(resaPacket.id, new ArrayList<>(aux)));
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
            for (ResaPacket resa : client.listResaClient) {
                if (resa.trafficClass.equals("DT")) {
                    debit += resa.debit;
                }
            }
        }
        return debit;
    }

    float computeDebitBE() {
        float debit = 0;
        for (Client client : listResaTotal) {
            for (ResaPacket resa : client.listResaClient) {
                if (resa.trafficClass.equals("BE")) {
                    debit += resa.debit;
                }
            }
        }
        return debit;
    }

    float computeDebitBK() {
        float debit = 0;
        for (Client client : listResaTotal) {
            for (ResaPacket resa : client.listResaClient) {
                if (resa.trafficClass.equals("BK")) {
                    debit += resa.debit;
                }
            }
        }
        return debit;
    }

    float computeDebitTR() {
        float debit = 0;
        for (Client client : listResaTotal) {
            for (ResaPacket resa : client.listResaClient) {
                if (resa.trafficClass.equals("TR")) {
                    debit += resa.debit;
                }
            }
        }
        return debit;
    }

    float computeCurrentDebit() {
        float debitTot = 0;
        for (Client client : listResaTotal) {
            for (ResaPacket resa : client.listResaClient) {
                debitTot += resa.debit;
            }
        }
        return debitTot;
    }
}
