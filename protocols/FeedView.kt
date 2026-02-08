package com.escargot.android.ui.feed

/**
 * FeedView: Le fil d'actualité de la fin du monde.
 */
class FeedView {

    fun onPostButtonClicked(content: String, channel: String) {
        if (content.length > 140) {
            showError("Trop long ! Les zombies arrivent, sois bref.")
            return
        }

        // Envoie au service Mesh (qui parle à l'ESP via Bluetooth)
        MeshService.sendSocialPost(
            content = content,
            channel = channel,
            onSuccess = {
                showToast("Post envoyé + Mining lancé sur l'Arduino !")
            }
        )
    }

    fun onLikeClicked(postId: String) {
        // Un like = une micro-transaction DUCO
        // Encourage les bonnes infos (ex: "Eau potable ici")
        MeshService.sendReaction(
            type = "LIKE",
            target = postId
        )
    }
}