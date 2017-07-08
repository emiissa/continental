import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PrestatairesMt } from './prestataires-mt.model';
import { PrestatairesMtPopupService } from './prestataires-mt-popup.service';
import { PrestatairesMtService } from './prestataires-mt.service';
import { OperationsMt, OperationsMtService } from '../operations';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-prestataires-mt-dialog',
    templateUrl: './prestataires-mt-dialog.component.html'
})
export class PrestatairesMtDialogComponent implements OnInit {

    prestataires: PrestatairesMt;
    authorities: any[];
    isSaving: boolean;

    operations: OperationsMt[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private prestatairesService: PrestatairesMtService,
        private operationsService: OperationsMtService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.operationsService.query()
            .subscribe((res: ResponseWrapper) => { this.operations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.prestataires.id !== undefined) {
            this.subscribeToSaveResponse(
                this.prestatairesService.update(this.prestataires), false);
        } else {
            this.subscribeToSaveResponse(
                this.prestatairesService.create(this.prestataires), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<PrestatairesMt>, isCreated: boolean) {
        result.subscribe((res: PrestatairesMt) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PrestatairesMt, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'continentalApp.prestataires.created'
            : 'continentalApp.prestataires.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'prestatairesListModification', content: 'OK'});
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

    trackOperationsById(index: number, item: OperationsMt) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-prestataires-mt-popup',
    template: ''
})
export class PrestatairesMtPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestatairesPopupService: PrestatairesMtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.prestatairesPopupService
                    .open(PrestatairesMtDialogComponent, params['id']);
            } else {
                this.modalRef = this.prestatairesPopupService
                    .open(PrestatairesMtDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
