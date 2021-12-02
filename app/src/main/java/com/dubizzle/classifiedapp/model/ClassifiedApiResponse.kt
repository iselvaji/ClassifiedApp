package com.dubizzle.classifiedapp.model

import com.google.gson.annotations.SerializedName

data class ClassifiedApiResponse(

    @SerializedName("results")
    val results : List<ClassifiedResults>,

    @SerializedName("pagination")
    val pagination : Pagination

)