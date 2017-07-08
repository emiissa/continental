import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PrestatairesMt } from './prestataires-mt.model';
import { PrestatairesMtService } from './prestataires-mt.service';

@Injectable()
export class PrestatairesMtPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private prestatairesService: PrestatairesMtService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.prestatairesService.find(id).subscribe((prestataires) => {
                this.prestatairesModalRef(component, prestataires);
            });
        } else {
            return this.prestatairesModalRef(component, new PrestatairesMt());
        }
    }

    prestatairesModalRef(component: Component, prestataires: PrestatairesMt): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.prestataires = prestataires;
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
