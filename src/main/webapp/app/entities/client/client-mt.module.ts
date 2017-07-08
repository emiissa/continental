import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContinentalSharedModule } from '../../shared';
import {
    ClientMtService,
    ClientMtPopupService,
    ClientMtComponent,
    ClientMtDetailComponent,
    ClientMtDialogComponent,
    ClientMtPopupComponent,
    ClientMtDeletePopupComponent,
    ClientMtDeleteDialogComponent,
    clientRoute,
    clientPopupRoute,
    ClientMtResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...clientRoute,
    ...clientPopupRoute,
];

@NgModule({
    imports: [
        ContinentalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClientMtComponent,
        ClientMtDetailComponent,
        ClientMtDialogComponent,
        ClientMtDeleteDialogComponent,
        ClientMtPopupComponent,
        ClientMtDeletePopupComponent,
    ],
    entryComponents: [
        ClientMtComponent,
        ClientMtDialogComponent,
        ClientMtPopupComponent,
        ClientMtDeleteDialogComponent,
        ClientMtDeletePopupComponent,
    ],
    providers: [
        ClientMtService,
        ClientMtPopupService,
        ClientMtResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContinentalClientMtModule {}
