package uz.mobiler.adiblar.models

import java.io.Serializable

data class Book(
    var id: Int = 0,
    var name: Name? = null,
    var desc: Desc? = null,
    var author: Author? = null,
    var url: String? = null,
    var genre: Genre? = null,
    var image: String? = null,
    var language: String? = null
) : Serializable

data class Name(
    var eng: String? = null,
    var ru: String? = null,
    var uz: String? = null
) : Serializable

data class Author(
    var eng: String? = null,
    var ru: String? = null,
    var uz: String? = null
) : Serializable

data class Desc(
    var eng: String? = null,
    var ru: String? = null,
    var uz: String? = null
) : Serializable

data class Genre(
    var eng: String? = null,
    var ru: String? = null,
    var uz: String? = null
) : Serializable
