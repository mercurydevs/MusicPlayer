package com.mercurydevs.newmusicplayer.adapters

import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.mercurydevs.commons.adapters.MyRecyclerViewAdapter
import com.mercurydevs.commons.extensions.*
import com.mercurydevs.commons.helpers.ensureBackgroundThread
import com.mercurydevs.commons.models.FileDirItem
import com.mercurydevs.commons.views.MyRecyclerView
import com.mercurydevs.newmusicplayer.R
import com.mercurydevs.newmusicplayer.activities.SimpleActivity
import com.mercurydevs.newmusicplayer.dialogs.NewPlaylistDialog
import com.mercurydevs.newmusicplayer.dialogs.RemovePlaylistDialog
import com.mercurydevs.newmusicplayer.extensions.config
import com.mercurydevs.newmusicplayer.extensions.deletePlaylists
import com.mercurydevs.newmusicplayer.extensions.getPlaylistSongs
import com.mercurydevs.newmusicplayer.extensions.playlistChanged
import com.mercurydevs.newmusicplayer.helpers.ALL_SONGS_PLAYLIST_ID
import com.mercurydevs.newmusicplayer.interfaces.RefreshPlaylistsListener
import com.mercurydevs.newmusicplayer.models.Playlist
import kotlinx.android.synthetic.main.item_playlist.view.*
import java.util.*

class PlaylistsAdapter(activity: SimpleActivity, val playlists: ArrayList<Playlist>, val listener: RefreshPlaylistsListener?, recyclerView: MyRecyclerView,
                       itemClick: (Any) -> Unit) : MyRecyclerViewAdapter(activity, recyclerView, null, itemClick) {

    init {
        setupDragListener(true)
    }

    override fun getActionMenuId() = R.menu.cab_playlists

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createViewHolder(R.layout.item_playlist, parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val playlist = playlists.getOrNull(position) ?: return
        holder.bindView(playlist, true, true) { itemView, layoutPosition ->
            setupView(itemView, playlist)
        }
        bindViewHolder(holder)
    }

    override fun getItemCount() = playlists.size

    override fun prepareActionMode(menu: Menu) {
        menu.apply {
            findItem(R.id.cab_rename).isVisible = isOneItemSelected()
        }
    }

    override fun actionItemPressed(id: Int) {
        when (id) {
            R.id.cab_delete -> askConfirmDelete()
            R.id.cab_rename -> showRenameDialog()
        }
    }

    override fun getSelectableItemCount() = playlists.size

    override fun getIsItemSelectable(position: Int) = true

    override fun getItemSelectionKey(position: Int) = playlists.getOrNull(position)?.id

    override fun getItemKeyPosition(key: Int) = playlists.indexOfFirst { it.id == key }

    override fun onActionModeCreated() {}

    override fun onActionModeDestroyed() {}

    private fun askConfirmDelete() {
        RemovePlaylistDialog(activity) {
            val ids = selectedKeys.map { it } as ArrayList<Int>
            if (it) {
                ensureBackgroundThread {
                    deletePlaylistSongs(ids) {
                        removePlaylists(ids)
                    }
                }
            } else {
                removePlaylists(ids)
            }
        }
    }

    private fun deletePlaylistSongs(ids: ArrayList<Int>, callback: () -> Unit) {
        var cnt = ids.size
        ids.map {
            val paths = activity.getPlaylistSongs(it).map { it.path }
            val fileDirItems = paths.map { FileDirItem(it, it.getFilenameFromPath()) } as ArrayList<FileDirItem>
            activity.deleteFiles(fileDirItems) {
                if (--cnt <= 0) {
                    callback()
                }
            }
        }
    }

    private fun removePlaylists(ids: ArrayList<Int>) {
        for (key in selectedKeys) {
            val playlist = getItemWithKey(key) ?: continue
            if (playlist.id == ALL_SONGS_PLAYLIST_ID) {
                activity.toast(R.string.all_songs_cannot_be_deleted)
                selectedKeys.remove(ALL_SONGS_PLAYLIST_ID)
                toggleItemSelection(false, getItemKeyPosition(ALL_SONGS_PLAYLIST_ID))
                break
            } else if (playlist.id == activity.config.currentPlaylist) {
                activity.playlistChanged(ALL_SONGS_PLAYLIST_ID)
            }
        }

        val playlistsToDelete = ArrayList<Playlist>(selectedKeys.size)
        val positions = ArrayList<Int>()
        for (key in selectedKeys) {
            val playlist = getItemWithKey(key) ?: continue
            val position = playlists.indexOfFirst { it.id == key }
            if (position != -1) {
                positions.add(position + positionOffset)
            }
            playlistsToDelete.add(playlist)
        }

        playlists.removeAll(playlistsToDelete)

        ensureBackgroundThread {
            val isDeletingCurrentPlaylist = ids.contains(activity.config.currentPlaylist)
            activity.deletePlaylists(playlistsToDelete)
            activity.runOnUiThread {
                if (isDeletingCurrentPlaylist) {
                    reloadList()
                } else {
                    removeSelectedItems(positions)
                }
            }
        }
    }

    private fun getItemWithKey(key: Int): Playlist? = playlists.firstOrNull { it.id == key }

    private fun showRenameDialog() {
        NewPlaylistDialog(activity, playlists[getItemKeyPosition(selectedKeys.first())]) {
            activity.runOnUiThread {
                reloadList()
            }
        }
    }

    private fun reloadList() {
        finishActMode()
        listener?.refreshItems()
    }

    private fun setupView(view: View, playlist: Playlist) {
        view.apply {
            playlist_frame?.isSelected = selectedKeys.contains(playlist.id)
            playlist_title.text = playlist.title
            playlist_title.setTextColor(textColor)
            playlist_icon.applyColorFilter(textColor)
            playlist_icon.beInvisibleIf(playlist.id != context.config.currentPlaylist)
        }
    }
}
