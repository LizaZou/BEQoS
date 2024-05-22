import java.net.*;
import java.util.ArrayList;
import java.util.List;

class Link {
    InetAddress source;
    InetAddress dest;
    float initialCapacity = 8000;
    float currentBE = 0;
    float currentVoix = 0;
    float currentDT = 0;
    float currentTR = 0;
}

class ResaPacket {
    int id;
    InetAddress ipDest;
    InetAddress ipSource;
    float debit;
    InetSocketAddress portDest;
    String protocol;
    String trafficClass;

    public ResaPacket(int id, InetAddress ipDest, InetAddress ipSource, float debit, InetSocketAddress portDest, String protocol, String trafficClass) {
        this.id = id;
        this.ipDest = ipDest;
        this.ipSource = ipSource;
        this.debit = debit;
        this.portDest = portDest;
        this.protocol = protocol;
        this.trafficClass = trafficClass;
    }
}

class Client {
    int idClient;
    public ArrayList<ResaPacket> listResaClient;

    public Client(int idClient, ArrayList<ResaPacket> listResaClient) {
        this.idClient = idClient;
        this.listResaClient = new ArrayList<>(listResaClient);
    }
}

public class BandwidthBroker {
    private float debitDispoDT;
    private float debitDispoTR;
    private float debitDispoBE;
    private float debitDispoBK;
    private float debitTot;
    private float debitDispo = 8000;

    private static List<Link> listLink = new ArrayList<>();
    private static List<Client> listResaTotal = new ArrayList<>();

    private InetAddress ref;

    public BandwidthBroker() throws UnknownHostException {
        this.ref = InetAddress.getByName("0.0.0.0");
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
