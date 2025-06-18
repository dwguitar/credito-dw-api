import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConsultaCreditosComponent } from './components/consulta-creditos/consulta-creditos.component';
import { ResultadoConsultaComponent } from './components/resultado-consulta/resultado-consulta.component';

const routes: Routes = [
  { path: '', component: ConsultaCreditosComponent },
  { path: 'resultado', component: ResultadoConsultaComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
