import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { OperationsMt } from './operations-mt.model';
import { OperationsMtService } from './operations-mt.service';

@Component({
    selector: 'jhi-operations-mt-detail',
    templateUrl: './operations-mt-detail.component.html'
})
export class OperationsMtDetailComponent implements OnInit, OnDestroy {

    operations: OperationsMt;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private operationsService: OperationsMtService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOperations();
    }

    load(id) {
        this.operationsService.find(id).subscribe((operations) => {
            this.operations = operations;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOperations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'operationsListModification',
            (response) => this.load(this.operations.id)
        );
    }
}
