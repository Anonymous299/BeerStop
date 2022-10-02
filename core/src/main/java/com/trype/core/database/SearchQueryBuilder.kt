package com.trype.core.database

import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import java.util.*
//TODO add ranking logic
fun buildQuery(category: Set<String> = emptySet(),
               subcategory: Set<String> = emptySet(),
title: String = ""): SimpleSQLiteQuery{
    var query = "SELECT * FROM alcohol" +
            " JOIN alcohol_fts ON alcohol.rowid = alcohol_fts.rowid" +
            ""
    val bindArgs: MutableList<Any> = emptyList<Any>().toMutableList()
    query += buildCategoryQuery(category)
    query += buildSubcategoryQuery(subcategory)
    query += buildTitleQuery(title)
    Log.d("SahilTest", "query: ${(SimpleSQLiteQuery(query)).sql}")
    return SimpleSQLiteQuery(query, bindArgs.toTypedArray())
}

fun buildCategoryQuery(category: Set<String>): String{
    if(category.isNotEmpty()){
        var categories = ""
        for(c in category){
            var tempCategory = "'$c'"
            if(categories != ""){
                tempCategory = ", $tempCategory"
            }
            categories += tempCategory
        }
        return " WHERE (alcohol.category IN ($categories))"
    }
    return ""
}

fun buildSubcategoryQuery(subcategory: Set<String>): String{
    if(subcategory.isNotEmpty()){
        var subcategories = ""
        for(s in subcategory){
            var tempSubcategory = "'$s'"
            if(subcategories != ""){
                tempSubcategory = ", $tempSubcategory"
            }
            subcategories += tempSubcategory
        }
        return " AND (alcohol.subcategory IN ($subcategories))"
    }
    return ""
}

fun buildTitleQuery(title: String): String{
//    var modifiedTitle = ""
//    val preprocessedTitle = title.filter { !it.isWhitespace() }
//    for(c in preprocessedTitle){
//        modifiedTitle += "$c*"
//    }
    return if(title.isNotEmpty()){
        " AND alcohol_fts MATCH '*$title*'"
    }
    else{
        ""
    }
}