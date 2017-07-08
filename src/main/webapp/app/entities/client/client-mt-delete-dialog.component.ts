import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { ClientMt } from './client-mt.model';
import { ClientMtPopupService } from './client-mt-popup.service';
import { ClientMtService } from './client-mt.service';

@Component({
    selector: 'jhi-client-mt-delete-dialog',
    templateUrl: './client-mt-delete-dialog.component.html'
})
export class ClientMtDeleteDialogComponent {

    client: ClientMt;

    constructor(
        private clientService: ClientMtService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clientService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clientListModification',
                content: 'Deleted an client'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('continentalApp.client.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-client-mt-delete-popup',
    template: ''
})
export class ClientMtDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clientPopupService: ClientMtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.clientPopupService
                .open(ClientMtDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
