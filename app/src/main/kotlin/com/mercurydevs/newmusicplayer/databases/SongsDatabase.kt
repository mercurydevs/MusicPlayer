package com.mercurydevs.newmusicplayer.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mercurydevs.newmusicplayer.R
import com.mercurydevs.newmusicplayer.extensions.playlistDAO
import com.mercurydevs.newmusicplayer.helpers.ALL_SONGS_PLAYLIST_ID
import com.mercurydevs.newmusicplayer.interfaces.PlaylistsDao
import com.mercurydevs.newmusicplayer.interfaces.SongsDao
import com.mercurydevs.newmusicplayer.models.Playlist
import com.mercurydevs.newmusicplayer.models.Song
import com.mercurydevs.newmusicplayer.objects.MyExecutor
import java.util.concurrent.Executors

@Database(entities = [(Song::class), (Playlist::class)], version = 2)
abstract class SongsDatabase : RoomDatabase() {

    abstract fun SongsDao(): SongsDao

    abstract fun PlaylistsDao(): PlaylistsDao

    companion object {
        private var db: SongsDatabase? = null

        fun getInstance(context: Context): SongsDatabase {
            if (db == null) {
                synchronized(SongsDatabase::class) {
                    if (db == null) {
                        db = Room.databaseBuilder(context.applicationContext, SongsDatabase::class.java, "songs.db")
                            .setQueryExecutor(MyExecutor.myExecutor)
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    Executors.newSingleThreadExecutor().execute {
                                        addInitialPlaylist(context)
                                    }
                                }
                            })
                            .addMigrations(MIGRATION_1_2)
                            .build()
                    }
                }
            }
            return db!!
        }

        fun destroyInstance() {
            db = null
        }

        private fun addInitialPlaylist(context: Context) {
            val allSongs = context.resources.getString(R.string.all_songs)
            val playlist = Playlist(ALL_SONGS_PLAYLIST_ID, allSongs)
            context.playlistDAO.insert(playlist)
        }

        // removing the "type" value of Song
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.apply {
                    execSQL("CREATE TABLE songs_new (media_store_id INTEGER NOT NULL, title TEXT NOT NULL, artist TEXT NOT NULL, path TEXT NOT NULL, duration INTEGER NOT NULL, " +
                            "album TEXT NOT NULL, playlist_id INTEGER NOT NULL, PRIMARY KEY(path, playlist_id))")

                    execSQL("INSERT INTO songs_new (media_store_id, title, artist, path, duration, album, playlist_id) " +
                            "SELECT media_store_id, title, artist, path, duration, album, playlist_id FROM songs")

                    execSQL("DROP TABLE songs")
                    execSQL("ALTER TABLE songs_new RENAME TO songs")

                    execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_playlists_id` ON `playlists` (`id`)")
                }
            }
        }
    }
}
