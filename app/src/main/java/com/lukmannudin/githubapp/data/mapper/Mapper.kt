package com.lukmannudin.githubapp.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}