import { useI18n } from "@komune-io/g2-providers"

export interface Languages {
  fr: string
  en: string
}

export const languages: Languages = {
  fr: "fr-FR", 
  en: "en-US"
}

export const useExtendedI18n = () => {
  return useI18n<Languages>()
}

