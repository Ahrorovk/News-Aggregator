package com.example.newsaggregator.domain.use_case

import com.example.newsaggregator.core.Resource
import com.example.newsaggregator.data.rss.dto.RssDto
import com.example.newsaggregator.domain.RssFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRssUseCase @Inject constructor(
    private val repository: RssFeedRepository
) {
    operator fun invoke(): Flow<Resource<RssDto>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getRss()
            emit(Resource.Success(response))
        } catch (ex: HttpException) {
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error has occurred"))
        } catch (ex: IOException) {
            emit(Resource.Error("The server could not be contacted. Check your internet connection."))
        } catch (ex: Exception) {
            emit(Resource.Error("${ex.message}"))
        }
    }
}