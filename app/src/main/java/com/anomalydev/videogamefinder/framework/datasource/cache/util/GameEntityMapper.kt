package com.anomalydev.videogamefinder.framework.datasource.cache.util

import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.domain.util.DateUtil
import com.anomalydev.videogamefinder.business.domain.util.DomainMapper
import com.anomalydev.videogamefinder.framework.datasource.cache.model.GameEntity

class GameEntityMapper: DomainMapper<GameEntity, Game> {

    /**
     *  Will return a Game object from the entity
     */
    override fun mapToDomainModel(model: GameEntity): Game {
        return Game(
            id = model.id,
            name = model.name,
            description = model.description,
            released = model.released,
            updated = model.updated,
            background_image = model.background_image,
            website = model.website,
            rating = model.rating,
            rating_top = model.rating_top,
            playtime = model.playtime,
        )
    }

    /**
     *  Will return a GameEntity object from the model
     */
    override fun mapFromDomainModel(domainModel: Game): GameEntity {
        return GameEntity(
            id = domainModel.id,
            name = domainModel.name,
            description = domainModel.description,
            released = domainModel.released,
            updated = domainModel.updated,
            background_image = domainModel.background_image,
            website = domainModel.website,
            rating = domainModel.rating,
            rating_top = domainModel.rating_top,
            playtime = domainModel.playtime,
        )
    }

    // Transforms each entity from list to Game object
    fun toModelList(initial: List<GameEntity>): List<Game> {
        return initial.map { mapToDomainModel(it) }
    }

    // Transforms each Game from list to GameEntity object
    fun toEntityList(initial: List<Game>): List<GameEntity> {
        return initial.map {  mapFromDomainModel(it) }
    }
}