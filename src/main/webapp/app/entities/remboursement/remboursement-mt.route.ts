import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RemboursementMtComponent } from './remboursement-mt.component';
import { RemboursementMtDetailComponent } from './remboursement-mt-detail.component';
import { RemboursementMtPopupComponent } from './remboursement-mt-dialog.component';
import { RemboursementMtDeletePopupComponent } from './remboursement-mt-delete-dialog.component';

import { Principal } from '../../shared';

export const remboursementRoute: Routes = [
    {
        path: 'remboursement-mt',
        component: RemboursementMtComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.remboursement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'remboursement-mt/:id',
        component: RemboursementMtDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.remboursement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const remboursementPopupRoute: Routes = [
    {
        path: 'remboursement-mt-new',
        component: RemboursementMtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.remboursement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'remboursement-mt/:id/edit',
        component: RemboursementMtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.remboursement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'remboursement-mt/:id/delete',
        component: RemboursementMtDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.remboursement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
