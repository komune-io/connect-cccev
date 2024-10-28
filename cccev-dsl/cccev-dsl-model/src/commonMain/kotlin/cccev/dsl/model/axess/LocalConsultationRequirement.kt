package cccev.dsl.model.axess

import cccev.dsl.model.builder.InformationRequirementBuilder
import cccev.dsl.model.informationRequirement

fun localConsultationProtocolPreparation(init: InformationRequirementBuilder.() -> Unit) =
    InformationRequirementBuilder().apply {
        type = ProtocolStep.Preparation.toString()
        isRequirementOf {
            +ProtocolPreparation
            +LocalConsultation
        }
    }.apply(init).build()

val LocalConsultationProtocolPreparationRequirement = informationRequirement {

    hasRequirement {
        +localConsultationProtocolPreparation {
            identifier = "D100"
            name = "TermesDeReference"
        }
        +localConsultationProtocolPreparation {
            identifier = "D101"
            name = "Termes de référence (TDR)"
        }
        +localConsultationProtocolPreparation {
            identifier = "D102"
            name = "Présentation de la composante des communautés locales"
        }
        +localConsultationProtocolPreparation {
            identifier = "D103"
            name = "PV des délégués communautaires"
        }
        +localConsultationProtocolPreparation {
            identifier = "D104"
            name = "Liste des projets communautaires de la concession"
        }
        +localConsultationProtocolPreparation {
            identifier = "D105"
            name = "Budget du Fonds de Développement Local (FDL) des communautés de la concession forestière"
        }
        +localConsultationProtocolPreparation {
            identifier = "D106"
            name = "PV de négociation d'avenant"
        }
        +localConsultationProtocolPreparation {
            identifier = "D107"
            name = "Guide pour l'établissement des modalités de gestion du FDL"
        }
        +localConsultationProtocolPreparation {
            identifier = "D108"
            name = "PV d'installation des membres du CLG"
        }
        +localConsultationProtocolPreparation {
            identifier = "D109"
            name = "Modèle de reglement d'ordre intérieur du CLG"
        }
        +localConsultationProtocolPreparation {
            identifier = "D110"
            name = "PV de collaboration (par groupement ethnique)"
        }
        +localConsultationProtocolPreparation {
            identifier = "D111"
            name = "Coordonnées de localisation du périmètre et de la superficie de l'investissement REDD+ (Shapefile)"
        }
        +localConsultationProtocolPreparation {
            identifier = "D112"
            name = "CLIP"
        }
    }
}
