package edu.XalDigital.com.utilities;

import edu.XalDigital.com.resources.RestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Map;

public class Answers {

    public int getAnsweredQuestions(JSONArray jsonArray){
        int counter = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject answer = jsonArray.getJSONObject(i);
            if((Boolean) answer.get("is_answered"))
            {
                counter += +1;
            }
        }

        return  counter;
    }

    public int getUnansweredQuestions (JSONArray jsonArray){
        int counter = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject answer = jsonArray.getJSONObject(i);
            if((Boolean) answer.get("is_answered") == false)
            {
                counter += +1;
            }
        }

        return  counter;
    }

    public HashMap<String, Integer> getLessAnsweredQuestionMap (JSONArray jsonArray){
        HashMap<String, Integer> lessAnsweredQuestion = new HashMap<String, Integer>();

        for (int i = 0; i < jsonArray.length();  i++) {
            JSONObject answer = jsonArray.getJSONObject(i);
            if ((Boolean) answer.get("is_answered")){
                lessAnsweredQuestion.put((String) answer.get("title"), (Integer) answer.get("view_count"));
            }
        }

        return  lessAnsweredQuestion;
    }

    /* For below method I've decided to return a List of String in case there are multiple answers
     with same amount of views */

    public List<String> getLessAnsweredQuestionTitle(HashMap<String, Integer> lessAnsweredQuestionMap){

        List<String> answerTitle = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: lessAnsweredQuestionMap.entrySet()) {

            // Adding the keys with the lowest value from the lessAnsweredQuestionMap
            if(entry.getValue() == Collections.min(lessAnsweredQuestionMap.values())) {
                answerTitle.add(entry.getKey());
            }
        }
        return answerTitle;
    }

    public List<Date> getCreationDateList(JSONArray jsonArray){
        Long creation_date;
        List<Date> creation_dates = new ArrayList<>();
        Instant instant;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject answer = jsonArray.getJSONObject(i);
            creation_date =  Long.parseLong(answer.get("creation_date").toString());
            instant = Instant.ofEpochSecond(creation_date);
            creation_dates.add(Date.from(instant));
        }

        return creation_dates;
    }

   public HashMap<String, Integer> getOwnersReputation(JSONArray jsonArray){
       HashMap<String, Integer> ownersReputation = new HashMap<String, Integer>();
       RestClient restClient                     = new RestClient();
       String                                    display_name;
       int                                       reputation;

       for (int i = 0; i < jsonArray.length(); i++) {
           JSONObject answer = jsonArray.getJSONObject(i);
           JSONObject owner  = restClient.parseJSONObject(answer.get("owner").toString());

           try {
               if (owner.has("display_name") && owner.get("display_name").toString().length() > 0) {
                   display_name = owner.get("display_name").toString();
               } else {
                   display_name = "Display name not available";
               }


               if (owner.has("reputation") && owner.get("reputation").toString().length() > 0) {
                   reputation = Integer.parseInt(owner.get("reputation").toString());
               }else{
                   reputation = 0;
               }
               ownersReputation.put( display_name ,reputation);

           } catch (JSONException e) {
               e.printStackTrace();
           } catch (NumberFormatException e) {
               e.printStackTrace();
           }

       }
        return  ownersReputation;
   }

    public List<String> getGreatestReputationOwner(HashMap<String, Integer> ownersReputation){

        List<String> ownerName = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: ownersReputation.entrySet()) {

            // Adding the keys with the lowest value from the lessAnsweredQuestionMap
            if(entry.getValue() == Collections.max(ownersReputation.values())) {
                ownerName.add(entry.getKey());
            }
        }
        return ownerName;
    }

    public String getAnswerTitle(JSONArray jsonArray, List<String> list) {

        String ownerGreatestReputation;
        String answerTitle;
        String owner;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject individualJsonObject = (JSONObject) jsonArray.get(i);

            if(individualJsonObject.has("owner")) {
                owner = individualJsonObject.getJSONObject("owner").get("display_name").toString();

                ownerGreatestReputation = list.get(0);
                if (owner.equalsIgnoreCase(ownerGreatestReputation)) {
                    answerTitle = individualJsonObject.get("title").toString();
                    return answerTitle;
                }
            }
        }
        return null;
    }
}
