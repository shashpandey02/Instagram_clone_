package com.example.instagram_clone.Models

class Reel {
    var reelUrl:String = ""
    var caption:String = ""
    var profile:String?= null
    constructor()

    constructor(reelUrl: String, caption: String, profile: String) {
        this.reelUrl = reelUrl
        this.caption = caption
        this.profile = profile
    }

    constructor(reelUrl: String, caption: String) {
        this.reelUrl = reelUrl
        this.caption = caption
    }


}