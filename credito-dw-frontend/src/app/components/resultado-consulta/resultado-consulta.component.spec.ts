import { TestBed } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { Router } from '@angular/router';
import { ResultadoConsultaComponent } from './resultado-consulta.component';


describe('ResultadoConsultaComponent', () => {
  let component: ResultadoConsultaComponent;
  let fixture: ComponentFixture<ResultadoConsultaComponent>;
  let mockRouter: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    mockRouter = jasmine.createSpyObj('Router', ['navigate', 'getCurrentNavigation']);

    await TestBed.configureTestingModule({
      declarations: [ResultadoConsultaComponent],
      providers: [
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ResultadoConsultaComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate back if no results', () => {
    mockRouter.getCurrentNavigation.and.returnValue({ extras: { state: null } } as any);
    fixture.detectChanges();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/']);
  });

  it('should display results', () => {
    const mockData = [{
      numeroCredito: '123',
      numeroNfse: '456',
      dataConstituicao: new Date(),
      valorIssqn: 100,
      tipoCredito: 'ISSQN',
      simplesNacional: true,
      aliquota: 5,
      valorFaturado: 2000,
      valorDeducao: 500,
      baseCalculo: 1500
    }];

    mockRouter.getCurrentNavigation.and.returnValue({ 
      extras: { state: { resultados: mockData } } 
    } as any);
    
    fixture.detectChanges();
    expect(component.resultados).toEqual(mockData);
  });

  it('should navigate back on voltar', () => {
    component.voltar();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/']);
  });
});