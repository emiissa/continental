import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContinentalSharedModule } from '../../shared';
import {
    RemboursementMtService,
    RemboursementMtPopupService,
    RemboursementMtComponent,
    RemboursementMtDetailComponent,
    RemboursementMtDialogComponent,
    RemboursementMtPopupComponent,
    RemboursementMtDeletePopupComponent,
    RemboursementMtDeleteDialogComponent,
    remboursementRoute,
    remboursementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...remboursementRoute,
    ...remboursementPopupRoute,
];

@NgModule({
    imports: [
        ContinentalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RemboursementMtComponent,
        RemboursementMtDetailComponent,
        RemboursementMtDialogComponent,
        RemboursementMtDeleteDialogComponent,
        RemboursementMtPopupComponent,
        RemboursementMtDeletePopupComponent,
    ],
    entryComponents: [
        RemboursementMtComponent,
        RemboursementMtDialogComponent,
        RemboursementMtPopupComponent,
        RemboursementMtDeleteDialogComponent,
        RemboursementMtDeletePopupComponent,
    ],
    providers: [
        RemboursementMtService,
        RemboursementMtPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContinentalRemboursementMtModule {}
