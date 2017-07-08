import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ContinentalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RemboursementMtDetailComponent } from '../../../../../../main/webapp/app/entities/remboursement/remboursement-mt-detail.component';
import { RemboursementMtService } from '../../../../../../main/webapp/app/entities/remboursement/remboursement-mt.service';
import { RemboursementMt } from '../../../../../../main/webapp/app/entities/remboursement/remboursement-mt.model';

describe('Component Tests', () => {

    describe('RemboursementMt Management Detail Component', () => {
        let comp: RemboursementMtDetailComponent;
        let fixture: ComponentFixture<RemboursementMtDetailComponent>;
        let service: RemboursementMtService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ContinentalTestModule],
                declarations: [RemboursementMtDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RemboursementMtService,
                    JhiEventManager
                ]
            }).overrideTemplate(RemboursementMtDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RemboursementMtDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RemboursementMtService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RemboursementMt(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.remboursement).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
