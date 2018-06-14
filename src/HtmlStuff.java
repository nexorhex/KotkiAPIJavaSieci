import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HtmlStuff {

    public StringBuilder getAccessKey(String login, String pass) throws IOException {
        URL url = new URL("http://smieszne-koty.herokuapp.com/oauth/token?grant_type=password&email=" + login + "&password=" + pass);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(30000);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        if (connection.getResponseCode() == 200) {
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }
            input.close();
            System.out.println(response);

            return response;
        }
        return null;
    }

    public List<String> getPage(int numPage) throws IOException {
        JSONObject autoryzacja = new JSONObject(getAccessKey("nexorhex@gmail.com", "nex").toString());
        System.out.println("Token = " + autoryzacja.getString("access_token"));
        String token = autoryzacja.getString("access_token");
        URL url = new URL("http://smieszne-koty.herokuapp.com/api/kittens" +
                "?access_token=" + token + "&page=" + numPage);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(30000);

        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder r = new StringBuilder();
        String inputLine;
        while ((inputLine = input.readLine()) != null)
            r.append(inputLine);
        input.close();
        //System.out.println(response);

        JSONArray kotki = new JSONArray(r.toString());
        JSONObject kotek;
        List<String> kotkiInfo = new ArrayList<>();

        for (int i = 0; i < kotki.length(); i++) {
            kotek = kotki.getJSONObject(i);
            kotkiInfo.add(kotek.getString("name"));
            kotkiInfo.add(kotek.getString("url"));
            kotkiInfo.add(String.valueOf(kotek.getInt("vote_count")));
        }
        return kotkiInfo;
    }

    public List<String> getCatsInfo(StringBuilder response) throws IOException {
        if (response != null) {
            JSONObject autoryzacja = new JSONObject(response.toString());
            System.out.println("Token = " + autoryzacja.getString("access_token"));
            String token = autoryzacja.getString("access_token");
            URL url = new URL("http://smieszne-koty.herokuapp.com/api/kittens" +
                    "?access_token=" + token);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(30000);

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            response = new StringBuilder();
            String inputLine;
            while ((inputLine = input.readLine()) != null)
                response.append(inputLine);
            input.close();
            //System.out.println(response);

            JSONArray kotki = new JSONArray(response.toString());
            JSONObject kotek;
            List<String> kotkiInfo = new ArrayList<>();

            for (int i = 0; i < kotki.length(); i++) {
                kotek = kotki.getJSONObject(i);
                System.out.println(kotek.getString("name"));
                kotkiInfo.add(kotek.getString("name"));
                kotkiInfo.add(kotek.getString("url"));
                kotkiInfo.add(String.valueOf(kotek.getInt("vote_count")));
            }
            return kotkiInfo;
        }
        return null;
    }

}
