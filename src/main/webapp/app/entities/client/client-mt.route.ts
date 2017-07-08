import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ClientMtComponent } from './client-mt.component';
import { ClientMtDetailComponent } from './client-mt-detail.component';
import { ClientMtPopupComponent } from './client-mt-dialog.component';
import { ClientMtDeletePopupComponent } from './client-mt-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ClientMtResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const clientRoute: Routes = [
    {
        path: 'client-mt',
        component: ClientMtComponent,
        resolve: {
            'pagingParams': ClientMtResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.client.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'client-mt/:id',
        component: ClientMtDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.client.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientPopupRoute: Routes = [
    {
        path: 'client-mt-new',
        component: ClientMtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.client.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-mt/:id/edit',
        component: ClientMtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.client.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'client-mt/:id/delete',
        component: ClientMtDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.client.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
