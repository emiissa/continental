import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ContinentalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PrestatairesMtDetailComponent } from '../../../../../../main/webapp/app/entities/prestataires/prestataires-mt-detail.component';
import { PrestatairesMtService } from '../../../../../../main/webapp/app/entities/prestataires/prestataires-mt.service';
import { PrestatairesMt } from '../../../../../../main/webapp/app/entities/prestataires/prestataires-mt.model';

describe('Component Tests', () => {

    describe('PrestatairesMt Management Detail Component', () => {
        let comp: PrestatairesMtDetailComponent;
        let fixture: ComponentFixture<PrestatairesMtDetailComponent>;
        let service: PrestatairesMtService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ContinentalTestModule],
                declarations: [PrestatairesMtDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PrestatairesMtService,
                    JhiEventManager
                ]
            }).overrideTemplate(PrestatairesMtDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrestatairesMtDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrestatairesMtService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PrestatairesMt(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.prestataires).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
