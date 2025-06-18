import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Credito } from 'src/app/models/credito.model';

@Component({
  selector: 'app-resultado-consulta',
  templateUrl: './resultado-consulta.component.html',
  styleUrls: ['./resultado-consulta.component.scss']
})
export class ResultadoConsultaComponent implements OnInit {
  resultados: Credito[] = [];

  displayedColumns: string[] = [
    'numeroCredito',
    'numeroNfse',
    'dataConstituicao',
    'valorIssqn',
    'tipoCredito',
    'simplesNacional',
    'aliquota',
    'valorFaturado',
    'valorDeducao',
    'baseCalculo'
  ];

  constructor(private router: Router) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras.state) {
      this.resultados = navigation.extras.state['resultados'];
    }
  }

  ngOnInit(): void {
    if (this.resultados.length === 0) {
      this.router.navigate(['/']);
    }
  }

  voltar() {
    this.router.navigate(['/']);
  }
}