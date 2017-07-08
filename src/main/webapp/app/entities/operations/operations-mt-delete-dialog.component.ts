import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { OperationsMt } from './operations-mt.model';
import { OperationsMtPopupService } from './operations-mt-popup.service';
import { OperationsMtService } from './operations-mt.service';

@Component({
    selector: 'jhi-operations-mt-delete-dialog',
    templateUrl: './operations-mt-delete-dialog.component.html'
})
export class OperationsMtDeleteDialogComponent {

    operations: OperationsMt;

    constructor(
        private operationsService: OperationsMtService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.operationsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'operationsListModification',
                content: 'Deleted an operations'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('continentalApp.operations.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-operations-mt-delete-popup',
    template: ''
})
export class OperationsMtDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private operationsPopupService: OperationsMtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.operationsPopupService
                .open(OperationsMtDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
