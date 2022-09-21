package com.triton.johnson_tap_app.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

public class DbUtil {

    static SQLiteDatabase db;
    Context context;
    ContentValues values;
    DbHelper dbHelper;

    public DbUtil(Context context) {
        this.context = context;
        values = new ContentValues();
    }

    public SQLiteDatabase open() {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
        }
        db = dbHelper.getWritableDatabase();
        return db;
    }

    public void close() {
        if (dbHelper != null) {
            db.close();
        }
    }

    public static final String[] MRList_FIELD = {
            DbHelper.MR_ID,
            DbHelper.PART_NAME,
            DbHelper.PART_NO,
            DbHelper.QUANTITY,
            DbHelper.STATUS,
            DbHelper.JOBID
    };

    public static final String[] P_MRList_FIELD = {
            DbHelper.P_MR_ID,
            DbHelper.P_PART_NAME,
            DbHelper.P_PART_NO,
            DbHelper.P_QUANTITY,
            DbHelper.P_STATUS,
            DbHelper.JOBID
    };

    public static final String[] SIGNFIELD = {
            DbHelper.ID,
            DbHelper.JOBID,
            DbHelper.MYACTIVITY,
            DbHelper.SIGN_FILE,
            DbHelper.SIGN_PATH,
            DbHelper.STATUS
    };

    public static final String[] CUSTACK_FIELD = {
            DbHelper.ID,
            DbHelper.JOBID,
            DbHelper.MYACTIVITY,
            DbHelper.CUSTACK_FILE,
            DbHelper.CUSTACK_PATH,
            DbHelper.STATUS
    };

    public static final String[] PAUSED_BMRList_FIELD = {
            DbHelper.MR_ID,
            DbHelper.MR1,
            DbHelper.MR2,
            DbHelper.MR3,
            DbHelper.MR4,
            DbHelper.MR5,
            DbHelper.MR6,
            DbHelper.MR7,
            DbHelper.MR8,
            DbHelper.MR9,
            DbHelper.MR10,
            DbHelper.JOBID,
            DbHelper.MYACTIVITY
    };

    public void Preportdelete(String job_id) {
        db.delete(DbHelper.P_MR_TABLE, DbHelper.JOBID + "= '" + job_id + "'", null);

    }
    public void reportdelete(String job_id) {
        db.delete(DbHelper.MR_TABLE, DbHelper.JOBID + "= '" + job_id + "'", null);

    }

    public int deleteSign(String job_id, String myactivity) {
        return db.delete(DbHelper.SIGN_TABLE,dbHelper.JOBID + "= '" + job_id + "'" + "AND " + DbHelper.MYACTIVITY + "= '" + myactivity + "'",null) ;
    }

    public int deleteBreakdownMR(String job_id, String myactivity) {
        return db.delete(DbHelper.PAUSED_BMRlIST_TABLE,dbHelper.JOBID + "= '" + job_id + "'" + "AND " + DbHelper.MYACTIVITY + "= '" + myactivity + "'",null);
    }

    public int deleteCustAck(String job_id, String myactivity) {
        return db.delete(DbHelper.CUSTACK_TABLE,dbHelper.JOBID + "= '" + job_id + "'" + "AND " + DbHelper.MYACTIVITY + "= '" + myactivity + "'",null) ;
    }



    public long addMR(String strPartname, String strPartno, String strQuantity, String job_id) {
        values.clear();
        values.put(DbHelper.PART_NAME,strPartname);
        values.put(DbHelper.PART_NO,strPartno);
        values.put(DbHelper.QUANTITY,strQuantity);
        values.put(DbHelper.JOBID,job_id);
        return db.insert(DbHelper.MR_TABLE,null,values);
    }

    public Cursor getMR(String job_id) {
        return db.query(DbHelper.MR_TABLE,MRList_FIELD,DbHelper.JOBID + "= '" + job_id + "'",null,null,null,null);
    }


    public int deleteMR(String toString) {
         return db.delete(DbHelper.MR_TABLE,DbHelper.MR_ID + "= '" + toString + "'",null);
    }



    public long addPMR(String strPartname, String strPartno, String strQuantity, String job_id) {
        values.clear();
        values.put(DbHelper.P_PART_NAME,strPartname);
        values.put(DbHelper.P_PART_NO,strPartno);
        values.put(DbHelper.P_QUANTITY,strQuantity);
        values.put(DbHelper.JOBID,job_id);
        return db.insert(DbHelper.P_MR_TABLE,null,values);
    }

    public Cursor getPMR(String job_id) {
        return db.query(DbHelper.P_MR_TABLE,P_MRList_FIELD,DbHelper.JOBID + "= '" + job_id + "'",null,null,null,null);
    }


    public int deletePMR(String toString) {
        return db.delete(DbHelper.P_MR_TABLE,DbHelper.P_MR_ID + "= '" + toString + "'",null);
    }



    public long addBreakdownMRList(String s_mr1, String s_mr2, String s_mr3, String s_mr4,
                                   String s_mr5, String s_mr6, String s_mr7, String s_mr8,
                                   String s_mr9, String s_mr10, String job_id, String myactivity) {

        values.clear();
        values.put(DbHelper.MR1,s_mr1);
        values.put(DbHelper.MR2,s_mr2);
        values.put(DbHelper.MR3,s_mr3);
        values.put(DbHelper.MR4,s_mr4);
        values.put(DbHelper.MR5,s_mr5);
        values.put(DbHelper.MR6,s_mr6);
        values.put(DbHelper.MR7,s_mr7);
        values.put(DbHelper.MR8,s_mr8);
        values.put(DbHelper.MR9,s_mr9);
        values.put(DbHelper.MR10,s_mr10);
        values.put(DbHelper.JOBID,job_id);
        values.put(DbHelper.MYACTIVITY,myactivity);
        return db.insert(DbHelper.PAUSED_BMRlIST_TABLE,null,values);

    }

    public Cursor getBreakdownMrList() {

        return db.query(DbHelper.PAUSED_BMRlIST_TABLE,PAUSED_BMRList_FIELD,null,null,null,null,null);
    }

    public Cursor getBreakdownMrList(String job_id, String myactivity) {
        return db.query(DbHelper.PAUSED_BMRlIST_TABLE,PAUSED_BMRList_FIELD,
                dbHelper.JOBID + "= '" + job_id + "'" + "AND " + DbHelper.MYACTIVITY + "= '" + myactivity + "'", null,null,null,null );
    }

    public long addEngSign(String job_id, String myactivity, String uploadimagepath, File file, String s) {

        values.clear();
        values.put(DbHelper.JOBID,job_id);
        values.put(DbHelper.MYACTIVITY,myactivity);
        values.put(DbHelper.SIGN_PATH,uploadimagepath);
        values.put(DbHelper.SIGN_FILE, String.valueOf(file));
        values.put(DbHelper.STATUS,s);
        return db.insert(DbHelper.SIGN_TABLE,null,values) ;

    }

    public Cursor getEngSign(String job_id, String myactivity) {
        return db.query(DbHelper.SIGN_TABLE,SIGNFIELD,dbHelper.JOBID + "= '" + job_id + "'" + "AND " +
                DbHelper.MYACTIVITY + "= '" + myactivity + "'",null,null,null,null);
    }

    public int updatesign(String job_id, String myactivity, String s) {
        values.clear();
        values.put(DbHelper.JOBID,job_id);
        values.put(DbHelper.MYACTIVITY,myactivity);
        values.put(DbHelper.STATUS,s);
        return db.update(DbHelper.SIGN_TABLE,values,DbHelper.JOBID + "= '" + job_id + "'" +
                "AND " + DbHelper.MYACTIVITY + "' " + myactivity + "'",null);
    }

    public Cursor getEngSign() {
        return db.query(DbHelper.SIGN_TABLE,SIGNFIELD,null,null,null,null,null);
    }


    public long addCustAck(String job_id, String myactivity, String uploadimagepath, File file) {
        values.clear();
        values.put(DbHelper.JOBID,job_id);
        values.put(DbHelper.MYACTIVITY,myactivity);
        values.put(DbHelper.CUSTACK_PATH,uploadimagepath);
        values.put(DbHelper.CUSTACK_FILE, String.valueOf(file));
        return db.insert(DbHelper.CUSTACK_TABLE,null,values) ;
    }

    public Cursor getCustAck(String job_id, String myactivity) {
        return db.query(DbHelper.CUSTACK_TABLE,CUSTACK_FIELD,dbHelper.JOBID + "= '" + job_id + "'" + "AND " +
                DbHelper.MYACTIVITY + "= '" + myactivity + "'",null,null,null,null);
    }


}
