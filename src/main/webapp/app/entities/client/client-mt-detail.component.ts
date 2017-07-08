import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ClientMt } from './client-mt.model';
import { ClientMtService } from './client-mt.service';

@Component({
    selector: 'jhi-client-mt-detail',
    templateUrl: './client-mt-detail.component.html'
})
export class ClientMtDetailComponent implements OnInit, OnDestroy {

    client: ClientMt;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private clientService: ClientMtService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClients();
    }

    load(id) {
        this.clientService.find(id).subscribe((client) => {
            this.client = client;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClients() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clientListModification',
            (response) => this.load(this.client.id)
        );
    }
}
