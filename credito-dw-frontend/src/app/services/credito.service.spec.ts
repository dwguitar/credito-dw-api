import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CreditoService } from './credito.service';
import { Credito } from '../models/credito.model';

describe('CreditoService', () => {
  let service: CreditoService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CreditoService]
    });
    service = TestBed.inject(CreditoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch creditos by NFS-e', () => {
    const mockResponse: Credito[] = [{
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

    service.buscarPorNfse('456').subscribe(creditos => {
      expect(creditos.length).toBe(1);
      expect(creditos[0].numeroNfse).toEqual('456');
    });

    const req = httpMock.expectOne('http://localhost:8080/api/creditos/456');
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });

  it('should handle empty response', () => {
    service.buscarPorNfse('000').subscribe(creditos => {
      expect(creditos.length).toBe(0);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/creditos/000');
    req.flush([]);
  });
});