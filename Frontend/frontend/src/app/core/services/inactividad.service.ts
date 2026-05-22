import { Injectable, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class inactividadService {

  private readonly TIEMPO_LIMITE = 30 * 60 * 1000;
  private timer: any;

  constructor(
    private router: Router,
    private storageService: StorageService,
    private ngZone: NgZone
  ) {}

  iniciar(): void {
    this.resetear();
    this.escucharEventos();
  }

  private escucharEventos(): void {
    const eventos = ['mousemove', 'keydown', 'click', 'scroll', 'touchstart'];
    eventos.forEach(evento => {
      window.addEventListener(evento, () => this.resetear());
    });
  }

  private resetear(): void {
    clearTimeout(this.timer);
    this.ngZone.runOutsideAngular(() => {
      this.timer = setTimeout(() => {
        this.ngZone.run(() => {
          this.cerrarSesion();
        });
      }, this.TIEMPO_LIMITE);
    });
  }

  cerrarSesion(): void {
    clearTimeout(this.timer);
    this.storageService.limpiar();
    this.router.navigate(['/login']);
  }

  detener(): void {
    clearTimeout(this.timer);
  }
}