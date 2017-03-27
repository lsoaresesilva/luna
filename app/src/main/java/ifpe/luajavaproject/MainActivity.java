package ifpe.luajavaproject;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.ResourceFinder;
import org.luaj.vm2.lib.jse.*;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements ResourceFinder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HandleLua hl = new HandleLua(this);
        Globals globals = JsePlatform.standardGlobals();
        globals.finder = this;
        LuaValue test = CoerceJavaToLua.coerce(hl);
        //LuaValue test = CoerceJavaToLua.coerce(this);
        globals.set("obj", test);

        LuaValue chunk = null;
        chunk = globals.loadfile("main.lua");

        chunk.call();

        // Como gerenciar pediso para gps , camera, etc? tlvz usar um factory que retorne ao usuário acesso a estas classes
    }





    public InputStream findResource(String name) {
        try {
            return getAssets().open(name);
        } catch (java.io.IOException ioe) {
            return null;
        }
    }

}
