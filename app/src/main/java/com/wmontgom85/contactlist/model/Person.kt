package com.wmontgom85.contactlist.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.ByteArrayOutputStream

@Entity
data class Person(
    @PrimaryKey(autoGenerate = true) var _id: Long = 0L,
    var gender: String?,
    @Ignore var name: Name?,
    @Ignore var location: Location?,
    var email: String?,
    @Ignore var dob: DOB?,
    var phone: String?,
    var cell: String?,
    @Ignore val picture: Picture?,
    var firstName : String? = "",
    var lastName : String? = "",
    var street : String? = "",
    var city : String? = "",
    var state : String? = "",
    var postcode : String? = "",
    var birthdate : String? = "",
    var avatarLarge : String? = "",
    var avatarMedium : String? = "",
    var thumbnail : String? = "",
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var image_blob : ByteArray? = null
) {
    constructor() : this(0L,"",Name("",""),Location("","","",""),"",DOB(""),"","",Picture("","",""),"","","","","","","","","","")

    fun fill() {
        name?.let {
            firstName = it.first
            lastName = it.last
        }

        location?.let {
            street = it.street
            city = it.city
            state = it.state
            postcode = it.postcode
        }

        dob?.let {
            birthdate = it.date
        }

        picture?.let {
            avatarLarge = it.large
            avatarMedium = it.medium
            thumbnail = it.thumbnail
        }
    }

    fun buildAddress() : String {
        var line1 = ""
        var line2 = ""

        street?.let { s -> line1 = s.split(' ').joinToString(" ") { it.capitalize() } }

        city?.let {
            line2 = it.capitalize()
        }

        state?.let {
            line2 += when {
                line2.isNotEmpty() -> ", ${it.capitalize()}"
                else -> it.capitalize()
            }
        }

        postcode?.let {
            line2 += when {
                line2.isNotEmpty() -> ", $it"
                else -> it
            }
        }

        return when {
            (line1.isNotEmpty() && line2.isNotEmpty()) -> "$line1 \n$line2"
            line1.isNotEmpty() -> line1
            line2.isNotEmpty() -> line2
            else -> ""
        }
    }

    fun setImage(img: Bitmap) {
        try {
            val stream = ByteArrayOutputStream()
            img.compress(Bitmap.CompressFormat.PNG, 100, stream)
            this.image_blob = stream.toByteArray()
        } catch (tx: Throwable) {
            this.image_blob = null
        }
    }

    fun getImage() : Bitmap? {
        image_blob?.let {
            try {
                return BitmapFactory.decodeByteArray(it,0, it.size)
            } catch (tx: Throwable) {}
        }
        return null
    }
}

data class Name(
    val first: String?,
    val last: String?
)

data class Location(
    val street: String?,
    val city: String?,
    val state: String?,
    val postcode: String
)

data class DOB(
    val date: String?
)

data class Picture(
    val large: String?,
    val medium: String?,
    val thumbnail: String?
)