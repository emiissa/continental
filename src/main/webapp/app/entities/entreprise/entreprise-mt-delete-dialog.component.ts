import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { EntrepriseMt } from './entreprise-mt.model';
import { EntrepriseMtPopupService } from './entreprise-mt-popup.service';
import { EntrepriseMtService } from './entreprise-mt.service';

@Component({
    selector: 'jhi-entreprise-mt-delete-dialog',
    templateUrl: './entreprise-mt-delete-dialog.component.html'
})
export class EntrepriseMtDeleteDialogComponent {

    entreprise: EntrepriseMt;

    constructor(
        private entrepriseService: EntrepriseMtService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entrepriseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'entrepriseListModification',
                content: 'Deleted an entreprise'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('continentalApp.entreprise.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-entreprise-mt-delete-popup',
    template: ''
})
export class EntrepriseMtDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entreprisePopupService: EntrepriseMtPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.entreprisePopupService
                .open(EntrepriseMtDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
