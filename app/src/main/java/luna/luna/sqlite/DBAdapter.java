package luna.luna.sqlite;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.mock.MockContext;

import org.luaj.vm2.LuaValue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import luna.luna.util.ArrayListNative;
import luna.luna.util.DictionaryNative;

import static android.database.Cursor.FIELD_TYPE_BLOB;
import static android.database.Cursor.FIELD_TYPE_FLOAT;
import static android.database.Cursor.FIELD_TYPE_INTEGER;
import static android.database.Cursor.FIELD_TYPE_NULL;
import static android.database.Cursor.FIELD_TYPE_STRING;

/**
 * Created by macbookair on 05/04/17.
 * TODO:
    * usar asynctask
    * permitir que o DB seja apagado a acada início de app estilo hibernate
    * permitir que uma coluna seja ignorada do mapeamento ORM
 */

public class DBAdapter extends SQLiteOpenHelper{

    static final String DATABASE_NAME = "luna";
    static final int DATABASE_VERSION = 1;
    static private final String DATABASE_CREATION = "CREATE TABLE ";
    static private final String DATABASE_CREATE_UPDATE = " IF NOT EXISTS ";
    Context context;

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * Executes the @param SQL on database. Should be used only for commands which do not produce results.
     * @param SQL
     * @return
     */
    public boolean executeSQL(String SQL){
        if(SQL == null){
            throw new IllegalArgumentException("Cannot execute SQL because there were no SQL informed.");
        }

        /*if(!SQL.isstring()){
            throw new IllegalArgumentException("SQL must be a string.");
        }*/

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL);
        return true;
    }

    public Integer save(String SQL){
        // inserir no banco
        if( this.executeSQL(SQL)){
            // pegar o último registro e seu id
            // pega a tabela do insert



            Pattern pattern = Pattern.compile("INTO (\\w+) \\(");
            Matcher matcher = pattern.matcher(SQL);
            String tableName = null;
            if (matcher.find())
            {
                tableName = matcher.group(1);
            }
            ArrayListNative<DictionaryNative<String, Object>> result = this.querySQL("SELECT rowid FROM "+tableName+" ORDER BY ROWID DESC LIMIT 1");
            if( result.size() > 0 ){
                if( result.get(0).get("rowid") != null ){
                    return (Integer)result.get(0).get("rowid");
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else{
            return null;
        }

    }

    /*public String generateCreateTableSQL(ActiveRecordNative model){
        if(model == null){
            throw new IllegalArgumentException("Cannot generate a SQL for a null model.");
        }

        if(model.getColumnsDefinitions() == null || model.getColumnsDefinitions().size() <= 0){
            throw new IllegalArgumentException("Cannot generate a SQL without columns.");
        }

        if(model.getTableName() == null || model.getTableName().equals("")){
            throw new IllegalArgumentException("Cannot generate a SQL without a table name.");
        }

        // Pegar a definição das colunas, gerar o SQL
        String sql = DATABASE_CREATION+DATABASE_CREATE_UPDATE+model.getTableName()+" ("+"";
        for(int i = 0; i < model.getColumnsDefinitions().size(); i++){
            HashMap<String, Object> column = model.getColumnsDefinitions().get(i);
            String columnName = (String)column.get("name");
            String columnType = (String)column.get("type");
            Integer columnLength = (Integer)column.get("maxLength");
            Boolean columnNotNull = (Boolean)column.get("notNull");
            if( columnName == null || columnName.equals("")){
                throw new IllegalArgumentException("Cannot generate SQL, missing column name property.");
            }

            if( columnType == null || columnType.equals("")){
                throw new IllegalArgumentException("Cannot generate SQL, missing column type property.");
            }

            if( columnLength == null || columnLength == 0){
                throw new IllegalArgumentException("Cannot generate SQL, missing column length property.");
            }

            if( columnNotNull == null ){
                throw new IllegalArgumentException("Cannot generate SQL, missing column not null property.");
            }


            sql = " "+sql+" "+
                    columnName+" "+
                    columnType+" "+
                    "("+columnLength+")"+" "+
                    (columnNotNull == Boolean.valueOf(true)?"NOT NULL":"");
            if(i+1>=model.getColumnsDefinitions().size()){
                sql = sql+" );";
            }else{
                sql = sql+" ),";
            }
        }


        return sql;
    }*/


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        SQLiteDatabase db = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayListNative<DictionaryNative<String, Object>> querySQL(String SQL) {
        if(SQL == null){
            throw new IllegalArgumentException("SQL query cannot be null");
        }
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(SQL, null);


        String[] columns = c.getColumnNames();
        ArrayListNative<DictionaryNative<String, Object>> results = new ArrayListNative<>();

        try {
            while (c.moveToNext()) {
                DictionaryNative<String, Object> columnsValues = new DictionaryNative<>();
                for (int i = 0; i < columns.length; i++) {
                    int index = c.getColumnIndexOrThrow(columns[i]);
                    int type = c.getType(index);

                    if (type == FIELD_TYPE_NULL) {
                        columnsValues.put(columns[i], null);
                    } else if (type == FIELD_TYPE_BLOB) {
                        columnsValues.put(columns[i], c.getBlob(index).toString());

                    } else if (type == FIELD_TYPE_FLOAT) {
                        columnsValues.put(columns[i], Float.valueOf(c.getFloat(index)));
                    } else if (type == FIELD_TYPE_INTEGER) {
                        columnsValues.put(columns[i], Integer.valueOf(c.getInt(i)));
                    } else if (type == FIELD_TYPE_STRING) {
                        columnsValues.put(columns[i], c.getString(i));
                    }


                }
                results.add(columnsValues);
            }
        }catch(Exception e){

        }finally {
            c.close();
        }

        return results;
    }

    /*public boolean createTable(ActiveRecordNative model) {
        if(this.context==null){
            throw new NullPointerException("Cannot create a table without Android context.");
        }

        String generatedSQL = this.generateCreateTableSQL(model);
        // Run SQL

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(generatedSQL);

        return true;
    }*/
}
