package fr.modibo.boxdomotique.Model;

/**
 * Classe <b>UrlServer</b> qui stocke les differentes URL du serveur.
 */
public class UrlServer {

    public static String URL_SERVER;

    //Local
    public static final String URL_CONNECTION = "domotique/login.php";
    public static final String URL_JSON = "domotique/json/index.json";
    public static final String SEND_URL_JSON = "domotique/receive_jsonfile.php";

    //Serveur
//    public static final String URL_CONNECTION = "auth";
//    public static final String URL_JSON = "jsonDevices";
//    public static final String SEND_URL_JSON = "jsonAPK";

    public static void setUrlServer(String urlServer) {
        URL_SERVER = "http://" + urlServer + "/";
    }
}
