package operation;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import listener.OperationListener;

/**
 * Created by umutserifler on 21.12.2017.
 */
public class HttpGetRequest extends AsyncTask<String, Integer, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;


    private OperationListener _operationListener;

    public HttpGetRequest(OperationListener operationListener) {
        this._operationListener = operationListener;
    }

    protected String doInBackground(String... urls) {

        String stringUrl = urls[0];
        URL url = null;
        HttpURLConnection connection = null;
        BufferedReader rd = null;
        String result = "", line;
        try {
            url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line + "\n";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        this._operationListener.httpCallBack(result);
    }



}