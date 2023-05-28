package com.lean.ciref.entities

import java.util.UUID


class Refact(val id:String,val commitId:String, val type:String,val description:String,val leftSideLocations: Array<RefactLocation>, val rightSideLocations: Array<RefactLocation>){
}