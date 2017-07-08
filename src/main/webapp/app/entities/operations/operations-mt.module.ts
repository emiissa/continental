import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContinentalSharedModule } from '../../shared';
import {
    OperationsMtService,
    OperationsMtPopupService,
    OperationsMtComponent,
    OperationsMtDetailComponent,
    OperationsMtDialogComponent,
    OperationsMtPopupComponent,
    OperationsMtDeletePopupComponent,
    OperationsMtDeleteDialogComponent,
    operationsRoute,
    operationsPopupRoute,
    OperationsMtResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...operationsRoute,
    ...operationsPopupRoute,
];

@NgModule({
    imports: [
        ContinentalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OperationsMtComponent,
        OperationsMtDetailComponent,
        OperationsMtDialogComponent,
        OperationsMtDeleteDialogComponent,
        OperationsMtPopupComponent,
        OperationsMtDeletePopupComponent,
    ],
    entryComponents: [
        OperationsMtComponent,
        OperationsMtDialogComponent,
        OperationsMtPopupComponent,
        OperationsMtDeleteDialogComponent,
        OperationsMtDeletePopupComponent,
    ],
    providers: [
        OperationsMtService,
        OperationsMtPopupService,
        OperationsMtResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContinentalOperationsMtModule {}
