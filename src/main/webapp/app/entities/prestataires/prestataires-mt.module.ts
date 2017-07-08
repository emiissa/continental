import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ContinentalSharedModule } from '../../shared';
import {
    PrestatairesMtService,
    PrestatairesMtPopupService,
    PrestatairesMtComponent,
    PrestatairesMtDetailComponent,
    PrestatairesMtDialogComponent,
    PrestatairesMtPopupComponent,
    PrestatairesMtDeletePopupComponent,
    PrestatairesMtDeleteDialogComponent,
    prestatairesRoute,
    prestatairesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...prestatairesRoute,
    ...prestatairesPopupRoute,
];

@NgModule({
    imports: [
        ContinentalSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PrestatairesMtComponent,
        PrestatairesMtDetailComponent,
        PrestatairesMtDialogComponent,
        PrestatairesMtDeleteDialogComponent,
        PrestatairesMtPopupComponent,
        PrestatairesMtDeletePopupComponent,
    ],
    entryComponents: [
        PrestatairesMtComponent,
        PrestatairesMtDialogComponent,
        PrestatairesMtPopupComponent,
        PrestatairesMtDeleteDialogComponent,
        PrestatairesMtDeletePopupComponent,
    ],
    providers: [
        PrestatairesMtService,
        PrestatairesMtPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContinentalPrestatairesMtModule {}
