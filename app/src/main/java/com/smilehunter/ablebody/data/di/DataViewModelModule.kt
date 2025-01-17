package com.smilehunter.ablebody.data.di

import com.smilehunter.ablebody.data.repository.BookmarkRepositoryImpl
import com.smilehunter.ablebody.data.repository.BrandRepositoryImpl
import com.smilehunter.ablebody.data.repository.CommentRepositoryImpl
import com.smilehunter.ablebody.data.repository.CreatorDetailRepositoryImpl
import com.smilehunter.ablebody.data.repository.FindCodyRepositoryImpl
import com.smilehunter.ablebody.data.repository.FindItemRepositoryImpl
import com.smilehunter.ablebody.data.repository.ItemRepositoryImpl
import com.smilehunter.ablebody.data.repository.LikeListRepositoryImpl
import com.smilehunter.ablebody.data.repository.ManageRepositoryImpl
import com.smilehunter.ablebody.data.repository.NotificationRepositoryImpl
import com.smilehunter.ablebody.data.repository.OnboardingRepositoryImpl
import com.smilehunter.ablebody.data.repository.OrderManagementRepositoryImpl
import com.smilehunter.ablebody.data.repository.SearchRepositoryImpl
import com.smilehunter.ablebody.data.repository.UserRepositoryImpl
import com.smilehunter.ablebody.domain.repository.BookmarkRepository
import com.smilehunter.ablebody.domain.repository.BrandRepository
import com.smilehunter.ablebody.domain.repository.CommentRepository
import com.smilehunter.ablebody.domain.repository.CreatorDetailRepository
import com.smilehunter.ablebody.domain.repository.FindCodyRepository
import com.smilehunter.ablebody.domain.repository.FindItemRepository
import com.smilehunter.ablebody.domain.repository.ItemRepository
import com.smilehunter.ablebody.domain.repository.LikeListRepository
import com.smilehunter.ablebody.domain.repository.ManageRepository
import com.smilehunter.ablebody.domain.repository.NotificationRepository
import com.smilehunter.ablebody.domain.repository.OnboardingRepository
import com.smilehunter.ablebody.domain.repository.OrderManagementRepository
import com.smilehunter.ablebody.domain.repository.SearchRepository
import com.smilehunter.ablebody.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataViewModelModule {

    @Binds
    fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun bindsOnboardingRepository(
        onboardingRepositoryImpl: OnboardingRepositoryImpl
    ): OnboardingRepository

    @Binds
    fun bindsBrandRepository(
        brandRepositoryImpl: BrandRepositoryImpl
    ): BrandRepository

    @Binds
    fun bindsBookmarkRepository(
        bookmarkRepositoryImpl: BookmarkRepositoryImpl
    ): BookmarkRepository

    @Binds
    fun bindsFindCodyRepository(
        findCodyRepositoryImpl: FindCodyRepositoryImpl
    ): FindCodyRepository

    @Binds
    fun bindsSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    fun bindsNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    fun bindsLikeListRepository(
        likeListRepositoryImpl: LikeListRepositoryImpl
    ): LikeListRepository

    @Binds
    fun bindsCreatorDetailRepository(
        creatorDetailRepositoryImpl: CreatorDetailRepositoryImpl
    ): CreatorDetailRepository

    @Binds
    fun bindsItemRepository(
        itemRepositoryImpl: ItemRepositoryImpl
    ): ItemRepository

    @Binds
    fun bindsCommentRepository(
        commentRepositoryImpl: CommentRepositoryImpl
    ): CommentRepository

    @Binds
    fun bindsOrderManagementRepository(
        orderManagementRepositoryImpl: OrderManagementRepositoryImpl
    ): OrderManagementRepository

    @Binds
    fun bindsManageRepository(
        manageRepositoryImpl: ManageRepositoryImpl
    ): ManageRepository

    @Binds
    fun bindsFindItemRepository(
        findItemRepository: FindItemRepositoryImpl
    ): FindItemRepository
}