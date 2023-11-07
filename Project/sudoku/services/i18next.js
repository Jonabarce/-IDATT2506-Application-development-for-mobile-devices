import i18next from 'i18next';
import { initReactI18next } from 'react-i18next';
import en from '../locales/en.json';
import no from '../locales/no.json';

export const languageResources = {
    en: { translation: en },
    no: { translation: no }
}

i18next.use(initReactI18next).init({
    compatibilityJSON: 'v3',
    lng: 'en',
    fallbackLng: 'no',
    resources: languageResources,
    interpolation: { 
        escapeValue: false
      } 
});


export default i18next;
