package cccev.dsl.client.requirement

import cccev.dsl.model.Code
import cccev.dsl.model.informationRequirement


object Methodology {
    object LocalConsultation: Code()
    object REDDPlus: Code()
    object VERRAVCS: Code()
}

val LocalConsultation = informationRequirement {
    identifier = "LocalConsultation"
    name = "Loc"
    type = Methodology.LocalConsultation.toString()
}

val REDDPlus = informationRequirement {
    identifier = "REDD+"
    name = "REDD+"
    description = "La description Ici"
    type = Methodology.REDDPlus.toString()

}

val VERRAVCS = informationRequirement {
    identifier = "VERRAVCS"
    name = "VERRA VCS"
    description = ""
    type = Methodology.VERRAVCS.toString()
}
