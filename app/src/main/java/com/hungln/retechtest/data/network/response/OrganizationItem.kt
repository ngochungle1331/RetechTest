package com.hungln.retechtest.data.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "organizations")
data class OrganizationItem(
    @PrimaryKey(autoGenerate = true)
    var organizationId:Int = 0,
    var avatar_url: String? = null,
    var description: String? = null,
    var events_url: String? = null,
    var hooks_url: String? = null,
    var id: Int? = null,
    var issues_url: String? = null,
    var login: String? = null,
    var members_url: String? = null,
    var node_id: String? = null,
    var public_members_url: String? = null,
    var repos_url: String? = null,
    var url: String? = null
)