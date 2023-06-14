import { Injectable } from '@angular/core';

import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../servicios/auth.service';

@Injectable()
export class InterceptorPrincipalInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) { }

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    // Obtenemos el token de nuestro servicio

    const token = this.authService.getToken();
    console.log("entra")
    if (token) {
      // Si el token existe, clonamos la solicitud HTTP
      
      const cloned = request.clone({
        // Agregamos el encabezado del token
        headers: request.headers.set('Authorization', 'Bearer ' + token),
      
      });
      // Devolvemos la solicitud clonada
      console.log(cloned);
      return next.handle(cloned);
     
    }

    // Si no hay token enviamos la solicitud sin el token
    return next.handle(request);
  }
}