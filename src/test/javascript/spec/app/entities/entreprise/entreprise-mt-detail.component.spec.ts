import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ContinentalTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EntrepriseMtDetailComponent } from '../../../../../../main/webapp/app/entities/entreprise/entreprise-mt-detail.component';
import { EntrepriseMtService } from '../../../../../../main/webapp/app/entities/entreprise/entreprise-mt.service';
import { EntrepriseMt } from '../../../../../../main/webapp/app/entities/entreprise/entreprise-mt.model';

describe('Component Tests', () => {

    describe('EntrepriseMt Management Detail Component', () => {
        let comp: EntrepriseMtDetailComponent;
        let fixture: ComponentFixture<EntrepriseMtDetailComponent>;
        let service: EntrepriseMtService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ContinentalTestModule],
                declarations: [EntrepriseMtDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EntrepriseMtService,
                    JhiEventManager
                ]
            }).overrideTemplate(EntrepriseMtDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntrepriseMtDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntrepriseMtService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EntrepriseMt(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.entreprise).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
