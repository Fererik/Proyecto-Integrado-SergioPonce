import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cita } from '../interfaces/cita';

@Injectable({
  providedIn: 'root'
})
export class CitaService {

  constructor(private authService: AuthService, private http: HttpClient) { }


  getCitasPorMedico(id:number):Observable<HttpResponse<any>>{
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    
      let url="http://localhost:8080/api/citas/medico/"+id;

      return this.http.get<any>(url,{headers,observe:"response"})
  }
  getCitas():Observable<HttpResponse<any>>{
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url="http://localhost:8080/api/citas";

    return this.http.get<any>(url,{headers,observe:"response"})
}

  eliminarCita(id:number| undefined | null):Observable<HttpResponse<any>>{
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url="http://localhost:8080/api/citas/"+id;

    return this.http.delete<any>(url,{headers,observe:"response"})
}

  actualizarCita(id:number | undefined | null ,cita:Cita){
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url="http://localhost:8080/api/citas/"+id;

    return this.http.put<any>(url,cita,{headers,observe:"response"})
  }

  asociarPaciente(idCita:number | undefined | null,idPaciente:number | undefined | null){
    let headers = new HttpHeaders().set("Authorization", "Bearer "+this.authService.getToken());
    let url="http://localhost:8080/api/citas/"+idCita+"/"+idPaciente;

    return this.http.put<any>(url,null,{headers,observe:"response"})
  }
}
