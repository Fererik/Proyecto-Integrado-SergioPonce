import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InicioSesionComponent } from './nucleo/vistas/inicio-sesion/inicio-sesion.component';
import { RegistroSesionComponent } from './nucleo/vistas/registro-sesion/registro-sesion.component';
import { AuthGuard } from './nucleo/guardianes/auth.guard';
import { AceptadoGuard } from './nucleo/guardianes/aceptado.guard';
import { HomeAdminComponent } from './nucleo/vistas/admin/home-admin/home-admin.component';
import { HomeMedicoComponent } from './nucleo/vistas/medico/home-medico/home-medico.component';
import { PacienteAdminComponent } from './nucleo/vistas/admin/paciente-admin/paciente-admin.component';
import { MedicoAdminComponent } from './nucleo/vistas/admin/medico-admin/medico-admin.component';
import { CitasLibresComponent } from './nucleo/vistas/medico/citas-libres/citas-libres.component';
import { CitasOcupadasComponent } from './nucleo/vistas/medico/citas-ocupadas/citas-ocupadas.component';
import { HomePacienteComponent } from './nucleo/vistas/paciente/home-paciente/home-paciente.component';
import { PacienteCitasComponent } from './nucleo/vistas/paciente/paciente-citas/paciente-citas.component';
import { PacienteHistoricoComponent } from './nucleo/vistas/paciente/paciente-historico/paciente-historico.component';

const routes: Routes = [
  { path: "", component:InicioSesionComponent},
  { path:"registro-sesion", component:RegistroSesionComponent},
  { path: "inicio-admin", component: HomeAdminComponent,  canActivate:[AuthGuard,AceptadoGuard] },
  { path: "admin-pacientes", component: PacienteAdminComponent, canActivate:[AuthGuard,AceptadoGuard] },
  {path: "admin-medicos", component: MedicoAdminComponent, canActivate:[AuthGuard, AceptadoGuard] },
  { path: "inicio-medico", component: HomeMedicoComponent,canActivate:[AuthGuard, AceptadoGuard]},
  { path: "medico-disponibilidad", component: CitasLibresComponent,canActivate:[AuthGuard, AceptadoGuard]},
  { path: "medico-citas", component: CitasOcupadasComponent,canActivate:[AuthGuard, AceptadoGuard]},
  { path: "inicio-paciente", component: HomePacienteComponent,canActivate:[AuthGuard, AceptadoGuard]},
  { path: "paciente-citas", component: PacienteCitasComponent,canActivate:[AuthGuard, AceptadoGuard]},
  { path: "paciente-historico", component: PacienteHistoricoComponent,canActivate:[AuthGuard, AceptadoGuard]},
 
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],

  exports: [RouterModule]
})
export class AppRoutingModule { }
