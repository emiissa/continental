export class ClientMt {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public numeroContrat?: string,
        public dateNaissance?: any,
        public telephone?: number,
        public email?: string,
        public remboursementId?: number,
        public entrepriseId?: number,
    ) {
    }
}
