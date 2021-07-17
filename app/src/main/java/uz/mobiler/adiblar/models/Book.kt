package uz.mobiler.adiblar.models

import java.io.Serializable

data class Book(
    val name: String? = null,
    val desc: String? = null,
    val author: String? = null,
    val url: String? = null,
    val genre: String? = null,
    val image: String? = null
) : Serializable