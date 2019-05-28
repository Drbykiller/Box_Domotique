package fr.modibo.boxdomotique.Model;

/**
 * Classe <b>UrlServer</b> qui stocke les differentes URL du serveur.
 */
public class UrlServer {

    public static String URL_SERVER;

    //Local
    public static final String URL_CONNECTION = "domotique/login.php";
    public static final String URL_JSON = "domotique/json/index.json";
    public static final String URL_SCENARIO = "domotique/json/scenario.json";
    public static final String SEND_JSON_URL_DEVICE = "domotique/receive_json_device.php";
    public static final String SEND_JSON_URL_SCENARIO = "domotique/receive_json_scenario.php";

    //Serveur
//    public static final String URL_CONNECTION = "auth";
//    public static final String URL_JSON = "jsonDevices";
//    public static final String URL_SCENARIO = "jsonScenarios";
//    public static final String SEND_JSON_URL_DEVICE = "jsonAPK";
//    public static final String SEND_JSON_URL_SCENARIO = "applyScenario";

    public static void setUrlServer(String urlServer) {
        URL_SERVER = "http://" + urlServer + "/";
    }
}
