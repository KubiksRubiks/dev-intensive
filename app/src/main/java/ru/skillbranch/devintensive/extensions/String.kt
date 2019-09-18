package ru.skillbranch.devintensive.extensions

fun String.truncate(value:Int = 16): String{
    val appendix = "..."

    if(this.length>value){
        var str = dropLast(this.length-value)
        return (str+appendix)
    } else {
        return this
    }

}