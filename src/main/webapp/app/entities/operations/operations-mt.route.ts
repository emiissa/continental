import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OperationsMtComponent } from './operations-mt.component';
import { OperationsMtDetailComponent } from './operations-mt-detail.component';
import { OperationsMtPopupComponent } from './operations-mt-dialog.component';
import { OperationsMtDeletePopupComponent } from './operations-mt-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class OperationsMtResolvePagingParams implements Resolve<any> {

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

export const operationsRoute: Routes = [
    {
        path: 'operations-mt',
        component: OperationsMtComponent,
        resolve: {
            'pagingParams': OperationsMtResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.operations.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'operations-mt/:id',
        component: OperationsMtDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.operations.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const operationsPopupRoute: Routes = [
    {
        path: 'operations-mt-new',
        component: OperationsMtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.operations.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operations-mt/:id/edit',
        component: OperationsMtPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.operations.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'operations-mt/:id/delete',
        component: OperationsMtDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'continentalApp.operations.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
