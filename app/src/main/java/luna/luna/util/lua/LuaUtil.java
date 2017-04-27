package luna.luna.util.lua;

import org.json.JSONObject;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.util.HashMap;

import static org.luaj.vm2.LuaValue.TBOOLEAN;
import static org.luaj.vm2.LuaValue.TINT;
import static org.luaj.vm2.LuaValue.TNIL;
import static org.luaj.vm2.LuaValue.TNUMBER;
import static org.luaj.vm2.LuaValue.TSTRING;
import static org.luaj.vm2.LuaValue.TTABLE;

/**
 * Created by macbookair on 26/04/17.
 */

public class LuaUtil {

    public static HashMap tableToHashMap(LuaTable table){
        HashMap hash = new HashMap();
        LuaValue k = LuaValue.NIL;
        while ( true ) {
            Varargs n = table.next(k);
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
                value =  tableToHashMap((LuaTable)v);
            }

            hash.put(k.toString(), value);
        }
        return hash;
    }
}
