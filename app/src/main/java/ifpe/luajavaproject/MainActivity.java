package ifpe.luajavaproject;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.stetho.Stetho;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.ResourceFinder;
import org.luaj.vm2.lib.jse.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import dalvik.system.DexFile;
import luna.luna.sqlite.ActiveRecordNative;
import luna.luna.sqlite.DBAdapter;
import luna.luna.userinterface.NativeInterface;

public class MainActivity extends AppCompatActivity implements ResourceFinder {

    private Globals globals;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        globals = JsePlatform.standardGlobals();
        globals.finder = this;



        // Should start all globals
        // SQLITE, GPS, Sensors, and such
        // TODO: Better if we could make it lazy loading instead of eager loading.
        try {
            NativeInterface nativeInterface = new NativeInterface(this);
            DBAdapter dbAdapter = new DBAdapter(this);

            globals.set("NativeInterface", CoerceJavaToLua.coerce(nativeInterface));
            globals.set("DBAdapter", CoerceJavaToLua.coerce(dbAdapter));
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
