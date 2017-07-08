import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { RemboursementMt } from './remboursement-mt.model';
import { RemboursementMtPopupService } from './remboursement-mt-popup.service';
import { RemboursementMtService } from './remboursement-mt.service';
import { ClientMt, ClientMtService } from '../client';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-remboursement-mt-dialog',
    templateUrl: './remboursement-mt-dialog.component.html'
})
export class RemboursementMtDialogComponent implements OnInit {

    remboursement: RemboursementMt;
    authorities: any[];
    isSaving: boolean;

    clients: ClientMt[];
    dateRDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private remboursementService: RemboursementMtService,
        private clientService: ClientMtService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.clientService.query()
            .subscribe((res: ResponseWrapper) => { this.clients = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, remboursement, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                remboursement[field] = base64Data;
                remboursement[`${field}ContentType`] = file.type;
            });
        }
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.remboursement, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.remboursement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.remboursementService.update(this.remboursement), false);
        } else {
            this.subscribeToSaveResponse(
                this.remboursementService.create(this.remboursement), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<RemboursementMt>, isCreated: boolean) {
        result.subscribe((res: RemboursementMt) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RemboursementMt, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'continentalApp.remboursement.created'
            : 'continentalApp.remboursement.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'remboursementListModification', content: 'OK'});
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

    trackClientById(index: number, item: ClientMt) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-remboursement-mt-popup',
    template: ''
})
export class RemboursementMtPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private remboursementPopupService: RemboursementMtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.remboursementPopupService
                    .open(RemboursementMtDialogComponent, params['id']);
            } else {
                this.modalRef = this.remboursementPopupService
                    .open(RemboursementMtDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
