import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { InicioSesionComponent } from './nucleo/vistas/inicio-sesion/inicio-sesion.component';
import { HttpClientModule } from '@angular/common/http';
import { RegistroSesionComponent } from './nucleo/vistas/registro-sesion/registro-sesion.component';
import { CabeceraComponent } from './nucleo/compartido/cabecera/cabecera.component';
import { FooterComponent } from './nucleo/compartido/footer/footer.component';
import { HomeMedicoComponent } from './nucleo/vistas/medico/home-medico/home-medico.component';
import { HomeAdminComponent } from './nucleo/vistas/admin/home-admin/home-admin.component';
import { HomePacienteComponent } from './nucleo/vistas/paciente/home-paciente/home-paciente.component'; 
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PacienteAdminComponent } from './nucleo/vistas/admin/paciente-admin/paciente-admin.component';
import { MedicoAdminComponent } from './nucleo/vistas/admin/medico-admin/medico-admin.component';
import { CitasLibresComponent } from './nucleo/vistas/medico/citas-libres/citas-libres.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; 
import { MatPaginatorModule } from '@angular/material/paginator';
import { CitasOcupadasComponent } from './nucleo/vistas/medico/citas-ocupadas/citas-ocupadas.component';
import { PacienteCitasComponent } from './nucleo/vistas/paciente/paciente-citas/paciente-citas.component';
import { PacienteHistoricoComponent } from './nucleo/vistas/paciente/paciente-historico/paciente-historico.component';

@NgModule({
  declarations: [
    AppComponent,
    InicioSesionComponent,
    RegistroSesionComponent,
    CabeceraComponent,
    FooterComponent,
    HomeMedicoComponent,
    HomeAdminComponent,
    HomePacienteComponent,
    PacienteAdminComponent,
    MedicoAdminComponent,
    CitasLibresComponent,
    CitasOcupadasComponent,
    PacienteCitasComponent,
    PacienteHistoricoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    BrowserAnimationsModule,
    MatPaginatorModule
    
  ],
  exports:[
    CabeceraComponent,
    FooterComponent,
    NgbModule
  ],

  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
