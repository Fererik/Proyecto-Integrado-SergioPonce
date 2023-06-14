import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Cita } from 'src/app/nucleo/interfaces/cita';
import { AuthService } from 'src/app/nucleo/servicios/auth.service';
import { CitaService } from 'src/app/nucleo/servicios/cita.service';
import { MedicoService } from 'src/app/nucleo/servicios/medico.service';
import { PageEvent } from '@angular/material/paginator';
import Swal from 'sweetalert2';
import { PacienteService } from 'src/app/nucleo/servicios/paciente.service';

@Component({
  selector: 'app-citas-ocupadas',
  templateUrl: './citas-ocupadas.component.html',
  styleUrls: ['./citas-ocupadas.component.scss']
})
export class CitasOcupadasComponent { 
  citas: Cita[] = [];
  citasAMostrar: Cita[] = [];
  citasFiltradas: Cita[] = []; // Citas filtradas por nombre del paciente
  id_medico!: number;
  especialidad_medico!:string;

  citasPorPagina = 6; // Número de citas por página
  paginaActual = 0; // Página actual
  totalCitas: number | undefined = 0; // Total de citas
 

  nuevaFecha!:Date|null|undefined;
  existe!:boolean;
  nombrePacienteFiltro:string="";
  constructor(
    private authService: AuthService,
    private router: Router,
    private medicoServicio: MedicoService,
    private citaServicio: CitaService,
    private pacienteService: PacienteService
  ) {}

  ngOnInit() {
    this.medicoServicio.obtenerMedicoPorEmail(this.authService.nombre).subscribe((resp) => {
      this.id_medico = resp.body.id;
      this.especialidad_medico=resp.body.especialidad;
      this.citaServicio.getCitasPorMedico(this.id_medico).subscribe((response) => {
        let aux = response.body;
        let fechaActual = new Date(); // Obtener la fecha actual
        aux = aux.filter((cita: Cita) => new Date(cita.fecha) >= fechaActual);
        aux=aux.filter((cita:Cita) => {
         return cita.paciente_id!=null})
         this.citas = aux.sort((a:any, b:any) => new Date(a.fecha).getTime() - new Date(b.fecha).getTime());
         this.obtenerNombresPacientes();
         this.filtrarCitasPorNombrePaciente();
        this.citasAMostrar = aux.map((cita:any) => {
          cita.fecha=new Date(cita.fecha).toLocaleString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' })
        });
        this.totalCitas = this.citasAMostrar?.length;
      });
    });
  }

  obtenerNombresPacientes() {
    this.citas.forEach((cita) => {
    this.pacienteService.obtenerPacientePorId(cita.paciente_id).subscribe((resp) => {
        cita.nombre_paciente = resp.body.nombre+" "+resp.body.apellidos; // Asignar el nombre del médico a la propiedad 'nombre_medico' de la cita
      });
    });
  }

  getCitasPaginadas(): Cita[]  {
    const startIndex = this.paginaActual * this.citasPorPagina;
    return this.citasFiltradas.slice(startIndex, startIndex + this.citasPorPagina);
  }

  

  onPageChange(event: PageEvent): void {
    this.paginaActual = event.pageIndex;
    this.citasAMostrar = this.getCitasPaginadas();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
  
  async modificarCita(id: number | undefined | null) {
   if(this.nuevaFecha!=null){
    this.nuevaFecha=new Date(this.nuevaFecha)
    if(!await this.comprobarDisponibilidadLibre()){
      let cita:Cita={
        fecha:this.nuevaFecha,
        medico_id:this.id_medico,
        especialidad:this.especialidad_medico
      }
       this.citaServicio.actualizarCita(id,cita).subscribe((resp)=>{
        this.ngOnInit()
        this.getCitasPaginadas();
        Swal.fire({
          icon:"success",
          title:"La cita ha actualizada con exito"
        })
       })
      
    }else{
      Swal.fire({
        icon: 'error',
        title: 'Ya tiene una cita asignada a esta hora',
        showConfirmButton: false,
      });
    }
   }
  }

  borrarCita(id: number | undefined | null) {
   Swal.fire({
    icon:"question",
    showConfirmButton:true,
    confirmButtonColor:"#A8DBA1",
    confirmButtonText:"Si,estoy seguro",
    title:"¿Estas seguro de cancelar esta cita?",
    showCancelButton:true,
    cancelButtonColor:"#FF4136",
    cancelButtonText:"No"
   }).then((resp)=>{
    if(resp.isDismissed){
      Swal.fire({
        icon:"info",
        title:"La cita ha sido mantenida"
      })
    }
    if(resp.isConfirmed){
      this.citaServicio.eliminarCita(id).subscribe((resp)=>{
       this.ngOnInit();
       this.getCitasPaginadas();
      })
    }
    
   })
  }

 
 


  comprobarDisponibilidadLibre(): Promise<boolean> {
    return new Promise<boolean>((resolve) => {
      this.existe = false; // Establecer existe a false
  
      this.citaServicio.getCitasPorMedico(this.id_medico).subscribe((resp) => {
        resp.body.filter((cita: any) => {
          const citaFecha = new Date(cita.fecha);
          const inputFecha = this.nuevaFecha;
          if (citaFecha.getTime() === inputFecha?.getTime()) {
            this.existe = true;
          }
        });
        resolve(this.existe);
      });
    });
  }

  filtrarCitasPorNombrePaciente() {
    if (this.nombrePacienteFiltro.trim() === '') {
      this.citasFiltradas = this.citas;
    } else {
      this.citasFiltradas = this.citas.filter((cita) =>
        cita.nombre_paciente?.toLowerCase().includes(this.nombrePacienteFiltro.toLowerCase())
      );
    }
    this.citasAMostrar = this.getCitasPaginadas();
  }
  
  
}
