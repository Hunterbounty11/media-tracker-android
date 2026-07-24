package edu.metrostate.ics342.mediatracker.data.network

import kotlinx.serialization.Serializable

@Serializable
data class LibraryRequest(val mediaId: Int, val status:String){


}