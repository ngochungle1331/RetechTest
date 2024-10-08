package com.hungln.retechtest.data.network.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "repositories")
data class RepositoryItem(
    @PrimaryKey(autoGenerate = true)
    var repoId:Int = 0,
    var allow_forking: Boolean? = null,
    var archive_url: String? = null,
    var archived: Boolean? = null,
    var assignees_url: String? = null,
    var blobs_url: String? = null,
    var branches_url: String? = null,
    var clone_url: String? = null,
    var collaborators_url: String? = null,
    var comments_url: String? = null,
    var commits_url: String? = null,
    var compare_url: String? = null,
    var contents_url: String? = null,
    var contributors_url: String? = null,
    var created_at: String? = null,
    var default_branch: String? = null,
    var deployments_url: String? = null,
    var description: String? = null,
    var disabled: Boolean? = null,
    var downloads_url: String? = null,
    var events_url: String? = null,
    var fork: Boolean? = null,
    var forks: Int? = null,
    var forks_count: Int? = null,
    var forks_url: String? = null,
    var full_name: String? = null,
    var git_commits_url: String? = null,
    var git_refs_url: String? = null,
    var git_tags_url: String? = null,
    var git_url: String? = null,
    var has_discussions: Boolean? = null,
    var has_downloads: Boolean? = null,
    var has_issues: Boolean? = null,
    var has_pages: Boolean? = null,
    var has_projects: Boolean? = null,
    var has_wiki: Boolean? = null,
    var homepage: String? = null,
    var hooks_url: String? = null,
    var html_url: String? = null,
    var id: Int? = null,
    var issue_comment_url: String? = null,
    var issue_events_url: String? = null,
    var issues_url: String? = null,
    var keys_url: String? = null,
    var labels_url: String? = null,
    var language: String? = null,
    var languages_url: String? = null,
    var merges_url: String? = null,
    var milestones_url: String? = null,
    var name: String? = null,
    var node_id: String? = null,
    var notifications_url: String? = null,
    var open_issues: Int? = null,
    var open_issues_count: Int? = null,
    var owner: Owner? = null,
    var `private`: Boolean? = null,
    var pulls_url: String? = null,
    var pushed_at: String? = null,
    var releases_url: String? = null,
    var size: Int? = null,
    var ssh_url: String? = null,
    var stargazers_count: Int? = null,
    var stargazers_url: String? = null,
    var statuses_url: String? = null,
    var subscribers_url: String? = null,
    var subscription_url: String? = null,
    var svn_url: String? = null,
    var tags_url: String? = null,
    var teams_url: String? = null,
    var trees_url: String? = null,
    var updated_at: String? = null,
    var url: String? = null,
    var visibility: String? = null,
    var watchers: Int? = null,
    var watchers_count: Int? = null,
    var web_commit_signoff_required: Boolean? = null,
    var owner_id: Int? = null
)

@Entity(tableName = "owners")
data class Owner(
    @PrimaryKey(autoGenerate = true)
    var ownerId: Int = 0,
    var avatar_url: String? = null,
    var events_url: String? = null,
    var followers_url: String? = null,
    var following_url: String? = null,
    var gists_url: String? = null,
    var gravatar_id: String? = null,
    var html_url: String? = null,
    var id: Int? = null,
    var login: String? = null,
    var node_id: String? = null,
    var organizations_url: String? = null,
    var received_events_url: String? = null,
    var repos_url: String? = null,
    var site_admin: Boolean? = null,
    var starred_url: String? = null,
    var subscriptions_url: String? = null,
    var type: String? = null,
    var url: String? = null
)

data class RepositoryWithOwner(
    @Embedded
    val repository: RepositoryItem,

    @Relation(
        parentColumn = "owner_id",
        entityColumn = "ownerId"
    )
    val owner: Owner
)


