import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { EntrepriseMt } from './entreprise-mt.model';
import { EntrepriseMtService } from './entreprise-mt.service';

@Component({
    selector: 'jhi-entreprise-mt-detail',
    templateUrl: './entreprise-mt-detail.component.html'
})
export class EntrepriseMtDetailComponent implements OnInit, OnDestroy {

    entreprise: EntrepriseMt;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private entrepriseService: EntrepriseMtService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEntreprises();
    }

    load(id) {
        this.entrepriseService.find(id).subscribe((entreprise) => {
            this.entreprise = entreprise;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEntreprises() {
        this.eventSubscriber = this.eventManager.subscribe(
            'entrepriseListModification',
            (response) => this.load(this.entreprise.id)
        );
    }
}
