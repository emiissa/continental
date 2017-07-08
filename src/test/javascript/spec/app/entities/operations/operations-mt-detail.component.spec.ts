import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ContinentalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OperationsMtDetailComponent } from '../../../../../../main/webapp/app/entities/operations/operations-mt-detail.component';
import { OperationsMtService } from '../../../../../../main/webapp/app/entities/operations/operations-mt.service';
import { OperationsMt } from '../../../../../../main/webapp/app/entities/operations/operations-mt.model';

describe('Component Tests', () => {

    describe('OperationsMt Management Detail Component', () => {
        let comp: OperationsMtDetailComponent;
        let fixture: ComponentFixture<OperationsMtDetailComponent>;
        let service: OperationsMtService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ContinentalTestModule],
                declarations: [OperationsMtDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OperationsMtService,
                    JhiEventManager
                ]
            }).overrideTemplate(OperationsMtDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OperationsMtDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperationsMtService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OperationsMt(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.operations).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
