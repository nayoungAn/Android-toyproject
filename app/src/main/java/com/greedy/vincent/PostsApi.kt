package com.greedy.vincent

import com.greedy.template.API.Item
import com.greedy.template.API.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsApi {


    @GET("B551011/GoCamping/basedList?serviceKey=HeF%2Fbl5S7APRtDPyFXBZW1zx4ByiA8JZqMnddbu42Me3grrFY%2FSxRHm0okJwTz%2BD%2FsaWL9G4clNbQvzoagGgXQ%3D%3D&numOfRows=10&pageNo=1&MobileOS=AND&MobileApp=Template&_type=json")
    suspend fun posts() : Response<ResponseData>

}