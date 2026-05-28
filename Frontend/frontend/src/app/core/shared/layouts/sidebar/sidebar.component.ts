import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { txt } from '../../../constantes/txt.constants';
import { StorageService } from '../../../services/storage.service';
import { inactividadService } from '../../../services/inactividad.service';


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
  styleUrls: ['./sidebar.component.scss']
})

export class SidebarComponent implements OnInit {

  constructor(
    private router: Router,
    private storageService: StorageService,
    private inactividadService: inactividadService
  ) {}

  
    

  t = txt;
  menuAbierto: boolean = false;
  nombreLocal: string = '';
  rol: string = '';
  iniciales: string = '';
  esSuperadmin: boolean = false;

  navSuperadmin: NavItem[] = [
  { icono: 'ti-layout-dashboard', label: this.t.superadmin.dashboard, ruta: '/admin/dashboard' },
  { icono: 'ti-users',            label: this.t.superadmin.clientes,  ruta: '/panelcontrol'    },
  ];

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



  ngOnInit(): void {
    this.nombreLocal = this.storageService.obtenerNombreLocal();
    this.rol = this.storageService.obtenerRol();
    this.esSuperadmin = this.storageService.esSuperadmin();
    this.iniciales = this.nombreLocal
        .split(' ')
        .map(p => p[0])
        .join('')
        .substring(0, 2)
        .toUpperCase();
    }

  navegarA(ruta: string): void {
    this.router.navigate([ruta]);
  }

  isActive(ruta: string): boolean {
    return this.router.url === ruta;
  }
  toggleMenu(): void {
  this.menuAbierto = !this.menuAbierto;
    }

  cerrarSesion(): void {
  this.menuAbierto = false;
  this.inactividadService.cerrarSesion();
    }
}