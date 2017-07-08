import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { RemboursementMt } from './remboursement-mt.model';
import { RemboursementMtPopupService } from './remboursement-mt-popup.service';
import { RemboursementMtService } from './remboursement-mt.service';

@Component({
    selector: 'jhi-remboursement-mt-delete-dialog',
    templateUrl: './remboursement-mt-delete-dialog.component.html'
})
export class RemboursementMtDeleteDialogComponent {

    remboursement: RemboursementMt;

    constructor(
        private remboursementService: RemboursementMtService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.remboursementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'remboursementListModification',
                content: 'Deleted an remboursement'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('continentalApp.remboursement.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-remboursement-mt-delete-popup',
    template: ''
})
export class RemboursementMtDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private remboursementPopupService: RemboursementMtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.remboursementPopupService
                .open(RemboursementMtDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
