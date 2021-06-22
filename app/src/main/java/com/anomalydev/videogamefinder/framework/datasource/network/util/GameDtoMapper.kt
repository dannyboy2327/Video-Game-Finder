package com.anomalydev.videogamefinder.framework.datasource.network.util

import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.domain.util.DateUtil
import com.anomalydev.videogamefinder.business.domain.util.DescriptionUtil
import com.anomalydev.videogamefinder.business.domain.util.DomainMapper
import com.anomalydev.videogamefinder.framework.datasource.network.model.GameDto

class GameDtoMapper: DomainMapper<GameDto, Game> {

    override fun mapToDomainModel(model: GameDto): Game {
        return Game(
            id = model.id,
            name = model.name,
            description = DescriptionUtil.removeTagFromDescriptionString(model.description?: ""),
            released = model.released?: "",
            updated = DateUtil.removeTimeFromDateString(model.updated),
            background_image = model.background_image?: "",
            website = model.website?: "",
            rating = model.rating,
            rating_top = model.rating_top,
            ratings = model.ratings,
            playtime = model.playtime?: 0,
            isFavorite = false,
        )
    }

    override fun mapFromDomainModel(domainModel: Game): GameDto {
        return GameDto(
            id = domainModel.id,
            name = domainModel.name,
            description = domainModel.description,
            released = domainModel.released,
            updated = DateUtil.removeTimeFromDateString(domainModel.updated),
            background_image = domainModel.background_image,
            website = domainModel.website,
            rating = domainModel.rating,
            rating_top = domainModel.rating_top,
            ratings = domainModel.ratings,
            playtime = domainModel.playtime,
        )
    }

    fun dtoToModelList(initial: List<GameDto>): List<Game> {
        return initial.map { mapToDomainModel(it) }
    }

    fun modelToDtoList(initial: List<Game>): List<GameDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}