import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PrestatairesMt } from './prestataires-mt.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PrestatairesMtService {

    private resourceUrl = 'api/prestataires';

    constructor(private http: Http) { }

    create(prestataires: PrestatairesMt): Observable<PrestatairesMt> {
        const copy = this.convert(prestataires);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(prestataires: PrestatairesMt): Observable<PrestatairesMt> {
        const copy = this.convert(prestataires);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PrestatairesMt> {
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

    private convert(prestataires: PrestatairesMt): PrestatairesMt {
        const copy: PrestatairesMt = Object.assign({}, prestataires);
        return copy;
    }
}
