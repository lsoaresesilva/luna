package luna.luna.sqlite;

import android.provider.SyncStateContract;
import android.util.Log;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Created by macbookair on 04/04/17.
 */

public class ActiveRecordNative {



    /*private ArrayList<HashMap<String, Object>> columnsDefinitions;
    private String tableName;
    private DBAdapter database;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<HashMap<String, Object>> getColumnsDefinitions() {
        return columnsDefinitions;
    }

    public void setColumnsDefinitions(ArrayList<HashMap<String, Object>>  columnsDefinitions) {
        this.columnsDefinitions = columnsDefinitions;
    }

    public ActiveRecordNative(){
        columnsDefinitions = new ArrayList();
    }

    public static ActiveRecordNative buildModel(LuaTable model, DBAdapter dataBase){
        if(model == null){
            throw new NullPointerException("Cannot create a model without a lua model instance");
        }

        if(dataBase == null){
            throw new NullPointerException("Cannot create a model without an instance from Android Database");
        }

        ActiveRecordNative instance = null;

        instance = new ActiveRecordNative();
        instance.setColumnsDefinitions(instance.getColumnsDefinitionsFromModel(model));
        if( !dataBase.createTable(instance) ){
            return null;
        }

        return instance;
    }



    public ArrayList<HashMap<String, Object>> getColumnsDefinitionsFromModel(LuaTable model){
        LuaTable columns = (LuaTable)model.get("columns");
        if( columns == null || columns.length() == 0)
            throw new IllegalArgumentException("Columns cannot be empty.");

        // Pegar todas as definições de colunas
        ArrayList<HashMap<String, Object>> columnsDefinitionsFromModel = new ArrayList<>();

        LuaValue luaTableName = model.get("name");
        if(luaTableName == null || !luaTableName.isstring()){
            throw new IllegalArgumentException("Cannot create a model without a table name. Set a property called name in your model.");
        }

        this.setTableName(luaTableName.toString());


        for(int i = 0; i <= columns.length(); i++){
            LuaValue luaColumnDefinition = columns.get(i);
            if( luaColumnDefinition != null && !luaColumnDefinition.isnil() && luaColumnDefinition.istable() ) {
                LuaTable column = (LuaTable) luaColumnDefinition;
                HashMap<String, Object> columnProperties = null;
                if (column != null && column.istable()) {
                    columnProperties = new HashMap<>();
                    LuaValue columnName = column.get("name");
                    LuaValue columnType = column.get("type");
                    LuaValue columnMaxLength = column.get("maxLength");
                    LuaValue columnNull = column.get("notNull");
                    //LuaValue columnpKey = column.get("pKey");
                    if (columnName != null && columnName.isstring()) {
                        columnProperties.put("name", columnName.toString());
                    }

                    if (columnType != null && columnType.isstring()) {
                        columnProperties.put("type", columnType.toString());
                    }

                    if (columnMaxLength != null && columnMaxLength.isstring()) {
                        columnProperties.put("maxLength", columnMaxLength.toint());
                    }

                    if (columnNull != null && columnNull.isboolean()) {
                        columnProperties.put("notNull", columnNull.toboolean());
                    }


                }

                if (columnProperties != null &&
                        columnProperties.containsKey("name") &&
                        columnProperties.containsKey("type")) {
                    columnsDefinitionsFromModel.add(columnProperties);
                }
            }
        }

        if( columnsDefinitionsFromModel.size() == 0){
            throw new IllegalArgumentException("Missing columns definitions. Did you inform name and type?");
        }


        return columnsDefinitionsFromModel;
    }

    public HashMap<String, Object> getColumnsValues(LuaTable model){
        if(model == null){
            throw new NullPointerException("Cannot get columns->values from a null model");
        }

        // extrair os valores para as colunas
        HashMap<String, Object> columnsValues = new HashMap<>();
        for(int i =0; i < this.getColumnsDefinitions().size(); i++){
            HashMap<String, Object> column = this.getColumnsDefinitions().get(i);
            String columnName = (String)column.get("name");





        }

        return null;
    }*/










}
