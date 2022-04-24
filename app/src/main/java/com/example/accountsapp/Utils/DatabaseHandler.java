package com.example.accountsapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.accountsapp.Info_page;
import com.example.accountsapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "MyMoney";
    private static final String ACCOUNTS_TABLE = "accounts";
    private static final String TRANSACTION_TABLE = "transact";
    private static final String LOGIN_TABLE = "login";
    private static final String ID = "id";
    private static final String ENTRY_NAME = "entry_name";
    private static final String STATUS = "status";
    private static final String PLACE = "place";
    private static final String NET_AMOUNT = "net_amount";
    private static final String NOTE= "note";
    private static final String DATE= "date";
    private static final String USERNAME= "username";
    private static final String PASSWORD= "password";
    private static final String PHONENUMBER= "phonenumber";
    private static final String LOGGED_IN= "logged_in";

    private static final String CREATE_ACCOUNTS_TABLE = "CREATE TABLE " + ACCOUNTS_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ENTRY_NAME + " TEXT, "
            + NET_AMOUNT +" INTEGER, "+ PLACE + " TEXT, "+ PHONENUMBER + " TEXT, "+ USERNAME + " TEXT, "+ STATUS + " TEXT)";

    private static final String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TRANSACTION_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ENTRY_NAME + " TEXT, "
            + NET_AMOUNT +" INTEGER, "+ PLACE + " TEXT, "+ PHONENUMBER + " TEXT, "+ USERNAME + " TEXT, "+ NOTE + " TEXT, "+ DATE + " TEXT, "+ STATUS + " TEXT)";

    private static final String CREATE_LOGIN_TABLE = "CREATE TABLE " + LOGIN_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERNAME + " TEXT, "+ PASSWORD + " TEXT, "+LOGGED_IN+ " TEXT)";
    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNTS_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }


    public void insertEntry(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(ENTRY_NAME, task.getName());
        cv.put(PLACE,task.getPlace());
        cv.put(NET_AMOUNT,task.getNet_amount());
        cv.put(STATUS,task.getStatus());
        cv.put(USERNAME,task.getUsername());
        cv.put(PHONENUMBER,task.getPhonenumber());
        db.insert(ACCOUNTS_TABLE, null, cv);

    }

    public void insertEntry2(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(ENTRY_NAME, task.getName());
        cv.put(PLACE,task.getPlace());
        cv.put(NET_AMOUNT,task.getNet_amount());
        cv.put(STATUS,task.getStatus());
        cv.put(USERNAME,task.getUsername());
        cv.put(PHONENUMBER,task.getPhonenumber());
        cv.put(NOTE,task.getNote());
        cv.put(DATE,task.getDate());
        db.insert(TRANSACTION_TABLE, null, cv);
    }

    public void insertEntry3(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, task.getUsername());
        cv.put(PASSWORD,task.getPassword());
        cv.put(LOGGED_IN,task.getLogged_in());

        db.insert(LOGIN_TABLE, null, cv);
    }

    public List<ToDoModel> getAllNames(String username){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        String sel="username=?";
        String[] args={username};
        db.beginTransaction();
        try{
            cur = db.query(ACCOUNTS_TABLE, null, sel, args, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setName(cur.getString(cur.getColumnIndex(ENTRY_NAME)));
                        task.setPlace(cur.getString(cur.getColumnIndex(PLACE)));
                        task.setNet_amount(cur.getInt(cur.getColumnIndex(NET_AMOUNT)));
                        task.setStatus(cur.getString(cur.getColumnIndex(STATUS)));
                        task.setPhonenumber(cur.getString(cur.getColumnIndex(PHONENUMBER)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }




    public void updateName(int id, String name) {
        ContentValues cv = new ContentValues();
        cv.put(ENTRY_NAME, name);
        db.update(ACCOUNTS_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteEntry(int id){
        db.delete(ACCOUNTS_TABLE, ID + "= ?", new String[] {String.valueOf(id)});

    }

    public void deleteTransactions(String name,String username){
       db.delete(TRANSACTION_TABLE,USERNAME+"=? AND "+ENTRY_NAME+"=?",new String[]{username,name});
       //db.execSQL("DELETE FROM "+TRANSACTION_TABLE+" WHERE "+ENTRY_NAME+" ="+name+" AND "+USERNAME+" ="+username);
    }

    public void updateStatus(int id, String status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(ACCOUNTS_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateNet_amount(int id, int net_amount){
        ContentValues cv = new ContentValues();
        cv.put(NET_AMOUNT, net_amount);
        db.update(ACCOUNTS_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updatePhonenumber(int id, String phonenumber){
        ContentValues cv = new ContentValues();
        cv.put(PHONENUMBER, phonenumber);
        db.update(ACCOUNTS_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updatePlace(int id, String place){
        ContentValues cv = new ContentValues();
        cv.put(PLACE,place);
        db.update(ACCOUNTS_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
       // db.update(TRANSACTION_TABLE,cv,NAME+"=? AND"+USERNAME+"=?",new String[]{name,username});

    }


    public void updateLogged_in(String login_in_status,String username){
        ContentValues cv = new ContentValues();
        cv.put(LOGGED_IN,login_in_status);
        db.update(LOGIN_TABLE, cv, USERNAME +"= ?", new String[] {username});

    }

    public void updatePassword(String password,String username){
        ContentValues cv = new ContentValues();
        cv.put(PASSWORD,password);
        db.update(LOGIN_TABLE, cv, USERNAME +"= ?", new String[] {username});

    }

    public List<String> getAllUserNames(){
        List<String> usernameslist = new ArrayList<>();
        Cursor cur=null;
        db.beginTransaction();
        try{
            cur = db.query(LOGIN_TABLE, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        usernameslist.add(cur.getString(cur.getColumnIndex(USERNAME)));

                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return usernameslist;
    }

    public List<ToDoModel> getLoginDetails(){
        List<ToDoModel> usernameslist = new ArrayList<>();
        Cursor cur=null;
        db.beginTransaction();
        try{
            cur = db.query(LOGIN_TABLE, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setUsername(cur.getString(cur.getColumnIndex(USERNAME)));
                        task.setPassword(cur.getString(cur.getColumnIndex(PASSWORD)));
                        task.setLogged_in(cur.getString(cur.getColumnIndex(LOGGED_IN)));
                        usernameslist.add(task);

                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return usernameslist;
    }



    public List<ToDoModel> getAllDetails(String name,String username){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        String sel="entry_name=? AND username=?";
        String[] args={name,username};
        db.beginTransaction();
        try{
            cur = db.query(TRANSACTION_TABLE, null, sel,args, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                        task.setName(cur.getString(cur.getColumnIndex(ENTRY_NAME)));
                        task.setPlace(cur.getString(cur.getColumnIndex(PLACE)));
                        task.setNet_amount(cur.getInt(cur.getColumnIndex(NET_AMOUNT)));
                        task.setStatus(cur.getString(cur.getColumnIndex(STATUS)));
                        task.setNote(cur.getString(cur.getColumnIndex(NOTE)));
                        task.setDate(cur.getString(cur.getColumnIndex(DATE)));
                        task.setPhonenumber(cur.getString(cur.getColumnIndex(PHONENUMBER)));
                        task.setUsername(cur.getString(cur.getColumnIndex(USERNAME)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }
}
