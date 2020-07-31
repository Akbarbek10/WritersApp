package uz.mobiler.adiblar.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.mobiler.adiblar.models.Writer
import uz.mobiler.adiblar.utils.Config

class MyDBHelper(context: Context) :
    SQLiteOpenHelper(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION),
    DatabaseService {


    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "create table ${Config.TABLE_NAME}(${Config.ID} integer primary key autoincrement, ${Config.WRITER} text, ${Config.BIRTH_DEAD} text, ${Config.DESCRIPTION} text, ${Config.IMG_URL} text)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addWriter(writer: Writer) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Config.WRITER, writer.writer)
        contentValues.put(Config.BIRTH_DEAD, writer.birthDead)
        contentValues.put(Config.DESCRIPTION, writer.description)
        contentValues.put(Config.IMG_URL, writer.imgUrl)
        database.insert(Config.TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun deleteWriter(writer: Writer) {
        val database = this.writableDatabase
        database.delete(Config.TABLE_NAME, "${Config.ID} = ?", arrayOf(writer.id.toString()))
        database.close()
    }

    override fun getAllWriters(): List<Writer> {
        val writerList = ArrayList<Writer>()
        val database = this.writableDatabase
        val query = "select * from ${Config.TABLE_NAME}"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {

            do {
                val writer = Writer()
                writer.id = cursor.getInt(0)
                writer.writer = cursor.getString(1)
                writer.birthDead = cursor.getString(2)
                writer.description = cursor.getString(3)
                writer.imgUrl = cursor.getString(4)
                writerList.add(writer)
            } while (cursor.moveToNext())
        }
        return writerList
    }

    override fun getWriterById(writer: Writer): Boolean {
        val database = this.readableDatabase
        val cursor = database.query(
            Config.TABLE_NAME,
            arrayOf(
                Config.ID,
                Config.WRITER,
                Config.BIRTH_DEAD,
                Config.DESCRIPTION,
                Config.IMG_URL
            ),
            "${Config.WRITER} = ? and ${Config.BIRTH_DEAD} = ?",
            arrayOf(writer.writer, writer.birthDead),
            null, null, null, null
        )
        cursor?.moveToFirst()
        return cursor.count > 0
    }

}