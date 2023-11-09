package com.example.geoApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GeoDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "geoDATA";
    private static final String DATABASE_TABLE = "mapTable";



    private static final String ID = "id";
    private static final String ADDR = "address";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";


    GeoDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + DATABASE_TABLE + "(" + ID + " INT PRIMARY KEY,"
                + ADDR +  " TEXT,"
                + LONGITUDE + " TEXT,"
                + LATITUDE + " TEXT" + ")";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i >= i1)
            return;
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long addN (Coord coord){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(ADDR, coord.getADDR());
        val.put(LONGITUDE, coord.getLONGITUDE());
        val.put(LATITUDE, coord.getLATITUDE());

        long id = db.insert(DATABASE_TABLE, null, val);
        Log.d("inserted", "ID -> " + id);
        return id;

    }

    public Coord getN(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{ID, ADDR, LONGITUDE, LATITUDE},
                ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return new Coord(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3));

    }

    public List<Coord> getAddresses(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Coord> allAddr = new ArrayList<>();

        String query = "SELECT * FROM " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Coord coord = new Coord();
                coord.setID(cursor.getInt(0));
                coord.setADDR(cursor.getString(1));
                coord.setLONGITUDE(cursor.getString(2));
                coord.setLATITUDE(cursor.getString(3));

                allAddr.add(coord);
            } while(cursor.moveToNext());
        }
        return allAddr;
    }

    public void deleteCoord(Coord coord){
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(DATABASE_TABLE, ID + "=?",
                new String[]{String.valueOf(coord.getID())});
        Log.d("NoteDatabase", "Deleting Coordinate with id of : " + coord.getID() + ". Rows deleted: " + deletedRows);
        db.close();
    }

}
