package com.cryptodev.cryptopedia.PriceBackEnd;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getName();

    private static String apiKey = "82af9bcd-8589-405a-882c-ccb1ecc38fe3";

    public static List<Price> fetchPriceData(String murl) {

        URL url = createUrl(murl);
        Log.i(LOG_TAG,"Test: fetchPriceData");
        String jsonResponse= null;

        try {
            jsonResponse=makeHttpRequest(url);
        }catch (IOException e){
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        List<Price> price =  extractPriceData(jsonResponse);
        return price;

    }

    private static List<Price> extractPriceData(String jsonResponse) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Price> price = new ArrayList<Price>();

        try {
            JSONObject root =new JSONObject(jsonResponse);
            JSONArray data = root.getJSONArray("data");

            for (int i=0;i<data.length();i++){
                JSONObject prices = data.getJSONObject(i);
                String name = prices.getString("name");
                String symbol = prices.getString("symbol");


                JSONObject quote = prices.getJSONObject("quote");
                JSONObject usd = quote.getJSONObject("USD");
                double latest = usd.getDouble("price");
                price.add(new Price(name,symbol,latest));

            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return price;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("X-CMC_PRO_API_KEY", apiKey);
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
