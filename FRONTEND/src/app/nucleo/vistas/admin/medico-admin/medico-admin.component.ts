import { Component } from '@angular/core';
import { Medico } from 'src/app/nucleo/interfaces/medico';
import { MedicoService } from 'src/app/nucleo/servicios/medico.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-medico-admin',
  templateUrl: './medico-admin.component.html',
  styleUrls: ['./medico-admin.component.scss']
})
export class MedicoAdminComponent {
  medicos!: Medico[]|null|undefined;
 medicosAMostrar!: Medico[]|null|undefined;
  buscador!:string;
 
  constructor(private servicioMedico:MedicoService){}
 
  ngOnInit(){
 
   this.servicioMedico.obtenerMedicos().subscribe((resp)=>{
     let aux = resp.body;
     this.medicos=aux;
     this.medicosAMostrar=this.medicos;
   })
 
  }
 
  buscar() {
   if (this.buscador) {
     this.medicosAMostrar = this.medicos?.filter(objeto => objeto.email.includes(this.buscador));
   } else {
     this.medicosAMostrar = this.medicos;
   }
 }

 aprobarPaciente(id:number|undefined){
   
  this.servicioMedico.aprobarSolicitud(id).subscribe(resp=>{
    Swal.fire({
      icon: 'info',
      title: 'Medico aceptado',
      text: 'Medico aceptado con exito',
    }).then(()=>{this.ngOnInit();})
  })

 }

 desestimarPaciente(id:number|undefined){
    this.servicioMedico.desestimarSolicitud(id).subscribe(resp=>{

      Swal.fire({
        icon: 'info',
        title: 'Medico desestimado',
        text: 'Medico eliminado con exito',
      }).then(()=>{ this.ngOnInit();})
    })
   

}
 
 }
 