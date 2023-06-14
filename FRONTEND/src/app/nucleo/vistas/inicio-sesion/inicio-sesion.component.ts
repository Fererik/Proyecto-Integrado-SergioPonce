import { Component, HostListener} from '@angular/core';
import { AuthService } from '../../servicios/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-inicio-sesion',
  templateUrl: './inicio-sesion.component.html',
  styleUrls: ['./inicio-sesion.component.scss']
})
export class InicioSesionComponent {
  email:string="";
  contrasenna:string="";

  constructor(private authService: AuthService, private router:Router){
  }

  @HostListener('document:keydown.enter', ['$event'])
  onEnterKey(event: KeyboardEvent) {
    this.onSubmit();
  }

  onSubmit():void{

    this.authService.iniciarSesion(this.email, this.contrasenna).subscribe(response => {
      if (this.authService.rol=="administrador") {
        this.router.navigate(["/inicio-admin"])
      }else if(this.authService.rol=="medico"){
      this.router.navigate(["/inicio-medico"])
    }else{
      this.router.navigate(["/inicio-paciente"])
    }
    
    
    },
    (error)=>{
      if(error.status == 500){
        Swal.fire({
          icon: 'error',
          title: 'No existe el usuario o ha sido eliminado por un administrador.\n\n Porfavor registrese',
          showConfirmButton: false,
          
        }).then(()=>{
          this.router.navigate(["registro-sesion"]);
        })
      }else if(error.status == "401"){
        Swal.fire({
          icon: 'info',
          title: 'La contraseña no es correcta.',
          showConfirmButton: false,
          
        })
      }
    })
  }
}
