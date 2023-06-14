import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse ,HttpHeaders} from '@angular/common/http';
import { Medico } from '../interfaces/medico';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Cita } from '../interfaces/cita';

@Injectable({
  providedIn: 'root'
})
export class MedicoService {

  constructor(private authService: AuthService, private http: HttpClient) {
    
  }

  insertarMedico(medico:Medico){
   

    let url = "http://localhost:8080/api/medicos"
    let body =medico;
    return this.http.post(url,medico);
  }

  obtenerMedicos():Observable<HttpResponse<any>>{

    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url = "http://localhost:8080/api/medicos?aceptado=false"
    return this.http.get(url,{headers,observe:"response"});
  }

  obtenerMedicoPorEmail(email:string|undefined|null):Observable<HttpResponse<any>>{
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url = "http://localhost:8080/api/medicos/email/"+email;
    return this.http.get(url,{headers,observe:"response"});
  }

  obtenerMedicoPorId(id:number|undefined|null):Observable<HttpResponse<any>>{
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url = "http://localhost:8080/api/medicos/id/"+id;
    return this.http.get(url,{headers,observe:"response"});
  }

  aprobarSolicitud(id:number|undefined){
    let headers = new HttpHeaders().set("Authorization", "Bearer " + this.authService.getToken());
let url = "http://localhost:8080/api/usuarios/" + id + "?aceptado=true";

return this.http.put<any>(url, null, { headers, observe: 'response' });

  }

  desestimarSolicitud(id:number|undefined){
    let headers = new HttpHeaders().set("Authorization", "Bearer " + this.authService.getToken());
    let url = "http://localhost:8080/api/usuarios/" + id + "?aceptado=false";
    
    return this.http.put<any>(url, null, { headers, observe: 'response' });

  }

  crearCita(cita:Cita){
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url ="http://localhost:8080/api/citas/"

    return this.http.post<any>(url,cita,{headers,observe:"response"});
  }
  
}
