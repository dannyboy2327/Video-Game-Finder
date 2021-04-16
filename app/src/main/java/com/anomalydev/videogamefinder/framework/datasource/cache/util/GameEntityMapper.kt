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
            released = model.released,
            imageUrl = model.imageUrl,
            rating = model.rating,
            rating_top = model.rating_top,
            updated = model.updated
        )
    }

    /**
     *  Will return a GameEntity object from the model
     */
    override fun mapFromDomainModel(domainModel: Game): GameEntity {
        return GameEntity(
            id = domainModel.id,
            name = domainModel.name,
            released = domainModel.released,
            imageUrl = domainModel.imageUrl,
            rating = domainModel.rating,
            rating_top = domainModel.rating_top,
            updated = domainModel.updated
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