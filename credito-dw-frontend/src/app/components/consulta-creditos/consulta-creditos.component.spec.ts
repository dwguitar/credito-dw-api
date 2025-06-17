import { TestBed } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { of, throwError } from 'rxjs';
import { ConsultaCreditosComponent } from './consulta-creditos.component';
import { CreditoService } from '../../services/credito.service';
import { Credito } from 'src/app/models/credito.model';

describe('ConsultaCreditosComponent', () => {
  let component: ConsultaCreditosComponent;
  let fixture: ComponentFixture<ConsultaCreditosComponent>;
  let mockCreditoService: jasmine.SpyObj<CreditoService>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockToastr: jasmine.SpyObj<ToastrService>;
  
  const mockCredito = {
    numeroCredito: '456789',
    numeroNfse: '3344556',
    dataConstituicao: new Date('2024-04-05'),
    valorIssqn: 120.25,
    tipoCredito: 'CONSULTORIA',
    simplesNacional: true,
    aliquota: 2.0,
    valorFaturado: 5000.00,
    valorDeducao: 800.00,
    baseCalculo: 4200.00
  };
 
 const mockCreditos: Credito[] = [{
   numeroCredito: '123456',
   numeroNfse: '7891011',
   dataConstituicao: new Date(),
   valorIssqn: 1500.75,
   tipoCredito: 'ISSQN',
   simplesNacional: true,
   aliquota: 5.0,
   valorFaturado: 30000.00,
   valorDeducao: 5000.00,
   baseCalculo: 25000.00
 },
 {
    numeroCredito: '654321',
    numeroNfse: '1122334',
    dataConstituicao: new Date('2024-03-10'),
    valorIssqn: 875.50,
    tipoCredito: 'ISSQN',
    simplesNacional: true,
    aliquota: 4.5,
    valorFaturado: 15000.00,
    valorDeducao: 2500.00,
    baseCalculo: 12500.00
  },
  {
    numeroCredito: '987654',
    numeroNfse: '5566778',
    dataConstituicao: new Date('2024-01-15'),
    valorIssqn: 3200.00,
    tipoCredito: 'CONSTRUCAO_CIVIL',
    simplesNacional: false,
    aliquota: 5.5,
    valorFaturado: 80000.00,
    valorDeducao: 15000.00,
    baseCalculo: 65000.00
  }
];

  beforeEach(async () => {
    mockCreditoService = jasmine.createSpyObj('CreditoService', ['buscarPorNfse', 'buscarPorCredito']);
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);
    mockToastr = jasmine.createSpyObj('ToastrService', ['error']);


    await TestBed.configureTestingModule({
      declarations: [ConsultaCreditosComponent],
      imports: [ReactiveFormsModule],
      providers: [
        { provide: CreditoService, useValue: mockCreditoService },
        { provide: Router, useValue: mockRouter },
        { provide: ToastrService, useValue: mockToastr }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ConsultaCreditosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form', () => {
    expect(component.consultaForm).toBeDefined();
    expect(component.consultaForm.get('numero')).toBeDefined();
  });

  it('should switch consulta type', () => {
    component.alterarTipoConsulta('credito');
    expect(component.tipoConsulta).toBe('credito');
    
    component.alterarTipoConsulta('nfse');
    expect(component.tipoConsulta).toBe('nfse');
  });

  it('should not submit invalid form', () => {
    component.consultaForm.get('numero')?.setValue('');
    component.consultar();
    expect(mockCreditoService.buscarPorNfse).not.toHaveBeenCalled();
  });

  it('should handle nfse search success', () => {
   
    mockCreditoService.buscarPorNfse.and.returnValue(of(mockCreditos));
    
    component.consultaForm.get('numero')?.setValue('123');
    component.consultar();
    
    expect(mockRouter.navigate).toHaveBeenCalled();
  });

  it('should handle nfse search error', () => {
    mockCreditoService.buscarPorNfse.and.returnValue(throwError(() => new Error('Error')));
    
    component.consultaForm.get('numero')?.setValue('123');
    component.consultar();
    
    expect(mockToastr.error).toHaveBeenCalled();
  });
});