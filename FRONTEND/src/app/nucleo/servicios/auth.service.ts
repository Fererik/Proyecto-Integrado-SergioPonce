import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map, of} from 'rxjs';
import jwtDecode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  //Declaración de variables
  nombre!:string | null;
  rol !: string | null;
  token!: string | null;
  aceptado!: boolean|null;

  //Creamos el constructor 
  constructor(private http: HttpClient) {
    this.token=sessionStorage.getItem("token")
    if(this.token){
      this.obtenerDatos(this.token)
    }
  }
  
  //Metodo login 
  iniciarSesion(username: string, password:string): Observable<boolean> {
    const url = 'http://localhost:8080/login';
    const body = {
        username:username,
        password:password
    };
    return this.http.post<any>(url, body, {observe:'response'}).pipe(
        map((response: HttpResponse<any>) => {
          let tokenRespuesta =  response.headers.get('Authorization')?.split(" ")[1]
          if(tokenRespuesta){
            this.token = tokenRespuesta
            this.obtenerDatos(this.token)
            sessionStorage.setItem('token', this.token);
            return true; // Autenticación exitosa
          } else {
            
            return false; // Autenticación fallida
          }
      })
    );
  }

  obtenerDatos(token:string){
    if(token){
      let nueva:any = jwtDecode(token)
      this.nombre = nueva.sub;
      this.rol = nueva.rol;
      if (nueva.aceptado =="true") {
        this.aceptado=true;
      }else{
        this.aceptado=false;
      }

    }
  }

  estaLogueado(): Observable<boolean> {
    if(this.token && this.nombre && this.rol){
      return of(true); // Sesión iniciada
    }
    return of(false); // Sesión no iniciada
  }

  cerrarSesion():void{
    sessionStorage.removeItem("token")
    this.token=null;
    this.nombre=null;
    this.rol=null;
  }

  getToken(){
    return sessionStorage.getItem('token');

   }
}

  
