package com.lean.ciref.DTO

class DetectCommitReq(val name:String, val url:String, val commit:String) {
    constructor():this("","","")
}