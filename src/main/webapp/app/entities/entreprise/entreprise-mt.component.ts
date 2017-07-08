import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { EntrepriseMt } from './entreprise-mt.model';
import { EntrepriseMtService } from './entreprise-mt.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-entreprise-mt',
    templateUrl: './entreprise-mt.component.html'
})
export class EntrepriseMtComponent implements OnInit, OnDestroy {
entreprises: EntrepriseMt[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private entrepriseService: EntrepriseMtService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.entrepriseService.query().subscribe(
            (res: ResponseWrapper) => {
                this.entreprises = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEntreprises();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EntrepriseMt) {
        return item.id;
    }
    registerChangeInEntreprises() {
        this.eventSubscriber = this.eventManager.subscribe('entrepriseListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
