import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { RemboursementMt } from './remboursement-mt.model';
import { RemboursementMtService } from './remboursement-mt.service';

@Component({
    selector: 'jhi-remboursement-mt-detail',
    templateUrl: './remboursement-mt-detail.component.html'
})
export class RemboursementMtDetailComponent implements OnInit, OnDestroy {

    remboursement: RemboursementMt;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private remboursementService: RemboursementMtService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRemboursements();
    }

    load(id) {
        this.remboursementService.find(id).subscribe((remboursement) => {
            this.remboursement = remboursement;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRemboursements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'remboursementListModification',
            (response) => this.load(this.remboursement.id)
        );
    }
}
