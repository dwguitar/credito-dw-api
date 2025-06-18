import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Credito } from '../models/credito.model';


@Injectable({
  providedIn: 'root'
})
export class CreditoService {
  private apiUrl = 'http://localhost:8080/api/v1/creditos';

  constructor(private http: HttpClient) { }

  buscarPorNfse(numeroNfse: string): Observable<Credito[]> {
    console.log(numeroNfse);
    return this.http.get<Credito[]>(`${this.apiUrl}/${numeroNfse}`);

  }

  buscarPorCredito(numeroCredito: string): Observable<Credito> {
    console.log(numeroCredito);
    return this.http.get<Credito>(`${this.apiUrl}/credito/${numeroCredito}`);
  }

  private handleError(error: HttpErrorResponse) {
    console.error('Ocorreu um erro:', error);
    return throwError(() => new Error('Erro ao buscar crédito'));
  }

}
