import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable, map } from 'rxjs';
import { AuthService } from '../servicios/auth.service';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AceptadoGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    return this.authService.estaLogueado().pipe(
      map(estaLogueado => {
        console.log(this.authService.aceptado)
        if (this.authService.aceptado) {
          return true;
        } else {
            Swal.fire({
              icon: 'error',
              title: 'No tienes acceso',
              text: 'Su usuario todavia no ha sido aceptado, porfavor sea paciente',
            })

          this.router.navigate(['']);
          return false;
        }
      })
    );
  }
  
}
