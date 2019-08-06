package com.qegle.konturtestapp.repository

interface ValueProvider<T> {
	fun setValue(value: T)
	fun getValue(): T
}