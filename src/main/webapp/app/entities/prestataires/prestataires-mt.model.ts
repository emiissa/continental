
const enum TypePrestataire {
    'CLINIQUE',
    'PHARMACIE'

};
export class PrestatairesMt {
    constructor(
        public id?: number,
        public nom?: string,
        public adresse?: string,
        public tel?: number,
        public numeroCompte?: number,
        public email?: string,
        public type?: TypePrestataire,
        public operationsId?: number,
    ) {
    }
}
