package luna.luna.util;

import java.util.ArrayList;

/**
 * An layer wich abstracts use of ArrayList (Java) and NSArray (OBjective-c) in Lua.
 * Created by macbookair on 07/04/17.
 */

public class ArrayListNative<E> extends ArrayList<E> {

    @Override
    public E get(int index){

        return super.get(index);
    }

    public int count(){
        return this.size();
    }



}
