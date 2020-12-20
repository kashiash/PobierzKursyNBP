package pl.kashiash;



import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NBPRatesReader {

    public List<Rate> GetRates() {
        String json = getContentFromEndpoint();
        List<Rate> ratesList = convertJSONtoList(json);
        return ratesList;
    }

    private String getContentFromEndpoint() {
        HttpURLConnection connection = null;

        try {
            URL nbpEndpoint = CreateURL("C");
            connection = (HttpURLConnection) nbpEndpoint.openConnection();
            int response = connection.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                StringBuilder builder = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }
                return builder.toString();


            } else {

            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            connection.disconnect(); // zamknij połączenie HttpURLConnection
        }

        return null;
    }

    private URL CreateURL(String table) {
        try {
            String urlString = "https://api.nbp.pl/api/exchangerates/tables/" + table + "/";
            return new URL(urlString);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    private List<Rate> convertJSONtoList(String ratesJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            KursNBP[] KursyNBP = mapper.readValue(ratesJson, KursNBP[].class); //readValue(myJsonString);
            KursNBP kurs = KursyNBP[0]; //pobieramy 1 dzien wiec ta lista ma tylko 1 element
            return kurs.getRates();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
