package com.wmontgom85.contactlist.api

import com.squareup.moshi.JsonAdapter

data class APITask(
    val jsonAdapter: JsonAdapter<Any>,

    val successMessage : String? = null,

    val errorMessage : String? = null
)