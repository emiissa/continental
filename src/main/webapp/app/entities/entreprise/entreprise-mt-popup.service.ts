import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EntrepriseMt } from './entreprise-mt.model';
import { EntrepriseMtService } from './entreprise-mt.service';

@Injectable()
export class EntrepriseMtPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private entrepriseService: EntrepriseMtService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.entrepriseService.find(id).subscribe((entreprise) => {
                this.entrepriseModalRef(component, entreprise);
            });
        } else {
            return this.entrepriseModalRef(component, new EntrepriseMt());
        }
    }

    entrepriseModalRef(component: Component, entreprise: EntrepriseMt): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.entreprise = entreprise;
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
