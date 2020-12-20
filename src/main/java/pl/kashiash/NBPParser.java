package pl.kashiash;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NBPParser {
    public static void main(String[] args) throws IOException {

        NBPRatesReader reader = new NBPRatesReader();
      List<Rate>  rates = reader.GetRates();
        int i = rates.size();
        Test1();
        Test2();
    }


    private static List<Rate> convertJSONtoList(String ratesJson) {
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

    private static URL CreateURL(String table) {
        try {
            String urlString = "https://api.nbp.pl/api/exchangerates/tables/" + table + "/";
            return new URL(urlString);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }


    private static void Test2() {

        HttpURLConnection connection = null;

        try {
            URL nbpEndpoint = CreateURL("C");//new URL("https://api.nbp.pl/api/exchangerates/tables/C/");
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
                String ratesJson = builder.toString();
                List<Rate> ratesList = convertJSONtoList(ratesJson);
                int lk = ratesList.size();


            } else {

            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            connection.disconnect(); // zamknij połączenie HttpURLConnection
        }


    }

    private static void Test1() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.nbp.pl/api/exchangerates/tables/C/")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "ee3la5eizeiY4Eix=jei1Xah3")
                .build();
        Response response = client.newCall(request).execute();
        String myJsonString = response.body().string();
        System.out.print(myJsonString);

        ObjectMapper mapper = new ObjectMapper();
        KursNBP[] wariantArray = mapper.readValue(myJsonString, KursNBP[].class); //readValue(myJsonString);

//        List<KursNBP> wariantList = mapper.readValue(myJsonString, new TypeReference<List<KursNBP>>(){});
//        List<KursNBP> wariantList2 = mapper.readValue(myJsonString, mapper.getTypeFactory().constructCollectionType(List.class, KursNBP.class));

//        List<KursNBP> wariantFast = Arrays.asList(mapper.readValue(myJsonString, KursNBP[].class));
        KursNBP kurs = wariantArray[0]; //pobieramy 1 dzien wiec ta lista ma tylko 1 element
        List<Rate> ratesList = kurs.getRates();
    }
}
