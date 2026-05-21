import { Component, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { txt } from '../../../constantes/txt.constants';



@Component({
  selector: 'app-topbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.scss'
})
export class TopbarComponent implements OnInit {

  @Output() accion = new EventEmitter<void>();

  t = txt;
  titulo: string = '';
  labelBoton: string = '';
  fechaHoy: string = '';

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.fechaHoy = new Date().toLocaleDateString('es-CO', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });

    this.actualizarTitulo(this.router.url);

    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      this.actualizarTitulo(event.urlAfterRedirects);
    });
  }

  actualizarTitulo(url: string): void {
    const ruta = '/' + url.split('/')[1];
    const pagina = this.t.paginas[ruta];
    this.titulo = pagina?.titulo || '';
    this.labelBoton = pagina?.boton || '';
  }

  accionPrimaria(): void {
    this.accion.emit();
  }
}