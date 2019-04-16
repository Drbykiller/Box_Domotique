package fr.modibo.boxdomotique.model.webservices;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import fr.modibo.boxdomotique.model.Person;
import fr.modibo.boxdomotique.model.User;

public class UserData {

    private static final String URL = "http://10.0.2.2/json/User.json";
    //public static final String URL = "http://192.168.1.2/json/User.json";

    public static ArrayList<Person> getPersonServer() throws Exception {

        // URL
        String reponse = OkHttpUtils.sendGetOkHttpRequest(URL);

        Gson gson = new Gson();
        User user = gson.fromJson(reponse, User.class);

        ArrayList<Person> person = new ArrayList<>();

        if (user == null)
            throw new Exception("Erreur !!!");
        else if (user.getPerson() != null)
            person.addAll(user.getPerson());

        Log.w("TAG", reponse);

        return person;
    }
}
