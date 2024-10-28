package cccev.dsl.client.requirement

import cccev.dsl.model.Code
import cccev.dsl.model.informationRequirement

object Protocol {
    object Preparation: Code()
    object Validation: Code()
    object Certification: Code()
}


val ProtocolPreparation = informationRequirement {
    identifier = "ProtocolPreparation"
    name = "Protocol Preparation"
    type = Protocol.Preparation.toString()
}


val ProtocolValidation = informationRequirement {
    identifier = "ProtocolValidation"
    name = "Protocol Validation"
    type = Protocol.Validation.toString()
}

val ProtocolCertification = informationRequirement {
    identifier = "ProtocolCertification"
    name = "Protocol Certification"
    type = Protocol.Certification.toString()
}
