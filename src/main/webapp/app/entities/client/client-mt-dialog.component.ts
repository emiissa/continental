import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ClientMt } from './client-mt.model';
import { ClientMtPopupService } from './client-mt-popup.service';
import { ClientMtService } from './client-mt.service';
import { EntrepriseMt, EntrepriseMtService } from '../entreprise';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-client-mt-dialog',
    templateUrl: './client-mt-dialog.component.html'
})
export class ClientMtDialogComponent implements OnInit {

    client: ClientMt;
    authorities: any[];
    isSaving: boolean;

    entreprises: EntrepriseMt[];
    dateNaissanceDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private clientService: ClientMtService,
        private entrepriseService: EntrepriseMtService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.entrepriseService.query()
            .subscribe((res: ResponseWrapper) => { this.entreprises = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.client.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clientService.update(this.client), false);
        } else {
            this.subscribeToSaveResponse(
                this.clientService.create(this.client), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<ClientMt>, isCreated: boolean) {
        result.subscribe((res: ClientMt) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClientMt, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'continentalApp.client.created'
            : 'continentalApp.client.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'clientListModification', content: 'OK'});
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

    trackEntrepriseById(index: number, item: EntrepriseMt) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-client-mt-popup',
    template: ''
})
export class ClientMtPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientPopupService: ClientMtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clientPopupService
                    .open(ClientMtDialogComponent, params['id']);
            } else {
                this.modalRef = this.clientPopupService
                    .open(ClientMtDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
