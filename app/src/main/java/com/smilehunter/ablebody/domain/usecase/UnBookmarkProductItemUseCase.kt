package com.smilehunter.ablebody.domain.usecase

import com.smilehunter.ablebody.domain.repository.BookmarkRepository
import javax.inject.Inject

class UnBookmarkProductItemUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    suspend operator fun invoke(itemId: Long) {
        bookmarkRepository.deleteBookmarkItem(itemId)
    }
}