import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PrestatairesMt } from './prestataires-mt.model';
import { PrestatairesMtService } from './prestataires-mt.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-prestataires-mt',
    templateUrl: './prestataires-mt.component.html'
})
export class PrestatairesMtComponent implements OnInit, OnDestroy {
prestataires: PrestatairesMt[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private prestatairesService: PrestatairesMtService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.prestatairesService.query().subscribe(
            (res: ResponseWrapper) => {
                this.prestataires = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrestataires();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PrestatairesMt) {
        return item.id;
    }
    registerChangeInPrestataires() {
        this.eventSubscriber = this.eventManager.subscribe('prestatairesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
