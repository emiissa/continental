import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OperationsMt } from './operations-mt.model';
import { OperationsMtPopupService } from './operations-mt-popup.service';
import { OperationsMtService } from './operations-mt.service';
import { RemboursementMt, RemboursementMtService } from '../remboursement';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-operations-mt-dialog',
    templateUrl: './operations-mt-dialog.component.html'
})
export class OperationsMtDialogComponent implements OnInit {

    operations: OperationsMt;
    authorities: any[];
    isSaving: boolean;

    remboursements: RemboursementMt[];
    dateOperationDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private operationsService: OperationsMtService,
        private remboursementService: RemboursementMtService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.remboursementService.query()
            .subscribe((res: ResponseWrapper) => { this.remboursements = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.operations.id !== undefined) {
            this.subscribeToSaveResponse(
                this.operationsService.update(this.operations), false);
        } else {
            this.subscribeToSaveResponse(
                this.operationsService.create(this.operations), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<OperationsMt>, isCreated: boolean) {
        result.subscribe((res: OperationsMt) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: OperationsMt, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'continentalApp.operations.created'
            : 'continentalApp.operations.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'operationsListModification', content: 'OK'});
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

    trackRemboursementById(index: number, item: RemboursementMt) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-operations-mt-popup',
    template: ''
})
export class OperationsMtPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operationsPopupService: OperationsMtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.operationsPopupService
                    .open(OperationsMtDialogComponent, params['id']);
            } else {
                this.modalRef = this.operationsPopupService
                    .open(OperationsMtDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
