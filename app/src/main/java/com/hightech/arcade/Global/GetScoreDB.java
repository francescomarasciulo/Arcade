package com.hightech.arcade.Global;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

public class GetScoreDB extends AsyncTask<String, Void, String> {
    Context context;

    public GetScoreDB(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL ("http://arcademobile.altervista.org/get_scores.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String sendData = URLEncoder.encode("table", "UTF-8")+"="+URLEncoder.encode(params[1], "UTF-8");
            bufferedWriter.write(sendData);
            bufferedWriter.flush();
            bufferedWriter.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray array = jsonObject.getJSONArray("resultArray");
            for (int i=0; i<array.length(); i++ ) {
                JSONObject ob=array.getJSONObject(i);
                HomeActivity.userList.add(i, ob.get("Username").toString());
                HomeActivity.scoreList.add(i, ob.get("Score").toString());
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        context.startActivity(new Intent(context, GlobalLeaderboard.class));
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
