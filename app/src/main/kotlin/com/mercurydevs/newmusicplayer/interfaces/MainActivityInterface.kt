package com.mercurydevs.newmusicplayer.interfaces

interface MainActivityInterface {
    fun getIsSearchOpen(): Boolean

    fun getIsThirdPartyIntent(): Boolean

    fun addFolderToPlaylist()
}
