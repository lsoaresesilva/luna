package luna.luna.util;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by macbookair on 07/04/17.
 */

public class DictionaryNative<K,V> extends HashMap<K,V>{

    @Override
    public V get(Object key){
        return super.get(key);
    }

    public ArrayListNative<Object> getAllKeys(){
        ArrayListNative<Object> keysToLua = new ArrayListNative<>();
        Object[]  keys = super.keySet().toArray();
        for(int i = 0; i < keys.length; i++){
            keysToLua.add(keys[i]);
        }

        return keysToLua;
    }

}
