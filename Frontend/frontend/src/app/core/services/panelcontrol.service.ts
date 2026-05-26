import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';


export interface Cliente {
  id: string;
  nombreLocal: string;
  nombreContacto: string;
  email: string;
  dbUrl: string;
  activo: boolean;
  fechaInicio: string;
  fechaVencimiento: string;
}

@Injectable({
  providedIn: 'root'
})
export class panelcontrolService {

  private apiUrl = `${environment.apiUrl}/admin`;

  constructor(private http: HttpClient) {}

  listarClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/clientes`);
  }
  registrarCliente(cliente: any): Observable<void> {
  return this.http.post<void>(`${this.apiUrl}/clientes`, cliente);
}
}