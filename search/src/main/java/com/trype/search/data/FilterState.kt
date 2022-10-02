package com.trype.search.data

data class FilterState(val category: Set<String> = emptySet(),
val subcategory: Set<String> = emptySet(), val title: String = "")