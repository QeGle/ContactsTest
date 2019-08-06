package com.qegle.konturtestapp.vm.model

sealed class State
object LoadingState : State()
object DoneState : State()
class ErrorState(var isShown: Boolean = false) : State()