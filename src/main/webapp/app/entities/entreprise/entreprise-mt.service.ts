import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { EntrepriseMt } from './entreprise-mt.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EntrepriseMtService {

    private resourceUrl = 'api/entreprises';

    constructor(private http: Http) { }

    create(entreprise: EntrepriseMt): Observable<EntrepriseMt> {
        const copy = this.convert(entreprise);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(entreprise: EntrepriseMt): Observable<EntrepriseMt> {
        const copy = this.convert(entreprise);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<EntrepriseMt> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(entreprise: EntrepriseMt): EntrepriseMt {
        const copy: EntrepriseMt = Object.assign({}, entreprise);
        return copy;
    }
}
