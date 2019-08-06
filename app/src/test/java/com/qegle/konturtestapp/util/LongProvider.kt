package com.qegle.konturtestapp.util

import com.qegle.konturtestapp.repository.ValueProvider

class LongProvider : ValueProvider<Long> {
	override fun setValue(value: Long) {}
	
	override fun getValue() = 0L
}