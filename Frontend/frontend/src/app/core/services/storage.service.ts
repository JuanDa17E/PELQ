import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

interface JwtPayload {
  sub: string;
  rol: string;
  clienteId?: string;
  dbUrl?: string;
  nombreLocal?: string;
  exp: number;
}

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private readonly TOKEN_KEY = 'pelq_token';

  guardarToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  obtenerToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  obtenerPayload(): JwtPayload | null {
    const token = this.obtenerToken();
    if (!token) return null;
    try {
      return jwtDecode<JwtPayload>(token);
    } catch {
      return null;
    }
  }

  obtenerRol(): string {
    return this.obtenerPayload()?.rol || '';
  }

  obtenerEmail(): string {
    return this.obtenerPayload()?.sub || '';
  }

  obtenerNombreLocal(): string {
    return this.obtenerPayload()?.nombreLocal || '';
  }

  esSuperadmin(): boolean {
    return this.obtenerRol() === 'superadmin';
  }

  tokenVencido(): boolean {
    const payload = this.obtenerPayload();
    if (!payload) return true;
    return Date.now() >= payload.exp * 1000;
  }

  limpiar(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
}