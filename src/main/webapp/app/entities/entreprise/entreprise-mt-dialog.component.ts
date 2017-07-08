import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntrepriseMt } from './entreprise-mt.model';
import { EntrepriseMtPopupService } from './entreprise-mt-popup.service';
import { EntrepriseMtService } from './entreprise-mt.service';

@Component({
    selector: 'jhi-entreprise-mt-dialog',
    templateUrl: './entreprise-mt-dialog.component.html'
})
export class EntrepriseMtDialogComponent implements OnInit {

    entreprise: EntrepriseMt;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private entrepriseService: EntrepriseMtService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.entreprise.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entrepriseService.update(this.entreprise), false);
        } else {
            this.subscribeToSaveResponse(
                this.entrepriseService.create(this.entreprise), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<EntrepriseMt>, isCreated: boolean) {
        result.subscribe((res: EntrepriseMt) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EntrepriseMt, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'continentalApp.entreprise.created'
            : 'continentalApp.entreprise.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'entrepriseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-entreprise-mt-popup',
    template: ''
})
export class EntrepriseMtPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entreprisePopupService: EntrepriseMtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.entreprisePopupService
                    .open(EntrepriseMtDialogComponent, params['id']);
            } else {
                this.modalRef = this.entreprisePopupService
                    .open(EntrepriseMtDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
