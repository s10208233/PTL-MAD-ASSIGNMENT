package sg.edu.np.mad.remembertodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CombinedTaskDatabaseHandler extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "taskcategory.db";
    public static String TASKCATEGORY = "TaskCategory";
    public static String COLUMN_TASKCATEGORYNAME = "TaskCategoryName";
    public static String COLUMN_TASKLIST = "TaskList";
    public static String COLUMN_COLORCODE = "ColorCode";

    public static int DATABASE_VERSION = 1;

    Gson gson = new Gson();

    public CombinedTaskDatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TASKCATEGORY + "(" +
                        COLUMN_TASKCATEGORYNAME + " TEXT," +
                        COLUMN_TASKLIST + " TEXT," +
                        COLUMN_COLORCODE + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TASKCATEGORY);
        onCreate(db);
    }

    //  Local ArrayList<TaskCategory> into database
    public void storeTaskCategoryList(TaskCategory taskcategory){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASKCATEGORYNAME,taskcategory.getTaskCategoryName());
        values.put(COLUMN_TASKLIST, gson.toJson(taskcategory.getTaskList()));
        values.put(COLUMN_COLORCODE,taskcategory.getColorCode());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TASKCATEGORY,null, values);
        db.close();
    }
    //store Local ArrayList<Task> into database
    public void storeTaskList(Task task, String catergoryName,int position){
        ArrayList<TaskCategory> category = getTaskCategoryList();

        String data;
        if (category.get(position).getTaskList().isEmpty()){
            data = "'[" + gson.toJson(task) + "]'";
        }
        else{
            data = "'" + gson.toJson(category.get(position).getTaskList()).replaceFirst(".$","") + "," + gson.toJson(task) + "]'";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+ TASKCATEGORY + " SET " + COLUMN_TASKLIST + " = "+ data + " WHERE( " + COLUMN_TASKCATEGORYNAME + " = " + '"' + catergoryName + '"' + ");");


    }

    //Remove a whole category from database
    public boolean deleteTaskCategory(String categoryName){
        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("Delete top(1) from TaskCategory where TaskCategoryName = " + categoryName );
        return db.delete(TASKCATEGORY, COLUMN_TASKCATEGORYNAME + " = " + "'" + categoryName + "'", null) > 0;
    }
    //Remove a single Task
    public void deleteSingleTask(String data,String categoryName){
        data = data.substring(1);
        data = data.replaceFirst(".$","");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE TaskCategory SET TaskList =" + "'[" + data + "]'" + "WHERE (TaskCategoryName =" + "'" + categoryName + "'"+");");
    }
    //CheckedTaskTimer : to make task check = true after a timer task is done
    public void CheckedTaskTimer(String data, String categoryName){
        data = data.substring(1);
        data = data.replaceFirst(".$","");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE TaskCategory SET TaskList =" + "'[" + data + "]'" + "WHERE (TaskCategoryName =" + "'" + categoryName + "'"+");");
    }



    //  Retrieving Database data into program
    public ArrayList<TaskCategory> getTaskCategoryList() {
        ArrayList<TaskCategory> returnTaskCategoryList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TASKCATEGORY, null);
        while (cursor.moveToNext()) {

            try {
                if (cursor.getString(0) != null) {
                    returnTaskCategoryList.add(
                            new TaskCategory(
                                    cursor.getString(0),
                                    jsonStringTaskListRebuilder(cursor.getString(1)),
                                    cursor.getString(2)
                            )
                    );
                }

            } catch (JSONException e) {
                Log.e("Exception", e.toString());
                if (cursor.getString(0) != null) {
                    returnTaskCategoryList.add(
                            new TaskCategory(
                                    cursor.getString(0),
                                    null,
                                    cursor.getString(2)
                            )
                    );
                }

            }


        }
        cursor.close();
        return returnTaskCategoryList;
    }

    public ArrayList<Task> jsonStringTaskListRebuilder(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        ArrayList<Task> returnTaskList = new ArrayList<Task>();

        for (int i = 0; i < jsonArray.length(); i++){

            String TaskName = (String)jsonArray.getJSONObject(i).get("TaskName");
            int Difficulty = (int)jsonArray.getJSONObject(i).get("Difficulty");
            String DueDate = (String)jsonArray.getJSONObject(i).get("DueDate");
            Boolean Completed = (Boolean) jsonArray.getJSONObject(i).get("Completed");
            String DateCreated = (String)jsonArray.getJSONObject(i).get("DueDate");

            returnTaskList.add(new Task(TaskName,Difficulty,DueDate,Completed,DateCreated));
        }
        return returnTaskList;
    }


}

