package fr.modibo.boxdomotique.Model;

/**
 * Classe <b>UrlServer</b> qui stocke les differentes URL du serveur.
 */
public class UrlServer {

    public static String URL_SERVER;

    //Local
    public static final String URL_CONNECTION = "user/login_user.php";
    public static final String SEND_URL_JSON = "phpjson/script.php";

    //Serveur
    //public static final String URL_SERVER = "http://192.168.0.25:3000/";
    //public static final String URL_CONNECTION = "auth";

    // JSON
    public static final String URL_JSON = "jsonDevices/index50.json";

    public static void setUrlServer(String urlServer) {
        URL_SERVER = "http://" + urlServer + "/";
    }
}
