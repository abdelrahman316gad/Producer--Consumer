package com.example.socket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.util.HashMap;
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
public class GreetingController {
    HashMap<String,HashMap<String,String[]>>Frontq;
    HashMap<String,HashMap<String,String[]>>Frontm;
    trying t;
    Board B;
    @Autowired
    public GreetingController (SimpMessagingTemplate messagingTemplate)
    {
        this.t= new trying(messagingTemplate);
       B =new Board(t);
    }
    @GetMapping("/clear")
    public void clear()
    {
        B.clear();
    }
    @GetMapping("/input")
     public void getmessag(@RequestParam String frontq,@RequestParam String frontm ,@RequestParam String products) throws InterruptedException, IOException, JSONException {
        Frontq=new HashMap<String, HashMap<String, String[]>>();
        Frontm=new HashMap<String, HashMap<String, String[]>>();
        JSONObject jazq=new JSONObject(frontq);
        JSONObject jazm=new JSONObject(frontm);
        JSONArray keyq = jazq.names ();
        JSONArray keym = jazm.names ();
        for (int i = 0; i < keyq.length (); ++i) {
            String idQ = keyq.getString (i);
            String valueq = jazq.getString (idQ);
            JSONObject item=new JSONObject(valueq);
            JSONArray itemKeys = item.names ();
            HashMap<String,String[]> itemhash=new HashMap<String,String[]>();
            for (int j = 0; j < itemKeys.length (); ++j) {
                String sss = itemKeys.getString (j);
                JSONArray sssv= item.getJSONArray(sss);
                String[] array = new String[sssv.length()];
                for (int k = 0; k < sssv.length(); k++) {
                    array[k] = (String)sssv.get(k);
                }
                itemhash.put(sss,array);
            }
            Frontq.put(idQ,itemhash);
        }

        for (int i = 0; i < keym.length (); i++) {
            String idM = keym.getString (i);
            String valueM = jazm.getString (idM);
            JSONObject item=new JSONObject(valueM);
            JSONArray itemKeys = item.names ();
            HashMap<String,String[]> itemhash=new HashMap<String,String[]>();
            for (int j = 0; j < itemKeys.length (); j++) {
                String sss = itemKeys.getString (j);
                JSONArray sssv= item.getJSONArray(sss);
                String[] array = new String[sssv.length()];
                for (int k = 0; k < sssv.length(); k++) {
                    array[k] = (String)sssv.get(k);
                }
                itemhash.put(sss,array);
            }
            Frontm.put(idM,itemhash);
        }

        ///////////////////////////////

        B.makequeue(Frontq);
        B.makemachine(Frontm);
        B.n=Integer.parseInt(products);
    }
  @GetMapping("/replay")
    public void replay()
  {
      B.replay();
  }
}
