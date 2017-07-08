import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ContinentalClientMtModule } from './client/client-mt.module';
import { ContinentalEntrepriseMtModule } from './entreprise/entreprise-mt.module';
import { ContinentalPrestatairesMtModule } from './prestataires/prestataires-mt.module';
import { ContinentalRemboursementMtModule } from './remboursement/remboursement-mt.module';
import { ContinentalOperationsMtModule } from './operations/operations-mt.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ContinentalClientMtModule,
        ContinentalEntrepriseMtModule,
        ContinentalPrestatairesMtModule,
        ContinentalRemboursementMtModule,
        ContinentalOperationsMtModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContinentalEntityModule {}
