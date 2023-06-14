export interface Cita {
   id?:number;
    fecha:Date;
    especialidad:string;
    medico_id:number;
    paciente_id?:number;
    nombre_medico?:string;
    nombre_paciente?:string;

}
