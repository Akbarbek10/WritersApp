package uz.mobiler.adiblar.utils

object LotinKrilService {

    fun convert(k: String): String {
        var k = k
        val x = "ABSYUKEHGOZXFQVPRLDJEMITNabsyukengozxfqvprldjemith"
        val y = "АБСЙУКЕҲГОЗХФҚВПРЛДЖЭМИТНабсйукенгозхфқвпрлджэмитҳ"
        k = k.replace("yo'", "ё")
        k = k.replace("yo’", "ё")
        k = k.replace("yo‘", "ё")
        k = k.replace("yo", "ё")
        k = k.replace("ch", "ч")
        k = k.replace("sh", "ш")
        k = k.replace("yu", "ю")
        k = k.replace("ya", "я")
        k = k.replace("Yo'", "Ё")
        k = k.replace("Yo’", "Ё")
        k = k.replace("Yo‘", "Ё")
        k = k.replace("Yo", "Ё")
        k = k.replace("Ch", "Ч")
        k = k.replace("Yu", "Ю")
        k = k.replace("Ya", "Я")
        k = k.replace("Sh", "Ш")
        k = k.replace("O'", "Ў")
        k = k.replace("O’", "Ў")
        k = k.replace("O‘", "Ў")
        k = k.replace("o'", "ў")
        k = k.replace("o’", "ў")
        k = k.replace("o‘", "ў")
        k = k.replace("g'", "ғ")
        k = k.replace("g’", "ғ")
        k = k.replace("g‘", "ғ")
        k = k.replace("G'", "Ғ")
        k = k.replace("G’", "Ғ")
        k = k.replace("G‘", "Ғ")
        k = k.replace("ye", "е")
        k = k.replace("'", "ъ")
        k = k.replace("’", "ъ")
        k = k.replace("‘", "ъ")
        val stringBuffer = StringBuilder(k)
        for (i in k.indices) {
            for (j in 0..49) if (stringBuffer[i] == x[j]) stringBuffer.setCharAt(
                i,
                y[j]
            )
        }
        return stringBuffer.toString()
    }
}