package fr.modibo.boxdomotique.model;

public class UrlServer {

    //Local
    public static String URL_SERVER;
    public static final String URL_CONNECTION = "user/login_user.php";

    //Serveur
//    public static final String URL_SERVER = "http://192.168.0.25:3000/";
//    public static final String URL_CONNECTION = "auth";

    // JSON
    public static final String URL_JSON = "jsonDevices/index50.json";

    public static void setUrlServer(String urlServer) {
        URL_SERVER = "http://" + urlServer + "/";
    }
}
