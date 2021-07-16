package sg.edu.np.mad.remembertodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class CombinedTaskDatabaseHandler extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "taskcategory.db";
    public static String TASKCATEGORY = "TaskCategory";
    public static String COLUMN_TASKCATEGORYNAME = "TaskCategoryName";
    public static String COLUMN_COLORCODE = "ColorCode";
    public static String COLUMN_TASKNAME = "TaskName";
    public static String COLUMN_DIFFICULTY = "Difficulty";
    public static String COLUMN_DUEDATE = "DueDate";
    public static String COLUMN_COMPLETEBOOL = "Completed";
    public static String COLUMN_CREATEDDATE = "CreatedDate";
    public static int DATABASE_VERSION = 1;

    public CombinedTaskDatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TASKCATEGORY + "(" +
                        COLUMN_TASKCATEGORYNAME + " TEXT," +
                        COLUMN_COLORCODE + " TEXT," +
                        COLUMN_TASKNAME + " TEXT," +
                        COLUMN_DIFFICULTY + " TEXT," +
                        COLUMN_DUEDATE + " TEXT," +
                        COLUMN_COMPLETEBOOL + " INTEGER," +
                        COLUMN_CREATEDDATE + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TASKCATEGORY);
        onCreate(db);
    }


    //  NOTE TO SELF: More needed, AddTask, Remove Task, getTaskCategoryList

//    public ArrayList<TaskCategory> getTaskCategoryList(){
//        ArrayList<TaskCategory> TaskCategoryList = new ArrayList<>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM "+ TASKCATEGORY,null);
//        try{
//            while (cursor.moveToNext()) {
//                boolean followquery;
//                if((cursor.getInt(3)==1)){
//                    followquery = true;
//                }else{followquery = false;}
//                TaskCategoryList.add(new TaskCategory(
//                        cursor.getString(0),
//                        cursor.getString(1),
//                        cursor.getInt(2),
//                        followquery));
//            }
//        }
//        finally {
//            cursor.close();
//            return TaskCategoryList;
//        }
//    }

//    public void addTask(Task task){
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_TASKNAME,task.getTaskName());
//        values.put(COLUMN_DESC,task.get());
//        values.put(COLUMN_ID,user.getId());
//        int boolVal;
//        if(user.isFollowed() == true){boolVal = 1;} else{boolVal=0;}
//        values.put(COLUMN_FOLLOWED,boolVal);
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.insert(USERS,null, values);
//        db.close();
//    }
}
