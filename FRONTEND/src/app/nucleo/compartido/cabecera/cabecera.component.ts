import { Component } from '@angular/core';
import { AuthService } from '../../servicios/auth.service';
import { Router } from '@angular/router';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-cabecera',
  templateUrl: './cabecera.component.html',
  styleUrls: ['./cabecera.component.scss']
})
export class CabeceraComponent {

  correoUser = this.authService.nombre;
  rol = this.authService.rol;

  constructor(private authService: AuthService, private router: Router){
  }

  cerrarSesion():void{
    this.authService.cerrarSesion();
    this.router.navigate(["/"])
  }
 
}
