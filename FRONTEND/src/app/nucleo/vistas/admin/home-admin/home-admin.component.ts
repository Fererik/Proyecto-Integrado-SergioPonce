import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../../interfaces/usuario';
import { SolicitudesService } from '../../../servicios/solicitudes.service';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.scss']
})
export class HomeAdminComponent implements OnInit  {
 solicitudes!: Usuario[]|null|undefined;
 solicitudesAMostrar!: Usuario[]|null|undefined;
 buscador!:string;

 constructor(private servicioSolicitudes:SolicitudesService){}

 ngOnInit(){

  this.servicioSolicitudes.obtenerSolicitudesAceptadas().subscribe((resp)=>{
    let aux = resp.body;
    aux?.shift();
    this.solicitudes=aux;
    this.solicitudesAMostrar=this.solicitudes;
  })

 }

 buscar() {
  if (this.buscador) {
    this.solicitudesAMostrar = this.solicitudes?.filter(objeto => objeto.email.includes(this.buscador));
  } else {
    this.solicitudesAMostrar = this.solicitudes;
  }
}

desestimarUsuario(id:number|undefined){

  Swal.fire({
    title: '¿Quiere eliminar este usuario?',
    text: 'Todos sus datos seran eliminados en cascada',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Si, borralo'
  }).then((result) => {
    if (result.isConfirmed) {
      this.servicioSolicitudes.desestimarSolicitud(id).subscribe(resp=>{

        Swal.fire({
          icon: 'info',
          title: 'Usuario elimnado',
          text: 'Usuario eliminado con exito',
        }).then(()=>{ this.ngOnInit();})
      })
    }
  })
 
 

}


}
