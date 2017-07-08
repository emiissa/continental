import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PrestatairesMtComponent } from './prestataires-mt.component';
import { PrestatairesMtDetailComponent } from './prestataires-mt-detail.component';
import { PrestatairesMtPopupComponent } from './prestataires-mt-dialog.component';
import { PrestatairesMtDeletePopupComponent } from './prestataires-mt-delete-dialog.component';

import { Principal } from '../../shared';

export const prestatairesRoute: Routes = [
    {
        path: 'prestataires-mt',
        component: PrestatairesMtComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.prestataires.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'prestataires-mt/:id',
        component: PrestatairesMtDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.prestataires.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prestatairesPopupRoute: Routes = [
    {
        path: 'prestataires-mt-new',
        component: PrestatairesMtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.prestataires.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestataires-mt/:id/edit',
        component: PrestatairesMtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.prestataires.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'prestataires-mt/:id/delete',
        component: PrestatairesMtDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.prestataires.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
