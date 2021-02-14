package com.hightech.arcade.Global;

import android.content.Context;
import android.os.AsyncTask;
import androidx.appcompat.app.AlertDialog;
import com.hightech.arcade.R;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class NewUserDB extends AsyncTask<String, Void, String> {
    Context context;

    NewUserDB(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL ("http://arcademobile.altervista.org/registration.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String sendData = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(params[1], "UTF-8");
            bufferedWriter.write(sendData);
            bufferedWriter.flush();
            bufferedWriter.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("Username already taken. Please choose another username.")) {
            new AlertDialog.Builder(context)
                    .setMessage(R.string.errorMessage)
                    .show();
            HomeActivity.username.edit().clear().apply();
        }
        else {
            HomeActivity.dialog.dismiss();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
