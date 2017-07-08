import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { PrestatairesMt } from './prestataires-mt.model';
import { PrestatairesMtPopupService } from './prestataires-mt-popup.service';
import { PrestatairesMtService } from './prestataires-mt.service';

@Component({
    selector: 'jhi-prestataires-mt-delete-dialog',
    templateUrl: './prestataires-mt-delete-dialog.component.html'
})
export class PrestatairesMtDeleteDialogComponent {

    prestataires: PrestatairesMt;

    constructor(
        private prestatairesService: PrestatairesMtService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prestatairesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'prestatairesListModification',
                content: 'Deleted an prestataires'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('continentalApp.prestataires.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-prestataires-mt-delete-popup',
    template: ''
})
export class PrestatairesMtDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private prestatairesPopupService: PrestatairesMtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.prestatairesPopupService
                .open(PrestatairesMtDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
