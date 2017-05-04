package ifpe.luajavaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.stetho.Stetho;

import org.json.JSONException;
import org.json.JSONObject;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.ResourceFinder;
import org.luaj.vm2.lib.jse.*;

import java.io.InputStream;

import luna.luna.geolocation.GeoLocationNative;
import luna.luna.rest.RestNative;
import luna.luna.sqlite.DBAdapter;
import luna.luna.userinterface.NativeInterface;

public class MainActivity extends AppCompatActivity implements ResourceFinder {

    private Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        /*ViewGroup layout = (RelativeLayout)findViewById(R.id.main_layout);
        View buttonCreated = new ImageButton(this);
        InputStream imgFileNormal = null;
        try {
            imgFileNormal = this.getAssets().open("img/btn.png");
            Bitmap bitmapNormal = BitmapFactory.decodeStream(imgFileNormal);

            ((ImageButton)buttonCreated).setImageBitmap(bitmapNormal);
            LinearLayout l = new LinearLayout(this);
            layout.addView(l);
            l.addView(buttonCreated);
        } catch (IOException e) {
            e.printStackTrace();
        }*/



        globals = JsePlatform.standardGlobals();
        globals.finder = this;

        JSONObject json = new JSONObject();
        try {
            json.put("maoe", "opa");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Should start all globals
        // SQLITE, GPS, Sensors, and such
        // TODO: Better if we could make it lazy loading instead of eager loading.
        try {
            NativeInterface nativeInterface = new NativeInterface(this);
            DBAdapter dbAdapter = new DBAdapter(this);
            GeoLocationNative glNative = new GeoLocationNative(this);
            RestNative restNative = new RestNative();

            globals.set("NativeInterface", CoerceJavaToLua.coerce(nativeInterface));
            globals.set("DBAdapter", CoerceJavaToLua.coerce(dbAdapter));
            globals.set("GeoLocationNative", CoerceJavaToLua.coerce(glNative));
            globals.set("RestNative", CoerceJavaToLua.coerce(restNative));
            //globals.set("context", CoerceJavaToLua.coerce(this));
            LuaValue chunk = null;
            chunk = globals.loadfile("main.lua");

            chunk.call();
        }catch(Exception e ){
            Log.e("error", e.getMessage());

        }

        // Como gerenciar pediso para gps , camera, etc? tlvz usar um factory que retorne ao usuário acesso a estas classes
    }



    // LuaJ Specifics
    public Globals getGlobals() {
        return globals;
    }

    public InputStream findResource(String name) {
        try {
            return getAssets().open(name);
        } catch (java.io.IOException ioe) {
            return null;
        }
    }

}
