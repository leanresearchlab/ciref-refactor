package com.lean.ciref.entities

import gr.uom.java.xmi.LocationInfo.CodeElementType

class RefactLocation(val filePath:String,val startLine:Int,val endLine:Int,val startColumn:Int,val endColumn:Int,val codeElementType: CodeElementType,val description:String,val codeElement:String ) {
}