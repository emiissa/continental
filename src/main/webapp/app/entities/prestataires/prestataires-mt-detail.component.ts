import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { PrestatairesMt } from './prestataires-mt.model';
import { PrestatairesMtService } from './prestataires-mt.service';

@Component({
    selector: 'jhi-prestataires-mt-detail',
    templateUrl: './prestataires-mt-detail.component.html'
})
export class PrestatairesMtDetailComponent implements OnInit, OnDestroy {

    prestataires: PrestatairesMt;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private prestatairesService: PrestatairesMtService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrestataires();
    }

    load(id) {
        this.prestatairesService.find(id).subscribe((prestataires) => {
            this.prestataires = prestataires;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrestataires() {
        this.eventSubscriber = this.eventManager.subscribe(
            'prestatairesListModification',
            (response) => this.load(this.prestataires.id)
        );
    }
}
