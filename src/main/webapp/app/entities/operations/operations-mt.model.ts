export class OperationsMt {
    constructor(
        public id?: number,
        public dateOperation?: any,
        public description?: string,
        public montant?: number,
        public tiersPayant?: number,
        public pourcentage?: number,
        public remboursementId?: number,
        public prestataireId?: number,
    ) {
    }
}
