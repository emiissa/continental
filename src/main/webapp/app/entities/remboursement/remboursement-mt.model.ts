
const enum LienMalade {
    'CLIENT',
    'CONJOINT',
    'ENFANT'

};
export class RemboursementMt {
    constructor(
        public id?: number,
        public numeroDossier?: number,
        public dateR?: any,
        public nomMalade?: string,
        public prenomMalade?: string,
        public dateNaissance?: string,
        public lien?: LienMalade,
        public pieceJointeContentType?: string,
        public pieceJointe?: any,
        public pieceJointe2?: any,
        public operationsId?: number,
        public clientId?: number,
    ) {
    }
}
