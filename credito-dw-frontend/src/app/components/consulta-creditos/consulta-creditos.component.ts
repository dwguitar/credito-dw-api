import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CreditoService } from 'src/app/services/credito.service';

@Component({
  selector: 'app-consulta-creditos',
  templateUrl: './consulta-creditos.component.html',
  styleUrls: ['./consulta-creditos.component.scss']
})
export class ConsultaCreditosComponent {
  consultaForm: FormGroup;
  tipoConsulta: 'nfse' | 'credito' = 'nfse';

  constructor(
    private fb: FormBuilder,
    private creditoService: CreditoService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.consultaForm = this.fb.group({
      numero: ['', Validators.required]
    });
  }

  alterarTipoConsulta(tipo: 'nfse' | 'credito') {
    this.tipoConsulta = tipo;
    this.consultaForm.reset();
  }

  consultar() {
    if (this.consultaForm.valid) {
      const numero = this.consultaForm.get('numero')?.value;

      if (this.tipoConsulta === 'nfse') {
        this.creditoService.buscarPorNfse('7891011').subscribe({
          next: (resultados) => {
            this.router.navigate(['/resultado'], { state: { resultados } });
          },
          error: (err) => {
            this.toastr.error('Nenhum crédito encontrado para esta NFS-e', 'Erro na consulta');
          }
        });
      } else {
        this.creditoService.buscarPorCredito(numero).subscribe({
          next: (resultado) => {
            this.router.navigate(['/resultado'], { state: { resultados: [resultado] } });
          },
          error: (err) => {
            this.toastr.error('Crédito não encontrado', 'Erro na consulta');
          }
        });
      }
    }
  }
}
