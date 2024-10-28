package cccev.dsl.model.axess

import cccev.dsl.model.Code
import cccev.dsl.model.informationRequirement

object ProtocolStep {
    object Preparation: Code()
    object Validation: Code()
    object Certification: Code()
}


val ProtocolPreparation = informationRequirement {
    identifier = "ProtocolPreparation"
    name = "Protocol Preparation"
    type = ProtocolStep.Preparation.toString()
}


val ProtocolValidation = informationRequirement {
    identifier = "ProtocolValidation"
    name = "Protocol Validation"
    type = ProtocolStep.Validation.toString()
}

val ProtocolCertification = informationRequirement {
    identifier = "ProtocolCertification"
    name = "Protocol Certification"
    type = ProtocolStep.Certification.toString()
}
