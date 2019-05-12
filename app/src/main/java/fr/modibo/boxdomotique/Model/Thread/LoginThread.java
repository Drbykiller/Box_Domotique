package fr.modibo.boxdomotique.Model.Thread;

import android.os.AsyncTask;

import androidx.fragment.app.FragmentManager;

import fr.modibo.boxdomotique.Model.UrlServer;
import fr.modibo.boxdomotique.View.LoadingDialog;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Classe <b>LoginThread</b> qui permet l'identification de l'utilisateur en interrogent le serveur.
 */
public class LoginThread extends AsyncTask<String, Void, Void> {

    private Exception e;
    private LoadingDialog dialog;
    private FragmentManager fragmentManager;
    private loginSuccessful loginSuccessful;
    private final String URL = UrlServer.URL_SERVER + UrlServer.URL_CONNECTION;


    /**
     * Constructeur de la classe LoginThread qui prend en paramètre 2 arguments.
     *
     * @param fragmentManager L'argument fragmentManager permet a l'objet 'dialog' de type : {@link fr.modibo.boxdomotique.View.LoadingDialog}, d'afficher correctement le pop-up de chargement.
     * @param loginSuccessful La classe qui implémente l'interface {@link loginSuccessful} passe en paramètre pour s'assurer que cette classe implémente bien les méthodes de l'interface.
     */
    public LoginThread(FragmentManager fragmentManager, loginSuccessful loginSuccessful) {
        this.fragmentManager = fragmentManager;
        this.loginSuccessful = loginSuccessful;
    }

    @Override
    protected void onPreExecute() {
        dialog = new LoadingDialog();
        dialog.show(fragmentManager, "Loading Dialog");
    }

    @Override
    protected Void doInBackground(String... strings) {
        String user = strings[0];
        String password = strings[1];

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("user", user)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(URL)
                .post(formBody)
                .build();

        Response response;

        try {
            response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                String result = response.body().string();

                if (result.equalsIgnoreCase("ok"))
                    loginSuccessful.login(user);
                else
                    loginSuccessful.errorLogin("userPassword");
            }
        } catch (Exception ex) {
            e = ex;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();

        if (e != null)
            loginSuccessful.errorLogin("server");

    }


     /* ************************
        INTERFACE + IMPLEMENTATION
     *************************/

    /**
     * Les 2 méthodes vont etre implémenté dans la classe {@link fr.modibo.boxdomotique.Controller.LoginActivity}
     */
    public interface loginSuccessful {
        /**
         * La 1er méthode permet de lancer l'activité principal.
         *
         * @param user L'Utilisateur passe en parametre ce qui permet de le récuperer.
         * @see fr.modibo.boxdomotique.Controller.LoginActivity
         */
        void login(String user);

        /**
         * La 2eme méthode permet d'envoyer quel type d'erreur la classe <b>LoginThread</b> a pu rencontrer.
         *
         * @param whatIsError L'erreur passe en parametre ce qui permet de le récuperer.
         */
        void errorLogin(String whatIsError);
    }
}
