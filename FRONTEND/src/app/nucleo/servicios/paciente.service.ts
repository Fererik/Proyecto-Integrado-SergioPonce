import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse,HttpHeaders } from '@angular/common/http';
import { Paciente } from '../interfaces/paciente';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PacienteService {
 
  constructor(private authService: AuthService, private http: HttpClient) {
    
  }

  insertarPaciente(paciente:Paciente){

    let url = "http://localhost:8080/api/pacientes"
    let body =paciente;
    return this.http.post(url,paciente);
  }

  
  obtenerPacientes(aceptado:boolean): Observable<HttpResponse<Paciente[]>>{
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url = "http://localhost:8080/api/pacientes?aceptado="+aceptado;
    return this.http.get<any>(url, {headers,observe:'response'});
  }
  obtenerPacientePorEmail(email:string|undefined|null):Observable<HttpResponse<any>>{
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url = "http://localhost:8080/api/pacientes/email/"+email;
    return this.http.get(url,{headers,observe:"response"});
  }
  obtenerPacientePorId(id:number|undefined|null):Observable<HttpResponse<any>>{
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url = "http://localhost:8080/api/pacientes/id/"+id;
    return this.http.get(url,{headers,observe:"response"});
  }

  aprobarSolicitud(id:number|undefined){
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url = "http://localhost:8080/api/usuarios/"+id+"?aceptado=true";

    return this.http.put<any>(url,null, {headers,observe:'response'});

  }

  desestimarSolicitud(id:number|undefined){
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url = "http://localhost:8080/api/usuarios/"+id+"?aceptado=false";

    return this.http.put<any>(url,null, {headers,observe:'response'});

  }
}
