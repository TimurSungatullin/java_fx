package sample.utils;

import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sample.model.Model;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Test {

    private static String URL = "http://127.0.0.1:3000/profile";

    public static void postModel(Model m) {
        try {
            HttpURLConnection connection = null;
            URL url = new URL(URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            DataOutputStream bw = new DataOutputStream(os);
            bw.writeBytes(m.toString());
            System.out.println(connection.getResponseCode());
        }
        catch (Exception e) {
            System.out.println("Сервер не отвечает");
        }

    }

    public static ArrayList<Model> getAll() {
        ArrayList<Model> models = new ArrayList<>();
        try {
            HttpURLConnection connection = null;
            URL url = new URL(URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuffer response = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(response.toString());
            for (Object jsonObject : jsonArray) {
                Model model = new Model();
                model.setId((Long) ((JSONObject) jsonObject).get("id"));
                model.setName((String) ((JSONObject) jsonObject).get("name"));
                model.setAge((Long) ((JSONObject) jsonObject).get("age"));
                model.setActive((Boolean) ((JSONObject) jsonObject).get("active"));
                model.setGender((String) ((JSONObject) jsonObject).get("gender"));
                models.add(model);
            }
        }
        catch (Exception e) {
            System.out.println("Сервер не отвечает");
            return models;
        }
        return models;
    }

    public static void deleteById(Long id) {
        try {
            HttpURLConnection connection = null;
            URL url = new URL(URL + "/" + id);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            System.out.println(connection.getResponseCode());
        }
        catch (Exception e) {
            System.out.println("Сервер не отвечает");
        }
    }

    public static void update(Model m) {
        try {
            HttpURLConnection connection = null;
            URL url = new URL(URL + "/" + m.getId());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            DataOutputStream bw = new DataOutputStream(os);
            bw.writeBytes(m.toString());
            System.out.println(connection.getResponseCode());
        }
        catch (Exception e) {
            System.out.println("Сервер не отвечает");
        }
    }

    public static Long getLastId() {
        ArrayList<Model> models = getAll();
        Long max = 0L;
        for (Model m: models) {
            if (max < m.getId()) {
                max = m.getId();
            }
        }
        return max;
    }
}
