import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { Cita } from 'src/app/nucleo/interfaces/cita';
import { AuthService } from 'src/app/nucleo/servicios/auth.service';
import { CitaService } from 'src/app/nucleo/servicios/cita.service';
import { MedicoService } from 'src/app/nucleo/servicios/medico.service';
import { PacienteService } from 'src/app/nucleo/servicios/paciente.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-paciente-citas',
  templateUrl: './paciente-citas.component.html',
  styleUrls: ['./paciente-citas.component.scss']
})
export class PacienteCitasComponent {

  citas: Cita[] = [];
  citasAMostrar: Cita[] = [];
  id_paciente!: number;
  especialidad_medico!:string;

  citasPorPagina = 6; // Número de citas por página
  paginaActual = 0; // Página actual
  totalCitas: number | undefined = 0; // Total de citas
 

  nuevaFecha!:Date|null|undefined;
  fecha!:Date;
  especialidad!:string|null|undefined;
  existe!:boolean;
  constructor(
    private authService: AuthService,
    private router: Router,
    private medicoServicio: MedicoService,
    private citaServicio: CitaService,
    private pacienteServicio:PacienteService
  ) {}
  ngOnInit() {

    this.pacienteServicio.obtenerPacientePorEmail(this.authService.nombre).subscribe((resp)=>{
      this.id_paciente = resp.body.id;
    })


    this.citaServicio.getCitas().subscribe((response) => {
      let aux = response.body;
      aux = aux.filter((cita: Cita) => cita.paciente_id == this.id_paciente);
      let fechaActual = new Date(); // Obtener la fecha actual
      aux = aux.filter((cita: Cita) => new Date(cita.fecha) >= fechaActual);

      // Aplicar filtros
      if (this.especialidad) {
        aux = aux.filter((cita: Cita) => cita.especialidad.toLowerCase().includes(this.especialidad!.toLowerCase()));
      }
  
      this.citas = aux.sort((a: any, b: any) => new Date(a.fecha).getTime() - new Date(b.fecha).getTime());
      

        // Obtener el nombre del médico para cada cita
        this.obtenerNombresMedicos();
      this.citasAMostrar = aux.map((cita: any) => {
        cita.fecha = new Date(cita.fecha).toLocaleString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' })
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

  
  aplicarFiltros() {
    this.paginaActual = 0; // Reiniciar la página actual al aplicar filtros
    this.ngOnInit(); // Volver a obtener las citas con los filtros aplicados
    this.getCitasPaginadas(); // Obtener las citas a mostrar en la página actual
  }

 getCitasPaginadas(): Cita[] {

  const citasFiltradas = this.citas.filter((cita)=>{
    
    return cita.fecha.toLocaleString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' }
    ) >=   new Date().toLocaleString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' }
    );
  });
 
   this.totalCitas=citasFiltradas.length;
  const startIndex = this.paginaActual * this.citasPorPagina;

  return citasFiltradas.slice(startIndex, startIndex + this.citasPorPagina);
}


  

  onPageChange(event: PageEvent): void {
    this.paginaActual = event.pageIndex;
    this.citasAMostrar = this.getCitasPaginadas();
    
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
  

  async modificarCita(id: number | undefined | null,idMedico:number,especialidad:string) {
    if(this.nuevaFecha!=null){
     this.nuevaFecha=new Date(this.nuevaFecha)
     let aux:Cita[]=this.citas.filter((cita:any)=>{  const citaFecha = new Date(cita.fecha);
      const inputFecha = this.nuevaFecha;
      return citaFecha.getTime() === inputFecha?.getTime() 
        
      })
     if(!await this.comprobarDisponibilidadLibre(idMedico) && aux.length==0){

       let cita:Cita={
         fecha:this.nuevaFecha,
         medico_id:idMedico,
         especialidad:especialidad
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
      if(aux.length==0){
        Swal.fire({
          icon: 'error',
          title: 'Usted tiene otra cita en esa fecha',
          showConfirmButton: false,
        });
      }else{
       Swal.fire({
         icon: 'error',
         title: 'El Dr/ La Drta tiene una cita asignada a esta hora',
         showConfirmButton: false,
       }); }
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
 
   comprobarDisponibilidadLibre(id_medico:number): Promise<boolean> {
    return new Promise<boolean>((resolve) => {
      this.existe = false; // Establecer existe a false
  
      this.citaServicio.getCitasPorMedico(id_medico).subscribe((resp) => {
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
  
}
