import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Cita } from 'src/app/nucleo/interfaces/cita';
import { AuthService } from '../../../servicios/auth.service';
import { MedicoService } from 'src/app/nucleo/servicios/medico.service';
import Swal from 'sweetalert2';
import { CitaService } from 'src/app/nucleo/servicios/cita.service';

@Component({
  selector: 'app-home-medico',
  templateUrl: './home-medico.component.html',
  styleUrls: ['./home-medico.component.scss']
})
export class HomeMedicoComponent {
  fecha!: Date;
  cita: Cita;
  id!: number;
  existe!: boolean;
 

  constructor(
    private authService: AuthService,
    private router: Router,
    private medicoServicio: MedicoService,
    private citaServicio: CitaService
  ) {

    

    this.cita = {
      fecha: new Date(),
      especialidad: "",
      medico_id: 0,
    };

    this.medicoServicio.obtenerMedicoPorEmail(this.authService.nombre).subscribe((resp) => {
      this.cita.especialidad = resp.body.especialidad;
      this.cita.medico_id = resp.body.id;
      this.id = resp.body.id;
    });
  }

  async agregarCita() {
    this.fecha = new Date(this.fecha);

    // Obtener la fecha y hora actual
    const fechaActual = new Date();

    // Comparar la fecha seleccionada con la fecha actual
    if (this.fecha < fechaActual) {
      // La fecha seleccionada es anterior a la fecha actual
      // Mostrar un mensaje de error
      Swal.fire({
        icon: 'error',
        title: 'No se puede crear una cita en una fecha anterior a la actual',
        showConfirmButton: false,
      });
      return; // Salir del método sin realizar ninguna acción
    }

    this.cita.fecha = new Date(this.fecha.getTime());

    if (!await this.comprobarDisponibilidadLibre()) {
      this.medicoServicio.crearCita(this.cita).subscribe((resp) => {
        Swal.fire({
          icon: 'info',
          title: 'Disponibilidad Agregada',
          showConfirmButton: false,
        });
      });
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Ya tiene una cita asignada a esta hora',
        showConfirmButton: false,
      });
    }
  }

  comprobarDisponibilidadLibre(): Promise<boolean> {
    return new Promise<boolean>((resolve) => {
      this.existe = false; // Establecer existe a false

      this.citaServicio.getCitasPorMedico(this.id).subscribe((resp) => {
        resp.body.filter((cita: any) => {
          const citaFecha = new Date(cita.fecha);
          const inputFecha = new Date(this.cita.fecha);
          if (citaFecha.getTime() === inputFecha.getTime()) {
            this.existe = true;
          }
        });
        resolve(this.existe);
      });
    });
  }
}
