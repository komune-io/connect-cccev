# Core Criterion and Core Evidence Vocabulary

StatusSemic RecommendationPublished at2022-01-20This version[https://semiceu.github.io/CCCEV/releases/2.00](https://semiceu.github.io/CCCEV/releases/2.00)

AuthorsCore Vocabularies Working GroupEditorsBarthelemy,
Florian

\- PwC EU ServicesDe Keyzer,
Michiel

\- PwC EU ServicesFragkou,
Pavlina

\- European CommissionSchepers,
Dimitri

\- PwC EU ServicesStani,
Emidio

\- PwC EU ServicesVan Nuffelen,
Bert

\- TenForceReviewed and approved byvan Hooland,
Seth

\- European Commission

Source[https://github.com/SEMICeu/CCCEV/tree/master/releases/2.00](https://github.com/SEMICeu/CCCEV/tree/master/releases/2.00)Feedback[https://github.com/SEMICeu/CCCEV/issues](https://github.com/SEMICeu/CCCEV/issues)

## Summary

This document describes the **Core Criterion and Core Evidence Vocabulary (CCCEV)**.
Similarly to all the Core Vocabularies, CCCEV is " [a context-neutral data model that captures the fundamental characteristics of an entity](https://joinup.ec.europa.eu/sites/default/files/inline-files/ISA%20Handbook%20for%20using%20Core%20Vocabularies.pdf)".
A Core Vocabulary specifies a semantic data model covering a set of use cases across domains.
The specification consists of terms with a minimal set of constraints (recommended codelists, usage guidelines, etc.).

The Core Criterion and Core Evidence Vocabulary is designed to support the exchange of information between organisations or persons (more generally Agents) defining Requirements and organisations or persons responding to these Requirements by means of structured or unstructured Evidences.

CCCEV contains two basic and complementary core concepts:


- the Requirement, a broad notion encompassing all forms of requests for information, that is often, but not necessarily, made with the objective to use it as a basis for making a judgement or decision; and

- the Evidence, the data proving or disproving that a specific Requirement is met by someone or something, and thus has been fulfilled.


Using these basic core concepts, CCCEV provides a generic setting to define Criteria, i.e. Requirements with an assessment or evaluation objective in mind. This is a key motivation for the existence of CCCEV.

Although CCCEV shapes a general framework around these core concepts, implementers have to make decisions on how the framework is actually used by further elaborating the Core Vocabulary to make it applicable in their specific context.

An explanatory example that helps the reader to better understand this specification can be found [here](https://semiceu.github.io/CCCEV/releases/2.00/html/CCCEV_AnExplanatoryExample.pdf).

## Status of this document

This Core Vocabulary has the status of Semic Recommendation published at 2022-01-20.


## Overview

This document describes the usage of the following entities for a correct usage of the Core Vocabulary:


\|
[Agent](https://semiceu.github.io/CCCEV/releases/2.00/#Agent) \|
[Constraint](https://semiceu.github.io/CCCEV/releases/2.00/#Constraint) \|
[Criterion](https://semiceu.github.io/CCCEV/releases/2.00/#Criterion) \|
[Dataset](https://semiceu.github.io/CCCEV/releases/2.00/#Dataset) \|
[Evidence](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence) \|
[Evidence Type](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type) \|
[Evidence Type List](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%20List) \|
[Information Concept](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Concept) \|
[Information Requirement](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Requirement) \|
[Location](https://semiceu.github.io/CCCEV/releases/2.00/#Location) \|
[Period of Time](https://semiceu.github.io/CCCEV/releases/2.00/#Period%20of%20Time) \|
[Reference Framework](https://semiceu.github.io/CCCEV/releases/2.00/#Reference%20Framework) \|
[Requirement](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement) \|
[Supported Value](https://semiceu.github.io/CCCEV/releases/2.00/#Supported%20Value) \|


[![](https://semiceu.github.io/CCCEV/releases/2.00/html/overview.jpg)](https://semiceu.github.io/CCCEV/releases/2.00/html/overview.jpg)

## Entities

### [Agent](http://xmlns.com/foaf/0.1/Agent)

DefinitionEntity that is able to carry out action.UsageIn compliance with the description from FOAF, an Agent is considered as any entity that is able to carry out actions. The Agent class acts as a generic element which can be further specified by implementers for their usages, for example by defining the Person class from the [Core Person Vocabulary](https://semiceu.github.io/Core-Person-Vocabulary/releases/2.00) or Organization from [W3C's Organization Ontology](https://www.w3.org/TR/vocab-org/) as subclasses of Agent. This Person or Organization can then issue a certain Requirement or be concerned by an Evidence provided.PropertiesNo properties have been defined for this entity in the scope of this specification.

### [Constraint](http://data.europa.eu/m8g/Constraint)

DefinitionLimitation applied to an Information Concept.UsageConstraints are requirements in themselves, since they impose prerequisites which influence the definition, use and/or fulfilment of the requirement. They represent hard conditions such as minimum or maximum expressions which can be used to evaluate pieces of information, the required age, income, involvement in activities, etc. An example from the eProcurement domain is a threshold as the minimum turnover required by the buying organisation to select the candidates. Note that CCCEV does not provide any specific guidance on when which kind of Requirement should be used. Users of this vocabulary should make decisions on this topic in their specific context.Subclass of[Requirement](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement)PropertiesFor this entity the following properties are defined:
[constrains](https://semiceu.github.io/CCCEV/releases/2.00/#Constraint%3Aconstrains).

| Property | Expected Range | Definition | Usage | Codelist |
| --- | --- | --- | --- | --- |
| `constrains` | [Information Concept](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Concept) | Information Concept about which a Constraint expresses a limitation. | Information Concepts are tools to make Requirements more machine processable: they allow to provide more detail about a Requirement. This way, Constraints can be made very precise, namely the limit that must be achieved, is a limit on the value for the associated Information Concept. For example, the Information Concept would be the age of a person and the Constraint would be the required age in the context of a specific evaluation. |  |

### [Criterion](http://data.europa.eu/m8g/Criterion)

DefinitionCondition for evaluation or assessment.UsageIn general, Criteria are used for comparison, filtering or selection purposes. Criteria usually set minimum conditions (e.g. limits, intervals, thresholds, etc.) that need to be met in order to pass the requirements or to fulfil them to a certain degree or quality. The concept of Criteria is broader than the concept of Constraint since it covers more usages. The evaluation of the fulfilment is usually supported by the provision of Evidence.
For example in the eProcurement domain, the eProcurement Ontology defines different subclasses of Criterion such as exclusion grounds, selection criteria or award criteria. A concrete example of a Criterion is 'participation in a criminal organisation' which could also be considered as an exclusion ground criterion in the procurement domain or for requiring a public service.Subclass of[Requirement](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement)PropertiesFor this entity the following properties are defined:
[bias](https://semiceu.github.io/CCCEV/releases/2.00/#Criterion%3Abias),
[weight](https://semiceu.github.io/CCCEV/releases/2.00/#Criterion%3Aweight),
[weighting consideration description](https://semiceu.github.io/CCCEV/releases/2.00/#Criterion%3Aweighting%20consideration%20description),
[weighting type](https://semiceu.github.io/CCCEV/releases/2.00/#Criterion%3Aweighting%20type).

| Property | Expected Range | Definition | Usage | Codelist |
| --- | --- | --- | --- | --- |
| `bias` | [Decimal](http://www.w3.org/2001/XMLSchema#decimal) | Parameter used to adjust the evaluation of the Criterion. | The bias parameter tries to correct a systematic error. For example in procurement, a home bias corresponds to the ["presence of local preferences distorting international specialisation and resource allocation"](https://trade.ec.europa.eu/doclib/docs/2018/september/tradoc_157319.pdf).<br>When quantified, this systematic error can be removed. |  |
| `weight` | [Decimal](http://www.w3.org/2001/XMLSchema#decimal) | Relative importance of the Criterion. | The weight must be between 0 and 1. Usually, all Criteria can be integrated within a weighted sum equal to 1. |  |
| `weighting consideration description` | [String](http://www.w3.org/2001/XMLSchema#string) | Explanation of how the weighting of a Criterion is to be used. | This description gives the view of the creator of the Criterion weights on how to interpret and use them during the evaluation process. |  |
| `weighting type` | [Code](http://www.w3.org/2004/02/skos/core#Concept) | Indication of how the weight should be interpreted in a complex evaluation expression, e.g. as a percentage in an evaluation expression. | An existing codelist [Number weight](http://publications.europa.eu/resource/authority/number-weight). An example of its use is shown [here](https://docs.ted.europa.eu/eforms/0.3.1/index.html#awardCriteriaSection) |  |

### [Dataset](http://www.w3.org/ns/dcat\#Dataset)

DefinitionCollection of data.UsageIn v2.0.0, CCCEV relies on DCAT, a vocabulary for cataloging datasets and services, to share metadata about an Evidence. This dependence is made explicit by subclassing Evidence from dcat:Dataset. This is justified by the fact that a lot of the information needed for describing Evidences is described in DCAT, such as the issue date, creator, title, etc. Furthermore, other useful parts from DCAT can be reused, such as the Distribution class.PropertiesFor this entity the following properties are defined:
[identifier](https://semiceu.github.io/CCCEV/releases/2.00/#Dataset%3Aidentifier).

| Property | Expected Range | Definition | Usage | Codelist |
| --- | --- | --- | --- | --- |
| `identifier` | [Literal](http://www.w3.org/2000/01/rdf-schema#Literal) | Unambiguous reference to the Dataset. |  |  |

### [Evidence](http://data.europa.eu/m8g/Evidence)

DefinitionProof that a Requirement is met.UsageThe class Evidence provides the means to support responses to Criteria or to a concrete Information Requirement or to an Information Concept inside an Information Requirement.

The proof described by an Evidence can \[1\] verify a claim (i.e. is it true that John is 25, yes/no), \[2\] prove a condition (i.e. is John 18+, yes/no), or \[3\] simply provide data (i.e. the age of a person, namely 25).
The proof can be given through documents or extracts of base registries, independently from its structure, format or medium used to exchange it: a pdf document, a video, a recording, etc.Subclass of[Dataset](https://semiceu.github.io/CCCEV/releases/2.00/#Dataset)PropertiesFor this entity the following properties are defined:
[confidentiality level type](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%3Aconfidentiality%20level%20type),
[is about](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%3Ais%20about),
[is conformant to](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%3Ais%20conformant%20to),
[is created by](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%3Ais%20created%20by),
[is issued by](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%3Ais%20issued%20by),
[is provided by](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%3Ais%20provided%20by),
[supports concept](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%3Asupports%20concept),
[supports requirement](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%3Asupports%20requirement),
[supports value](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%3Asupports%20value),
[validity period](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%3Avalidity%20period).

| Property | Expected Range | Definition | Usage | Codelist |
| --- | --- | --- | --- | --- |
| `confidentiality level type` | [Code](http://www.w3.org/2004/02/skos/core#Concept) | Security classification assigned to an Evidence e.g. classified, sensitive, public. | Classifications should be defined by an organisation/country as an outcome of a security assessment. |  |
| `is about` | [Agent](https://semiceu.github.io/CCCEV/releases/2.00/#Agent) | Agent that is the subject in the provided Evidence. |  |  |
| `is conformant to` | [Evidence Type](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type) | Evidence Type that specifies characteristics of the Evidence. | Examples of characteristics could be the layout or the configuration of the Evidence. |  |
| `is created by` | [Agent](https://semiceu.github.io/CCCEV/releases/2.00/#Agent) | Agent that produces the Evidence. | The production of evidence could involve the generation of a document or the extraction of data from a database. |  |
| `is issued by` | [Agent](https://semiceu.github.io/CCCEV/releases/2.00/#Agent) | Agent legally responsible for the Evidence, e.g. a legal authority. | This property captures cases such as when a legal authority is responsible for the regulation about an Evidence (e.g. a ministry). |  |
| `is provided by` | [Agent](https://semiceu.github.io/CCCEV/releases/2.00/#Agent) | Agent that transmits the Evidence. | Agents transmitting Evidence are usually the Agents requesting the Evidence or service providers acting on behalf of the requesting Agents such as software developer companies. |  |
| `supports concept` | [Information Concept](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Concept) | Information Concept providing facts found/inferred from the Evidence. | Examples of Information Concepts are values found explictly in the evidence such as a birth date or information derived from the Evidence such as "I am older that 18 years". |  |
| `supports requirement` | [Requirement](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement) | Requirement for which the Evidence provides proof. |  |  |
| `supports value` | [Supported Value](https://semiceu.github.io/CCCEV/releases/2.00/#Supported%20Value) | Supported Value that the Evidence contains. |  |  |
| `validity period` | [Period of Time](https://semiceu.github.io/CCCEV/releases/2.00/#Period%20of%20Time) | Period of Time during which the Evidence holds true or has force. |  |  |

### [Evidence Type](http://data.europa.eu/m8g/EvidenceType)

DefinitionInformation about the characteristics of an Evidence.UsageThe Evidence Type and the characteristics it describes are not concrete individual responses to a Requirement (i.e. Evidence), but descriptions about the desired form, content, source and/or other characteristics that an actual response should have and provide (e.g. membership of a class of Evidences).PropertiesFor this entity the following properties are defined:
[evidence type classification](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%3Aevidence%20type%20classification),
[identifier](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%3Aidentifier),
[is specified in](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%3Ais%20specified%20in),
[issuing place](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%3Aissuing%20place),
[validity period constraint](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%3Avalidity%20period%20constraint).

| Property | Expected Range | Definition | Usage | Codelist |
| --- | --- | --- | --- | --- |
| `evidence type classification` | [Code](http://www.w3.org/2004/02/skos/core#Concept) | Category to which the Evidence Type belongs. | The categories agreed are left open but could for example specify the layout and content expected for an Evidence. |  |
| `identifier` | [Literal](http://www.w3.org/2000/01/rdf-schema#Literal) | Unambiguous reference to the Evidence Type. |  |  |
| `is specified in` | [Evidence Type List](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%20List) | Evidence Type List in which the Evidence Type is included. |  |  |
| `issuing place` | [Location](https://semiceu.github.io/CCCEV/releases/2.00/#Location) | Refers to the Location where an Evidence Type is issued. | E.g. Belgian ID cards are issued in Belgium. |  |
| `validity period constraint` | [Period of Time](https://semiceu.github.io/CCCEV/releases/2.00/#Period%20of%20Time) | Temporal condition on the validity period of the Evidence Type. | E.g. A Belgian birth evidence is valid for X months after emission. To express constraints on the validity period that must hold when assessing the evidence (e.g. the certificate of good conduct cannot be issued more than 3 months ago), we refer to the Constraint class. |  |

### [Evidence Type List](http://data.europa.eu/m8g/EvidenceTypeList)

DefinitionGroup of Evidence Types for conforming to a Requirement.UsageAn Evidence Type List is satisfied, if and only if, for all included Evidence Types in this List, corresponding conformant Evidence(s) are supporting the Requirement having this List.

The Evidence Type List describes thus an AND condition on the different Evidence Types within the list and an OR condition between two or more Evidence Type Lists. Combinations of alternative Lists can be provided for a respondent of a Requirement to choose amongst them.PropertiesFor this entity the following properties are defined:
[description](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%20List%3Adescription),
[identifier](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%20List%3Aidentifier),
[name](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%20List%3Aname),
[specifies evidence type](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%20List%3Aspecifies%20evidence%20type).

| Property | Expected Range | Definition | Usage | Codelist |
| --- | --- | --- | --- | --- |
| `description` | [String](http://www.w3.org/2001/XMLSchema#string) | Short explanation supporting the understanding of the Evidence Type List. | The explanation can include information about the nature, attributes, uses or any other additional information about the Evidence Type List. |  |
| `identifier` | [Literal](http://www.w3.org/2000/01/rdf-schema#Literal) | Unambiguous reference to the Evidence Type List. |  |  |
| `name` | [String](http://www.w3.org/2001/XMLSchema#string) | Name of the Evidence Type List. |  |  |
| `specifies evidence type` | [Evidence Type](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type) | Evidence Type included in this Evidence Type List. |  |  |

### [Information Concept](http://data.europa.eu/m8g/InformationConcept)

DefinitionPiece of information that the Evidence provides or the Requirement needs.UsageThe Information Concept class offers the ability to describe conceptually the Requirements and provided facts in Evidences. In complementarity with the Supported Value class, this is a (first) step towards facilitating the assessment of the requirements in an automated way based on the Evidence provided.PropertiesFor this entity the following properties are defined:
[description](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Concept%3Adescription),
[expression of expected value](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Concept%3Aexpression%20of%20expected%20value),
[identifier](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Concept%3Aidentifier),
[name](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Concept%3Aname),
[type](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Concept%3Atype).

| Property | Expected Range | Definition | Usage | Codelist |
| --- | --- | --- | --- | --- |
| `description` | [String](http://www.w3.org/2001/XMLSchema#string) | Short explanation supporting the understanding of the Information Concept. | The explanation can include information about the nature, attributes, uses or any other additional information about the Information Concept. |  |
| `expression of expected value` | [Literal](http://www.w3.org/2000/01/rdf-schema#Literal) | Formulation in a formal language of the expected value(s) for the Information Concept which is aligned with the concepts from the Requirements defined and must be respected by the supplied Supported Values . | The property encapsulates all kind of expectations on the required and provided values one could have. <br>This may range from representational expectations such as the type (e.g. the value is expected to be a xsd:decimal) to expectations that reduce the allowed value range. Commonly this is done using min or max bounderary expressions (e.g. the maximum value is 1 Million Euro).<br>Other usage could be to harmonise the supplied values (e.g. rounding, turning to percentages) to facilitate further processing.<br>Implementers are free to use their own approach for defining the expected values in more details. For instance, this can be by defining their own datatypes extending or encapsulating common xsd datatypes. But also by using more complex languages such as XPath, Object Constraint Language (OCL), JavaScript and Rule Interchange Format (RIF).<br>Because of this freedom, implementers are recommended to well-document their usage of this property (and related information). |  |
| `identifier` | [Literal](http://www.w3.org/2000/01/rdf-schema#Literal) | Unambiguous reference to the Information Concept. |  |  |
| `name` | [String](http://www.w3.org/2001/XMLSchema#string) | Name of the Information Concept. |  |  |
| `type` | [Code](http://www.w3.org/2004/02/skos/core#Concept) | Category to which the Information Concept belongs. | In addition to the expression of the expected value, the type classification can be used to express the kind of value the information concept is processing. This can be primitive types such as a date or a string, but also more business domain terminology such as age or number of employees. It is recommended to well-document the usage of the property. |  |

### [Information Requirement](http://data.europa.eu/m8g/InformationRequirement)

DefinitionRequested data that is to be proven by Evidence.UsageInformation Requirements are the most neutral kind of Requirements. They aim to request information in any form, e.g. a person's date of birth or a company's turnover. They represent requests for data that prove one or more facts of the real world in a formal manner, or that leads to the source of such a proof. They can be understood as 'requests for Evidences'. The response to an Information Requirement is an Evidence when the issuer of the response is an authoritative source (e.g. a Civil Registry providing data about a natural person for the provision of public service through the Single Digital Gateway). In other cases, the responses might not be issued by an authoritative source, but the issuer supports the responses with Evidences (or commits to support them timely, e.g. a self-declaration or a declaration of oath). The Information Requirement can require structured data or documents of any form. For structured data, the Requirement can use 'Concepts' to specify the structure and type of the data expected in the response. For both structured and unstructured data, the Information Requirement can indicate the expected Type of Evidence, its format, source, and other properties related to the Evidence.Subclass of[Requirement](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement)PropertiesNo properties have been defined for this entity in the scope of this specification.

### [Location](http://purl.org/dc/terms/Location)

DefinitionIdentifiable geographic place or named place.PropertiesNo properties have been defined for this entity in the scope of this specification.

### [Period of Time](http://www.w3.org/2006/time\#ProperInterval)

DefinitionA temporal entity with non-zero extent or duration.UsageThe value of the start time and end time properties should be different, and/or a duration should be given.PropertiesFor this entity the following properties are defined:
[duration](https://semiceu.github.io/CCCEV/releases/2.00/#Period%20of%20Time%3Aduration),
[endtime](https://semiceu.github.io/CCCEV/releases/2.00/#Period%20of%20Time%3Aendtime),
[starttime](https://semiceu.github.io/CCCEV/releases/2.00/#Period%20of%20Time%3Astarttime).

| Property | Expected Range | Definition | Usage | Codelist |
| --- | --- | --- | --- | --- |
| `duration` | [Duration](http://www.w3.org/2001/XMLSchema#duration) | Extent of the Period of Time. | Amount of time between start time and end time. |  |
| `endtime` | [DateTime](http://www.w3.org/2001/XMLSchema#dateTime) | Time instant at which the Period was terminated. | For example, the property ends the duration during which an Evidence Type or an Evidence is considered valid. The duration must be equal to the time between the starttime and endtime. |  |
| `starttime` | [DateTime](http://www.w3.org/2001/XMLSchema#dateTime) | Time instant at which the Period was initiated. | For example, the property starts the duration during which an Evidence Type or an Evidence is considered valid. The duration must be equal to the time between the starttime and endtime. |  |

### [Reference Framework](http://data.europa.eu/m8g/ReferenceFramework)

DefinitionLegislation or official policy from which Requirements are derived.UsageUsual Reference Frameworks are legal and non-legal specifications. Examples include procedures, tendering legislation, etc.PropertiesFor this entity the following properties are defined:
[identifier](https://semiceu.github.io/CCCEV/releases/2.00/#Reference%20Framework%3Aidentifier).

| Property | Expected Range | Definition | Usage | Codelist |
| --- | --- | --- | --- | --- |
| `identifier` | [Literal](http://www.w3.org/2000/01/rdf-schema#Literal) | An unambiguous reference to a Reference Framework. |  |  |

### [Requirement](http://data.europa.eu/m8g/Requirement)

DefinitionCondition or prerequisite that is to be proven by Evidence.UsageRequirement is a generic class representing any type of prerequisite that may be desired, needed or imposed as an obligation. CCCEV recommends to not use the Requirement class directly, but rather a more semantically-enriched subclass such as Criterion, Information Requirement or Constraint. Also note that the Requirement class is specified at a more abstract level and is not to be used as the instantiation of a Requirement for a specific Agent.


To illustrate the notion: the [European Directive on services in the internal market](https://eur-lex.europa.eu/legal-content/EN/TXT/?uri=celex%3A32006L0123) defines requirement as any obligation, prohibition, condition or limit provided for in the laws, regulations or administrative provisions of the Member States or in consequence of case-law, administrative practice, the rules of professional bodies, or the collective rules of professional associations or other professional organisations, adopted in the exercise of their legal autonomy.PropertiesFor this entity the following properties are defined:
[description](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Adescription),
[has concept](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Ahas%20concept),
[has evidence type list](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Ahas%20evidence%20type%20list),
[has qualified relation](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Ahas%20qualified%20relation),
[has requirement](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Ahas%20requirement),
[has supporting evidence](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Ahas%20supporting%20evidence),
[identifier](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Aidentifier),
[is derived from](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Ais%20derived%20from),
[is issued by](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Ais%20issued%20by),
[is requirement of](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Ais%20requirement%20of),
[name](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Aname),
[type](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Atype).

| Property | Expected Range | Definition | Usage | Codelist |
| --- | --- | --- | --- | --- |
| `description` | [String](http://www.w3.org/2001/XMLSchema#string) | A short explanation supporting the understanding of the Requirement. | The explanation can include information about the nature, attributes, uses or any other additional information about the Requirement. |  |
| `has concept` | [Information Concept](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Concept) | Information Concept for which a value is expected by the Requirement. | Information Concepts defined for specific Requirements also represent the basis for specifying the Supported Value an Evidence should provide. |  |
| `has evidence type list` | [Evidence Type List](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence%20Type%20List) | Evidence Type List that specifies the Evidence Types that are needed to meet the Requirement. | One or several Lists of Evidence Types can support a Requirement. At least one of them must be satisfied by the response to the Requirement. |  |
| `has qualified relation` | [Requirement](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement) | Described and/or categorised relation to another Requirement. | This property leaves the possiblity to define a qualified relation from Requirement to Information Requirement or Constraint as well as a qualified relation from Requirement to Requirement. A use case would be to specialize an EU requirement in Member States' specific requirements. |  |
| `has requirement` | [Requirement](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement) | A more specific Requirement that is part of the Requirement. |  |  |
| `has supporting evidence` | [Evidence](https://semiceu.github.io/CCCEV/releases/2.00/#Evidence) | Evidence that supplies information, proof or support for the Requirement. |  |  |
| `identifier` | [Literal](http://www.w3.org/2000/01/rdf-schema#Literal) | Unambiguous reference to a Requirement. |  |  |
| `is derived from` | [Reference Framework](https://semiceu.github.io/CCCEV/releases/2.00/#Reference%20Framework) | Reference Framework on which the Requirement is based, such as a law or regulation. | Note that a Requirement can have several Reference Frameworks from which it is derived. |  |
| `is issued by` | [Agent](https://semiceu.github.io/CCCEV/releases/2.00/#Agent) | Agent that has published the Requirement. |  |  |
| `is requirement of` | [Requirement](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement) | A reference between a sub-Requirement and its parent Requirement. | The relation between a parent Requirement and a sub-Requirement can be complex. Therefore, qualified relations (see [hasQualifiedRelation](https://semiceu.github.io/CCCEV/releases/2.00/#Requirement%3Ahas%20qualified%20relation)) can be used to represent this relationship on its own and qualify it with additional information such as a date, a place. This is left to implementers.<br>In the case where the purpose is to link the two Requirements without additional information, the simple relationship as proposed here can be directly used. |  |
| `name` | [String](http://www.w3.org/2001/XMLSchema#string) | Name of the Requirement. |  |  |
| `type` | [Code](http://www.w3.org/2004/02/skos/core#Concept) | Category to which the Requirement belongs. |  |  |

### [Supported Value](http://data.europa.eu/m8g/SupportedValue)

DefinitionValue for an Information Concept that is provided by an Evidence.UsageThe notion of Supported Value is closely related to actual data exchange between two parties:
(a) the Requirement processor, i.e. the Agent setting out Requirements for an objective and processing the supplied Evidences in the context of the Requirements, and
(b) the Evidence provider, i.e. the Agent supplying information to an information request expressed as Requirements.

The Requirement processor has expressed its expectations (both business as technical) for the information it wants to recieve as an Information Concept. The Evidence provider is able to supply information for that Information Concept, but its native data representation might not be coherent with the expectations set by the Requirement processor. The Supported Value is bridging both.

The Evidence provider can either provide a derived value (fact) from its native data representation that complies with the Information Concept expectations. Or it can provide a query in an agreed language between Evidence provider and Requirement processor that allows the Requirement processor to retrieve the value from the native data representation.

Implementers are free to choose their language. It is recommended to document the made agreements well.
PropertiesFor this entity the following properties are defined:
[provides value for](https://semiceu.github.io/CCCEV/releases/2.00/#Supported%20Value%3Aprovides%20value%20for),
[query](https://semiceu.github.io/CCCEV/releases/2.00/#Supported%20Value%3Aquery),
[value](https://semiceu.github.io/CCCEV/releases/2.00/#Supported%20Value%3Avalue).

| Property | Expected Range | Definition | Usage | Codelist |
| --- | --- | --- | --- | --- |
| `provides value for` | [Information Concept](https://semiceu.github.io/CCCEV/releases/2.00/#Information%20Concept) | Information Concept for which the Supported Value provides a value. |  |  |
| `query` | [Literal](http://www.w3.org/2000/01/rdf-schema#Literal) | Search statement that allows the value for the Information Concept to be retrieved from the Evidence data. | The query must be executed on the business data provided by the supporting Evidence. In order to be able to evaluate the query on the provided data, the format of the provided data must be aligned with the query expression. For instance if the provided data is XML, a query in XPath could be expected. This alignment is part of the implementation agreements that implementors must make. |  |
| `value` | [Literal](http://www.w3.org/2000/01/rdf-schema#Literal) | Value for the Information Concept that the Evidence supports. |  |  |

## Implementation note

(non-normative)


CCCEV as a Core Vocabulary is implementation-neutral. CCCEV does not impose any choices regarding _how_ the data is being exchanged. Furthermore, CCCEV does not enforce the information carried in an Evidence to be directly accessible i.e. contained within the payload of the response. However, CCCEV does offer several ways to implement these aspects: (1) via subclassing of the Evidence class, (2) via sharing it as a dcat:Distribution or (3) via the class Supported Value. These are three distinguishable approaches, which can be used independently from each other, or in combination.

CCCEV provides a framework for the implementers to define the actual information models and metadata models an Evidence should comply to. For example, a Birth Certificate model could be defined as a particular Evidence Type to which the Evidence shared by the competent authority should conform to. This information model would make use of existing metadata models. The implementer can define one or several domain specific models for defining the information acceptable or required within an Evidence.

In practice, an Evidence (from the perspective of being a document) often provides more information than is required for the decision making process. Despite that it might be possible to express all these information pieces as Information Concepts, this remains a choice for the implementer. It is not the intent of CCCEV to provide a full-fledged data modeling framework with the notion of Information Concept and Supported Value. Usages such as creating Information Concepts independent from Requirements or providing Values for Information Concepts that are not requested by a Requirement should be considered as unintended usages, beyond the scope of CCCEV.

## Changelog w.r.t. latest version

(non-normative)


A changelog describing the (major) changes to the previous version (1.0.0) of the Core Criterion and Core Evidence Vocabulary (released in December 2016) and the new version that is here being proposed (2.0.0), can be found [here](https://github.com/SEMICeu/CCCEV/blob/master/releases/2.00/Changelog.md).



## UML representation

(non-normative)



The UML representation from which this Core Vocabulary has been build is available [here](https://semiceu.github.io/CCCEV/releases/2.00/cccev.eap).


## RDF representation

(non-normative)



A reusable RDF representation (in turtle) for this Core Vocabulary is retrievable [here](https://semiceu.github.io/CCCEV/releases/2.00/voc/cccev.ttl).


This RDF file contains only the terminology for which the URI is minted in the Core Vocabularies domain http://data.europa.eu/m8g.
Terms that are mapped on an existing URI (hence reused from other vocabularies) are not included.


## JSON-LD context

(non-normative)



A reusable JSON-LD context definition for this Core Vocabulary is retrievable [here](https://semiceu.github.io/CCCEV/releases/2.00/context/cccev.jsonld).

## SHACL template

(non-normative)



A reusable SHACL template for this Core Vocabulary is retrievable [here](https://semiceu.github.io/CCCEV/releases/2.00/shacl/cccev-SHACL.ttl).