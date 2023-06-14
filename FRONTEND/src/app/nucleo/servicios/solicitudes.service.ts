import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient, HttpResponse,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../interfaces/usuario';

@Injectable({
  providedIn: 'root'
})
export class SolicitudesService {

  constructor(private authService: AuthService, private http: HttpClient) {
    
  }

  obtenerSolicitudesAceptadas(): Observable<HttpResponse<Usuario[]>>{
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url = "http://localhost:8080/api/usuarios?aceptado=true";
    console.log(this.http.get<any>(url, {headers,observe:'response'}))
    return this.http.get<any>(url, {headers,observe:'response'});
  }

  
  desestimarSolicitud(id:number|undefined){
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url = "http://localhost:8080/api/usuarios/"+id+"?aceptado=false";

    return this.http.put<any>(url, {headers,observe:'response'});

  }

}
