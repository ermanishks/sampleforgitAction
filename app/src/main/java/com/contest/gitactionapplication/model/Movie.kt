package com.contest.gitactionapplication.model

import com.contest.gitactionapplication.model.Actor

/**
 * Created by Manish Kumar
 */
data class Movie(val id:String,
                 val title:String,
                 val year:String,
                 val length:String,
                 val rating:String,
                 val rating_votes:String,
                 val poster:String,
                 val plot:String,
                 val cast:List<Actor>) {

    // need to add technical aspects
}