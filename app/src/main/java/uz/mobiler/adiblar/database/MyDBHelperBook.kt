package uz.mobiler.adiblar.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson
import uz.mobiler.adiblar.models.*
import uz.mobiler.adiblar.utils.ConfigBook
import java.util.*

class MyDBHelperBook(context: Context) :
    SQLiteOpenHelper(context, ConfigBook.DATABASE_NAME, null, ConfigBook.DATABASE_VERSION),
    DatabaseBooksService {
    private val gson = Gson()

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE ${ConfigBook.TABLE_NAME} (${ConfigBook.ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${ConfigBook.BOOK_NAME} TEXT, ${ConfigBook.BOOK_AUTHOR} TEXT, ${ConfigBook.DESCRIPTION} TEXT, ${ConfigBook.IMG_URL} TEXT, ${ConfigBook.URL} TEXT, ${ConfigBook.GENRE} TEXT, ${ConfigBook.LANGUAGE} TEXT)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addBook(book: Book) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ConfigBook.BOOK_NAME, gson.toJson(book.name))
        contentValues.put(ConfigBook.BOOK_AUTHOR, gson.toJson(book.author))
        contentValues.put(ConfigBook.DESCRIPTION, gson.toJson(book.desc))
        contentValues.put(ConfigBook.IMG_URL, book.image)
        contentValues.put(ConfigBook.URL, book.url)
        contentValues.put(ConfigBook.GENRE, gson.toJson(book.genre))
        contentValues.put(ConfigBook.LANGUAGE, book.language)


        database.insert(ConfigBook.TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun deleteBook(book: Book) {
        val database = this.writableDatabase
        database.delete(
            ConfigBook.TABLE_NAME,
            "${ConfigBook.BOOK_NAME} = ? AND ${ConfigBook.BOOK_AUTHOR} = ?",
            arrayOf(gson.toJson(book.name), gson.toJson(book.author))
        )
        database.close()
    }

    override fun getAllBooks(): ArrayList<Book> {
        val bookList = ArrayList<Book>()
        val database = this.writableDatabase
        val query = "SELECT * FROM ${ConfigBook.TABLE_NAME}"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val book = Book()
                book.id = cursor.getInt(0)
                book.name = gson.fromJson(cursor.getString(1), Name::class.java)
                book.author = gson.fromJson(cursor.getString(2), Author::class.java)
                book.desc = gson.fromJson(cursor.getString(3), Desc::class.java)
                book.image = cursor.getString(4)
                book.url = cursor.getString(5)
                book.genre = gson.fromJson(cursor.getString(6), Genre::class.java)
                book.language = cursor.getString(7)
                bookList.add(book)
            } while (cursor.moveToNext())
        }
        return bookList
    }

    override fun getBookById(book: Book): Boolean {
        val database = this.readableDatabase
        val cursor = database.query(
            ConfigBook.TABLE_NAME,
            arrayOf(
                ConfigBook.ID,
                ConfigBook.BOOK_NAME,
                ConfigBook.BOOK_AUTHOR,
                ConfigBook.DESCRIPTION,
                ConfigBook.IMG_URL,
                ConfigBook.URL,
                ConfigBook.GENRE,
                ConfigBook.LANGUAGE
            ),
            "${ConfigBook.BOOK_NAME} = ? AND ${ConfigBook.BOOK_AUTHOR} = ?",
            arrayOf(gson.toJson(book.name),gson.toJson(book.author)),
            null, null, null, null
        )
        cursor?.moveToFirst()
        return cursor.count > 0
    }

}