import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { txt } from '../../../constantes/txt.constants';


interface NavItem {
  icono: string;
  label: string;
  ruta: string;
  badge?: number;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

  t = txt;

  navPrincipal: NavItem[] = [
    { icono: 'ti-layout-dashboard', label: this.t.nav.items.dashboard,      ruta: '/dashboard'      },
    { icono: 'ti-calendar',         label: this.t.nav.items.agenda,          ruta: '/agenda'          },
    { icono: 'ti-clock',            label: this.t.nav.items.citasHoy,        ruta: '/citas',  badge: 8 },
  ];

  navGestion: NavItem[] = [
    { icono: 'ti-users',    label: this.t.nav.items.clientes,   ruta: '/clientes'   },
    { icono: 'ti-user',     label: this.t.nav.items.empleados,  ruta: '/empleados'  },
    { icono: 'ti-scissors', label: this.t.nav.items.servicios,  ruta: '/servicios'  },
  ];

  navOperaciones: NavItem[] = [
    { icono: 'ti-box',       label: this.t.nav.items.inventario, ruta: '/inventario' },
    { icono: 'ti-chart-bar', label: this.t.nav.items.reportes,   ruta: '/reportes'   },
  ];

  navSistema: NavItem[] = [
    { icono: 'ti-bell',     label: this.t.nav.items.notificaciones, ruta: '/notificaciones', badge: 3 },
    { icono: 'ti-settings', label: this.t.nav.items.configuracion,  ruta: '/configuracion'           },
  ];

  constructor(private router: Router) {}

  navegarA(ruta: string): void {
    this.router.navigate([ruta]);
  }

  isActive(ruta: string): boolean {
    return this.router.url === ruta;
  }
}