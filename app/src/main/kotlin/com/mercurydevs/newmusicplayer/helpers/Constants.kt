package com.mercurydevs.newmusicplayer.helpers

const val SONG_POS = "song_position"
const val PROGRESS = "progress"
const val CALL_SETUP_AFTER = "call_setup_after"
const val SONG_IDS = "song_ids"
const val EDITED_SONG = "edited_song"
const val ALL_SONGS_PLAYLIST_ID = 1
const val START_SLEEP_TIMER = "start_sleep_timer"
const val STOP_SLEEP_TIMER = "stop_sleep_timer"

private const val PATH = "com.mercurydevs.musicplayer.action."

const val INIT = PATH + "INIT"
const val INIT_PATH = PATH + "INIT_PATH"
const val SETUP = PATH + "SETUP"
const val FINISH = PATH + "FINISH"
const val FINISH_IF_NOT_PLAYING = PATH + "FINISH_IF_NOT_PLAYING"
const val PREVIOUS = PATH + "PREVIOUS"
const val PAUSE = PATH + "PAUSE"
const val PLAYPAUSE = PATH + "PLAYPAUSE"
const val NEXT = PATH + "NEXT"
const val RESET = PATH + "RESET"
const val EDIT = PATH + "EDIT"
const val PLAYPOS = PATH + "PLAYPOS"
const val REFRESH_LIST = PATH + "REFRESH_LIST"
const val SET_PROGRESS = PATH + "SET_PROGRESS"
const val SET_EQUALIZER = PATH + "SET_EQUALIZER"
const val SKIP_BACKWARD = PATH + "SKIP_BACKWARD"
const val SKIP_FORWARD = PATH + "SKIP_FORWARD"
const val REMOVE_CURRENT_SONG = PATH + "REMOVE_CURRENT_SONG"
const val REMOVE_SONG_IDS = PATH + "REMOVE_SONG_IDS"
const val BROADCAST_STATUS = PATH + "BROADCAST_STATUS"
const val NOTIFICATION_DISMISSED = PATH + "NOTIFICATION_DISMISSED"

const val NEW_SONG = "NEW_SONG"
const val IS_PLAYING = "IS_PLAYING"
const val SONG_CHANGED = "SONG_CHANGED"
const val SONG_STATE_CHANGED = "SONG_STATE_CHANGED"

// shared preferences
const val SHUFFLE = "shuffle"
const val EQUALIZER = "equalizer"
const val REPEAT_SONG = "repeat_song"
const val AUTOPLAY = "autoplay"
const val IGNORED_PATHS = "ignored_paths"
const val CURRENT_PLAYLIST = "current_playlist"
const val SHOW_FILENAME = "show_filename"
const val SHOW_ALBUM_COVER = "show_album_cover"
const val SWAP_PREV_NEXT = "swap_prev_next"
const val LAST_SLEEP_TIMER_SECONDS = "last_sleep_timer_seconds"
const val SLEEP_IN_TS = "sleep_in_ts"

const val LIST_HEADERS_COUNT = 2
const val LOWER_ALPHA = 0.5f

const val SHOW_FILENAME_NEVER = 1
const val SHOW_FILENAME_IF_UNAVAILABLE = 2
const val SHOW_FILENAME_ALWAYS = 3
