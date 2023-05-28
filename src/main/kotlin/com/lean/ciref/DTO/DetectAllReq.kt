package com.lean.ciref.DTO

class DetectAllReq(val name:String,val url:String, val branch:String) {
    constructor():this("","","")
}