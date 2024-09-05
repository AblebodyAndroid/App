package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkProductItemUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    suspend operator fun invoke(itemId: Long) {
        bookmarkRepository.addBookmarkItem(itemId)
    }
}