package edu.XalDigital.com.resources;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class RestClient {

    public JSONObject parseJSONObject(String response){
           JSONObject responseJson = new JSONObject(response);
        return  responseJson;
    }


    public JSONArray parseJSONArray(JSONObject jsonObject, String key){
        JSONArray responseArray =  jsonObject.getJSONArray(key);
        return responseArray;
    }


    public CloseableHttpClient getHttpClient(){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        System.out.println("CloseableHttpClient was opened");
        return httpClient;
    }

    public  CloseableHttpResponse getHttpResponse(CloseableHttpClient httpClient, String URL) {

        CloseableHttpResponse closeableHttpResponse;

        try {
            HttpGet getRequest = new HttpGet(URL);
            closeableHttpResponse = httpClient.execute(getRequest);

            // Verifying HTTP Code response
            int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
            if (statusCode > 299) {
                System.out.println("\nThere was an error,status code = " + statusCode);
            }else {
                System.out.println("\nHTTP Response Status Code = "+statusCode);
            }

            return closeableHttpResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String parseToStringHttpResponse(CloseableHttpResponse closeableHttpResponse) throws IOException {
        String entityResponse = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
        return  entityResponse;
    }

    public void CloseHttpConnection(CloseableHttpClient httpClient ,CloseableHttpResponse httpResponse){
        try{
            httpClient.close();
            System.out.println("\nHttpClient connection closed successfully!");
            httpResponse.close();
            System.out.println("HttpClient response closed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
