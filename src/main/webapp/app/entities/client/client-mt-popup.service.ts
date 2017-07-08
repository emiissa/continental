import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ClientMt } from './client-mt.model';
import { ClientMtService } from './client-mt.service';

@Injectable()
export class ClientMtPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private clientService: ClientMtService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clientService.find(id).subscribe((client) => {
                if (client.dateNaissance) {
                    client.dateNaissance = {
                        year: client.dateNaissance.getFullYear(),
                        month: client.dateNaissance.getMonth() + 1,
                        day: client.dateNaissance.getDate()
                    };
                }
                this.clientModalRef(component, client);
            });
        } else {
            return this.clientModalRef(component, new ClientMt());
        }
    }

    clientModalRef(component: Component, client: ClientMt): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.client = client;
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
