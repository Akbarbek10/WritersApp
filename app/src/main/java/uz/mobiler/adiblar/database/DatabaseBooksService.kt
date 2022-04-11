package uz.mobiler.adiblar.database

import uz.mobiler.adiblar.models.Book

interface DatabaseBooksService {
    fun addBook(book: Book)

    fun deleteBook(book: Book)

    fun getAllBooks(): List<Book>

    fun getBookById(book: Book): Boolean
}