package com.hungln.retechtest.data.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    var uid:Int = 0,
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,
    var bio: String? = null,
    var blog: String? = null,
    var company: String? = null,
    @SerializedName("created_at")
    var createdAt: String? = null,
    var email: String? = null,
    @SerializedName("events_url")
    var eventsUrl: String? = null,
    var followers: Int? = null,
    @SerializedName("followers_url")
    var followersUrl: String? = null,
    var following: Int? = null,
    @SerializedName("following_url")
    var followingUrl: String? = null,
    @SerializedName("gists_url")
    var gistsUrl: String? = null,
    @SerializedName("gravatar_id")
    var gravatarId: String? = null,
    var hireable: String? = null,
    @SerializedName("html_url")
    var htmlUrl: String? = null,
    var id: Int? = null,
    var location: String? = null,
    var login: String? = null,
    var name: String? = null,
    @SerializedName("node_id")
    var nodeId: String? = null,
    @SerializedName("organizations_url")
    var organizationsUrl: String? = null,
    @SerializedName("public_gists")
    var publicGists: Int? = null,
    @SerializedName("public_repos")
    var publicRepos: Int? = null,
    @SerializedName("received_events_url")
    var receivedEventsUrl: String? = null,
    @SerializedName("repos_url")
    var reposUrl: String? = null,
    @SerializedName("site_admin")
    var siteAdmin: Boolean? = null,
    @SerializedName("starred_url")
    var starredUrl: String? = null,
    @SerializedName("subscriptions_url")
    var subscriptionsUrl: String? = null,
    @SerializedName("twitter_username")
    var twitterUsername: String? = null,
    var type: String? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    var url: String? = null
)