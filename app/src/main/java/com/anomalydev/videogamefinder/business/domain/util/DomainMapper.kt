package com.anomalydev.videogamefinder.business.domain.util

/**
 *  Interface used for different mappers such as cache/network
 */
interface DomainMapper <T, DomainModel> {

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T
}