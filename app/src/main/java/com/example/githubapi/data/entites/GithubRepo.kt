package com.example.githubapi.data.entites

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.githubapi.data.local.Converter
import java.io.Serializable

@Entity(tableName = "github_repo")
@TypeConverters(Converter::class)
data class GithubRepo(
    @PrimaryKey
    var id: Int,
    var name: String = "",
    var owner: Owner,
    var description: String = "",
    var language: String = "",
    var watchers: String = "",
    var score: Float = 0.0f,
) : Serializable {
    var isBookmark: Boolean = false
//
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readString() ?: "",
//        parcel.readParcelable(Owner::class.java.classLoader)!!,
//        parcel.readString() ?: "",
//        parcel.readString() ?: "",
//        parcel.readString() ?: "",
//        parcel.readFloat()
//    ) {
//        isBookmark = parcel.readByte() != 0.toByte()
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(id)
//        parcel.writeString(name)
//        parcel.writeParcelable(owner, flags)
//        parcel.writeString(description)
//        parcel.writeString(language)
//        parcel.writeString(watchers)
//        parcel.writeFloat(score)
//        parcel.writeByte(if (isBookmark) 1 else 0)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<GithubRepo> {
//        override fun createFromParcel(parcel: Parcel): GithubRepo {
//            return GithubRepo(parcel)
//        }
//
//        override fun newArray(size: Int): Array<GithubRepo?> {
//            return arrayOfNulls(size)
//        }
//    }


}
