import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ContinentalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClientMtDetailComponent } from '../../../../../../main/webapp/app/entities/client/client-mt-detail.component';
import { ClientMtService } from '../../../../../../main/webapp/app/entities/client/client-mt.service';
import { ClientMt } from '../../../../../../main/webapp/app/entities/client/client-mt.model';

describe('Component Tests', () => {

    describe('ClientMt Management Detail Component', () => {
        let comp: ClientMtDetailComponent;
        let fixture: ComponentFixture<ClientMtDetailComponent>;
        let service: ClientMtService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ContinentalTestModule],
                declarations: [ClientMtDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClientMtService,
                    JhiEventManager
                ]
            }).overrideTemplate(ClientMtDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClientMtDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientMtService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClientMt(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.client).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
