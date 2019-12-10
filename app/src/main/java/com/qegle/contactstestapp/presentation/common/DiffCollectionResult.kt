package com.qegle.contactstestapp.presentation.common

import androidx.recyclerview.widget.DiffUtil

data class DiffCollectionResult<T>(
    val diff: DiffUtil.DiffResult,
    val collection: List<T>
)