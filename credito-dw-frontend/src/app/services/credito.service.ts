import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Credito } from '../models/credito.model';


@Injectable({
  providedIn: 'root'
})
export class CreditoService {
  private apiUrl = 'http://localhost:8080/api/creditos'; // Ajustar conforme necessário

  constructor(private http: HttpClient) { }

  buscarPorNfse(numeroNfse: string): Observable<Credito[]> {
    return this.http.get<Credito[]>(`${this.apiUrl}/${numeroNfse}`);
  }

  buscarPorCredito(numeroCredito: string): Observable<Credito> {
    return this.http.get<Credito>(`${this.apiUrl}/credito/${numeroCredito}`);
  }
}