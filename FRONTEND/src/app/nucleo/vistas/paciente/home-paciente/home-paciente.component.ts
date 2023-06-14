import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { Cita } from 'src/app/nucleo/interfaces/cita';
import { AuthService } from 'src/app/nucleo/servicios/auth.service';
import { CitaService } from 'src/app/nucleo/servicios/cita.service';
import { MedicoService } from 'src/app/nucleo/servicios/medico.service';
import { PacienteService } from 'src/app/nucleo/servicios/paciente.service';
import Swal from 'sweetalert2';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { TDocumentDefinitions } from 'pdfmake/interfaces';
import { Observable } from 'rxjs';
import { map } from 'rxjs';


(pdfMake as any).vfs = pdfFonts.pdfMake.vfs;


@Component({
  selector: 'app-home-paciente',
  templateUrl: './home-paciente.component.html',
  styleUrls: ['./home-paciente.component.scss']
})
export class HomePacienteComponent {
  citas: Cita[] = [];
  citasAMostrar: Cita[] = [];
  id_paciente!: number;
  especialidad_medico!: string;

  citasPorPagina = 6; // Número de citas por página
  paginaActual = 0; // Página actual
  totalCitas: number | undefined = 0; // Total de citas


  fecha!: Date;
  especialidad!: string | null | undefined;
  existe!: boolean;
 
  constructor(
    private authService: AuthService,
    private router: Router,
    private medicoServicio: MedicoService,
    private citaServicio: CitaService,
    private pacienteServicio: PacienteService
  ) {}
  ngOnInit() {

    this.pacienteServicio.obtenerPacientePorEmail(this.authService.nombre).subscribe((resp) => {
      this.id_paciente = resp.body.id;
    })


    this.citaServicio.getCitas().subscribe((response) => {
      let aux = response.body;
      aux = aux.filter((cita: Cita) => cita.paciente_id == null);
      // Aplicar filtros
      if (this.especialidad) {
        aux = aux.filter((cita: Cita) => cita.especialidad.toLowerCase().includes(this.especialidad!.toLowerCase()));
      }

      this.citas = aux.sort((a: any, b: any) => new Date(a.fecha).getTime() - new Date(b.fecha).getTime());

      // Obtener el nombre del médico para cada cita
      this.obtenerNombresMedicos();
    
      
      this.citasAMostrar = aux.map((cita: any) => {
        cita.fecha = new Date(cita.fecha).toLocaleString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' });
        return cita;
      });
      
      this.totalCitas = this.citasAMostrar?.length;
    });
  }
  
  obtenerNombresMedicos() {
    this.citas.forEach((cita) => {
      this.medicoServicio.obtenerMedicoPorId(cita.medico_id).subscribe((resp) => {
        cita.nombre_medico = resp.body.nombre+" "+resp.body.apellidos; // Asignar el nombre del médico a la propiedad 'nombre_medico' de la cita
      });
    });
  }

  
  obtenerNombresPacientes() {
    this.citas.forEach((cita) => {
    this.pacienteServicio.obtenerPacientePorId(cita.paciente_id).subscribe((resp) => {
        cita.nombre_paciente = resp.body.nombre+" "+resp.body.apellidos; // Asignar el nombre del paciente a la propiedad 'nombre_paciente' de la cita
      });
    });
  }

  

  aplicarFiltros() {
    this.paginaActual = 0; // Reiniciar la página actual al aplicar filtros
    this.ngOnInit(); // Volver a obtener las citas con los filtros aplicados
    this.getCitasPaginadas(); // Obtener las citas a mostrar en la página actual
  }

  getCitasPaginadas(): Cita[] {
    const citasFiltradas = this.citas.slice().filter((cita: Cita) => {
      if (this.especialidad && !cita.especialidad.toLowerCase().includes(this.especialidad.toLowerCase())) {
        return false;
      }
      return true;
    });

    const startIndex = this.paginaActual * this.citasPorPagina;
    return citasFiltradas.slice(startIndex, startIndex + this.citasPorPagina);
  }

  onPageChange(event: PageEvent): void {
    this.paginaActual = event.pageIndex;
    this.citasAMostrar = this.getCitasPaginadas();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  async solicitarCita(id: number | undefined | null) {
    this.citaServicio.asociarPaciente(id, this.id_paciente).subscribe((resp) => {
      this.ngOnInit();
      this.getCitasPaginadas();
      Swal.fire({
        icon: "success",
        title: "La cita ha sido reservada"
      });
      const cita = this.citas.find((c) => c.id === id);
    if (cita) {

      this.obtenerNombrePaciente(this.id_paciente).subscribe((respuesta) => {
      cita.nombre_paciente=respuesta.body.nombre + " "+respuesta.body.apellidos;

      this.generarPDF(cita);
    });
  }
}); 
}

obtenerNombrePaciente(id: number|null|undefined): Observable<any> {
  return this.pacienteServicio.obtenerPacientePorId(id)
}
  generarPDF(cita: Cita) {
    let documentDefinition:TDocumentDefinitions = {
      content: [
        { text: 'Detalles de la cita', style: 'header' },
        { text: `Fecha: ${cita.fecha}`, style: 'subheader' },
        { text: `Especialidad: ${cita.especialidad}`, style: 'subheader' },
        { text: `Médico: ${cita.nombre_medico}`, style: 'subheader' },
        {text:`Paciente: ${cita.nombre_paciente}`, style: 'subheader'}
      ],
      styles: {
        header: {
          fontSize: 18,
          bold: true,
          margin: [0, 0, 0, 10]
        },
        subheader: {
          fontSize: 14,
          bold: true,
          margin: [0, 10, 0, 5]
        }
      }
    };
  
    pdfMake.createPdf(documentDefinition).download();
  }

}
