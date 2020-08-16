package com.mercurydevs.newmusicplayer.interfaces

interface SongListListener {
    fun refreshItems()

    fun toggleShuffle()

    fun toggleSongRepetition()
}
