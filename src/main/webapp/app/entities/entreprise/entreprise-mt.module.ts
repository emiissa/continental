import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContinentalSharedModule } from '../../shared';
import {
    EntrepriseMtService,
    EntrepriseMtPopupService,
    EntrepriseMtComponent,
    EntrepriseMtDetailComponent,
    EntrepriseMtDialogComponent,
    EntrepriseMtPopupComponent,
    EntrepriseMtDeletePopupComponent,
    EntrepriseMtDeleteDialogComponent,
    entrepriseRoute,
    entreprisePopupRoute,
} from './';

const ENTITY_STATES = [
    ...entrepriseRoute,
    ...entreprisePopupRoute,
];

@NgModule({
    imports: [
        ContinentalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EntrepriseMtComponent,
        EntrepriseMtDetailComponent,
        EntrepriseMtDialogComponent,
        EntrepriseMtDeleteDialogComponent,
        EntrepriseMtPopupComponent,
        EntrepriseMtDeletePopupComponent,
    ],
    entryComponents: [
        EntrepriseMtComponent,
        EntrepriseMtDialogComponent,
        EntrepriseMtPopupComponent,
        EntrepriseMtDeleteDialogComponent,
        EntrepriseMtDeletePopupComponent,
    ],
    providers: [
        EntrepriseMtService,
        EntrepriseMtPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContinentalEntrepriseMtModule {}
