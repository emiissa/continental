import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EntrepriseMtComponent } from './entreprise-mt.component';
import { EntrepriseMtDetailComponent } from './entreprise-mt-detail.component';
import { EntrepriseMtPopupComponent } from './entreprise-mt-dialog.component';
import { EntrepriseMtDeletePopupComponent } from './entreprise-mt-delete-dialog.component';

import { Principal } from '../../shared';

export const entrepriseRoute: Routes = [
    {
        path: 'entreprise-mt',
        component: EntrepriseMtComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'entreprise-mt/:id',
        component: EntrepriseMtDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entreprisePopupRoute: Routes = [
    {
        path: 'entreprise-mt-new',
        component: EntrepriseMtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entreprise-mt/:id/edit',
        component: EntrepriseMtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entreprise-mt/:id/delete',
        component: EntrepriseMtDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.entreprise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
