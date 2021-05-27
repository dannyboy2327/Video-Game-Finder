package com.anomalydev.videogamefinder.framework.presentation.ui.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.anomalydev.videogamefinder.framework.presentation.ui.components.dialog.GenericDialogInfo
import com.anomalydev.videogamefinder.framework.presentation.ui.components.dialog.PositiveAction
import java.util.*

class DialogQueue {

    val queue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())

    private fun removeHeadMessage() {
        if (queue.value.isNotEmpty()) {
            val update = queue.value
            update.remove() // Remove first oldest queue
            queue.value = ArrayDeque() // force recompose (bug?)
            queue.value = update
        }
    }

    fun appendErrorMessage(title: String, description: String) {
        GenericDialogInfo.Builder()
            .title(title)
            .onDismiss(this::removeHeadMessage)
            .description(description)
            .positiveAction(
                PositiveAction(
                    positiveBtnTxt = "OK",
                    onPositiveAction = this::removeHeadMessage
                )
            )
            .build()
    }
}