import { Component } from '@angular/core';
import { Paciente } from 'src/app/nucleo/interfaces/paciente';
import { PacienteService } from 'src/app/nucleo/servicios/paciente.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-paciente-admin',
  templateUrl: './paciente-admin.component.html',
  styleUrls: ['./paciente-admin.component.scss']
})
export class PacienteAdminComponent {
  aceptados:boolean=false;
  pacientes!: Paciente[]|null|undefined;
 pacientesAMostrar!: Paciente[]|null|undefined;
  buscador!:string;
 
  constructor(private servicioPaciente:PacienteService){}
 
  ngOnInit(){
 
   this.servicioPaciente.obtenerPacientes(this.aceptados).subscribe((resp)=>{
     let aux = resp.body;
     this.pacientes=aux;
     this.pacientesAMostrar=this.pacientes;
   })
 
  }
 
  buscar() {
   if (this.buscador) {
     this.pacientesAMostrar = this.pacientes?.filter(objeto => objeto.email.includes(this.buscador));
   } else {
     this.pacientesAMostrar = this.pacientes;
   }
 }

 aprobarPaciente(id:number|undefined){
   
  this.servicioPaciente.aprobarSolicitud(id).subscribe(resp=>{
    Swal.fire({
      icon: 'info',
      title: 'Paciente aceptado',
      text: 'Paciente aceptado con exito',
    }).then(()=>{ this.ngOnInit();
    })
  })

 }

 desestimarPaciente(id:number|undefined){
    this.servicioPaciente.desestimarSolicitud(id).subscribe(resp=>{

      Swal.fire({
        icon: 'info',
        title: 'Paciente desestimado',
        text: 'Paciente eliminado con exito',
      }).then(()=>{
        this.ngOnInit();

      })
    })
   

}
 
 }
 