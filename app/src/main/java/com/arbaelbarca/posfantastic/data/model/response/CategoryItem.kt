package com.arbaelbarca.posfantastic.data.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryItem(
    var id: Long,
    var name: String
): Parcelable
