// // This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.json.*;

public class SpeakerID {
	String key1;
	String key2;
	
	

	public SpeakerID() {
		this.key1 = "8a41b555234b4b2888846eb450d7e466";
		this.key2 = "66ac94885ae94fe38943d641bc4ec0b9";

	}

	public String createProfile() {
		HttpClient httpclient = HttpClients.createDefault();

		try {
			URIBuilder builder = new URIBuilder(
					"https://westus.api.cognitive.microsoft.com/spid/v1.0/identificationProfiles");

			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", this.key1);

			// Request body
			StringEntity reqEntity = new StringEntity("{\"locale\":\"en-us\",}");
			request.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String responseString = EntityUtils.toString(entity);
				JSONObject obj = new JSONObject(responseString);
				String profileID = obj.getString("identificationProfileId");
				System.out.println(profileID);
				System.out.println(responseString);
				return profileID;
				

				
			}
			else{
				System.out.println("its null");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return null;
	}

	public String enrollUser(String userID, File in) {
		HttpClient httpclient = HttpClients.createDefault();

		try {
			URIBuilder builder = new URIBuilder(
					"https://westus.api.cognitive.microsoft.com/spid/v1.0/identificationProfiles/dd4c3e98-598c-44b6-b540-4dd2f573abaa/enroll");

			builder.setParameter("shortAudio", "false");

			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "multipart/form-data");
			request.setHeader("Ocp-Apim-Subscription-Key", key1);

			// Request body
			request.setEntity(new FileEntity(in,
					ContentType.MULTIPART_FORM_DATA));
			// request.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			Header[] headers = response.getAllHeaders();
			for(Header header: headers){
				if( header.toString().contains("Operation-Location")){
					String[] opeartionHeader = header.toString().split("/");
					return opeartionHeader[opeartionHeader.length-1];
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	public String identityUser(String userID, File in) {
		HttpClient httpclient = HttpClients.createDefault();

		try {
			URIBuilder builder = new URIBuilder(
					"https://westus.api.cognitive.microsoft.com/spid/v1.0/identify?identificationProfileIds=dd4c3e98-598c-44b6-b540-4dd2f573abaa");

			builder.setParameter("shortAudio", "false");

			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			request.setHeader("Content-Type", "multipart/form-data");
			request.setHeader("Ocp-Apim-Subscription-Key", key1);

			// Request body
			request.setEntity(new FileEntity(in,
					ContentType.MULTIPART_FORM_DATA));
			// request.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(request);
			Header[] headers = response.getAllHeaders();
			for(Header header: headers){
				if( header.toString().contains("Operation-Location")){
					String[] opeartionHeader = header.toString().split("/");
					return opeartionHeader[opeartionHeader.length-1];
				}
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
    public String checkOperation(String operationID) 
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/spid/v1.0/operations/"+operationID);


            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", key1);


            // Request body
//            StringEntity reqEntity = new StringEntity("{body}");
//            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
            	String responseString = EntityUtils.toString(entity);
            	System.out.println(responseString);
            	JSONObject obj = new JSONObject( responseString);
				String status = obj.getString("status");
				if(status == "succeeded "){
					String confidence = obj.getJSONObject("processingResult").getString("confidence");
					return "Status: " + status + "Confidence: "+ confidence;
				}
				else{
					return "Status: " + status;
				}
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
		return null;
    }
    public String idenitfy(String speakerIDs, File sample) 
    {
    	
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/spid/v1.0/identify?identificationProfileIds="+speakerIDs);

            builder.setParameter("shortAudio", "false");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", this.key1);


            // Request body
            request.setEntity(new FileEntity(sample,
					ContentType.MULTIPART_FORM_DATA));
          

            HttpResponse response = httpclient.execute(request);
            Header[] headers = response.getAllHeaders();
            for(Header header: headers){
				if( header.toString().contains("Operation-Location")){
					String[] opeartionHeader = header.toString().split("/");
					return opeartionHeader[opeartionHeader.length-1];
				}
			}
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public String checkUser(String id) 
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/spid/v1.0/identificationProfiles/"+id);


            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", this.key1);


          

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
            	String repo = EntityUtils.toString(entity);
                System.out.println(repo);
                return repo;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
