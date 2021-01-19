package com.phishing.app

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Results {
    @SerializedName("url")
    @Expose
    var url:String? = null
    @SerializedName("in_database")
    @Expose
    var inDatabase:Boolean? = null
    @SerializedName("phish_id")
    @Expose
    var phishId:String? = null
    @SerializedName("phish_detail_page")
    @Expose
    var phishDetailPage:String? = null
    @SerializedName("verified")
    @Expose
    var verified:String? = null
    @SerializedName("verified_at")
    @Expose
    var verifiedAt:String? = null
    @SerializedName("valid")
    @Expose
    var valid:String? = null
}