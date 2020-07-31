package uz.mobiler.adiblar.models

import java.io.Serializable

class Writer :Serializable{

    var id: Int = 0
    var writer: String = ""
    var birthDead: String = ""
    var description: String = ""
    var imgUrl: String = ""

    constructor(id: Int, writer: String, birthDead: String, description: String, imgUrl: String) {
        this.id = id
        this.writer = writer
        this.birthDead = birthDead
        this.description = description
        this.imgUrl = imgUrl
    }

    constructor()


}