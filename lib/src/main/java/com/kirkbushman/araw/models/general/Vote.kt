package com.kirkbushman.araw.models.general

/**
 * This enum defines the possible states of an item the logged in user has voted,
 * represented by the inteface Votable
 *
 * There are 3 states: UPVOTE, DOWNVOTE and the default NONE,
 *
 * @property dir the vote as seen by the Reddit Api,
 * 1 for UPVOTE,
 * -1 for DOWNVOTE and
 * 0 for NONE
 *
 */
enum class Vote(val dir: Int) {

    UPVOTE(1),
    DOWNVOTE(-1),
    NONE(0)
}
