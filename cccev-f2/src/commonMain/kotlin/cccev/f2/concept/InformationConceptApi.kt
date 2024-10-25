package cccev.f2.concept

import cccev.core.concept.command.InformationConceptCreateFunction
import cccev.core.concept.command.InformationConceptUpdateFunction
import cccev.f2.concept.query.InformationConceptGetByIdentifierFunction
import cccev.f2.concept.query.InformationConceptGetFunction

/**
 * @d2 api
 * @parent [cccev.core.concept.D2InformationConceptPage]
 */
interface InformationConceptApi: InformationConceptCommandApi, InformationConceptQueryApi

interface InformationConceptCommandApi {
    fun conceptCreate(): InformationConceptCreateFunction
    fun conceptUpdate(): InformationConceptUpdateFunction
}

interface InformationConceptQueryApi {
    fun conceptGet(): InformationConceptGetFunction
    fun conceptGetByIdentifier(): InformationConceptGetByIdentifierFunction
}
