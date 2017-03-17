package com.example.stdreg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class CountriesDbAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_STD_id = "id";
    public static final String KEY_CLASS = "class";
    public static final String KEY_SECTION = "section";
    public static final String KEY_YEAR = "year";
    public static final String KEY_dya = "date";
    public static final String KEY_MONTH = "month";
    public static final String KEY_TIME = "time";
    public static final String KEY_ATTENDENCE = "att";
    public static final String KEY_PHONE = "ph";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_SubjectName = "book_name";
    public static final String KEY_SubjectID = "book_id";

    public ArrayList<String> StbAttListsuarch = new ArrayList<String>();

    private static final String TAG = "CountriesDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "eduInstitute_Menegment";
    private static final String SQLITE_TABLE = "Std_reg";
    private static final String SQLITE_TABLE1 = "class_list";
    private static final String SQLITE_TABLE2 = "std_att";
    private static final String SQLITE_TABLE3 = "class_sub_List";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer," +
                    KEY_NAME + " VARoCHAR(20)," +
                    KEY_STD_id + " integer," +
                    KEY_CLASS + " VARCHAR(10)," +
                    KEY_SECTION + " VARCHAR(10)," +
                    KEY_YEAR + " VARCHAR(10)," +
                    KEY_PHONE + " VARCHAR(15)," +
                    KEY_MAIL + " VARCHAR(50)," +
                    "primary key (_id)" + " )" + ";";
    private static final String DATABASE_CREATE1 =
            "CREATE TABLE if not exists " + SQLITE_TABLE1 + " (" +
                    KEY_ROWID + " integer," +
                    KEY_CLASS + " VARCHAR(10)," +
                    KEY_SECTION + " VARCHAR(10)," +
                    "primary key (_id)" + " )" + ";";
    private static final String DATABASE_CREATE2 =
            "CREATE TABLE if not exists " + SQLITE_TABLE2 + " (" +
                    KEY_ROWID + " integer," +
                    KEY_NAME + " VARCHAR(10)," +
                    KEY_STD_id + " VARCHAR(10)," +
                    KEY_CLASS + " VARCHAR(10)," +
                    KEY_SECTION + " VARCHAR(10)," +
                    KEY_SubjectName + " VARCHAR(10)," +
                    KEY_SubjectID + " VARCHAR(10)," +
                    KEY_ATTENDENCE + " VARCHAR(10)," +
                    KEY_dya + " VARCHAR(10)," +
                    KEY_MONTH + " VARCHAR(10)," +
                    KEY_YEAR + " VARCHAR(10)," +
                    KEY_TIME + " VARCHAR(10)," +
                    "primary key (_id)" + " )" + ";";
    private static final String DATABASE_CREATE3 =
            "CREATE TABLE if not exists " + SQLITE_TABLE3 + " (" +
                    KEY_ROWID + " integer," +
                    KEY_CLASS + " VARCHAR(10)," +
                    KEY_SubjectName + " VARCHAR(10)," +
                    KEY_SubjectID + " VARCHAR(10)," +
                    "primary key (_id)" + " )" + ";";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);

            Log.w(TAG, DATABASE_CREATE1);
            db.execSQL(DATABASE_CREATE1);

            Log.w(TAG, DATABASE_CREATE2);
            db.execSQL(DATABASE_CREATE2);

            Log.w(TAG, DATABASE_CREATE3);
            db.execSQL(DATABASE_CREATE3);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE1);
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE2);
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE3);
            onCreate(db);
        }
    }

    public CountriesDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public CountriesDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }


    public int studentRegistration(String stdID, String name,
                                   String xclass, String xsection, String mail) {

        try {

            //String myallrows = null;
            int myallrows = 0;
            //int allrows4ph = 0;
            int allrows4mail = 0;
            int allrows4StdID = 0;
            int doneInsert = 0;
            mDb = mDbHelper.getReadableDatabase();
            try {

                Cursor allrows_count = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE + " WHERE " + KEY_CLASS + "='" + xclass + "' AND " + KEY_SECTION + "='" + xsection + "'", null);
                //Cursor checkP = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE + " WHERE " + KEY_PHONE + "='" + ph + "'", null);
                Cursor checkM = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE + " WHERE " + KEY_MAIL + "='" + mail + "'", null);
                myallrows = allrows_count.getCount();
                //allrows4ph = checkP.getCount();
                allrows4mail = checkM.getCount();

                allrows_count.close();
                //checkP.close();
                checkM.close();
            } catch (Exception e) {
            }


            //throw new Exception("ee");

            SimpleDateFormat getY = new SimpleDateFormat("yy"); // Just the year, with 2 digits
            String formattedDate = getY.format(Calendar.getInstance().getTime());

            int getid = myallrows + 1;
            String f_getid = null;
            if (String.valueOf(getid).length() <= 1) {
                f_getid = "0" + String.valueOf(getid);
            } else {
                f_getid = String.valueOf(getid);
            }
            //String newid=formattedDate+xclass+xsection+f_getid;
            String newid = stdID;

            try {
                Cursor checkID = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE + " WHERE " + KEY_STD_id + "='" + newid + "'", null);
                allrows4StdID = checkID.getCount();
                checkID.close();
            } catch (Exception e) {
            }


            Calendar calendar = Calendar.getInstance();
            int yearint = calendar.get(Calendar.YEAR);
            String year = Integer.toString(yearint);
            if (allrows4mail > 0 || allrows4StdID > 0) {
                if (allrows4mail > 0) {
                    doneInsert = 1;
                }
                if (allrows4StdID > 0) {
                    doneInsert = 3;
                }
            } else {
                ContentValues initialValues = new ContentValues();
                initialValues.put(KEY_NAME, name);
                initialValues.put(KEY_STD_id, newid);
                initialValues.put(KEY_CLASS, xclass);
                initialValues.put(KEY_SECTION, xsection);
                initialValues.put(KEY_YEAR, year);
                //initialValues.put(KEY_PHONE, ph);
                initialValues.put(KEY_MAIL, mail);
                long ccc = mDb.insert(SQLITE_TABLE, null, initialValues);
                if (ccc > 0) {
                    doneInsert = 4;
                } else {
                    doneInsert = 0;
                }

            }
            return doneInsert;

        } catch (Exception e) {
            Log.d("BILLAH", e.getMessage());
        }

        return 0;
    }


    public int studentAtt_insart(ArrayList<String> x, ArrayList<String> y, ArrayList<String> z, ArrayList<String> comm) {
        int doneInsert = 0;
        SimpleDateFormat getY = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String year = getY.format(Calendar.getInstance().getTime());

        SimpleDateFormat getM = new SimpleDateFormat("MM"); // Just the month, with 2 digits
        String month = getM.format(Calendar.getInstance().getTime());

        SimpleDateFormat getD = new SimpleDateFormat("dd"); // Just the date, with 2 digits
        String date = getD.format(Calendar.getInstance().getTime());
        int myallrows = 0;
        int checkallrows = 0;

        mDb = mDbHelper.getReadableDatabase();
        try {
            Cursor allrows_count = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE2 + " WHERE " +
                    KEY_CLASS + "='" + comm.get(0) + "' AND " + KEY_SECTION + "='" + comm.get(1) +
                    "' AND " + KEY_SubjectName + "='" + comm.get(2) + "' AND " + KEY_SubjectID + "='" + comm.get(3) +
                    "' AND " + KEY_dya + "='" + date + "' AND " + KEY_MONTH + "='" + month +
                    "' AND " + KEY_YEAR + "='" + year + "'", null);
            myallrows = allrows_count.getCount();
            allrows_count.close();
        } catch (Exception e) {
            Toast.makeText(null, "Error encountered.",
                    Toast.LENGTH_LONG);
        }
        if (myallrows > 0) {
            doneInsert = 3;
            System.out.println("no_0");
        } else {

            ContentValues initialValues = new ContentValues();

            for (int i = 0; i < x.size(); i++) {
                initialValues.put(KEY_NAME, x.get(i));
                initialValues.put(KEY_STD_id, y.get(i));
                initialValues.put(KEY_CLASS, comm.get(0));
                initialValues.put(KEY_SECTION, comm.get(1));
                initialValues.put(KEY_SubjectName, comm.get(2));
                initialValues.put(KEY_SubjectID, comm.get(3));
                initialValues.put(KEY_ATTENDENCE, z.get(i));
                initialValues.put(KEY_dya, date);
                initialValues.put(KEY_MONTH, month);
                initialValues.put(KEY_YEAR, year);
                mDb.insert(SQLITE_TABLE2, null, initialValues);
            }
            try {
                Cursor checkInsaer = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE2 + " WHERE " +
                        KEY_CLASS + "='" + comm.get(0) + "' AND " + KEY_SECTION + "='" + comm.get(1) +
                        "' AND " + KEY_SubjectName + "='" + comm.get(2) + "' AND " + KEY_SubjectID + "='" + comm.get(3) +
                        "' AND " + KEY_dya + "='" + date + "' AND " + KEY_MONTH + "='" + month +
                        "' AND " + KEY_YEAR + "='" + year + "'", null);
                checkallrows = checkInsaer.getCount();
                checkInsaer.close();
            } catch (Exception k) {
            }
            if (checkallrows == x.size()) {
                doneInsert = 1;
            } else {
                try {
                    Cursor delInsaer = mDb.rawQuery("DELETE * FROM " + SQLITE_TABLE2 + " WHERE " +
                            KEY_CLASS + "='" + comm.get(0) + "' AND " + KEY_SECTION + "='" + comm.get(1) +
                            "' AND " + KEY_SubjectName + "='" + comm.get(2) + "' AND " + KEY_SubjectID + "='" + comm.get(3) +
                            "' AND " + KEY_dya + "='" + date + "' AND " + KEY_MONTH + "='" + month +
                            "' AND " + KEY_YEAR + "='" + year + "'", null);

                    delInsaer.close();
                } catch (Exception j) {
                }
                doneInsert = 2;
                System.out.println("no_2");
            }
        }

        return doneInsert;
    }

    public int classInsart(String xclass, String xsection) {
        long sss = 0;
        int doneInsert = 0;
        int allrows4classANDsection = 0;
        try {
            Cursor checkClass_section = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE1 + " WHERE " + KEY_CLASS + "='" + xclass + "' AND " + KEY_SECTION + "='" + xsection + "'", null);
            allrows4classANDsection = checkClass_section.getCount();
            checkClass_section.close();
        } catch (Exception e) {
            Toast.makeText(null, "Error encountered.",
                    Toast.LENGTH_LONG);
        }
        if (!(allrows4classANDsection > 0)) {
            ContentValues initialValues = new ContentValues();

            initialValues.put(KEY_CLASS, xclass);
            initialValues.put(KEY_SECTION, xsection);
            sss = mDb.insert(SQLITE_TABLE1, null, initialValues);
            if (sss > 0) {
                doneInsert = 1;
            } else {
                doneInsert = 0;
            }
        } else {
            doneInsert = 2;
        }

        return doneInsert;
    }

    public int newSubjectInsert(ArrayList<String> gSubInfo) {
        long check;
        int confarm = 0;
        int allrows4classSubN = 0;
        int allrows4classSubID = 0;
        try {
            Cursor checkClass_sub = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE3 + " WHERE " + KEY_CLASS + "='" + gSubInfo.get(0) + "' AND " + KEY_SubjectName + "='" + gSubInfo.get(1) + "'", null);
            allrows4classSubN = checkClass_sub.getCount();

            Cursor checkClass_subID = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE3 + " WHERE " + KEY_SubjectID + "='" + gSubInfo.get(2) + "'", null);
            allrows4classSubID = checkClass_subID.getCount();

            checkClass_sub.close();
            checkClass_subID.close();
        } catch (Exception e) {
            Toast.makeText(null, "Error encountered.",
                    Toast.LENGTH_LONG);
        }
        if (allrows4classSubN > 0) {
            confarm = 2;
        } else if (allrows4classSubID > 0) {
            confarm = 3;
        } else {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_CLASS, gSubInfo.get(0));
            initialValues.put(KEY_SubjectName, gSubInfo.get(1));
            initialValues.put(KEY_SubjectID, gSubInfo.get(2));
            check = mDb.insert(SQLITE_TABLE3, null, initialValues);
            if (check > 0) {
                confarm = 1;
            } else {
                confarm = 0;
            }
        }

        return confarm;
    }
     /*public boolean deleteAllCountries() {

	  int doneDelete = 0;
	  doneDelete = mDb.delete(SQLITE_TABLE, null , null);
	  Log.w(TAG, Integer.toString(doneDelete));
	  return doneDelete > 0;
	 
	 }*/

	 /*public Cursor fetchCountriesByName(String inputText) throws SQLException {
      Log.w(TAG, inputText);
	  Cursor mCursor = null;
	  if (inputText == null  ||  inputText.length () == 0)  {
	   mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
	     KEY_CODE, KEY_NAME, KEY_CONTINENT, KEY_REGION}, 
	     null, null, null, null, null);
	 
	  }
	  else {
	   mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
	     KEY_CODE, KEY_NAME, KEY_CONTINENT, KEY_REGION}, 
	     KEY_NAME + " like '%" + inputText + "%'", null,
	     null, null, null, null);
	  }
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }*/

    public Cursor fetchAllCountries(String x, String y) {
        Cursor mCursor = null;
        mDb = mDbHelper.getWritableDatabase();
        try {
            mCursor = mDb.query(SQLITE_TABLE, new String[]{KEY_ROWID,
                            KEY_NAME, KEY_STD_id, KEY_MAIL, KEY_PHONE},
                    KEY_CLASS + "='" + x + "' AND " + KEY_SECTION + "='" + y + "'", null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public Cursor fetchAllClass() {
        Cursor mCursor = null;
        mDb = mDbHelper.getWritableDatabase();
        try {
            mCursor = mDb.query(SQLITE_TABLE1, new String[]{KEY_ROWID,
                            KEY_CLASS, KEY_SECTION},
                    null, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //***************************Get Year List***************************//
    public ArrayList<String> getclassList() {

        ArrayList<String> my_array = new ArrayList<String>();
        my_array.add("Select Year");
        try {
            mDb = mDbHelper.getWritableDatabase();
            Cursor allrows = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE1 + " GROUP BY class", null);
            if (allrows.moveToFirst()) {
                do {
                    String NAME = allrows.getString(1);
                    my_array.add(NAME);

                } while (allrows.moveToNext());
            }
            allrows.close();
            mDb.close();
        } catch (Exception e) {
            Toast.makeText(null, "Error encountered.",
                    Toast.LENGTH_LONG);
        }
        return my_array;
    }

    //********************Semester List get Year Wise********************//
    public ArrayList<String> getsection_by_Class(String returnSectuion) {
        ArrayList<String> sectioList = new ArrayList<String>();
        System.out.println(returnSectuion);
        try {
            //mDb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            mDb = mDbHelper.getWritableDatabase();
            Cursor allrows = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE1 + " WHERE " + KEY_CLASS + "='" + returnSectuion + "'", null);

            if (allrows.moveToFirst()) {
                do {
                    String NAME = allrows.getString(2);
                    sectioList.add(NAME);

                } while (allrows.moveToNext());
            }
            allrows.close();
            mDb.close();
        } catch (Exception e) {
            Toast.makeText(null, "Error encountered.",
                    Toast.LENGTH_LONG);
        }
        return sectioList;
    }
//********************Semester List get Year Wise********************//


    //********************Student List get Year & Semester Wise********************//
    public ArrayList<String> getstdList_by_CS(String mclass, String msection) {
        ArrayList<String> STDList = new ArrayList<String>();
        ArrayList<String> NList = new ArrayList<String>();
        ArrayList<String> IDList = new ArrayList<String>();
        try {
            //mDb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            mDb = mDbHelper.getWritableDatabase();
            Cursor allrows = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE + " WHERE " + KEY_CLASS + "='" + mclass + "' AND " + KEY_SECTION + "='" + msection + "'", null);

            if (allrows.moveToFirst()) {
                do {
                    String NAME = allrows.getString(1);
                    NList.add(NAME);
                    String ID = allrows.getString(2);
                    IDList.add(ID);

                } while (allrows.moveToNext());
                STDList.addAll(NList);
                STDList.addAll(IDList);
            }
            allrows.close();
            mDb.close();
        } catch (Exception e) {
            Toast.makeText(null, "Error encountered.",
                    Toast.LENGTH_LONG);
        }
        return STDList;
    }

//********************Student List get Year and Semester Wise********************//


    //********************Student List get Year and Semester Wise********************//
    public ArrayList<String> getsubList_by_CS(String mclass) {
        ArrayList<String> STDList = new ArrayList<String>();
        ArrayList<String> SubNList = new ArrayList<String>();
        ArrayList<String> SubIDList = new ArrayList<String>();
        try {
            mDb = mDbHelper.getWritableDatabase();
            Cursor allrows = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE3 + " WHERE " + KEY_CLASS + "='" + mclass + "'", null);

            if (allrows.moveToFirst()) {
                do {
                    String NAME = allrows.getString(2);
                    SubNList.add(NAME);
                    String ID = allrows.getString(3);
                    SubIDList.add(ID);

                } while (allrows.moveToNext());
                STDList.addAll(SubNList);
                STDList.addAll(SubIDList);
            }
            allrows.close();
            mDb.close();
        } catch (Exception e) {
            Toast.makeText(null, "Error encountered.",
                    Toast.LENGTH_LONG);
        }
        return STDList;
    }


    public ArrayList<String> getsubNMList_by_CS(String mclass) {
        ArrayList<String> SubNList = new ArrayList<String>();
        try {
            mDb = mDbHelper.getWritableDatabase();
            Cursor allrows = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE3 + " WHERE " + KEY_CLASS + "='" + mclass + "'", null);

            if (allrows.moveToFirst()) {
                do {
                    String NAME = allrows.getString(2);
                    SubNList.add(NAME);

                } while (allrows.moveToNext());
            }
            allrows.close();
            mDb.close();
        } catch (Exception e) {
            Toast.makeText(null, "Error encountered.",
                    Toast.LENGTH_LONG);
        }
        return SubNList;
    }
    //********************Student List get Year and Semester Wise********************//

    public ArrayList<String> getstdAtt(ArrayList<String> x) {

        String CountStudent;
        String Count_A_Student;
        String Count_P_Student;
        ArrayList<String> STDAttinfoListget = new ArrayList<String>();
        try {
            mDb = mDbHelper.getReadableDatabase();

            Cursor allrows1 = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE + " WHERE " + KEY_CLASS + "='" + x.get(0) + "' AND " + KEY_SECTION + "='" + x.get(1) + "'", null);
            allrows1.moveToNext();
            CountStudent = String.valueOf(allrows1.getCount());

            Cursor allrows2 = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE2 + " WHERE "
                    + KEY_CLASS + "='" + x.get(0) + "' AND " + KEY_SECTION + "='" + x.get(1) + "' AND " +
                    KEY_dya + "='" + x.get(2) + "' AND " + KEY_MONTH + "='" + x.get(3) + "' AND " +
                    KEY_YEAR + "='" + x.get(4) + "' AND " + KEY_SubjectName + "='" + x.get(5) + "' AND " +
                    KEY_ATTENDENCE + "='0'", null);
            allrows2.moveToNext();
            Count_A_Student = String.valueOf(allrows2.getCount());

            Cursor allrows3 = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE2 + " WHERE "
                    + KEY_CLASS + "='" + x.get(0) + "' AND " + KEY_SECTION + "='" + x.get(1) + "' AND " +
                    KEY_dya + "='" + x.get(2) + "' AND " + KEY_MONTH + "='" + x.get(3) + "' AND " +
                    KEY_YEAR + "='" + x.get(4) + "' AND " + KEY_SubjectName + "='" + x.get(5) + "' AND " +
                    KEY_ATTENDENCE + "='1'", null);
            allrows2.moveToNext();
            Count_P_Student = String.valueOf(allrows3.getCount());

            STDAttinfoListget.add(CountStudent);
            STDAttinfoListget.add(Count_P_Student);
            STDAttinfoListget.add(Count_A_Student);
            if (Integer.valueOf(Count_A_Student) > 0) {
                ArrayList<String> SubNList = new ArrayList<String>();
                ArrayList<String> SubIDList = new ArrayList<String>();
                ArrayList<String> SubAtt = new ArrayList<String>();

                Cursor allrows = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE2 + " WHERE "
                        + KEY_CLASS + "='" + x.get(0) + "' AND " + KEY_SECTION + "='" + x.get(1) + "' AND " +
                        KEY_dya + "='" + x.get(2) + "' AND " + KEY_MONTH + "='" + x.get(3) + "' AND " +
                        KEY_YEAR + "='" + x.get(4) + "' AND " + KEY_SubjectName + "='" + x.get(5) + "' AND " +
                        KEY_ATTENDENCE + "='0'", null);

                if (allrows.moveToFirst()) {
                    do {
                        SubNList.add(allrows.getString(1));
                        SubIDList.add(allrows.getString(2));
                        SubAtt.add(allrows.getString(7));

                    } while (allrows.moveToNext());


                    STDAttinfoListget.addAll(SubAtt);
                    STDAttinfoListget.addAll(SubNList);
                    STDAttinfoListget.addAll(SubIDList);
                }
                allrows.close();
            }


            allrows1.close();
            allrows2.close();
            mDb.close();
        } catch (Exception e) {
            Toast.makeText(null, "Error encountered.",
                    Toast.LENGTH_LONG);
        }
        return STDAttinfoListget;
    }


    public ArrayList<String> getAbsentInfobyIDdate(String id, String date) {
        mDb = mDbHelper.getWritableDatabase();
        int check_id = 0;
        ArrayList<String> subNmList = new ArrayList<String>();
        try {
            Cursor allrows1 = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE + " WHERE " +
                    KEY_STD_id + "='" + id + "'", null);
            allrows1.moveToNext();
            check_id = allrows1.getCount();
        } catch (Exception e1) {

        }
        if (check_id > 0) {
            String y = id.substring(0, 2);
            String cl = id.substring(2, 4);
            String se = id.substring(4, 6);
            String m = date.substring(3, 5);
            String d = date.substring(0, 2);

            StbAttListsuarch.clear();
            try {

                Cursor allrows = mDb.rawQuery("SELECT * FROM " + SQLITE_TABLE2 + " WHERE "
                        + KEY_CLASS + "='" + cl + "' AND " + KEY_SECTION + "='" + se + "' AND " +
                        KEY_dya + "='" + d + "' AND " + KEY_MONTH + "='" + m + "' AND " +
                        KEY_YEAR + "='" + y + "' AND " + KEY_STD_id + "='" + id + "'", null);

                if (allrows.moveToFirst()) {
                    do {
                        subNmList.add(allrows.getString(5));
                        StbAttListsuarch.add(allrows.getString(7));

                    } while (allrows.moveToNext());

                }
                allrows.close();
                mDb.close();
            } catch (Exception e) {
                Toast.makeText(null, "Error encountered.",
                        Toast.LENGTH_LONG);
            }

        } else {

        }

        return subNmList;
    }

    public ArrayList<String> setStdAttlistsuarch() {
        return StbAttListsuarch;
    }

}
