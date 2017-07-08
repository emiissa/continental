import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ClientMt } from './client-mt.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ClientMtService {

    private resourceUrl = 'api/clients';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(client: ClientMt): Observable<ClientMt> {
        const copy = this.convert(client);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(client: ClientMt): Observable<ClientMt> {
        const copy = this.convert(client);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ClientMt> {
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
        entity.dateNaissance = this.dateUtils
            .convertLocalDateFromServer(entity.dateNaissance);
    }

    private convert(client: ClientMt): ClientMt {
        const copy: ClientMt = Object.assign({}, client);
        copy.dateNaissance = this.dateUtils
            .convertLocalDateToServer(client.dateNaissance);
        return copy;
    }
}
