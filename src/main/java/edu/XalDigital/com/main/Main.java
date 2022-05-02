package edu.XalDigital.com.main;

import edu.XalDigital.com.resources.RestClient;
import edu.XalDigital.com.utilities.Answers;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {
        // Declaring the variables
        String URL               = "https://api.stackexchange.com/2.2/search?order=desc&sort=activity&intitle=perl&site=stackoverflow";
        RestClient restClient    = new RestClient();
        CloseableHttpClient      httpClient;
        CloseableHttpResponse    httpResponse;
        String                   httpStringResponse;

        JSONObject               responseJSON;
        JSONArray                responseJSONArray;

        Answers answers          = new Answers();
        HashMap<String, Integer> lessAnsweredQuestionMap;
        List<String>             lessAnsweredQuestionTitle;
        Integer                  lessAnsweredQuestionValue;
        List<Date>               creation_dates;

        HashMap<String, Integer> ownersReputationMap;
        List<String>             greatestReputationOwner;
        int                      maxReputation;
        String                   answerTitle;
        // Requesting information using HTTP request
        httpClient     = restClient.getHttpClient();
        httpResponse   = restClient.getHttpResponse(httpClient,URL);
        httpStringResponse = restClient.parseToStringHttpResponse(httpResponse);

        // Converting HTTP response to JSON objects
        responseJSON      = restClient.parseJSONObject(httpStringResponse);
        responseJSONArray = restClient.parseJSONArray(responseJSON,"items");

        // Calling the methods from answer class to respond the questions
        System.out.printf("\nThe number of answered questions   : %d%n", answers.getAnsweredQuestions(responseJSONArray));
        System.out.printf("The number of unanswered questions   : %d%n", answers.getUnansweredQuestions(responseJSONArray));

        lessAnsweredQuestionMap   = answers.getLessAnsweredQuestionMap(responseJSONArray);
        lessAnsweredQuestionTitle = answers.getLessAnsweredQuestionTitle(lessAnsweredQuestionMap);
        lessAnsweredQuestionValue = Collections.min(lessAnsweredQuestionMap.values());

        System.out.println("\nThe answer with less views is: "+lessAnsweredQuestionTitle +" with "+ lessAnsweredQuestionValue +" views");

        creation_dates = answers.getCreationDateList(responseJSONArray);
        
        System.out.println("\nMost recent question : " + Collections.max(creation_dates));
        System.out.println("Oldest question : " + Collections.min(creation_dates));


        ownersReputationMap               = answers.getOwnersReputation(responseJSONArray);
        greatestReputationOwner           = answers.getGreatestReputationOwner(ownersReputationMap);
        maxReputation                     = Collections.max(ownersReputationMap.values());
        answerTitle                       = answers.getAnswerTitle(responseJSONArray,greatestReputationOwner);

        System.out.println("\nThe owner with the greatest reputation is: "+greatestReputationOwner+" with total reputation of "
                +maxReputation+" and the title of the answer is: '"+answerTitle+"'");

        // Finally closing the HttpConnections to free the memory
        restClient.CloseHttpConnection(httpClient,httpResponse);

    }
}

