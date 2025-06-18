import {Component, OnInit, ViewChild} from '@angular/core';
import { Router } from '@angular/router';
import { Credito } from 'src/app/models/credito.model';
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-resultado-consulta',
  templateUrl: './resultado-consulta.component.html',
  styleUrls: ['./resultado-consulta.component.scss']
})
export class ResultadoConsultaComponent implements OnInit {
  resultados: Credito[] = [];
  dataSource: MatTableDataSource<Credito> = new MatTableDataSource<Credito>();

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

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private router: Router) {
    const navigation = this.router.getCurrentNavigation();
    if (navigation?.extras.state) {
      this.resultados = navigation.extras.state['resultados'];
      this.dataSource.data = this.resultados;
    }
  }

  ngOnInit(): void {
    if (this.resultados.length === 0) {
      this.router.navigate(['/']);
    }
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  voltar() {
    this.router.navigate(['/']);
  }
}
