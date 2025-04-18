package cccev.f2.concept

import cccev.f2.concept.command.InformationConceptCreateFunction
import cccev.f2.concept.command.InformationConceptUpdateFunction
import cccev.f2.concept.query.InformationConceptGetByIdentifierFunction
import cccev.f2.concept.query.InformationConceptGetFunction

/**
 * @d2 api
 * @parent [cccev.dsl.model.d2.D2InformationConceptPage]
 */
interface InformationConceptApi: InformationConceptCommandApi,
    InformationConceptQueryApi

interface InformationConceptCommandApi {
    fun conceptCreate(): InformationConceptCreateFunction
    fun conceptUpdate(): InformationConceptUpdateFunction
}

interface InformationConceptQueryApi {
    fun conceptGet(): InformationConceptGetFunction
    fun conceptGetByIdentifier(): InformationConceptGetByIdentifierFunction
}
