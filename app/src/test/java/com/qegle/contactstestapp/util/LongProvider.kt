package com.qegle.contactstestapp.util

class LongProvider : ValueProvider<Long> {
	override fun setValue(value: Long) {}
	
	override fun getValue() = 0L
}