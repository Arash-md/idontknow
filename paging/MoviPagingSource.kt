package com.example.pagination.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagination.Api.movie_responses
import com.example.pagination.Repository.Repository
import javax.inject.Inject

class MoviPagingSource @Inject constructor(private val Repository:Repository):PagingSource<Int,movie_responses.Data>() {
    override fun getRefreshKey(state: PagingState<Int, movie_responses.Data>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, movie_responses.Data> {
        return try {
            val currentpage=params.key ?: 1
            val responses=Repository.getmovielist(currentpage)
            val data=responses.body()?.data ?: emptyList()
            val responsesdata= mutableListOf<movie_responses.Data>()
            responsesdata.addAll(data)

            LoadResult.Page(
                data=responsesdata,
                prevKey = if(currentpage == 1) null else -1,
                nextKey = currentpage.plus(1)
            )
        }
        catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}