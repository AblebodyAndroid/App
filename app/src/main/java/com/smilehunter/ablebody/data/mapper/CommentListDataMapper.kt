package com.smilehunter.ablebody.data.mapper

import com.smilehunter.ablebody.domain.model.CommentListData
import com.smilehunter.ablebody.network.model.response.CreatorDetailResponseData

fun CreatorDetailResponseData.CommentOrReply.toDomain(uid: String) =
    CommentListData(
        type = when (type) {
            CreatorDetailResponseData.CommentOrReply.CommentReplyType.COMMENT ->
                CommentListData.CommentType.COMMENT

            CreatorDetailResponseData.CommentOrReply.CommentReplyType.REPLY ->
                CommentListData.CommentType.REPLY
        },
        createDate = createDate,
        modifiedDate = modifiedDate,
        id = id,
        writer = CommentListData.User(
            uid = writer.uid,
            nickname = writer.nickname,
            name = writer.name,
            profileUrl = writer.profileUrl
        ),
        contents = contents,
        likeCount = likes,
        parentID = parentId,
        isLiked = likeUsers.find { it.uid == uid } != null
    )