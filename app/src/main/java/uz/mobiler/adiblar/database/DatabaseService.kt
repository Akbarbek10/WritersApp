package uz.mobiler.adiblar.database

import uz.mobiler.adiblar.models.Writer

interface DatabaseService {

    fun addWriter(writer: Writer)

    fun deleteWriter(writer: Writer)

    fun getAllWriters(): List<Writer>

    fun getWriterById(writer: Writer): Boolean
}