package edu.metrostate.ics342.mediatracker.data

sealed class RegisterResult {
    data object Success      : RegisterResult()
    data object Conflict     : RegisterResult()
    data object NetworkError : RegisterResult()
    data object UnknownError : RegisterResult()
}