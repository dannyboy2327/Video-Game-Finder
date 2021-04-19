package com.anomalydev.videogamefinder.framework.datasource.network.util

import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.domain.util.DateUtil
import com.anomalydev.videogamefinder.business.domain.util.DomainMapper
import com.anomalydev.videogamefinder.framework.datasource.network.model.GameDto

class GameDtoMapper: DomainMapper<GameDto, Game> {

    override fun mapToDomainModel(model: GameDto): Game {
        return Game(
            id = model.id,
            name = model.name,
            released = model.released?: "Unknown",
            imageUrl = model.image?: "",
            rating = model.rating,
            rating_top = model.ratingTop,
            updated = DateUtil.removeTimeFromDateString(model.updated)
        )
    }

    override fun mapFromDomainModel(domainModel: Game): GameDto {
        return GameDto(
            id = domainModel.id,
            name = domainModel.name,
            released = domainModel.released,
            image = domainModel.imageUrl,
            rating = domainModel.rating,
            ratingTop = domainModel.rating_top,
            updated = domainModel.updated
        )
    }

    fun dtoToModelList(initial: List<GameDto>): List<Game> {
        return initial.map { mapToDomainModel(it) }
    }

    fun modelToDtoList(initial: List<Game>): List<GameDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}