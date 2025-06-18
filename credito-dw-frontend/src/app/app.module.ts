import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';


import { MaterialModule } from './shared/material.module';
import { ToastrModule } from 'ngx-toastr';
import { ConsultaCreditosComponent } from './components/consulta-creditos/consulta-creditos.component';
import { ResultadoConsultaComponent } from './components/resultado-consulta/resultado-consulta.component';

@NgModule({
  declarations: [
    AppComponent,
    ConsultaCreditosComponent,
    ResultadoConsultaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MaterialModule,
    ToastrModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
