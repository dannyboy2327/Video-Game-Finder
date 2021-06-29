package com.anomalydev.videogamefinder.framework.datasource.network.util

import com.anomalydev.videogamefinder.business.domain.model.GameTrailers
import com.anomalydev.videogamefinder.business.domain.util.DomainMapper
import com.anomalydev.videogamefinder.framework.datasource.network.model.GameTrailersDto

class GameTrailerDtoMapper: DomainMapper<GameTrailersDto, GameTrailers> {

    override fun mapToDomainModel(model: GameTrailersDto): GameTrailers {
        return GameTrailers(
            id = model.id,
            name = model.name,
            preview = model.preview,
            trailer = model.trailer,
        )
    }

    override fun mapFromDomainModel(domainModel: GameTrailers): GameTrailersDto {
        return GameTrailersDto(
            id = domainModel.id,
            name = domainModel.name,
            preview = domainModel.preview,
            trailer = domainModel.trailer,
        )
    }

    fun dtoToModelList(initial: List<GameTrailersDto>): List<GameTrailers> {
        return initial.map { mapToDomainModel(it) }
    }

    fun modelToDtoList(initial: List<GameTrailers>): List<GameTrailersDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}