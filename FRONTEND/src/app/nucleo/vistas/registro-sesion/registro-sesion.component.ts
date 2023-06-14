import { Component } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Paciente } from '../../interfaces/paciente';
import { Medico } from '../../interfaces/medico';
import { MedicoService } from '../../servicios/medico.service';
import { PacienteService } from '../../servicios/paciente.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-registro-sesion',
  templateUrl: './registro-sesion.component.html',
  styleUrls: ['./registro-sesion.component.scss']
})
export class RegistroSesionComponent {
  esMedico:boolean=true;
  paciente:Paciente
  medico:Medico



  constructor(private router: Router, private medicoServicio:MedicoService, private pacienteService:PacienteService){
    this.medico={
      "nombre":"",
      "apellidos":"",
      "email":"",
      "especialidad":"",
      "password":""
    };
    this.paciente={
      "nombre":"",
      "apellidos":"",
      "email":"",
      "numPoliza":"",
      "password":""
    };
  }

  

  registrarse(){
    if(this.esMedico){
      this.medicoServicio.insertarMedico(this.medico).subscribe((resp)=>{
        Swal.fire({
          icon: 'success',
          title: 'Primer Paso Completado',
          text: 'Se ha registrado con exito, porfavor sea paciente hasta que sea aceptado',
          
        })
      },
      (error)=>{ Swal.fire({
        icon: 'error',
        title: 'Correo anteriormente registrado',
        
        
      })})
    }else{
      this.pacienteService.insertarPaciente(this.paciente).subscribe((resp)=>{
        Swal.fire({
          icon: 'success',
          title: 'Primer Paso Completado',
          text: 'Se ha registrado con exito, porfavor sea paciente hasta que sea aceptado',
          
        })
    },
    (error)=>{Swal.fire({
      icon: 'error',
      title: 'Correo anteriormente registrado',
    })}
    )
    
    }
    
  }

  usuarioCambiado(){
    this.medico={
      "nombre":"",
      "apellidos":"",
      "email":"",
      "especialidad":"",
      "password":""
    };
    this.paciente={
      "nombre":"",
      "apellidos":"",
      "email":"",
      "numPoliza":"",
      "password":""
    };

  }
}
