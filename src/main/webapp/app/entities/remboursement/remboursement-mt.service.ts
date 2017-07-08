import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { RemboursementMt } from './remboursement-mt.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RemboursementMtService {

    private resourceUrl = 'api/remboursements';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(remboursement: RemboursementMt): Observable<RemboursementMt> {
        const copy = this.convert(remboursement);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(remboursement: RemboursementMt): Observable<RemboursementMt> {
        const copy = this.convert(remboursement);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<RemboursementMt> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dateR = this.dateUtils
            .convertLocalDateFromServer(entity.dateR);
    }

    private convert(remboursement: RemboursementMt): RemboursementMt {
        const copy: RemboursementMt = Object.assign({}, remboursement);
        copy.dateR = this.dateUtils
            .convertLocalDateToServer(remboursement.dateR);
        return copy;
    }
}
