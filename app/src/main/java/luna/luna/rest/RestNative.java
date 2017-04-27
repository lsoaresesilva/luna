package luna.luna.rest;

import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.luaj.vm2.LuaValue.TBOOLEAN;
import static org.luaj.vm2.LuaValue.TINT;
import static org.luaj.vm2.LuaValue.TNIL;
import static org.luaj.vm2.LuaValue.TNUMBER;
import static org.luaj.vm2.LuaValue.TSTRING;
import static org.luaj.vm2.LuaValue.TTABLE;

/**
 * This class implements methods to make use of REST.
 * This is the native implementation which will be called when using REST api from Luna.
 * TODO: testar casos de falha
 * Created by: Leonardo Soares e Silva.
 */
public class RestNative extends AsyncTask<LuaTable, Integer, Void>{

    /**
     * RestResponseWrapper instance used to wrap response elements from REST requests.
     * Luna REST API use this internally to send response code and message to user's application.
     */
    RestResponseWrapper responseWrapper;

    /**
     * Creates a RestNative instance which will be used by Luna REST API to make REST requests.
     * @return RestNative instance.
     */
    public RestNative newRestNative(){
        return new RestNative();
    }

    public RestResponseWrapper getResponse(){
        return responseWrapper;
    }

    public void makeRequest(LuaTable options){
        this.execute(options);
    }

    private String readInputStreamToString(HttpURLConnection connection) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        try {
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();
        }
        catch (Exception e) {
            //Log.i(TAG, "Error reading InputStream");
            result = null;
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                    //Log.i(TAG, "Error closing InputStream");
                }
            }
        }

        return result;
    }

    public JSONObject tableToJson(LuaTable luaParams){
        if(luaParams == null){
            throw new IllegalArgumentException("Cannot generate JSON without a valid table.");
        }
        JSONObject json = new JSONObject();
        try {
            LuaValue k = LuaValue.NIL;
            while ( true ) {
                Varargs n = luaParams.next(k);
                if ( (k = n.arg1()).isnil() )
                    break;
                LuaValue v = n.arg(2);
                Object value = null;
                if( v.type() == TSTRING){
                    value = v.toString();
                }else if(v.type() == TINT){
                    value = v.toint();
                }else if(v.type() == TNIL){
                    value = JSONObject.NULL;
                }else if(v.type() == TBOOLEAN){
                    value = v.toboolean();
                }else if(v.type() == TNUMBER){
                    value = v.todouble();
                }else if(v.type() == TTABLE){
                    value =  tableToJson((LuaTable)v).toString();
                }
                json.put(k.toString(), value);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    public Void doInBackground(LuaTable... luaTables) {
        if( luaTables == null || luaTables.length == 0 ||
            (luaTables[0].get("url") == null && !luaTables[0].get("url").isstring()) ||
            (luaTables[0].get("method") == null  && !luaTables[0].get("method").isstring())||
            (luaTables[0].get("callback") == null && !luaTables[0].get("callback").istable())){
            throw new IllegalArgumentException("A table must be passed as parameter.");
        }

        String url = luaTables[0].get("url").toString();
        String method = luaTables[0].get("method").toString();
        LuaFunction callback = luaTables[0].get("callback").checkfunction();
        LuaTable luaParams = (LuaTable)luaTables[0].get("params");
        HttpURLConnection httpConnection = null;

        try {
            URL urlRequest = new URL(url);
            httpConnection = (HttpURLConnection) urlRequest.openConnection();
            httpConnection.setRequestMethod(method);
            httpConnection.setRequestProperty("User-Agent", "luna");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty( "Accept-Encoding", "" );
            if(method.equals("POST") || method.equals("PUT")) {
                httpConnection.setDoOutput(true);
                String json = this.tableToJson(luaParams).toString();

                OutputStream os = httpConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(json);
                writer.flush();
                writer.close();
                os.close();
            }
            int responseCode = httpConnection.getResponseCode();
            String response = readInputStreamToString(httpConnection);
            responseWrapper = new RestResponseWrapper(response, responseCode);

        } catch (Exception e) {
            responseWrapper = new RestResponseWrapper(e.getMessage(), -1);
        } finally {
            httpConnection.disconnect();
            callback.call();
        }

        return null;
    }
}
