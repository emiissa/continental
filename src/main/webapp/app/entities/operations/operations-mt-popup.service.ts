import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { OperationsMt } from './operations-mt.model';
import { OperationsMtService } from './operations-mt.service';

@Injectable()
export class OperationsMtPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private operationsService: OperationsMtService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.operationsService.find(id).subscribe((operations) => {
                if (operations.dateOperation) {
                    operations.dateOperation = {
                        year: operations.dateOperation.getFullYear(),
                        month: operations.dateOperation.getMonth() + 1,
                        day: operations.dateOperation.getDate()
                    };
                }
                this.operationsModalRef(component, operations);
            });
        } else {
            return this.operationsModalRef(component, new OperationsMt());
        }
    }

    operationsModalRef(component: Component, operations: OperationsMt): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.operations = operations;
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
