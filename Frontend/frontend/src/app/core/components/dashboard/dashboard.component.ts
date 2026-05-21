import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { txt } from '../../constantes/txt.constants';


interface Cita {
  hora: string;
  cliente: string;
  servicio: string;
  estado: 'confirmada' | 'pendiente';
}

interface Empleado {
  iniciales: string;
  nombre: string;
  citas: number;
  porcentaje: number;
  color: string;
  colorFondo: string;
}

interface Stat {
  label: string;
  valor: string | number;
  delta: string;
  positivo: boolean;
  gold: boolean;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  t = txt;
  fechaHoy: string = '';

  stats: Stat[] = [
    { label: this.t.dashboard.stats.citasHoy,        valor: 8,      delta: '↑ 2 más que ayer',     positivo: true,  gold: true  },
    { label: this.t.dashboard.stats.ingresosHoy,     valor: '$340k', delta: '↑ 12% esta semana',   positivo: true,  gold: false },
    { label: this.t.dashboard.stats.clientesActivos, valor: 124,    delta: '↑ 5 este mes',          positivo: true,  gold: false },
    { label: this.t.dashboard.stats.noShows,         valor: 2,      delta: '↑ 1 vs semana pasada',  positivo: false, gold: false },
  ];

  proximasCitas: Cita[] = [
    { hora: '10:00', cliente: 'Carlos Méndez', servicio: 'Corte + barba · Pedro', estado: 'confirmada' },
    { hora: '10:30', cliente: 'Andrés López',  servicio: 'Tinte · María',          estado: 'pendiente'  },
    { hora: '11:00', cliente: 'Luis Torres',   servicio: 'Corte · Pedro',          estado: 'confirmada' },
    { hora: '11:30', cliente: 'Mario García',  servicio: 'Corte clásico · Juan',   estado: 'pendiente'  },
  ];

  empleados: Empleado[] = [
    { iniciales: 'PE', nombre: 'Pedro Estrada', citas: 6, porcentaje: 90, color: '#C9A84C', colorFondo: '#1e1a0e' },
    { iniciales: 'MR', nombre: 'María Ríos',    citas: 4, porcentaje: 60, color: '#7a9e5a', colorFondo: '#0f1e14' },
    { iniciales: 'JC', nombre: 'Juan Castro',   citas: 3, porcentaje: 45, color: '#5a7aae', colorFondo: '#101520' },
    { iniciales: 'LM', nombre: 'Laura Mora',    citas: 2, porcentaje: 30, color: '#ae5a5a', colorFondo: '#1a1010' },
  ];

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.fechaHoy = new Date().toLocaleDateString('es-CO', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  }

  navegarA(ruta: string): void {
    this.router.navigate([ruta]);
  }

  nuevaCita(): void {
    this.router.navigate(['/agenda/nueva']);
  }
}