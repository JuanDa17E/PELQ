import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { StorageService } from './storage.service';
import { Router } from '@angular/router';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  rol: string;
  nombreLocal: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'https://pelq.onrender.com/api/auth';

  constructor(
    private http: HttpClient,
    @Inject(StorageService) private storageService: StorageService,
    private router: Router
  ) {}

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, request).pipe(
      tap(response => {
        this.storageService.guardarToken(response.token);
      })
    );
  }

  logout(): void {
    this.storageService.limpiar();
    this.router.navigate(['/login']);
  }

  estaAutenticado(): boolean {
    const token = this.storageService.obtenerToken();
    if (!token) return false;
    return !this.storageService.tokenVencido();
  }

  esSuperadmin(): boolean {
    return this.storageService.esSuperadmin();
  }

  obtenerRol(): string {
    return this.storageService.obtenerRol();
  }
}