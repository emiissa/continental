import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RemboursementMt } from './remboursement-mt.model';
import { RemboursementMtService } from './remboursement-mt.service';

@Injectable()
export class RemboursementMtPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private remboursementService: RemboursementMtService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.remboursementService.find(id).subscribe((remboursement) => {
                if (remboursement.dateR) {
                    remboursement.dateR = {
                        year: remboursement.dateR.getFullYear(),
                        month: remboursement.dateR.getMonth() + 1,
                        day: remboursement.dateR.getDate()
                    };
                }
                this.remboursementModalRef(component, remboursement);
            });
        } else {
            return this.remboursementModalRef(component, new RemboursementMt());
        }
    }

    remboursementModalRef(component: Component, remboursement: RemboursementMt): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.remboursement = remboursement;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
