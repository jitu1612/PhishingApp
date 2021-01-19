package com.phishing.app

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PhishResPojo {

    @SerializedName("results")
    @Expose
    var results: Results? = null
}